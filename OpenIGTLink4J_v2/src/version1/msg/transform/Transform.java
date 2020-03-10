/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.BytesArray;

/**
 * Class representing a {@link Transform} as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/transform.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class Transform {

	/** Size of the serialized form of a {@link Transform} in bytes */
	public static int IGTL_TRANSFORM_SIZE = 48;
	/** Position of the origin */
	public float[] origin;
	/** the rotation matrix*/
	public float[][] rotationMatrix;
	private Logger log;

	/**
	 * Constructor to create a fully specified {@link Transform}
	 * 
	 * @param inOrigin
	 * 		the origin of the {@link Transform}
	 * @param rotationMatrix2
	 * 		the rotation matrix
	 */
	public Transform(float[] inOrigin, float[][] rotationMatrix2) {
		this.origin = inOrigin;
		this.rotationMatrix = rotationMatrix2; 
		log = LoggerFactory.getLogger(this.getClass());
	};

	/**
	 * Constructor to create a neutral {@link Transform}
	 */
	public Transform() {
		this(new float[]{0,0,0}, 
				new float[][]{{1,0,0},{0,1,0},{0,0,1}});
	}

	/**
	 * Constructor to de-serialize a {@link Transform} from bytes
	 * 
	 * @param bytes
	 * 		the serialized form of the {@link Transform}
	 */
	public Transform(byte[] bytes) {
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(bytes);

		// the 3x3 rotation matrix
		rotationMatrix = new float[3][3];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				rotationMatrix[j][i] = (float) bytesArray.getDouble(4); // float32
			}      
		}
		        
		origin = new float[3];
		for(int i=0;i<3;i++){
			origin[i] = (float) bytesArray.getDouble(4); // float32
		}
		log = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * To serialize this
	 * @return
	 */
	public byte[] getBytes() {
		BytesArray bytesArray = new BytesArray();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				bytesArray.putDouble(rotationMatrix[j][i], 4);
		bytesArray.putDouble(origin[0], 4);
		bytesArray.putDouble(origin[1], 4);
		bytesArray.putDouble(origin[2], 4);
		
		return bytesArray.getBytes();
	}

	/**
	 * To set the origin
	 * @param origin
	 */
	public void setTranslation(float a,float b, float c) {
		float [] o = {a,b,c};
		setTranslation(o);
	}

	/**
	 * To set the origin
	 * @param o accepts only float arrays with 3 elements
	 *** 
	 */
	public void setTranslation(float o[]) {
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
	 * @return the origin
	 */
	public float[] getTranslation() {
		return origin;
	}
	
	/**
	 *** To set normals
	 * @param normals accepts only float arrays with 3 by 3 elements
	 *** 
	 */
	public void setRotation(float normals[][]) {
		if(normals.length != 3  || normals[0].length != 3 || normals[1].length != 3 || normals[2].length != 3)
			log.debug("Rotation matrix of incorrect dimensions. desired dimensions = 3x3;");
		else {
			rotationMatrix = new float[3][3];
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					this.rotationMatrix[i][j] = normals[i][j];
				}      
			}
		}
	}

	/**
	 *** To set normals
	 * @param column1 accepts only float arrays with 3 elements
	 * @param column2 accepts only float arrays with 3 elements
	 * @param column3 accepts only float arrays with 3 elements
	 *** 
	 */
	public void setRotation(float column1[], float column2[], float column3[]) {
		rotationMatrix = new float[3][3];
//		this.t = column1;
//		this.s = column2;
//		this.n = column3;
		
		rotationMatrix[0][0] = column1[0];
		rotationMatrix[1][0] = column1[1];
		rotationMatrix[2][0] = column1[2];

		rotationMatrix[0][1] = column2[0];
		rotationMatrix[1][1] = column2[1];
		rotationMatrix[2][1] = column2[2];

		rotationMatrix[0][2] = column3[0];
		rotationMatrix[1][2] = column3[1];
		rotationMatrix[2][2] = column3[2];
	}

	/**
	 *** To get normals
	 *** 
	 * @return the normals matrix
	 */
	public float[][] getRotation() {
		return rotationMatrix;
	}
	
	/**
	 *** To get the transformation matrix
	 *** 
	 * @return the image matrix
	 */
	public float[][] getTransformMatrix() {
		float[][] retVal = new float[4][4];
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				retVal[i][j] = rotationMatrix[i][j];
			}
		}
		
		for (int i = 0; i < 3; i++) {
			retVal[i][3] = origin[i];
		}
		
		retVal[3][3] = 1;
		
		return retVal;
	}
	
	/**
	 *** To get transform String
	 *** 
	 * @return the transform String
	 */
	@Override
	public String toString() {
		String transformString = "TRANSFORM: [";
		float[][] mat = getTransformMatrix();
		for (int i = 0; i <= 3; i++) {
			if (i > 0)
				transformString += ";";
			for (int j = 0; j <= 3; j++)
				transformString = " " + transformString.concat(Double.toString(mat[i][j]));
		}
		transformString += "; 0.0 0.0 0.0 1.0]";
		return transformString;
	}
}