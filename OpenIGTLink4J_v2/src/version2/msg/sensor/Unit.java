/*=========================================================================
 
  Program:   The OpenIGTLink Library
  Language:  C
  Web page:  http://openigtlink.org/
 
  Copyright (c) Insight Software Consortium. All rights reserved.
 
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
 
=========================================================================*/
/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

import util.BytesArray;


/**
 * Class representing the unit field for {@link SensorMessage} as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/unit.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class Unit{
	
	/** Size of the serialized form of the {@link Unit}*/
	public static final int BYTE_SIZE = 8;
	
	/** Field representing the number of units in this {@link Unit} */
	int nUnits;
	/** Prefix of the {@link Unit}*/
	SI_PREFIX prefix; 
	/** Array containing the {@link SI_UNIT}s */
	SI_UNIT[] unit = new SI_UNIT[6];           	
	/** Array containing the {@link SI_EXP}s */
	SI_EXP[] exp = new SI_EXP[6];           	
	
	/**
	 * Constructor to create an empty {@link Unit}
	 */
	public Unit(){
		this(SI_UNIT.BASE_NONE, SI_EXP.PLUS0);
		this.nUnits = 0;
	}

	/**
	 * Constructor to create an {@link Unit} with the 
	 * {@link SI_UNIT} unit
	 * 
	 * @param unit
	 * 		the {@link SI_UNIT} to create the {@link Unit} from	  
	 */
	public Unit(SI_UNIT unit) {
		this(unit, SI_EXP.PLUS0);
	}

	/**
	 * Constructor to create an {@link Unit} with the 
	 * {@link SI_UNIT} unit and {@link SI_EXP} exp
	 * 
	 * @param unit
	 * 		the {@link SI_UNIT} to create the {@link Unit} from	  
	 * @param exp
	 * 		the {@link SI_EXP} to create the {@link Unit} from	 
	 */
	public Unit(SI_UNIT unit, SI_EXP exp) {
		this(SI_PREFIX.NONE, unit, exp);
	}

	/**
	 * Constructor to create an {@link Unit} with the 
	 * {@link SI_PREFIX} prefix the {@link SI_UNIT} unit and 
	 * {@link SI_EXP} exp
	 * 
	 * @param prefix
	 * 		the {@link SI_PREFIX} to create the {@link Unit} from	
	 * @param unit
	 * 		the {@link SI_UNIT} to create the {@link Unit} from	  
	 * @param exp
	 * 		the {@link SI_EXP} to create the {@link Unit} from	 
	 */
	public Unit(SI_PREFIX prefix, SI_UNIT unit, SI_EXP exp) {
	    this.prefix = prefix;
	    nUnits = 1;
	    this.unit[0] = unit;
	    this.exp[0] = exp;
	    for (int i = 1; i < 6; i ++)
	    {
	        this.unit[i] = SI_UNIT.BASE_NONE;
	        this.exp[i] = SI_EXP.PLUS0;
	    }
	}    

	/**
	 * To set the prefix
	 * 
	 * @param prefix
	 * 		new prefix
	 */
	public void setPrefix(SI_PREFIX prefix){
		this.prefix = prefix;
	}

	/**
	 * To append an unit with exponent
	 * 
	 * @param unit
	 * 		the unit to append
	 * @param exp
	 * 		the exponent for the unit
	 * @throws IllegalAccessException 
	 */
	public void append(SI_UNIT unit, SI_EXP exp) throws IllegalAccessException{
		/* Check number of units already appended */
		if (this.nUnits < 6){
			/* Append */
			this.unit[this.nUnits] = unit;
			this.exp[this.nUnits] =  exp;
			nUnits++;
	    }	 
		else
			throw new IllegalAccessException("Unit field is already full");
	}

	/**
	 * To create an unit from two units with exponents
	 * 
	 * @param unit1 first unit
	 * @param exp1	first exponent
	 * @param unit2 second unit
	 * @param exp2  second exponent
	 * @return The created unit
	 */
	public static Unit of(SI_UNIT unit1, SI_EXP exp1, SI_UNIT unit2, SI_EXP exp2){
		Unit retVal = new Unit(unit1, exp1);
		try {
			retVal.append(unit2, exp2);
		} catch (IllegalAccessException e) {e.printStackTrace();}
		return retVal;
	}

	/**
	 * To convert the {@link Unit} to it's serialized form
	 * 
	 * @return the serialized form of the {@link Unit}
	 */
	public byte[] converToBytes() {
		BytesArray bytesArray = new BytesArray();
		long bitRep = 0;
		
		/** set prefix: first 4 bit */
		bitRep = bitRep << 4;
		bitRep = bitRep | (prefix.code & Byte.decode("0x0F"));	
		
		for (int i = 0; i < unit.length; i++){
			/** set unit: 6 bit */
			bitRep = bitRep << 6;
			bitRep = bitRep | (unit[i].code & Byte.decode("0x3F"));	
			
			/** set exp: 4 bit */
			bitRep = bitRep << 4;
			bitRep = bitRep | (exp[i].code & Byte.decode("0x0F"));	
		}
			
		bytesArray.putLong(bitRep, 8);
		return bytesArray.getBytes();
	}


	/**
	 * To create the {@link Unit} from it's serialized form
	 * 
	 * @return the {@link Unit} created from it's serialized form
	 */
	public static Unit fromBytes(byte[] bytes) {
		Unit retVal = new Unit();
		
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(bytes);
		
		long bitRep = bytesArray.getLong(8);
		
		SI_UNIT[] parsUnit = new SI_UNIT[6];
		SI_EXP[] parsExp = new SI_EXP[6]; 
		for (int i = retVal.unit.length - 1; i >= 0 ; i--){
			/** get exp: 4 bit */
			parsExp[i] = SI_EXP.fromValue((byte) (bitRep & Long.decode("0x0F")));	
			bitRep = bitRep >> 4;
			
			/** get unit: 6 bit */
			parsUnit[i] = SI_UNIT.fromValue((byte) (bitRep & Long.decode("0x3F")));	
			bitRep = bitRep >> 6;
		}
		try {
			for (int i = 0; i < parsUnit.length ; i++){
				if (!parsExp[i].equals(SI_EXP.PLUS0) | !parsUnit[i].equals(SI_UNIT.BASE_NONE))
					retVal.append(parsUnit[i], parsExp[i]);
			}
		}catch (IllegalAccessException e) {e.printStackTrace();}
	
		/** set prefix: first 4 bit */
		retVal.prefix = SI_PREFIX.fromValue((byte) (bitRep & Byte.decode("0x0F")));	
		bitRep = bitRep >> 4;
			
		return retVal;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Unit) {
			Unit toTest = (Unit)other;
			boolean retVal = true;
			retVal = retVal & nUnits == toTest.nUnits;
			retVal = retVal & prefix == toTest.prefix;
			for (int i = 0; i < unit.length; i++){
				retVal = retVal & unit[i] == toTest.unit[i];
				retVal = retVal & exp[i] == toTest.exp[i];
			}
			return retVal;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String retVal = "Prefix: " + prefix + ", Units: ";
		for (int i = 0; i < unit.length; i++) {
			if (unit[i] != SI_UNIT.BASE_NONE) {
				if (i > 0) {
					retVal += ", ";
				}
				retVal += unit[i] + "^" + exp[i];
			}
		}
		return retVal;
	}
}
