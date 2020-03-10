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

/**
 * Enumeration representing the MIBenum (EXP) as specified in 
 * https://www.iana.org/assignments/character-sets/character-sets.xhtml
 * 
 * @author Andreas Rothfuss
 *
 */
public enum MIBenum {
	UNKNOWN_0,
	UNKNOWN_1,
	UNKNOWN_2,
	US_ASCII(3, "US-ASCII");


	/** code for the MIBenum value as specified in 
	 * https://www.iana.org/assignments/character-sets/character-sets.xhtml */
	public final String code;
	
	MIBenum(){
			this.code = null;
	}
	
	MIBenum(int ordinal, String code){
    	if (ordinal == ordinal())
    		this.code = code;
    	else
    		throw new IllegalArgumentException();
    }

	/**
	 * To get the {@link MIBenum} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the MIBenum from
	 * @return
	 * 		the {@link MIBenum}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known exponent
	 */
	public static MIBenum from(int value) throws IllegalArgumentException {
	    try{
	    	if (MIBenum.values()[value].code != null);
	        	return MIBenum.values()[value];
	    }catch( ArrayIndexOutOfBoundsException e ) {}
	    throw new IllegalArgumentException("Unknown enum value :"+ value);
	}

	/**
	 * To decode a (serialized) value
	 * 
	 * @param values
	 * 		value to decode
	 * @return
	 * 		the {@link String} representation
	 * @throws UnsupportedEncodingException
	 */
	public String decode(byte[] values) throws UnsupportedEncodingException {
		return new String(values, this.code);
	//		return new String(values, "US-ASCII");
	}

	/**
	 * To decode a (serialized) value
	 * 
	 * @param values
	 * 		value to decode
	 * @param valueEncoding
	 * 		the encoding of values according to  
	 * https://www.iana.org/assignments/character-sets/character-sets.xhtml
	 * @return
	 * 		the {@link String} representation
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeVaule(byte[] values, int valueEncoding) throws UnsupportedEncodingException {
		switch (valueEncoding) {
		case 3:
			BytesArray bytesArray = new BytesArray();
			bytesArray.putBytes(values);
			return bytesArray.getString(values.length);
	//			return new String(values, "US_ASCII");
		default:
			throw new UnsupportedEncodingException();
		}
	}

}
