/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.BytesArray;
import util.ExtendedHeader;
import util.Header;
import util.MetaData;

/**
 * Class representing an OpenIGTLink message
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class OpenIGTMessage{

	/**
	 * Object containing the {@link Header} of the {@link OpenIGTMessage}
	 */
	protected Header header;
	/**
	 * Object containing the {@link ExtendedHeader} of the {@link OpenIGTMessage}
	 */
	protected ExtendedHeader extendedHeader = null;
	/**
	 * Object containing the {@link MetaData} of the {@link OpenIGTMessage}
	 */
	protected MetaData metaData = null;
	protected Logger log;
	
    /**
     * Constructor to be used to create message to getBytes to send them
     * 
     * @param messageType
     * 		The message data type
     * @param deviceName
     * 		The message device name
     * @param body_size
     * 		The message body size
     **/
    public OpenIGTMessage(String messageType, String deviceName, long body_size) {
		log = LoggerFactory.getLogger(this.getClass());
        this.header = new Header(messageType, deviceName, body_size);
    }

    /**
     *** Constructor to be used to build messages from incoming bytes
     *** 
     * @param header
     *            header of this message
     * @param body
     *            bytes array containing message body
     **/
    public OpenIGTMessage(Header header, byte[] body) {
		log = LoggerFactory.getLogger(this.getClass());
        this.header = header;

        if (body != null && body.length > 0) {
        	/* check CRC*/
        	long actualCRC = header.getCrc();
        	long expectedCRC = BytesArray.crc64(body, body.length, 0L);
            if (actualCRC != expectedCRC) {
            	String errorString = "Crc control fail during unpacking CRC is: " + 
            			actualCRC + " calculated: " + expectedCRC;
            	log.warn(errorString);
            	/* throw new IllegalArgumentException(errorString); */
            }
	        
	        byte[] bodyBytes;
	        
	        /* get body bytes*/
	        if (header.getVersion() == 2L) {
	        	/* Header version 2
	        	 * split up body into extended header, body and metaData */
	
	            /* set the extended header */
	            byte[] extendedHeaderBytes = new byte[ExtendedHeader.LENGTH];
	            System.arraycopy(body, 0, extendedHeaderBytes, 0, extendedHeaderBytes.length);
	            extendedHeader = new ExtendedHeader(extendedHeaderBytes);
	            
	            /* set the body */
	            int bodyLength = (int) (body.length - extendedHeaderBytes.length - 
	            		extendedHeader.getMetaDataHeaderSize() - extendedHeader.getMetaDataSize());
	            bodyBytes = new byte[bodyLength];
	            System.arraycopy(body, extendedHeaderBytes.length, bodyBytes, 0, bodyBytes.length);
	            UnpackBody(bodyBytes);
	            
	            /* set the meta data */
	            byte[] metaDataBytes = new byte[(int) (extendedHeader.getMetaDataHeaderSize() + extendedHeader.getMetaDataSize())];
	            System.arraycopy(body, extendedHeaderBytes.length + bodyBytes.length, metaDataBytes, 0, metaDataBytes.length);
	            metaData = new MetaData(metaDataBytes);
	        }
	        else {
	        	/* Header version 1
	        	 * bodyBytes equals body*/
	        	bodyBytes = body;
		        UnpackBody(bodyBytes);
	        }
	        
        }
    }

	/**
	 *** To create body from body byte array
	 * 
	 * @return byte [] representing the message body
	 * 
	 * @throws IllegalAccessException if the method is accessed while 
	 * the body is not ready to be a packed or the method is not 
	 * implemented for the class
	 */
	public abstract byte[] getBody();

	/**
	 * generate the serialized representation of the {@link OpenIGTMessage}
	 * 
	 * @return
	 * 		serialized message
	 */
	public byte[] getBytes() {
		BytesArray bytesArray = new BytesArray();
        
		/* figure out the message version and serialize meta data, extended header and body*/
        if (extendedHeader == null && metaData == null) {
        	header.setVersion(1L);
        	bytesArray.putBytes(getBody());
        }
        else {
        	header.setVersion(2L);
        	byte[] metaDataBytes = metaData.getBytes();
        	extendedHeader.setMetaDataHeaderSize(metaData.getMetaDataHeaderSize());
        	extendedHeader.setMetaDataSize(metaDataBytes.length - metaData.getMetaDataHeaderSize());
        	bytesArray.putBytes(extendedHeader.getBytes());
        	bytesArray.putBytes(getBody());
        	bytesArray.putBytes(metaDataBytes);
        }
        byte[] bodyBytes = bytesArray.getBytes();
        
        /* set body size and CRC in header*/
        header.setBodySize(bodyBytes.length);
        header.setCrc(BytesArray.crc64(bodyBytes, bodyBytes.length, 0L));
        
        /* if the headers timestamp has not been set, set it to current system time */
        if (getHeader().getTimeStamp() == 0 && getHeader().getTimeStampFrac() == 0){
        	getHeader().setTimeStamp_ms(System.currentTimeMillis());
        }
        
        /* serialize header and build message byte array*/
        byte[] header_bytes = getHeader().getBytes();
        byte[] bytes = new byte[header_bytes.length +
                                bodyBytes.length];
        if(bytes.length > 1452) {
        	log.trace("Sending a message with a payload data frame bigger than 1452 byte. "
        			+ "This may cause transmission errors when using Ethernet.");
        }
        
        System.arraycopy(header_bytes, 0, bytes, 0, Header.LENGTH);
        System.arraycopy(bodyBytes, 0, bytes, Header.LENGTH,
        		bodyBytes.length);
        return bytes;
	}


    /**
     *** To create body from body array
     * 
     *** 
     * @return true if unpacking is ok
     */
	abstract protected boolean UnpackBody(byte[] body);

	/**
	 *** Unique device name.
	 *** 
	 * @return The name of the device
	 **/
	public String getDeviceName() {
        return this.getHeader().getDeviceName();
	}
	
	/**
	 *** Message data type.
	 *** 
	 * @return The data type of the message
	 **/
	public String getDataType() {
        return this.getHeader().getDataType();
	}

	/**
	 *** header.
	 *** 
	 * @return bytes array containing the header of the message
	 **/
	public Header getHeader() {
		return header;
	}

	/**
	 * set the message header
	 * 
	 * @param header message header
	 */
	protected void setHeader(Header header) {
		this.header = header;
	}
	

    /**
     * set the messages extended header
     * 
     * @param extendedHeader extended message header
     */
    public void setExtendedHeader(ExtendedHeader extendedHeader) {
        this.extendedHeader = extendedHeader;
    }
	
    /**
     * set the messages meta data
     * 
     * @param metaData message meta data
     * @param msgID message ID
     */
    public void setMetaData(MetaData metaData, long msgID) {
        this.metaData = metaData;
        this.extendedHeader = new ExtendedHeader(msgID);
    }
    
    /**
     * Get the messages extended header
     * 
     * @return the extendedHeader
     */
    public ExtendedHeader getExtendedHeader() {
        return extendedHeader;
    }

    /**
     * Get the messages meta data
     * 
     * @return the metaData
     */
    public MetaData getMetaData() {
        return metaData;
    }


}