/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

import org.slf4j.LoggerFactory;

/**
 * OpenIGTLink message header as defined in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/header.md
 *  
 * @author Andreas Rothfuss
 *
 */
public class Header {
	
	/** Size of the serialized form of a {@link Header} in bytes */
	public static int LENGTH = 58;
	/** Header version number*/
	protected long version;
	/** Type name of data */
	protected String dataType;
	/** Unique device name */
	protected String deviceName;
	/** Timestamp seconds part or 0 if unused */
	protected long timeStampSec;
	/** Timestamp subseconds part or 0 if unused */
	protected long timeStampSecFrac;
	/** 	Size of the body in bytes */
	protected long body_size;
	/** 64 bit CRC for body data */
	protected long crc = Long.MIN_VALUE;
	
	/**
	 *** Destination Constructor
	 *
	 * @param dataType
	 *          Type name of data char 12 bits
	 * @param deviceName
	 *          Unique device name char 20 bits
	 * @param body
	 * 			body in bytes
	 **/
	public Header(String dataType, String deviceName, long bodySize) {
	    this.dataType = dataType;
	    this.deviceName = deviceName;
	    this.timeStampSec = 0;
	    this.timeStampSecFrac = 0;
	    this.body_size = bodySize;
	}
	
	/**
	 *** Destination Constructor
	 *** 
	 * @param dataType
	 *			Type name of data char 12 bits
	 * @param deviceName
	 *          Unique device name char 20 bits
	 **/
	    public Header(String dataType, String deviceName) {
	    	this(dataType, deviceName, 0);
	    }

    /**
     *** Constructor to be used to create a header from received data
     * 
     * @param bytes		byte array representing the message header
     */
	public Header(byte[] bytes) {
	    BytesArray bytesArray = new BytesArray();
	    bytesArray.putBytes(bytes);
	    
	     /* unsigned int 16bits */
	    version = bytesArray.getLong(2);
	    /* char 12 bits */
	    dataType = bytesArray.getString(12); 
	    /* char 20 bits */
	    deviceName = bytesArray.getString(20); 
	    timeStampSec = bytesArray.getLong(4);
	    timeStampSecFrac = bytesArray.getLong(4);
	    /* unsigned int 64 bits */
	    body_size = bytesArray.getULong(8);  
	    /* unsigned int 64 bits */
	    crc = bytesArray.getULong(8);
	    
	    LoggerFactory.getLogger(this.getClass()).trace("New header: "+this, Byte.MAX_VALUE);
	}
	
	/**
	 *** Version number.
	 *** 
	 * @return The current version of the bytesArray
	 **/
	public long getVersion() {
	        return this.version;
	}

	/**
	 * To set the version of the header
	 */
	public void setVersion(long version) {
		this.version = version;
	}
	
	/**
	 *** Type name of data
	 *** 
	 * @return The type of the device
	 **/
	public String getDataType() {
	        return this.dataType;
	}
	
	/**
	 *** Unique device name.
	 *** 
	 * @return The current name of the device
	 **/
	public String getDeviceName() {
	        return this.deviceName;
	}
	
	/**
	 *** TimeStamp or 0 if unused.
	 *** 
	 * @return The time stamp at the creation of the header
	 **/
	public long getTimeStamp() {
	        return this.timeStampSec;
	}
	
	/**
	 *** TimeStamp or 0 if unused.
	 *** 
	 * @return The time stamp at the creation of the header
	 **/
	public long getTimeStamp_ms() {
		return (this.timeStampSec * 1000) + (this.timeStampSecFrac / 1000);
	}
	
	/**
	 *** TimeStampFrac or 0 if unused.
	 *** 
	 * @return The time stamp at the creation of the header
	 **/
	public long getTimeStampFrac() {
	        return this.timeStampSecFrac;
	}

	/**
	 * To set both parts of the timestamp 
	 */
	public void setTimeStamp(long seconds, long fraction) {
		this.timeStampSec = seconds;
		this.timeStampSecFrac = fraction;
	}

	/**
	 * To set both parts of the timestamp from time in
	 * milliseconds
	 */
	public void setTimeStamp_ms(long timeMillis) {
		this.timeStampSec = timeMillis / 1000;
		this.timeStampSecFrac = timeMillis % 1000;
	}
	
	/**
	 *** Size of body in bytes.
	 *** 
	 * @return The current body_size of the bytesArray
	 **/
	public long getBodySize() {
	        return this.body_size;
	}
	
	/**
	 *** Size of body in bytes.
	 *** 
	 * @parma The current body_size of the bytesArray
	 **/
	public void setBodySize(long body_size) {
		this.body_size = body_size;
	}
	
	/**
	 *** 64 bit CRC for body data.
	 *** 
	 * @return The current crc of the bytesArray
	 **/
	public long getCrc() {
		return this.crc;
	}
	
	/**
	 * @param 64 bit CRC for body data.
	 *** 
	 **/
	public void setCrc(long crc) {
		this.crc = crc;
	}
	
	/**
	 *** this header bytes.
	 *** 
	 * @return A copy of the byte array currently in the bytesArray (as-is)
	 **/
	public byte[] getBytes() {
		if (version == 0) {
			throw new IllegalAccessError("Version has not been set");
		}
		if (crc == Long.MIN_VALUE && body_size > 0) {
			throw new IllegalAccessError("CRC has not been set");
		}
		BytesArray bytesArray = new BytesArray();
		bytesArray.putLong(version, 2);
		bytesArray.putString(dataType, 12);
		bytesArray.putString(deviceName, 20);
		bytesArray.putULong(Math.max(timeStampSec, 0), 4);
		bytesArray.putULong(Math.max(timeStampSecFrac, 0), 4);
		bytesArray.putULong(body_size, 8);
		bytesArray.putULong(crc, 8);
		return bytesArray.getBytes();
	}
	
	@Override
	public String toString() {
		String s="";
		s+="Version: "+getVersion();
		s+=" Type: "+ getDataType();
		s+=" Name: "+ getDeviceName();
		s+=" Timestamp: "+getTimeStamp();
		s+=" Body Size:"+getBodySize();
		s+=" CRC: "+getCrc();
		return s;
	}
}