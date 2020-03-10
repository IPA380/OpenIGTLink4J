/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.track;

/**
 * {@link TrackingInstrumentType} as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/trackingdata.md
 * 
 * @author Andreas Rothfuss
 *
 */
public enum TrackingInstrumentType {
	UNUSED(0),
	TRACKER(1),
	INSTRUMENT_6D(2),
	INSTRUMENT_3D(3),
	INSTRUMENT_5D(4);

	/** code for the unit as specified in 
	https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/trackingdata.md
	 **/
	public final byte code;
    
	TrackingInstrumentType(int code){
    	if (code == ordinal())
    		this.code = (byte) ordinal();
    	else
    		throw new IllegalArgumentException();
    }

	/**
	 * To get the {@link TrackingInstrumentType} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the exponent from
	 * @return
	 * 		the {@link TrackingInstrumentType}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known unit
	 */
    public static TrackingInstrumentType fromValue(int value) throws IllegalArgumentException {
        try{
            return TrackingInstrumentType.values()[value];
        }catch( ArrayIndexOutOfBoundsException e ) {
            throw new IllegalArgumentException("Unknown enum value :"+ value);
        }
    }
}
