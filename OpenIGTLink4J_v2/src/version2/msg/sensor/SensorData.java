/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

import java.util.Arrays;

import util.BytesArray;

/**
 * Class representing all data included in a sensor messages as specified in
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/sensordata.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class SensorData {

	protected static final int maxArrayLength = 255;
	
	/** Sensor status */
	short status;
	/** {@link Unit} of the sensor data*/
	Unit unit = new Unit();
	/** Sensor data array (0-255)*/
	double[] array;

	/**
	 * Constructor to create an empty {@link SensorData} object
	 */
	public SensorData() {
	}

	/**
	 * To get the length of the sensor data array
	 * 
	 * @return the length
	 */
	public short getLength() {
		return (short) array.length;
	}

	/**
	 * To set the length of the sensor data array and 
	 * initialize a new sensor data array with length length
	 * 
	 * @param length the new length
	 */
	@Deprecated /* Andreas Rothfuss, 2019-05-09 */
	public void setLength(short length) {
		if (length > 255)
			throw new IllegalArgumentException("Data set can not be larger than 255 entries");
		array = new double[length];
	}
	
	/**
	 * To check the length of the sensor data array and 
	 * initialize a new sensor data array with length length
	 * 
	 * @param length the new length
	 */
	protected void checkLength(short length) {
		if (length > maxArrayLength)
			throw new IllegalArgumentException("Data set can not be larger than 255 entries");
	}

	/**
	 * To get the status of the sensor
	 * 
	 * @return the status
	 */
	public short getStatus() {
		return status;
	}

	/**
	 * To set the status of the {@link SensorData}
	 * 
	 * @param status the new status
	 */
	public void setStatus(short status) {
		this.status = status;
	}

	/**
	 * To get the Unit of the sensor data array
	 * 
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * To set the unit of the {@link SensorData}
	 * 
	 * @param unit the new unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * To get the sensor data array
	 * 
	 * @return the sensor data array
	 */
	public double[] getArray() {
		return array;
	}

	/**
	 * To set the sensor data of the {@link SensorData}
	 * 
	 * @param unit the sensor data
	 */
	public void setArray(double[] array) {
		this.checkLength((short) array.length);
		this.array = array;
	}

	/**
	 * To get the serialized form of the {@link SensorData}
	 * 
	 * @return the serialized form
	 */
	public byte[] getBytes(){
		BytesArray bytesArray = new BytesArray();
		
		bytesArray.putByte((byte)getLength());
		serializeStatusUnitAndArray(bytesArray);
		return bytesArray.getBytes();
	}

	protected void serializeStatusUnitAndArray(BytesArray bytesArray) {
		bytesArray.putByte((byte)getStatus());
		bytesArray.putBytes(getUnit().converToBytes());

		for (int i = 0; i < this.getLength(); i ++)
		{
			bytesArray.putDouble(getArray()[i], 8);
		}
	}

	/**
	 * To create the {@link SensorData} from it's serialized form
	 * 
	 * @param sensorBytes the serialized form
	 */
	public static SensorData fromBytes(byte[] sensorBytes){
		SensorData retVal = new SensorData();
		
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(sensorBytes);
		
		/* Copy data */
		int arrayLength = (int) bytesArray.getULong(1);
		deserializeStatusUnitAndArray(retVal, bytesArray, arrayLength);
		
		return retVal;
	}

	public static void deserializeStatusUnitAndArray(SensorData retVal, BytesArray bytesArray, int arrayLength) {
		retVal.setStatus((short) bytesArray.getULong(1));
		retVal.setUnit(Unit.fromBytes(bytesArray.getBytes(Unit.BYTE_SIZE)));

		double[] array = new double[arrayLength];
		for (int i = 0; i < array.length; i ++){
			array[i] = bytesArray.getDouble(8);
	    }
		retVal.setArray(array);
	}

	@Override
	public String toString(){
		String retVal = "";
		retVal = retVal + "SENSOR MESSAGE: ";
		retVal += Arrays.toString(array);
		if (unit != null) {
			retVal += " unit: " + unit.toString();
		}
		
		return retVal;
	}
}