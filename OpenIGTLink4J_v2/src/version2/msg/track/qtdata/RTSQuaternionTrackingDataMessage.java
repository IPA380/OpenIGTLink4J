/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track.qtdata;

import msg.OIGTL_RTSMessage;
import util.Header;
import util.RTSMessageStatus;

/**
 * Class representing a reply message for streaming messages with data type
 * RTS_QTDATA
 * 
 * @author Andreas Rothfuss
 *
 */
public class RTSQuaternionTrackingDataMessage extends OIGTL_RTSMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "RTS_QTDATA";

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of the request for which this represents the reply
	 * @param status		status of the request for which this represents the reply
	 */
    public RTSQuaternionTrackingDataMessage(String deviceName, RTSMessageStatus status) {
            super(DATA_TYPE, deviceName, status);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public RTSQuaternionTrackingDataMessage(Header header, byte body[]) {
            super(header, body);
    }

}
