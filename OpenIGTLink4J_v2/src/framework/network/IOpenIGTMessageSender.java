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

import msg.OpenIGTMessage;

/**
 * Interface defining methods all OpenIGTLink message senders need to implement
 * 
 * @author Andreas Rothfuss
 *
 */
public interface IOpenIGTMessageSender {

	/**
	 * Method called to send a message
	 * 
	 * @param message
	 * 		message to be sent
	 * @throws IOException
	 * 		if there was a problem with the connection(s)
	 */
	public void send(OpenIGTMessage message) throws IOException;
}
