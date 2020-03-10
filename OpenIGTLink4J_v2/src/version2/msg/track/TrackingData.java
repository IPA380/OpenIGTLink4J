/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track;

import msg.track.qtdata.QuaternionTrackingDataMessage;
import msg.track.qtdata.QuaternionTransformTrackingData;
import util.BytesArray;

/**
 *** This class represents a {@link TrackingData} object as uses in 
 * {@link QuaternionTrackingDataMessage} and {@link TransformTrackingDataMessage}
 * 
 * @author Andreas Rothfuss
 * 
 */
public abstract class TrackingData {

	/** Size of the serialized form of a quaternion tracking date name in bytes */
	public static int TRACKING_DATA_NAME_SIZE = 20;
	/** Size of the serialized form of a {@link QuaternionTransformTrackingData} in bytes */
	public static int SIZE = TRACKING_DATA_NAME_SIZE + 
			2;// + Transform.IGTL_TRANSFORM_SIZE;

	/** Name (=Id) of the instrument/tracker */
	protected String name;
	/** Type of instrument */
	protected TrackingInstrumentType type;

	/**
	 * Constructor to create a fully specified {@link TrackingData} object
	 * 
	 * @param name
	 * 		Name (=Id) of the instrument/tracker
	 * @param type 
	 * 		Type of instrument
	 */
	public TrackingData(String name, TrackingInstrumentType type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Constructor to create an empty {@link TrackingData} object
	 */
	public TrackingData() {
		this("", TrackingInstrumentType.TRACKER);
	}

	/**
	 * Constructor to de-serialize a {@link TrackingData} object from bytes
	 * 
	 * @param bytes
	 * 		the serialized form of the {@link TrackingData} object
	 */
	public TrackingData(byte[] bytes) {
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(bytes);

		name = bytesArray.getString(TRACKING_DATA_NAME_SIZE);
		
		type = TrackingInstrumentType.fromValue(bytesArray.getBytes(1)[0]);
		bytesArray.getBytes(1);
	}

	/**
	 * To serialize this
	 * @return
	 */
	public byte[] getBytes() {
		BytesArray bytesArray = new BytesArray();
		
		bytesArray.putString(name, TRACKING_DATA_NAME_SIZE);
		bytesArray.putByte(type.code);
		bytesArray.putByte((byte)0);
		
		return bytesArray.getBytes();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public TrackingInstrumentType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TrackingInstrumentType type) {
		this.type = type;
	}

	/**
	 *** To get transform String
	 *** 
	 * @return the transform String
	 */
	@Override
	public String toString() {
		return "Tracking transform data: Name = " + 
				name + " type: " + type;
	}


}
