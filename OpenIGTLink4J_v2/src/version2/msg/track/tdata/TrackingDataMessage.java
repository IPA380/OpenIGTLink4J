/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track.tdata;

import java.util.ArrayList;

import msg.OIGTL_DataMessage;
import util.BytesArray;
import util.Header;

/**
 *** This class creates a transform object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andreas Rothfuss
 * 
 */
public class TrackingDataMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "TDATA";

	/** List of all {@link TransformTrackingData}s  contained in this */
	ArrayList<TransformTrackingData> transformList;

	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method SetImageHeader, then CreateBody and then
	 * getBytes to send them
	 *** 
	 * @param deviceName
	 *            Device Name
	 **/
	public TrackingDataMessage(String deviceName) {
		super(DATA_TYPE, deviceName);
		transformList = new ArrayList<TransformTrackingData>();
	}

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public TrackingDataMessage(Header header, byte body[]){
		super(header, body);
	}

	/**
	 *** To create body from body array
	 * 
	 * @return true if unpacking is ok
	 */
	@Override
	public boolean UnpackBody(byte[] body) {
		log.trace("Body size: "+ body.length + " date size: "+ body.length);
		
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(body);
		
		transformList = new ArrayList<TransformTrackingData>();
		
		int nElement = body.length / TransformTrackingData.SIZE;
		  
		// TODO: have a look at little / big endian conversion
		for (int iter = 0; iter < nElement; iter ++){
			TransformTrackingData element = new TransformTrackingData(
					bytesArray.getBytes(TransformTrackingData.SIZE));
		    transformList.add(element);
		}

		return true;
	}

	/**
	 *** To create body from image_header and image_data
	 *  SetTransformData must have called first
	 * 
	 *** 
	 * @return the bytes array containing the body
	 */
	@Override
	public byte[] getBody() {
		BytesArray bytesArray = new BytesArray();
		
		for (TransformTrackingData transformTrackingData : transformList) {
			bytesArray.putBytes(transformTrackingData.getBytes());
		}
		
		return bytesArray.getBytes();
	}

	/** 
	 * @param the tracking data to add
	 */
	public void addTrackingData(TransformTrackingData data) {
		transformList.add(data);
	}

	/**
	 * @return get tracking data at index i
	 *** 
	 */
	public TransformTrackingData getTrackingData(int i) {
		return transformList.get(i);
	}

	/**
	 * @return get tracking data at index i
	 *** 
	 */
	public ArrayList<TransformTrackingData> getTrackingData() {
		return transformList;
	}
	
	/**
	 *** To get transform String
	 *** 
	 * @return the transform String
	 */
	@Override
	public String toString() {
		String retVal =  "TDATA Device Name : " + getDeviceName() + " ";
		if (transformList != null) {
			for (TransformTrackingData transformTrackingData : transformList) {
				retVal += transformTrackingData.toString() + " ";
			} 
		}
		else
			retVal += " TransformList is null";
		return retVal;
	}
}

