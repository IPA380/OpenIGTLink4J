/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Absynt Technologies Ltd, Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.position;

import msg.OIGTL_DataMessage;
import util.Header;


/**
 *** This class create an Position object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andre Charles Legendre
 * 
 */
public class PositionMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "POSITION";
    /** The former messages data type */
	public static final String OLD_DATA_TYPE = "QTRANS";

	/** Transform as {@link QuaternionTransform} */
    QuaternionTransform transform;
    
    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     * @param ds 
     * @param RotationNR 
     **/
    public PositionMessage(String deviceName, float[] position, float[] quaternion) {
    	this(deviceName, position, quaternion, false);
    }
    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     * @param ds 
     * @param RotationNR 
     **/
    public PositionMessage(String deviceName, float[] position, float[] quaternion, boolean legacyDataType) {
            super(legacyDataType ? OLD_DATA_TYPE : DATA_TYPE, deviceName, 0);     
            transform = new QuaternionTransform(position, quaternion);     		
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public PositionMessage(Header header, byte body[]){
            super(header, body);
    }

    @Override
    public boolean UnpackBody(byte[] body) {
    	transform = new QuaternionTransform(body);
    	
        return true;
    }

    @Override
    public byte[] getBody() {	
    	return transform.getBytes();
    }
    
    /**
     *** To set Image position
     * @param position
     *** 
     */
    public void setPosition(float[] position) {
            this.transform.setPositionVector(position);
    }

    /**
     *** To set Image position
     * @param x
     * @param y
     * @param z
     *** 
     */
    public void setPosition(float x, float y, float z) {
            this.setPosition(new float[] {x, y, z});
    }

    /**
     *** To get Image position
     *** 
     * @return the position bytes array
     */
    public float[] getPosition() {
    	return transform.getPositionVector();
    }

    /**
     *** To set Image quaternion
     * @param quaternion
     *** 
     */
    void setQuaternion(float[]  quaternion) {
    	transform.setQuaternion(quaternion);
    }

    /**
     *** To set Image quaternion
     * @param ox
     * @param oy
     * @param oz
     * @param w
     *** 
     */
    void setQuaternion(float ox, float oy, float oz, float w) {
    	setQuaternion(new float[] {ox, oy, oz, w});
    }

    /**
     *** To get Image quaternion
     *** 
     * @return the quaternion array
     */
    public float[] getQuaternion() {
    	return transform.getRotation();
    }

    @Override
    public String toString() {
            String positionString = "POSITION Device Name : " + this.getDeviceName();
            if (transform != null) 
            	positionString += transform.toString();
            else
            	positionString += " Position is null";
            return positionString;
    }
}

