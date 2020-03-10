/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track.tdata;

import msg.OIGTL_STPMessage;
import util.Header;

/**
 * Class representing a {@link STPTrackingDataMessage} that implements the
 * STP message for the streaming mechanism for {@link TrackingDataMessage}
 * 
 * @author Andreas Rothfuss
 *
 */
public class STPTrackingDataMessage extends OIGTL_STPMessage{

    /** The messages data type */
	public static final String DATA_TYPE = "STP_TDATA";

	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public STPTrackingDataMessage(String deviceName) {
            super(DATA_TYPE, deviceName);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public STPTrackingDataMessage(Header header, byte body[]) {
            super(header, body);
    }

}
