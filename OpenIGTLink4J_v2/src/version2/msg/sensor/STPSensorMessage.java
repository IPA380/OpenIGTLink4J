/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

import util.Header;

import msg.OIGTL_STPMessage;

/**
 * Class representing the STP message to stop the streaming mechanism
 * for {@link SensorMessage}s
 * 
 * @author Andreas Rothfuss
 *
 */
public class STPSensorMessage extends OIGTL_STPMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "STP_SENSOR";
	
	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method CreateBody and then
	 * getBytes to send them
	 *** 
	 * @param deviceName
	 *            Device Name
	 **/
	public STPSensorMessage(String deviceName) {
	        super(DATA_TYPE, deviceName);
	}
	
    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public STPSensorMessage(Header header, byte body[]){
	        super(header, body);
	}

}



