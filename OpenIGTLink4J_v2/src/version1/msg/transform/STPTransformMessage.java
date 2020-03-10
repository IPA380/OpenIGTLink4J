/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.transform;

import msg.OIGTL_STPMessage;
import util.Header;

/**
 * Class representing a {@link STPTransformMessages} that implements the
 * STP message for the streaming mechanism for {@link TransformMessages}
 * 
 * @author Andreas Rothfuss
 *
 */
public class STPTransformMessage extends OIGTL_STPMessage{

    /** The messages data type */
	public static final String DATA_TYPE = "STP_TRANS";

	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public STPTransformMessage(String deviceName) {
            super(DATA_TYPE, deviceName);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public STPTransformMessage(Header header, byte body[]) {
            super(header, body);
    }

}
