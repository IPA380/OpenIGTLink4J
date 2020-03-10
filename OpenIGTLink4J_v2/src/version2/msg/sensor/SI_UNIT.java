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
 * Enumeration representing the exponent (UNIT) as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/unit.md
 * 
 * @author Andreas Rothfuss
 *
 */
public enum SI_UNIT{
	// SI Base Units
	BASE_NONE(0x00),
	BASE_METER(0x01), 	/* meter */
	BASE_GRAM(0x02), 	/* gram */
	BASE_SECOND(0x03), 	/* second */
	BASE_AMPERE(0x04), 	/* ampere */
	BASE_KELVIN(0x05), 	/* kelvin */
	BASE_MOLE(0x06), 	/* mole */
	BASE_CANDELA(0x07), /* candela */
	
	// SI Derived Units
	DERIVED_RADIAN  (0x08),  /* radian     meter/meter */
	DERIVED_STERADIAN(0x09), /* steradian  meter^2/meter^2 */
	DERIVED_HERTZ   (0x0A),  /* hertz      /second */
	DERIVED_NEWTON  (0x0B),  /* newton     meter-kilogram/second^2 */
	DERIVED_PASCAL  (0x0C),  /* pascal     kilogram/meter-second^2 */
	DERIVED_JOULE   (0x0D),  /* joule      meter^2-kilogram/second^2 */
	DERIVED_WATT    (0x0E),  /* watt       meter^2-kilogram/second^3 */
	DERIVED_COULOMB (0x0F),  /* coulomb    second-ampere */
	DERIVED_VOLT    (0x10),  /* volt       meter^2-kilogram/second^3-ampere */
	DERIVED_FARAD   (0x11),  /* farad      second^4-ampere^2/meter^2-kilogram */
	DERIVED_OHM     (0x12),  /* ohm        meter^2-kilogram/second^3-ampere^2 */
	DERIVED_SIEMENS (0x13),  /* siemens    second^3-ampere^2/meter^2-kilogram */
	DERIVED_WEBER   (0x14),  /* weber      meter^2-kilogram/second^2-ampere */
	DERIVED_TESLA   (0x15),  /* tesla      kilogram/second^2-ampere */
	DERIVED_HENRY   (0x16),  /* henry      meter^2-kilogram/second^2-ampere^2 */
	DERIVED_LUMEN   (0x17),  /* lumen      candela-steradian */
	DERIVED_LUX     (0x18),  /* lux        candela-steradian/meter^2 */
	DERIVED_BECQUEREL(0x19), /* becquerel  /second */
	DERIVED_GRAY    (0x1A),  /* gray       meter^2/second^2 */
	DERIVED_SIEVERT (0x1B),  /* sievert    meter^2/second^2 */
	
	DERIVED_NEWTONMETER  (0x1C),  /* newtonmeter     (meter-kilogram/second^2) * meter */
	
	NUM_UNIT_TYPES  (0x1D);

	/** code for the unit as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/unit.md */
	public final byte code;
	
	SI_UNIT(int code){
		if (code == ordinal())
			this.code = (byte) ordinal();
		else
			throw new IllegalArgumentException();
	}

	/**
	 * To get the {@link SI_UNIT} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the exponent from
	 * @return
	 * 		the {@link SI_UNIT}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known unit
	 */
	public static SI_UNIT fromValue(int value) throws IllegalArgumentException {
	    try{
	        return SI_UNIT.values()[value];
	    }catch( ArrayIndexOutOfBoundsException e ) {
	        throw new IllegalArgumentException("Unknown enum value :"+ value);
	    }
	}
}