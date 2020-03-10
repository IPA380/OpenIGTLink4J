/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track.tdata;

import msg.track.TrackingData;
import msg.track.TrackingInstrumentType;
import msg.track.qtdata.QuaternionTransformTrackingData;
import msg.transform.Transform;
import util.BytesArray;

/**
 *** This class create a {@link TransformTrackingData} object from
 * bytes received or help to generate bytes to send from it as specified in
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/qtrackingdata.md
 * 
 * @author Andreas Rothfuss
 * 
 */
public class TransformTrackingData extends TrackingData{
	
	/** Size of the serialized form of a {@link QuaternionTransformTrackingData} in bytes */
	public static int SIZE = TRACKING_DATA_NAME_SIZE + 
			2 + Transform.IGTL_TRANSFORM_SIZE;

	/** Transform as {@link Transform} */
	Transform transform;

	/**
	 * Constructor to create a fully specified {@link TransformTrackingData}
	 * 
	 * @param name
	 * 		Name (=Id) of the instrument/tracker
	 * @param type 
	 * 		Type of instrument
	 * @param transform
	 * 		Transform as {@link Transform}
	 */
	public TransformTrackingData(String name, TrackingInstrumentType type, 
			Transform transform) {
		super(name, type);
		this.transform = transform;
	};

	/**
	 * Constructor to create an empty {@link TransformTrackingData}
	 */
	public TransformTrackingData() {
		this("", TrackingInstrumentType.TRACKER, new Transform());
	}

	/**
	 * Constructor to de-serialize a {@link TransformTrackingData} from bytes
	 * 
	 * @param bytes
	 * 		the serialized form of the {@link TransformTrackingData}
	 */
	public TransformTrackingData(byte[] bytes) {
		super(bytes);
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(bytes);
		bytesArray.getBytes(TrackingData.SIZE);
		
		transform = new Transform(bytesArray.getBytes(Transform.IGTL_TRANSFORM_SIZE));
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
	public Transform getTransform() {
		return transform;
	}

	/**
	 * @param transform the transform to set
	 */
	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	@Override
	public String toString() {
		return super.toString() + " " + transform.toString();
	}
}