/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track.qtdata;

import java.util.ArrayList;

import msg.OIGTL_DataMessage;
import msg.trajectory.TrajectoryElement;
import util.BytesArray;
import util.Header;

/**
 *** This class creates a quaternion transform tracking message object from bytes 
 * received or help to generate bytes to send from it
 * 
 * @author Andreas Rothfuss
 * 
 */
public class QuaternionTrackingDataMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "QTDATA";

    /** List of all {@link QuaternionTransformTrackingData}s  contained in this */
	ArrayList<QuaternionTransformTrackingData> transformList;

	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method SetImageHeader, then CreateBody and then
	 * getBytes to send them
	 *** 
	 * @param deviceName
	 *            Device Name
	 **/
	public QuaternionTrackingDataMessage(String deviceName) {
		super(DATA_TYPE, deviceName);
		transformList = new ArrayList<QuaternionTransformTrackingData>();
	}


    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public QuaternionTrackingDataMessage(Header header, byte body[]){
		super(header, body);
	}

	@Override
	public boolean UnpackBody(byte[] body) {
		log.debug("Body size: "+ body.length + " date size: "+ body.length);
		
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(body);
		
		transformList = new ArrayList<QuaternionTransformTrackingData>();
		
		int nElement = body.length / QuaternionTransformTrackingData.SIZE;
		  
		/* TODO: have a look at little / big endian conversion */
		for (int iter = 0; iter < nElement; iter ++){
			QuaternionTransformTrackingData element = new QuaternionTransformTrackingData(
					bytesArray.getBytes(TrajectoryElement.IGTL_TRAJECTORY_ELEMENT_SIZE));
		    transformList.add(element);
		}
		return true;
	}

	@Override
	public byte[] getBody() {
		BytesArray bytesArray = new BytesArray();
		
		for (QuaternionTransformTrackingData transformTrackingData : transformList) {
			bytesArray.putBytes(transformTrackingData.getBytes());
		}
		
		return bytesArray.getBytes();
	}

	/** 
	 * @param the tracking data to add
	 */
	public void addTrackingData(QuaternionTransformTrackingData data) {
		transformList.add(data);
	}

	/**
	 * @return get tracking data at index i
	 *** 
	 */
	public QuaternionTransformTrackingData getTrackingData(int i) {
		return transformList.get(i);
	}

	/**
	 * @return get tracking data at index i
	 *** 
	 */
	public ArrayList<QuaternionTransformTrackingData> getTrackingData() {
		return transformList;
	}
	
	@Override
	public String toString() {
		String retVal =  "TDATA Device Name : " + getDeviceName() + " ";
		for (QuaternionTransformTrackingData transformTrackingData : transformList) {
			retVal += transformTrackingData.toString() + " ";
		} 
		return retVal;
	}
}

