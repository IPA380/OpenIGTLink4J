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
 * This class creates a HeaderOnlyMessage object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author	Andreas Rothfuss
 * 
 */
public class OIGTL_HeaderOnlyMessage extends OpenIGTMessage {
	
    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public OIGTL_HeaderOnlyMessage(String dataType, String deviceName) {
            super(dataType, deviceName, 0);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public OIGTL_HeaderOnlyMessage(Header header, byte body[]) {
            super(header, body);
    }

    @Override
    public byte[] getBody() {
        return new byte[0];
    }

    @Override
    public boolean UnpackBody(byte[] body) {
    	return true;
    }

    @Override
    public String toString() {
            return getHeader().toString();
    }
}

