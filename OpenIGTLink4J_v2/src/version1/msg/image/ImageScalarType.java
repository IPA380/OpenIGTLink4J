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
 * Enum to represent the the scalar tzpe of an image as defined in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/image.md
 * 
 * @author Andreas Rothfuss
 *
 */
public enum ImageScalarType{ 
	UNKNOWN_0(0),
	UNKNOWN_1(1),
	TYPE_INT8(2),
	TYPE_UINT8(3),
	TYPE_INT16(4),
	TYPE_UINT16(5),
	TYPE_INT32(6),
	TYPE_UINT32(7),
	UNKNOWN_2(8),
	UNKNOWN_3(9),
	TYPE_FLOAT32(10),
	TYPE_FLOAT64(11);

	/** The code representing the scalar type */
    public final byte code;

    private ImageScalarType(int code){
    	if (code == ordinal())
    		this.code = (byte) ordinal();
    	else
    		throw new IllegalArgumentException();
    }


	/**
	 * To get the {@link ImageScalarType} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the {@link ImageScalarType} from
	 * @return
	 * 		the {@link ImageScalarType}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known unit
	 */
    public static ImageScalarType fromValue(int value) throws IllegalArgumentException {
        try{
            return ImageScalarType.values()[value];
        }catch( ArrayIndexOutOfBoundsException e ) {
            throw new IllegalArgumentException("Unknown enum value :"+ value);
        }
    }
}