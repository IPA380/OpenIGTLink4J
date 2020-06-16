/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import java.io.IOException;

import msg.INoReplyMessage;
import msg.OIGTLMessageType;
import msg.OIGTL_GetMessage;
import msg.OIGTL_HeaderOnlyMessage;
import msg.OIGTL_RTSMessage;
import msg.OIGTL_STPMessage;
import msg.OIGTL_STTMessage;
import msg.OpenIGTMessage;
import msg.RawOpenIGTMessage;
import msg.capability.CapabilityMessage;
import msg.capability.GetCapabilityMessage;
import protocol.IOpenIGTLinkMessageListener;
import util.Header;
import util.RTSMessageStatus;

/**
 * Class representing a {@link Thread} that handles the protocol
 * for incoming messages 
 * 
 * @author Andreas Rothfuss
 *
 */
public class MessageRunner extends NetManagerRunner {

	/**
	 * Destination constructor to create a new {@link MessageRunner}
	 * 
	 * @param netManager
	 * 		the {@link NetManager} managing this {@link MessageRunner}
	 */
	public MessageRunner(NetManager netManager) {
		super("MessageRunner", netManager);
	}

	@Override
	protected void update() {
		if (!netManager.receiveQueueisEmpty()){
			/* there is a new message in the receive queue*/
			if (!netManager.listenersIsEmpty()){
				/* there is at least one message handler available */
				
				/* get the message */
				RawOpenIGTMessage rawMsg = netManager.pollReceiveQueue();
				OpenIGTMessage message = netManager.messageParser.parse(
						new Header(rawMsg.headerBytes), rawMsg.body);
				if (message == null) {
					log.warn("Message couldn't be parsed!");
				}
					
				
				else {		
					/* check if the message is valid or the message parser is set to 
					 * ignore invalid messages */
					if (netManager.messageParser.testReceivedMessageValidity(message) || 
							!netManager.messageParser.ignoreInvalidMessages) {
						/* call the appropriate method for the messages type for every message handler */
						for (IOpenIGTLinkMessageListener listener : netManager.getListeners()) {
							try {
								switch (OIGTLMessageType.from(message)) {
								case GET:
									if (message instanceof GetCapabilityMessage) {
										handleCapabilityQuery((GetCapabilityMessage)message, listener);
									}
									else {
										handleQuery((OIGTL_GetMessage)message, listener);
									}
									break;
								case STT:
									handleSTTMessage((OIGTL_STTMessage) message, listener);
									break;
								case STP:
									handleSTPMessage((OIGTL_STPMessage) message, listener);
									break;
								case RTS:
									listener.rtsMesssageReceived((OIGTL_RTSMessage) message, netManager);
									break;
								default:
									listener.messageReceived(message);
									break;
								}
							} catch (IOException e) {
								netManager.report(e);
							}
						}
					}
					else {
						log.warn("Message has been ignored due to invalidity." +  message.toString());
					}
				}
			}
		}
		else{
			synchronized (this) {
				try {
					this.wait(20);
				} catch (InterruptedException e) {}
			}
		}
	}

	/**
	 * Method to handle a new streaming start message
	 * 
	 * @param message
	 * 		the message to be handled
	 * @param listener
	 * 		the listener to call
	 * @throws IOException
	 * 		if there was a problem with the connection(s)
	 * 		
	 */
	void handleSTTMessage(OIGTL_STTMessage message, IOpenIGTLinkMessageListener listener) throws IOException {
		/* get reply message from listener */
		OIGTL_RTSMessage reply = listener.sttMessageReceived(message, netManager);

		handleSTT_STP_message(message, reply);
	}

	/**
	 * Method to handle a new streaming stop message
	 * 
	 * @param message
	 * 		the message to be handled
	 * @param listener
	 * 		the listener to call
	 * @throws IOException
	 * 		if there was a problem with the connection(s)
	 */
	void handleSTPMessage(OIGTL_STPMessage message, IOpenIGTLinkMessageListener listener) throws IOException {
		/* get reply message from listener */
		OIGTL_RTSMessage reply = listener.stpMessageReceived(message, netManager);

		handleSTT_STP_message(message, reply);
	}

	/**
	 * Method to handle a new streaming start or stop message
	 * 
	 * @param message
	 * 		the message to be handled
	 * @param reply
	 * 		the reply message
	 * @throws IOException
	 * 		if there was a problem with the connection(s)
	 */
	void handleSTT_STP_message(OpenIGTMessage message, OIGTL_RTSMessage reply) throws IOException {
		/* The receiver of "STT_<datatype>" or "STP_<datatype>" message must return 
		 * "RTS_<datatype>" message with the same name as the query message to notify 
		 * that the receiver receives the query.*/
		String dataType = netManager.messageParser.getReplyDataType(message.getDataType());
		if (reply == null) {
			/* return null body message */
			reply = new OIGTL_RTSMessage(dataType, message.getDeviceName(), RTSMessageStatus.Error);
		}
		else {
			if (!reply.getDataType().equals(dataType)) {
				if (!(reply instanceof INoReplyMessage)){
					/* reply message is not of type queried */
					log.debug("DATA_TYPE " + reply.getDataType() + " is no valid repy to " + message.getDataType());
					reply = new OIGTL_RTSMessage(dataType, message.getDeviceName(), RTSMessageStatus.Error);
				}
			}
			else {
				if (!message.getDeviceName().isEmpty() && !reply.getDataType().equals(dataType)) {
					/* reply message is not of requested deviceName (if not empty)*/
					log.debug("DeviceName: " + reply.getDeviceName() + " is no valid repy to " + message.getDeviceName());
					reply = new OIGTL_RTSMessage(dataType, message.getDeviceName(), RTSMessageStatus.Error);
				}
			}
		}
		if (!(reply instanceof INoReplyMessage)){
			netManager.send(reply);
		}
	}

	/**
	 * Method to handle a CAPABILITY query message
	 * 
	 * @param message
	 * 		the message to be handled
	 * @param listener
	 * 		the listener to call
	 * @throws IOException
	 * 		if there was a problem with the connection(s)
	 */
	void handleCapabilityQuery(GetCapabilityMessage message, IOpenIGTLinkMessageListener listener) throws IOException {
		/* get capabilty list from listener and create CAPABILITY message*/
		CapabilityMessage reply = new CapabilityMessage(message.getDeviceName());
		try{
			reply.setCapability(listener.getCapability());

			netManager.send(reply);
		}catch (IllegalArgumentException e) {}
	}

	/**
	 * Method to handle a query message
	 * 
	 * @param message
	 * 		the message to be handled
	 * @param listener
	 * 		the listener to call
	 * @throws IOException
	 * 		if there was a problem with the connection(s)
	 */
	void handleQuery(OIGTL_GetMessage message, IOpenIGTLinkMessageListener listener) throws IOException {
		/* get reply message from listener */
		OpenIGTMessage reply = listener.getMessageReceived(message);

		/* check reply validity and act according to query specification 
		 * The receiver of "GET_<datatype>" message must return a message with 
		 * type <datatype> and the same name as the query message. A "GET_<datatype>" 
		 * message without device name requests any available data. If data is not 
		 * available, a returned message must be null body (data size = 0).*/
		String replyDataType = netManager.messageParser.getReplyDataType(message.getDataType());
		if (reply == null) {
			/* return null body message */
			reply = new OIGTL_HeaderOnlyMessage(replyDataType, message.getDeviceName());
		}
		else {
			if (!reply.getDataType().equals(replyDataType)) {
				/* reply message is not of type queried */
				if (!(reply instanceof INoReplyMessage)){
					log.debug("DATA_TYPE " + reply.getDataType() + " is no valid repy to " + message.getDataType());
					reply = new OIGTL_HeaderOnlyMessage(replyDataType, message.getDeviceName());
				}					
			}
			else {
				if (!message.getDeviceName().isEmpty() && !reply.getDataType().equals(replyDataType)) {
					/* reply message is not of requested deviceName (if not empty)*/
					log.debug("DeviceName: " + reply.getDeviceName() + " is no valid repy to " + message.getDeviceName());
					reply = new OIGTL_HeaderOnlyMessage(replyDataType, message.getDeviceName());
				}
			}
		}
		if (!(reply instanceof INoReplyMessage)){
			netManager.send(reply);
		}
	}

}
