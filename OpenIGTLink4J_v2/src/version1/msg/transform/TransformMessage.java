/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Absynt Technologies Ltd, Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.transform;

import util.Header;
//import com.neuronrobotics.sdk.common.ByteList;

import msg.OIGTL_DataMessage;

/**
 *** This class creates a transform object from bytes received or help to generate
 * bytes to send from it
 * 
 * The TRANSFORM data type is used to transfer a homogeneous linear transformation
 * in 4-by-4 matrix form. One such matrix was shown earlier in equation (1).
 * Note that if a device is sending only translation and rotation, then TRANSFORM
 * is equivalent to POSITION. But TRANSFORM can also be used to transfer affine
 * transformations or simple scaling. Like IMAGE and POSITION, TRANSFORM carries
 * information about the coordinate system used.
 * 
 * @author Andre Charles Legendre
 * @author Andreas Rothfuss
 * 
 */
public class TransformMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "TRANSFORM";

	/** The transform */
	private Transform transform;

	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method SetImageHeader, then CreateBody and then
	 * getBytes to send them
	 *** 
	 * @param deviceName
	 *            Device Name
	 **/
	public TransformMessage(String deviceName) {
		super(DATA_TYPE, deviceName);
		transform = new Transform();
	}

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName
	 * 		the device name of the message
	 * @param positionAray
	 * 		the origin of the transform
	 * @param rotationMatrix
	 * 		the rotation matrix
	 * */
	public TransformMessage(String deviceName,float[] positionAray, float[][] rotationMatrix) {
		this(deviceName);
		transform = new Transform(positionAray, rotationMatrix);
	}


    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public TransformMessage(Header header, byte body[]){
		super(header, body);
	}

	/**
	 *** To create body from body array
	 * 
	 *** 
	 * @return true if unpacking is ok
	 */
	@Override
	public boolean UnpackBody(byte[] body) {
		log.trace("Body size: "+ body.length + " date size: "+ body.length);
 
		transform = new Transform(body);

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
		return transform.getBytes();
	}

	/**
	 *** To set Image origin
	 * @param data.origin
	 *** 
	 */
	public void setPositionVector(float a,float b, float c) {
		float [] o = {a,b,c};
		setPositionVector(o);
	}

	/**
	 *** To set Image origin
	 * @param o
	 *** 
	 */
	public void setPositionVector(float[] o) {
		transform.setTranslation(o);
	}

	/**
	 *** To get Image origin
	 *** 
	 * @return the origin bytes array
	 */
	public float[] getPositionVector() {
		return transform.getTranslation();
	}

	/**
	 *** To set Image normals
	 * @param normals
	 *** 
	 */
	public void setRotationMatrix(float[][] normals) {
		transform.setRotation(normals);
	}

	/**
	 *** To set Image normals
	 * @param column1 array
	 * @param column2 array
	 * @param column3 array
	 *** 
	 */
	public void setRotationMatrix(float column1[], float column2[], float column3[]) {
		transform.setRotation(column1, column2, column3);
	}

	/**
	 *** To get Image normals
	 *** 
	 * @return the normals matrix
	 */
	public float[][] getRotationMatrixArray() {
		return transform.getRotation();
	}
	
	/**
	 *** To get Image matrix
	 *** 
	 * @return the image matrix
	 */
	public float[][] getTransformMatrix() {
		return transform.getTransformMatrix();
	}
	
	@Override
	public String toString() {
		String retVal = "TRANSFORM Device Name : " + getDeviceName();
		if (transform != null)
			retVal += transform.toString();
		else
			retVal += "TRANSFORM is null";
		return retVal;
	}

	/**
	 * Convenience method to print an matrix array
	 */
	public void printDoubleDataArray(float[][] matrixArray) {
		for (int i = 0; i < matrixArray.length; i++) {
			for (int j = 0; j < matrixArray[0].length; j++) {
				System.out.print(matrixArray[i][j] + " ");
			}
			log.debug("\n");
		}
	}
}

