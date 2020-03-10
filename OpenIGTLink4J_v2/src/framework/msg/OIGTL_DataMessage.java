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
 * are not specific to the query or streaming mechanisms
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class OIGTL_DataMessage extends OpenIGTMessage {

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public OIGTL_DataMessage(Header header, byte[] body){
		super(header, body);
	}

    /**
     *** Constructor to be used to create message to be sent
     * 
     * @param messageType 	data type of the message
	 * @param deviceName	device name of message
	 * @param bodySize		body size of the message
	 * */
	public OIGTL_DataMessage(String messageType, String deviceName, int bodySize){
		super(messageType, deviceName, bodySize);
	}

    /**
     *** Constructor to be used to create message to be sent
     * 
     * @param messageType 	data type of the message
	 * @param deviceName	device name of message
	 * */
	public OIGTL_DataMessage(String messageType, String deviceName){
		this(messageType, deviceName, 0);
	}
}
