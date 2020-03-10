/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.position;

import msg.OIGTL_STPMessage;
import util.Header;

/**
 * Class representing a {@link STPPositionMessage} that implements the
 * STP message for the streaming mechanism for {@link PositionMessage}
 * 
 * @author Andreas Rothfuss
 *
 */
public class STPPositionMessage extends OIGTL_STPMessage{

    /** The messages data type */
	public static final String DATA_TYPE = "STP_POSITION";
    /** The messages former data type */
	public static final String OLD_DATA_TYPE = "STP_QTRANS";
	
	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public STPPositionMessage(String deviceName) {
    	this(deviceName, false);
    }
	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public STPPositionMessage(String deviceName, boolean legacyDataType) {
        super(legacyDataType ? OLD_DATA_TYPE : DATA_TYPE, deviceName);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public STPPositionMessage(Header header, byte body[]) {
            super(header, body);
    }

}
