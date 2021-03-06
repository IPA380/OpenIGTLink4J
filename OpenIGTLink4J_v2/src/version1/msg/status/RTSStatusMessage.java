/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.status;

import msg.OIGTL_RTSMessage;
import util.Header;
import util.RTSMessageStatus;

/**
 * Class representing a reply message for streaming messages with data type
 * RTS_STATUS
 * 
 * @author Andreas Rothfuss
 *
 */
public class RTSStatusMessage extends OIGTL_RTSMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "RTS_STATUS";

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of the request for which this represents the reply
	 * @param status		status of the request for which this represents the reply
	 */
    public RTSStatusMessage(String deviceName, RTSMessageStatus status) {
            super(DATA_TYPE, deviceName, status);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public RTSStatusMessage(Header header, byte body[]) {
            super(header, body);
    }

}
