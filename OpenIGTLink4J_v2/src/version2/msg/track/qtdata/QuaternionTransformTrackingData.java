/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track.qtdata;

import msg.position.QuaternionTransform;
import msg.track.TrackingData;
import msg.track.TrackingInstrumentType;
import util.BytesArray;

/**
 *** This class create a {@link QuaternionTransformTrackingData} object from
 * bytes received or help to generate bytes to send from it as specified in
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/qtrackingdata.md
 * 
 * @author Andreas Rothfuss
 * 
 */
public class QuaternionTransformTrackingData extends TrackingData{

	/** Transform as {@link QuaternionTransform} */
	QuaternionTransform transform;

	/**
	 * Constructor to create a fully specified {@link QuaternionTransformTrackingData}
	 * 
	 * @param name
	 * 		Name (=Id) of the instrument/tracker
	 * @param type 
	 * 		Type of instrument
	 * @param transform
	 * 		Transform as {@link QuaternionTransform}
	 */
	public QuaternionTransformTrackingData(String name, TrackingInstrumentType type, 
			QuaternionTransform transform) {
		super(name, type);
		this.transform = transform;
	}

	/**
	 * Constructor to create an empty {@link QuaternionTransformTrackingData}
	 */
	public QuaternionTransformTrackingData() {
		this("", TrackingInstrumentType.TRACKER, new QuaternionTransform());
	}

	/**
	 * Constructor to de-serialize a {@link QuaternionTransformTrackingData} from bytes
	 * 
	 * @param bytes
	 * 		the serialized form of the {@link QuaternionTransformTrackingData}
	 */
	public QuaternionTransformTrackingData(byte[] bytes) {
		super(bytes);
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(bytes);
		bytesArray.getBytes(TrackingData.SIZE);
		
		transform = new QuaternionTransform(bytesArray.getBytes(QuaternionTransform.SIZE));
	}

	@Override
	public byte[] getBytes() {
		BytesArray bytesArray = new BytesArray();
		
		bytesArray.putBytes(super.getBytes());
		bytesArray.putBytes(transform.getBytes());
		
		return bytesArray.getBytes();
	}

	/**
	 * @return the transform
	 */
	public QuaternionTransform getTransform() {
		return transform;
	}

	/**
	 * @param transform the transform to set
	 */
	public void setTransform(QuaternionTransform transform) {
		this.transform = transform;
	}

	/**
	 *** To get transform String
	 *** 
	 * @return the transform String
	 */
	@Override
	public String toString() {
		return super.toString() + " " + transform.toString();
	}
}