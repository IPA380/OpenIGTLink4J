/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.position;

import msg.OIGTL_RTSMessage;
import util.Header;
import util.RTSMessageStatus;

/**
 * Class representing a reply message for streaming messages with data type
 * RTS_POSITION or RTS_QTRANS (legacy)
 * 
 * @author Andreas Rothfuss
 *
 */
public class RTSPositionMessage extends OIGTL_RTSMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "RTS_POSITION";
    /** The messages former data type */
	public static final String OLD_DATA_TYPE = "RTS_QTRANS";

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of the request for which this represents the reply
	 * @param status		status of the request for which this represents the reply
	 */
    public RTSPositionMessage(String deviceName, RTSMessageStatus status) {
    	this(deviceName, status, false);
    }
	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public RTSPositionMessage(String deviceName, RTSMessageStatus status, boolean legacyDataType) {
        super(legacyDataType ? OLD_DATA_TYPE : DATA_TYPE, deviceName, status);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public RTSPositionMessage(Header header, byte body[]) {
            super(header, body);
    }

}
