/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

import msg.OIGTL_DataMessage;
import util.Header;

/**
 * Class representing a {@link SensorMessage} as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/sensordata.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class SensorMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "SENSOR";

	/** Field representing the seonsor data */
	SensorData sensorData;

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of message
	 * */
	protected SensorMessage(String messageType, String deviceName){
		super(messageType, deviceName, 0); 
		setSensorData(new SensorData());
	}
	
    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of message
	 * */
	protected SensorMessage(String messageType, String deviceName, SensorData data){
		super(messageType, deviceName, 0); 
		setSensorData(data);
	}

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of message
	 * */
	public SensorMessage(String deviceName){
		super(DATA_TYPE, deviceName, 0); 
		setSensorData(new SensorData());
	}
	
    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of message
	 * */
	public SensorMessage(String deviceName, SensorData data){
		super(DATA_TYPE, deviceName, 0); 
		setSensorData(data);
	}

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public SensorMessage(Header header, byte[] body){
		super(header, body);
	}

	/**
	 * To set the length of the sensor data array and 
	 * initialize a new sensor data array with length length
	 * 
	 * @param length the new length
	 */
	@Deprecated //Andreas Rothfuss, 2019-05-09
	public void setLength(short n){
		if (n <= 256){
			sensorData.setLength(n);
	    }
		else	
			throw new IllegalArgumentException("New lenght must be smaller than"
				+ "256");
	}

	/**
	 * To get the length of the sensor data array
	 * 
	 * @return the length
	 */
	public short getLength(){
		return sensorData.getLength();
	}

	/**
	 * To set the unit of the {@link SensorData}
	 * 
	 * @param unit the new unit
	 */
	public void setUnit(Unit unit){
		this.sensorData.setUnit(unit);
	}

	/**
	 * To get the Unit of the sensor data array
	 * 
	 * @return the unit
	 */
	public Unit getUnit(){
		return sensorData.getUnit();
	}

	/**
	 * Sets sensor values from an array of 64-bit floating data
	 * 
	 * @param data the new sensor data
	 */
	public void setValue(double[] data){
		setLength((short) data.length);
		for (int i = 0; i < this.sensorData.getLength(); i ++){
			this.sensorData.getArray()[i] = data[i];
	    }
	}


	/**
	 * Sets the value for the i-th sensor value
	 * 
	 * @param i the index of the sesnor value to set
	 * @param data the new sensor value
	 */
	public void setValue(int i, double value){
		if (i >= 0 && i < sensorData.getLength()) {
			sensorData.getArray()[i] = value;
		}
	}

	/**
	 * To get the status of the sensor
	 * 
	 * @return the status
	 */
	public short getSensorStatus(){
		return sensorData.getStatus();
	}

	/**
	 * To set the status of the {@link SensorData}
	 * 
	 * @param status the new status
	 */
	public void setSensorStatus(short status) {
		sensorData.setStatus(status);
	}

	/**
	 * To set the {@link SensorData}
	 * 
	 * @param the new sensor data
	 */
	public void setSensorData(SensorData data) {
//		this.setLength((short)data.getLength());
		this.sensorData = data;
	}

	/**
	 * To get the {@link SensorData}
	 * 
	 * @return the sensor data
	 */
	public SensorData getSensorData() {
		return sensorData;
	}

	/**
	 * To get the sensor data values
	 * 
	 * @return the sensor data values
	 */
	public double[] getSensorValues() {
		return sensorData.getArray();
	}

	/**
	 * To get the i-th sensor data value
	 * 
	 * @param i the index of the sensor value to get
	 * @return the i-th sensor data value
	 */
	public double getSensorValue( int i){
		double[] sensorValues = getSensorValues();
		if (i >= 0 && i < sensorValues.length){
			return sensorData.getArray()[i];
		}
		else{
			return 0.0;
		}
	}
	
	@Override
	public byte[] getBody(){
		byte[] bytes = sensorData.getBytes();
		return bytes;
	}
	
	@Override
	protected boolean UnpackBody(byte[] body){
		sensorData = SensorData.fromBytes(body);
		
		return true;
	}
	
	@Override
	public String toString() {
		if (sensorData != null)
			return sensorData.toString();
		else
			return "Empty sensor message";
	}
}
