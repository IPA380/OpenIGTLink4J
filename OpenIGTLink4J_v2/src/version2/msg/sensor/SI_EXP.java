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
 * Enumeration representing the exponent (EXP) as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/unit.md
 * 
 * @author Andreas Rothfuss
 *
 */
public enum SI_EXP{
	PLUS0(0),
	PLUS1(1),
	PLUS2(2), 
	PLUS3(3), 
	PLUS4(4), 
	PLUS5(5),
	PLUS6(6),
	PLUS7(7),
	UNUSED(8), 
	MINUS7(9), 
	MINUS6(0xA), 
	MINUS5(0xB), 
	MINUS4(0xC), 
	MINUS3(0xD),
	MINUS2(0xE), 
	MINUS1(0xF); 

	/** code for the exponent as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/unit.md */
	public final int code;

	SI_EXP(int code){
		if (code == ordinal())
			this.code = ordinal();
		else
			throw new IllegalArgumentException();
	}

	/**
	 * To get the {@link SI_EXP} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the exponent from
	 * @return
	 * 		the {@link SI_EXP}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known exponent
	 */
	public static SI_EXP fromValue(int value) throws IllegalArgumentException {
	    try{
	        return SI_EXP.values()[value];
	    }catch( ArrayIndexOutOfBoundsException e ) {
	        throw new IllegalArgumentException("Unknown enum value :"+ value);
	    }
	}
	
	public byte getCode() {
		return (byte)code;
	}
}