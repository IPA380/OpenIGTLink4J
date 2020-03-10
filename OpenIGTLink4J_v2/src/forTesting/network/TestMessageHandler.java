/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import java.util.Vector;

import org.slf4j.LoggerFactory;

import msg.OIGTL_DataMessage;
import msg.OIGTL_GetMessage;
import msg.OIGTL_RTSMessage;
import msg.OIGTL_STPMessage;
import msg.OIGTL_STTMessage;
import msg.OpenIGTMessage;
import protocol.MessageHandler;

/**
 * Implementation of {@link MessageHandler} for testing purposes that enables assement
 * of correct messaging behavior
 * 
 * @author Andreas Rothfuss
 *
 */
public class TestMessageHandler extends MessageHandler {

	/** Field to store received messages in order to enable assment of correct behavior */
	protected Vector<OpenIGTMessage> receivedMessages = new Vector<OpenIGTMessage>();
	
	public final boolean printMessageInfo;
	
	public TestMessageHandler() {
		this(true);
	}
	
	public TestMessageHandler(boolean printMessageInfo) {
		this.printMessageInfo = printMessageInfo;
		log = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public String[] getCapability() {
		return new String[0];
	}
	
	@Override
	public void messageReceived(OpenIGTMessage message) {
		if (printMessageInfo) {
			log.info("Message received: " + message.toString());
		}
		receivedMessages.addElement(message);
	}

	@Override
	public OIGTL_DataMessage getMessageReceived(OIGTL_GetMessage message) {
		if (printMessageInfo) {
			log.info("Message received: " + message.toString());
		}
		receivedMessages.addElement(message);
		return null;		
	}

	@Override
	public OIGTL_RTSMessage sttMessageReceived(OIGTL_STTMessage message, IOpenIGTMessageSender replyTo) {
		if (printMessageInfo) {
			log.info("Message received: " + message.toString());
		}
		receivedMessages.addElement(message);
		return null;
	}

	@Override
	public OIGTL_RTSMessage stpMessageReceived(OIGTL_STPMessage message, IOpenIGTMessageSender replyTo) {
		if (printMessageInfo) {
			log.info("Message received: " + message.toString());
		}
		receivedMessages.addElement(message);
		return null;
	}

	@Override
	public void rtsMesssageReceived(OIGTL_RTSMessage message, IOpenIGTMessageSender replyTo) {
		if (printMessageInfo) {
			log.info("Message received: " + message.toString());
		}
		receivedMessages.addElement(message);
	}

	/**
	 * To get the received messages
	 * 
	 * @return the receivedMessages
	 */
	public Vector<OpenIGTMessage> getReceivedMessages() {
		return receivedMessages;
	}

}
