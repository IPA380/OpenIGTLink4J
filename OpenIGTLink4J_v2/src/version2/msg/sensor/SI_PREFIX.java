/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

/**
 * Enumeration representing the exponent (PREFIX) as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/unit.md
 * 
 * @author Andreas Rothfuss
 *
 */
public enum SI_PREFIX{
	NONE(0x0), /* None */
	DEKA(0x1), /* deka (deca) (1e1) */
	HECTO(0x2), /* hecto (1e2) */
	KILO(0x3), /* kilo (1e3) */
	MEGA(0x4), /* mega (1e6) */
	GIGA(0x5), /* giga (1e9) */
	TERA(0x6), /* tera (1e12) */
	PETA(0x7), /* peta (1e15) */
	UNUSED(0x08), 
	DECI(0x9), /* deci (1e-1) */
	CENTI(0xA), /* centi (1e-2) */
	MILLI(0xB), /* milli (1e-3) */
	MICRO(0xC), /* micro (1e-6) */
	NANO(0xD), /* nano (1e-9) */
	PICO(0xE), /* pico (1e-12) */
	FEMTO(0xF); /* femto (1e-15) */
	
	/** code for the exponent as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/unit.md */
	public final byte code;
	
	SI_PREFIX(int code){
		if (code == ordinal())
			this.code = (byte) ordinal();
		else
			throw new IllegalArgumentException();
	}

	/**
	 * To get the {@link SI_PREFIX} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the exponent from
	 * @return
	 * 		the {@link SI_PREFIX}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known prefix
	 */
	public static SI_PREFIX fromValue(int value) throws IllegalArgumentException {
	    try{
	        return SI_PREFIX.values()[value];
	    }catch( ArrayIndexOutOfBoundsException e ) {
	        throw new IllegalArgumentException("Unknown enum value :"+ value);
	    }
	}
}