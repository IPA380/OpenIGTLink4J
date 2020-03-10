/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

import msg.OIGTL_STTMessage;
import util.Header;

/**
 * Class representing the STT message to start the streaming mechanism
 * for {@link SensorMessage}s
 * 
 * @author Andreas Rothfuss
 *
 */
public class STTSensorMessage extends OIGTL_STTMessage{

    /** The messages data type */
	public static final String DATA_TYPE = "STT_SENSOR";

	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public STTSensorMessage(String deviceName) {
            super(DATA_TYPE, deviceName);
    }

	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     * @param resolution
     * 			targeted streaming resolution in milliseconds, 0 is requesting 
     * 			data as fast as possible
     **/
    public STTSensorMessage(String deviceName, long resolution) {
            super(DATA_TYPE, deviceName, resolution);
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header
     * @param body
     * @throws Exception 
     */
    public STTSensorMessage(Header header, byte body[]) {
            super(header, body);
    }

}
