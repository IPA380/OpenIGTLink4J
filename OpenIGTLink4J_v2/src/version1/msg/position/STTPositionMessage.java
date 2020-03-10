/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.position;

import msg.OIGTL_STTMessage;
import util.Header;

/**
 * Class representing a {@link STTQuaternionTransform} that implements the
 * STT message for the streaming mechanism for {@link QuaternionTransform}
 * 
 * @author Andreas Rothfuss
 *
 */
public class STTPositionMessage extends OIGTL_STTMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "STT_POSITION";
    /** The messages former data type */
	public static final String OLD_DATA_TYPE = "STT_QTRANS";
	
	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method CreateBody and then
	 * getBytes to send them
	 *** 
	 * @param deviceName
	 *            Device Name
	 **/
	public STTPositionMessage(String deviceName) {
		this(deviceName, false);
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
	public STTPositionMessage(String deviceName, long resolution) {
		this(deviceName, false, resolution);
	}
	
	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method CreateBody and then
	 * getBytes to send them
	 *** 
	 * @param deviceName
	 *            Device Name
	 **/
	public STTPositionMessage(String deviceName, boolean legacyDataType) {
	    super(legacyDataType ? OLD_DATA_TYPE : DATA_TYPE, deviceName);
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
	public STTPositionMessage(String deviceName, boolean legacyDataType, long resolution) {
	    super(legacyDataType ? OLD_DATA_TYPE : DATA_TYPE, deviceName, resolution);
	}
	
	/**
	 *** Constructor to be used to create message from received data
	 * 
	 * @param header
	 * @param body
	 */
	public STTPositionMessage(Header header, byte body[]) {
	        super(header, body);
	}
}
