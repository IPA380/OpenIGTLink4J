/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

/**
 * Enum representing endian formats and their coding in OpenIGTLink
 * 
 * @author Andreas Rothfuss
 *
 */
public enum Endian {
	UNKNOWN(0),
	ENDIAN_BIG(1),
	ENDIAN_LITTLE(2);

	/** The code for the endian format */
    public final byte code;
    
	Endian(int code){
    	if (code == ordinal())
    		this.code = (byte) ordinal();
    	else
    		throw new IllegalArgumentException();
    }


	/**
	 * To get the {@link Endian} format from (serialized) value
	 * 
	 * @param value
	 * 		value to get the exponent from
	 * @return
	 * 		the {@link Endian} format
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known unit
	 */
    public static Endian fromValue(int value) throws IllegalArgumentException {
        try{
            return Endian.values()[value];
        }catch( ArrayIndexOutOfBoundsException e ) {
            throw new IllegalArgumentException("Unknown enum value :"+ value);
        }
    }
}
