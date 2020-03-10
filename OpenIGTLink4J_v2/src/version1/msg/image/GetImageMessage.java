/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Absynt Technologies Ltd, Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.image;

import msg.OIGTL_GetMessage;
import util.Header;

/**
 *** This class create a GetStatus object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andre Charles Legendre
 * @author Andreas Rothfuss
 * 
 */
public class GetImageMessage extends OIGTL_GetMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "GET_IMAGE";

    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public GetImageMessage(String deviceName) {
            super(DATA_TYPE, deviceName);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public GetImageMessage(Header header, byte body[]){
            super(header, body);
    }

}

