/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.ndarray;

/*=========================================================================

Program:   OpenIGTLink Library
Language:  java
Date:      $Date: 2014-17-06 17:31 PM EST
Version:   $Revision: 0$

Copyright (c) AIMLab, Worcester Polytechnic Institute

This software is distributed WITHOUT ANY WARRANTY; without even
the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE.  See the above copyright notices for more information.

Author: Nirav Patel: napatel@wpi.edu
=========================================================================*/

import java.nio.ByteBuffer;
import java.util.ArrayList;
import util.BytesArray;
import util.Header;
//import com.neuronrobotics.sdk.common.ByteList;

import msg.OIGTL_DataMessage;

/**
*** This class create an NDArray object from bytes received or help to generate
* bytes to send from it
* 
* @author Nirav Patel
* 
*/
public class NDArrayMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "NDARRAY";
	
	private byte type;
	private byte dim;
	private short size[]; 
	private byte byteData[];
	@SuppressWarnings("rawtypes")
	private ArrayList data;
	
	@SuppressWarnings("unused")
	private static byte TYPE_INT8 = 2 , 
			TYPE_UINT8=3,
		    TYPE_INT16=4,
		    TYPE_UINT16=5,
		    TYPE_INT32=6,
		    TYPE_UINT32=7,
		    TYPE_FLOAT32=10,
		    TYPE_FLOAT64=11,
		    TYPE_COMPLEX=13 ;
	
	  /**
	   *** Constructor to be used to create message to send them with this
	   * constructor you must use method SetImageHeader, then CreateBody and then
	   * getBytes to send them
	   *** 
	   * @param deviceName
	   *            Device Name
	   **/
	  public NDArrayMessage(String deviceName) {
	          super(DATA_TYPE, deviceName, 0);
	  }
	

	  /**
	  *** Constructor to be used to create message from received data
	   * 
	   * @param header	header object
	   * @param body		byte array representing the message body
	   */
	  public NDArrayMessage(Header header, byte body[]) {
	      super(header, body);
	  }

	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method SetImageHeader, then CreateBody and then
	 * getBytes to send them
	 **/
	public NDArrayMessage(String deviceName,  float[] data) {
	  super(DATA_TYPE, deviceName);
	  //This implements 1D Float array
	  
	  set1D_FloatData(data);
	}

//	/**
//	 *** Constructor to be used to create message to send them with this
//	 * constructor you must use method SetImageHeader, then CreateBody and then
//	 * getBytes to send them
//	 **/
//	public NDArrayMessage(String deviceName, byte type, byte dim, short[] size, float[] data) {
//		super(DATA_TYPE, deviceName);
//	  	//TODO: THIS IS FOR NDimensional Array Implementation and its not implemented yet
//	}

	/**
	 * Method to set one dimensional float data
	 * 
	 * @param data	the data to be set
	 */
	@SuppressWarnings("unchecked")
	public void set1D_FloatData( float[] data){
		  this.type = TYPE_FLOAT32;  
		  this.dim = 1;
		  this.size = new short[dim]; 
		  this.size[0] = (short)data.length;
		  
		  this.data = new ArrayList<Float>();
		  
		  BytesArray bytesArray = new BytesArray();
	      for (int i = 0; i < data.length; i++){
	    	  bytesArray.putDouble(data[i], 4);
	    	  this.data.add(data[i]);
	      }
	      this.byteData = bytesArray.getBytes();
	      
	  }

	/**
	 * Method to initialize an empty message
	 */
	@SuppressWarnings("unchecked")
	public void set1D_FloatData(){
		this.data = new ArrayList<Float>();
		  
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(byteData);
	    for (int i = 0; i < byteData.length/4; i++){
	    	  float val = (float)bytesArray.getDouble(4);
	    	  this.data.add(val);
	    }
	}
	
	
	/**
	 * Method to get one dimensional float data
	 */
	public float[] get1DFloatData(){
		float floatData[] = new float[data.size()];
		for(int i=0;i<data.size();i++){
			floatData[i] = ((Float)data.get(i)).floatValue();
		}
		return floatData;
	}

	  @Override
	  public boolean UnpackBody(byte[] body) {
		  ByteBuffer bodyBuffer = ByteBuffer.wrap(getBody());
		  
		  type  = bodyBuffer.get();
		  dim = bodyBuffer.get();
		  size = new short[dim];
		  for( int i=0;i<dim;i++){
			  size[i] = bodyBuffer.getShort();
		  }
		  int dataLen = getBody().length-(2+2*dim);
		  byteData = new byte[dataLen];
		  bodyBuffer.get(byteData, 0 , dataLen-1);
		  if( type == TYPE_FLOAT32 ){
			  set1D_FloatData();
		  }
	  	  return true;
	  }
	
	  @Override
	  public byte[] getBody() {
		BytesArray body = new BytesArray();
		body.putByte(this.type);
		body.putByte(this.dim);
		for(int i=0;i<this.size.length;i++){
			body.putShort(this.size[i]);
		}
		body.putBytes(this.byteData);
		return body.getBytes();
	  }
	
	
	  @Override
	  public String toString() {
	          String transformString = "NDArray Device Name           : " + getDeviceName();
	  transformString = transformString + "NDArray Type           : " + type + " ";
	  transformString = transformString + "NDArray Dim           : " + dim + " ";
	  for(int i=0;i<dim;i++){
	      transformString = transformString + "Dim[" + i + "]=" + size[i] + " ";
	  }
	  for(int i=0;i<data.size();i++){
	      transformString = transformString + "Data[" + i + "]=" + data.get(i) + " ";
	          }
	          return transformString;
	  }
      
}

