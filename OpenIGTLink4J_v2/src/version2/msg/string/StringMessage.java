/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.string;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import util.BytesArray;
import util.Header;

import msg.OIGTL_DataMessage;

/**
 *** This class is used to create an {@link StringMessage} object from received  bytes
 * or help to generate bytes to send from it
 * 
 * @author Andreas Rothfuss
 * 
 */
public class StringMessage extends OIGTL_DataMessage{

    /** The messages data type */
	public static final String DATA_TYPE = "STRING";

	/** Character encoding type as MIBenum value. Default=3 (US-ASCII). */
	short encoding=3;
	/** Length of string (bytes) */
	short length;
	/** Byte array of the string */
	String message;

    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName	Device Name
     **/
	public StringMessage(String deviceName){
		super(DATA_TYPE, deviceName);
	}

    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName	Device Name
     * @param msg 			The string to be sent
     **/
	public StringMessage(String deviceName, String msg){
		super(DATA_TYPE, deviceName, msg.getBytes().length);
		setMessage(msg);
	}

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public StringMessage(Header head, byte[] body){
		super(head, body);
	}

	/**
	 * To set the message to be sent
	 */
	public void setMessage(String msg){
		this.message = msg;
		this.length = (short)msg.length();
	}
	
	@Override
	public boolean UnpackBody(byte[] body) {
		encoding  = ByteBuffer.wrap(body, 0,2).getShort();
		length = ByteBuffer.wrap(body, 2,2).getShort();
		//TODO always US-ASCII may be need to update if required
		try {
			message = new String(body, 4, body.length-4, "US-ASCII");
		} catch (UnsupportedEncodingException e) {}
		message = message.trim();
		return true;
	}

	@Override
	public byte[] getBody() {
		BytesArray body = new BytesArray();
		body.putShort(encoding);
		body.putShort(length);
		body.putString(message);
		return body.getBytes();
	}

	@Override
	public String toString() {
        String statusString = "STRING Device Name           : " + getDeviceName();
        statusString = statusString + " Encoding      : " + getEncoding() + "\n";
        statusString = statusString + " Length   : " + getLength() + "\n";
        statusString = statusString + " Message: " + getMessage() + "\n";
        statusString = statusString + "============================\n";
        return statusString;
	}
	
	/**
	 * @return Character encoding type as MIBenum value
	 */
	public short getEncoding(){
		return encoding;
	}
	
	/**
	 * @return Length of string (bytes)
	 */
	public short getLength(){
		return length;
	}

	/**
	 * @return the message string 
	 */
	public String getMessage(){
		return message;
	}
}
