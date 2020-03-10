/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import msg.transform.Transform;
import util.BytesArray;

/**
 *** This class create an {@link QuaternionTransform} object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andreas Rothfuss
 * 
 */
public class QuaternionTransform {

	/** 
	 * Size of the serialized form of a {@link QuaternionTransform} 
	 * in bytes 
	 **/
	public static int SIZE = 48;
	/** Position of the origin */
	public float[] origin;
	/** the rotation quaternion*/
	public float[] quaternion;
	private Logger log;

	/**
	 * Constructor to create a fully specified {@link QuaternionTransform}
	 * 
	 * @param inOrigin
	 * 		the origin of the {@link Transform}
	 * @param rotationMatrix2
	 * 		the rotation quaternion
	 */
	public QuaternionTransform(float origin[], float quaternion[]) {
		this.origin = origin;
		this.quaternion = quaternion; 
		log = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Constructor to create a neutral {@link QuaternionTransform}
	 */
	public QuaternionTransform() {
		this(new float[]{0,0,0}, 
				new float[]{1,0,0,0});
	}

	/**
	 * Constructor to de-serialize a {@link QuaternionTransform} from bytes
	 * 
	 * @param bytes
	 * 		the serialized form of the {@link QuaternionTransform}
	 */
	public QuaternionTransform(byte[] bytes) {
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(bytes);

    	origin = new float[3];
    	for (int i = 0; i < origin.length; i++) {
    		origin[i] = (float) bytesArray.getDouble(4);			
		}
    	quaternion = new float[4];
    	for (int i = 0; i < quaternion.length; i++) {
    		quaternion[i] = (float) bytesArray.getDouble(4);			
		}
		log = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * To serialize this
	 * @return
	 */
	public byte[] getBytes() {
		BytesArray bytesArray = new BytesArray();
    	
    	for (int i = 0; i < origin.length; i++) {
    		bytesArray.putDouble(origin[i], 4);
    	}
    	for (int i = 0; i < quaternion.length; i++) {
    		bytesArray.putDouble(quaternion[i], 4);		
		}

		
		return bytesArray.getBytes();
	}

	/**
	 *** To set the origin
	 * @param data.origin
	 *** 
	 */
	public void setPositionVector(float a,float b, float c) {
		float [] o = {a,b,c};
		setPositionVector(o);
	}

	/**
	 *** To set the origin
	 * @param o accepts only float arrays with 3 elements
	 *** 
	 */
	public void setPositionVector(float o[]) {
		if(o.length != 3)
			log.debug("Origin vector length is incorrect. desired value = 3; actual value: " + o.length);
		else {
			origin = new float[3];
			for(int i=0;i<origin.length;i++){
				origin[i] = o[i];
			}
		}
	}

	/**
	 *** To get the origin
	 */
	public float[] getPositionVector() {
		return origin;
	}
	
	/**
	 *** To set normals
	 * @param normals accepts only float arrays with 4 elements
	 *** 
	 */
	public void setQuaternion(float quaternion[]) {
		if(quaternion.length != 4)
			log.debug("Origin vector length is incorrect. desired value = 3; actual value: " + quaternion.length);
		else {
			this.quaternion = new float[4];
			for(int i=0;i<quaternion.length;i++){
				this.quaternion[i] = quaternion[i];
			}
		}
	}

	/**
	 *** To get normals
	 */
	public float[] getRotation() {
		return quaternion;
	}
	
	/**
	 *** To get transform String
	 *** 
	 * @return the transform String
	 */
	@Override
	public String toString() {
		String transformString = "Quaternion transform: ";
		for (int i = 0; i <= 3; i++)
			transformString = transformString.concat(Double.toString(origin[i]));
		for (int i = 0; i <= 4; i++)
			transformString = transformString.concat(Double.toString(quaternion[i]));
		return transformString;
	}
}