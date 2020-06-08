/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import msg.OIGTL_DataMessage;
import msg.OIGTL_GetMessage;
import msg.OIGTL_RTSMessage;
import msg.OIGTL_STPMessage;
import msg.OIGTL_STTMessage;
import msg.OpenIGTMessage;
import network.IOpenIGTMessageSender;
/**
 * Class implementing an {@link IOpenIGTLinkMessageListener} that logs every 
 * incoming message but does nothing more
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class MessageHandler implements IOpenIGTLinkMessageListener {
	
	protected Logger log = LoggerFactory.getLogger(MessageHandler.class);
		
	@Override
	public void messageReceived(OpenIGTMessage message) {
		if (message != null)
			log.trace("Message received: {}", message);
	}

	@Override
	public OIGTL_DataMessage getMessageReceived(OIGTL_GetMessage message) {
		if (message != null)
			log.trace("Message received: {}", message);
		return null;		
	}

	@Override
	public OIGTL_RTSMessage sttMessageReceived(OIGTL_STTMessage message, IOpenIGTMessageSender replyTo) {
		if (message != null)
			log.trace("Message received: {}", message);
		return null;
	}

	@Override
	public OIGTL_RTSMessage stpMessageReceived(OIGTL_STPMessage message, IOpenIGTMessageSender replyTo) {
		if (message != null)
			log.trace("Message received: {}", message);
		return null;
	}

	@Override
	public void rtsMesssageReceived(OIGTL_RTSMessage message, IOpenIGTMessageSender replyTo) {
		if (message != null)
			log.trace("Message received: {}", message);
	}

}