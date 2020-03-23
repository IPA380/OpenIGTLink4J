/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Absynt Technologies Ltd, Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.status;

import msg.OIGTL_DataMessage;
import msg.status.Status.STATUS;
import util.BytesArray;
import util.Header;

/**
 *** This class create an Status object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andre Charles Legendre
 * @author Andreas Rothfuss
 * 
 */
public class StatusMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "STATUS";
    /** Size of the status header */
    public static final int HEADER_SIZE = 30;
    /** Maximum length of the error name */
    public static final int ERROR_NAME_LENGTH = 20;
    /** The status object */
    private Status status;

    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName	Device Name
     **/
    public StatusMessage(String deviceName) {
            super(DATA_TYPE, deviceName, 0);
    }

    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName 	Device Name
     * @param status 		the {@link Status}
     **/
    public StatusMessage(String deviceName, Status status) {
            this(deviceName);
            setStatus(status);
    }
  
    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName 	Device Name
     * @param code 				
     * 		the status code as defined in 
     * 		https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/status.md
     * @param subCode
     *   	the status code - not defined specifically
     * @param errorName
     * 		freely definable error name, up to 20 characters
     * @param status
     * 		optional (English) description
     **/
    public StatusMessage(String deviceName, STATUS code, int subCode, 
    		String errorName, String status) {
            this(deviceName);
            setStatus(new Status(code,subCode, errorName, status));
    }
  
    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName 	Device Name
     * @param code 				
     * 		the status code as defined in 
     * 		https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/status.md
     * @param subCode
     *   	the status code - not defined specifically
     * @param errorName
     * 		freely definable error name, up to 20 characters
     * @param status
     * 		optional (English) description
     **/
    public StatusMessage(String deviceName, int code, int subCode, 
    		String errorName, String status) {
            this(deviceName);
            setStatus(new Status(code,subCode, errorName, status));
    }
    
    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public StatusMessage(Header header, byte body[]) {
            super(header, body);
    }

    @Override
    public boolean UnpackBody(byte[] body) {
    	BytesArray bytesArray = new BytesArray();
        bytesArray.putBytes(body);
        /* Unsigned short 16bits */
        int code = (int) bytesArray.getLong(2); 
        /* int 64 */
        long subCode = bytesArray.getLong(8); 
        /* char 20 */
        String errorName = bytesArray.getString(ERROR_NAME_LENGTH); 
        String statusString = bytesArray.getString(body.length-HEADER_SIZE-1);
        this.status = new Status(code, subCode, errorName, statusString);
        return true;
    }

    @Override
    public byte[] getBody() {
    	BytesArray bytesArray = new BytesArray();
        bytesArray.putULong(status.getCode(), 2);
        bytesArray.putLong(status.getSubCode(), 8);
        bytesArray.putString(status.getErrorName(), 20);
        bytesArray.putString(status.getStatusString());
        
        return bytesArray.getBytes();
    }

    /**
     *** To set client or server code
     * @param code
     *** 
     */
    public void setCode(int code) {
        this.status.setCode(code);
    }

    /**
     *** To get client or server code
     *** 
     * @return the status code
     */
    public long getCode() {
        return this.status.getCode();
    }

    /**
     *** To set client or server status
     * @param status
     *** 
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     *** To get client or server status
     *** 
     * @return the status code
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     *** To set Image subCode
     * @param subCode
     *** 
     */
    void setSubCode(long subCode) {
        status.setSubCode(subCode);
    }

    /**
     *** To get Image subCode
     *** 
     * @return the subCode array
     */
    public long getSubCode() {
        return status.getSubCode();
    }

    /**
     *** To set Error Name
     * @param errorName
     *** 
     */
    void setErrorName(String errorName) {
        status.setErrorName(errorName);
    }

    /**
     *** To get errorName subCode
     *** 
     * @return the errorName
     */
    public String getErrorName() {
        return status.getErrorName();
    }

    /**
     *** To set Status String
     * @param statusString
     *** 
     */
    void setStatusString(String statusString) {
    	status.setStatusString(statusString);
    }

    /**
     *** To get statusString subCode
     *** 
     * @return the statusString
     */
    public String getStatusString() {
        return status.getStatusString();
    }

    @Override
    public String toString() {
            String statusString = "STATUS Device Name           : " + getDeviceName();
            if (status != null) { 
	            statusString = statusString + " Code      : " + getCode() + "\n";
	            statusString = statusString + " SubCode   : " + getSubCode() + "\n";
	            statusString = statusString + " Error Name: " + getErrorName() + "\n";
	            statusString = statusString + " Status    : " + getStatusString() + "\n";
	            statusString = statusString + "============================\n";
            }
            else 
            	statusString += " Status is null";
            return statusString;
    }
}

