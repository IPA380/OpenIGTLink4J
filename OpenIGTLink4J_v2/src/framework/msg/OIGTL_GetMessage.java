/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;

import util.Header;

/**
 * Abstract base class representing all {@link OpenIGTMessage} implementations that
 * implement a GET message from the query mechanism
 * 
 * @author Andreas Rothfuss
 *
 */
public class OIGTL_GetMessage extends OIGTL_HeaderOnlyMessage {

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public OIGTL_GetMessage(Header header, byte[] body) {
		super(header, body);
	}

    /**
     *** Constructor to be used to create message to be sent
     * 
     * @param messageType 	data type of the message
	 * @param deviceName	device name of message
	 * */
	public OIGTL_GetMessage(String messageType, String deviceName){
		super(messageType, deviceName);
	}

}
