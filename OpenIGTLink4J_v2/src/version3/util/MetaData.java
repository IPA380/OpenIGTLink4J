/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Class representing an Metadata body part of an OpenIGTLink V3 message
 * as specified in http://openigtlink.org/developers/spec
 * 
 * @author Andreas Rothfuss
 *
 */
public class MetaData {

	/** List of all keys of type {@link String}*/
	ArrayList<String> keys;
	/** List of all values of type {@link String}*/
	ArrayList<String> values;

    /**
     *** Constructor to be used to build meta data from incoming bytes
     *** 
     * @param metaDataBytes
     *            bytes array containing meta data body
     **/
	public MetaData(byte[] metaDataBytes) {
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(metaDataBytes);
		
		int indexCount = (int) bytesArray.getLong(2);
		
		MetaHeader[] metaHeaders = new MetaHeader[indexCount];
		
		for (int i = 0; i < indexCount; i++) {
			int keySize = (int) bytesArray.getLong(2);
			int valueEncoding = (int) bytesArray.getLong(2);
			long valueSize = bytesArray.getLong(4);
			metaHeaders[i] = new MetaHeader(keySize, valueEncoding, valueSize);
		} 
		
		keys = new ArrayList<String>(indexCount);
		values = new ArrayList<String>(indexCount);
		
		for (int i = 0; i < indexCount; i++) {
			String key = bytesArray.getString(metaHeaders[i].keySize);
			byte[] valueBytes = new byte[(int) (metaHeaders[i].valueSize)];
			valueBytes = bytesArray.getBytes(valueBytes.length);
			String value;
			try {
				value = MIBenum.decodeVaule(valueBytes, metaHeaders[i].valueEncoding);
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgumentException("The encoding " + 
			metaHeaders[i].valueEncoding + "is not supported.");
			}
			keys.add(key);
			values.add(value);
		} 
	}

    /**
     * Constructor to be used to create metadate to getBytes to send them
     **/
	public MetaData(){
		keys = new ArrayList<String>();
		values = new ArrayList<String>();
	}

	/**
	 * Method to add a key-value pair to the meta data set
	 * 
	 * @param key
	 * 		to find the value by
	 * @param value
	 */
	public void addKeyValuePair(String key, String value){
		keys.add(key);
		values.add(value);
	}

	/**
	 *** Get the value by key
	 *** 
	 * @return the value
	 **/
	public String getValue(String key) {
		return values.get(keys.lastIndexOf(key));
	}

	/**
	 *** Get the metadata header size for this set of metadata
	 *** 
	 * @return the size
	 **/
	public int getMetaDataHeaderSize() {
		return 2+(2+2+4)*keys.size();
	}

	/**
	 * generate the serialized representation of the {@link MetaData}
	 * 
	 * @return
	 * 		serialized {@link MetaData}
	 */
	public byte[] getBytes(){
		BytesArray bytesArray = new BytesArray();
		
		int indexCount = keys.size();
		bytesArray.putLong(indexCount, 2);
		/** put meta data header **/
		for (int i = 0; i < keys.size(); i++) {
			bytesArray.putLong(keys.get(i).length(), 2);
			/*Encoding: US-ASCII */
			bytesArray.putLong(3, 2); 
			bytesArray.putLong(values.get(i).length(), 4);
		}
		/** put meta data **/
		for (int i = 0; i < keys.size(); i++) {
			bytesArray.putString(keys.get(i));
			bytesArray.putString(values.get(i));
		}
		
		return bytesArray.getBytes();
	}
}
