/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.image;

/**
 * Image coordinates as specified in
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/image.md 
 * 
 * @author Andreas Rothfuss
 *
 */
public enum CoordinateConvention{
	UNKNOWN(0),
	RAS(1),
	LPS(2);

	/** code for the coordinate convetion as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/image.md */
    public final byte code;
  
    CoordinateConvention(int code){
    	if (code == ordinal())
    		this.code = (byte) ordinal();
    	else
    		throw new IllegalArgumentException();
    }

	/**
	 * To get the {@link CoordinateConvention} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the coordinate convention from
	 * @return
	 * 		the {@link CoordinateConvention}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known unit
	 */
    public static CoordinateConvention fromValue(int value) throws IllegalArgumentException {
        try{
            return CoordinateConvention.values()[value];
        }catch( ArrayIndexOutOfBoundsException e ) {
            throw new IllegalArgumentException("Unknown enum value :"+ value);
        }
    }
}
