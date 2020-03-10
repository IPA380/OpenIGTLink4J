/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package protocol;

import msg.OpenIGTMessage;
import network.IOpenIGTMessageSender;
import msg.OIGTL_DataMessage;
import msg.OIGTL_GetMessage;
import msg.OIGTL_RTSMessage;
import msg.OIGTL_STPMessage;
import msg.OIGTL_STTMessage;
import util.RTSMessageStatus;

/**
 * Interface defining methods all OpenIGTLink message listeners need to implement
 * 
 * @author Andreas Rothfuss
 *
 */
public interface IOpenIGTLinkMessageListener {

	/**
	 * Method called by the messaging framework when a {@link OpenIGTMessage} except 
	 * {@link OIGTL_GetMessage}, {@link OIGTL_STTMessage}, {@link OIGTL_STPMessage} and {@link OIGTL_RTSMessage}
	 * is received
	 * 
	 * @param message subclass of {@link OpenIGTMessage} to which a reply is queried
	 */
	public void messageReceived(OpenIGTMessage message);

	/**
	 * Method called by the messaging framework when a {@link OIGTL_GetMessage} is received
	 * 
	 * @param message subclass of {@link OIGTL_GetMessage} to which a reply is queried
	 * @return subclass of {@link OIGTL_DataMessage} as reply with corresponding data type 
	 * and same device name, <code>null</code> for empty reply message
	 */
	public OIGTL_DataMessage getMessageReceived(OIGTL_GetMessage message);

	/**
	 * Method called by the messaging framework when a {@link OIGTL_STTMessage} is received
	 * 
	 * @param message subclass of {@link OIGTL_STTMessage} requesting the start of a series of messages
	 * @param origin subclass of {@link IOpenIGTMessageSender} as the address of the origin of message
	 * @return subclass of {@link OIGTL_RTSMessage} as reply with corresponding data type 
	 * and same device name, <code>null</code> for repy message with {@link RTSMessageStatus} ERROR
	 */
	public OIGTL_RTSMessage sttMessageReceived(OIGTL_STTMessage message, IOpenIGTMessageSender origin);

	/**
	 * Method called by the messaging framework when a {@link OIGTL_STPMessage} is received
	 * 
	 * @param message subclass of {@link OIGTL_STPMessage} requesting the stop of a series of messages
	 * @param origin subclass of {@link IOpenIGTMessageSender} as the address of the origin of message
	 * @return subclass of {@link OIGTL_RTSMessage} as reply with corresponding data type 
	 * and same device name, <code>null</code> for repy message with {@link RTSMessageStatus} ERROR
	 */
	public OIGTL_RTSMessage stpMessageReceived(OIGTL_STPMessage message, IOpenIGTMessageSender origin);

	/**
	 * Method called by the messaging framework when a {@link OIGTL_RTSMessage} is received
	 * 
	 * @param message subclass of {@link OIGTL_RTSMessage}
	 * @param origin subclass of {@link IOpenIGTMessageSender} as the address of the origin of message
	 * @return subclass of {@link OIGTL_RTSMessage} as reply with corresponding data type 
	 * and same device name, <code>null</code> for reply message with {@link RTSMessageStatus} ERROR
	 */
	public void rtsMesssageReceived(OIGTL_RTSMessage message, IOpenIGTMessageSender origin);
	
	/** 
	 * Method to query the {@link IOpenIGTLinkMessageListener} for all the message data types it is capable of handling
	 * 
	 * @return {@link String[]} containing the capabilities
	 */
	public String[] getCapability();
}
