/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the extended header part of the OpenIGTLink header 
 * as described in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/header.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class ExtendedHeader {

	/** Size of the {@link ExtendedHeader}*/
	public static final int LENGTH = 12;

	/** Size of the {@link ExtendedHeader}*/
	int extendedHeaderSize = LENGTH;

	/** Size of the {@link MetaHeader} as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/header.md*/
	long metaDataHeaderSize;
	/** Size of the {@link MetaData} as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/header.md*/
	long metaDataSize;
	/** Message ID as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/header.md*/
	long msgID;
	/** Reserved field specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/header.md*/
	long reserved = 0;
	
	/**
     *** Destination Constructor
     *** 
     * @param metaDataSize
     *            meta data size int 16bits
     * @param msgID
     *            message ID int 32 bits
     **/
    public ExtendedHeader(int metaDataSize, long msgID) {
        this.metaDataSize = metaDataSize;
        this.msgID = msgID;
    }
    
	/**
     *** Destination Constructor
     *** 
     * @param msgID
     *            message ID int 32 bits
     **/
    public ExtendedHeader(long msgID) {
    	this(0, msgID);
    }

    /**
     * Constructor to create a {@link ExtendedHeader} from serialized
     * data
     * 
     * @param bytes
     * 		Serialized for of the {@link ExtendedHeader}
     */
    public ExtendedHeader(byte[] bytes) {
        BytesArray bytesArray = new BytesArray();
        bytesArray.putBytes(bytes);
        
        /* unsigned int 16bits */
        extendedHeaderSize = (int) bytesArray.getLong(2); 
        /* unsigned int 16bits */
        metaDataHeaderSize = (int) bytesArray.getLong(2); 
        /* unsigned int 16bits */
        metaDataSize = bytesArray.getULong(4); 
        /* unsigned int 32 bits */
        msgID = bytesArray.getULong(4); 
        
		Logger log = LoggerFactory.getLogger(this.getClass());        
        log.debug("New extended header: "+this);
    }
    
    /**
	 *** To get the serialized data for the {@link ExtendedHeader}.
	 *** 
	 * @return the serialized data for the {@link ExtendedHeader}
	 **/
	public byte[] getBytes() {
		BytesArray bytesArray = new BytesArray();
		bytesArray.putLong(extendedHeaderSize, 2);
		bytesArray.putLong(metaDataHeaderSize, 2);
		bytesArray.putLong(metaDataSize, 4);
		bytesArray.putLong(msgID, 4);
		return bytesArray.getBytes();
	}
	
	/**
	 * To get the extended header size
	 * 
	 * @return the extendedHeaderSize
	 */
	public int getExtendedHeaderSize() {
		return extendedHeaderSize;
	}

	/**
	 * To get the meta data header size
	 * 
	 * @return the meta data header size
	 */
	public long getMetaDataHeaderSize() {
		return metaDataHeaderSize;
	}

	/**
	 * @return the metaDataSize
	 */
	public long getMetaDataSize() {
		return metaDataSize;
	}

	/**
	 * @return the msgID
	 */
	public long getMsgID() {
		return msgID;
	}

	@Override
	public String toString() {
		String s="";
		s+="EXT_HEADER_SIZE: "+getExtendedHeaderSize();
		s+=" METADATA_SIZE: "+ getMetaDataSize();
		s+=" MSG_ID: "+ getMsgID();
		return s;
	}

	/**
	 * To set the meta data header size
	 * 
	 * @param metaDataHeaderSize
	 * 		size of the meta data header
	 */
	public void setMetaDataHeaderSize(int metaDataHeaderSize) {
		this.metaDataHeaderSize = metaDataHeaderSize;
	}

	/**
	 * To set the meta data size
	 * 
	 * @param metaDataSize
	 * 		size of the meta data
	 */
	public void setMetaDataSize(int metaDataSize) {
		this.metaDataSize = metaDataSize;
	}
}
