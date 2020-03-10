/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.trajectory;

/**
 * Enumeration representing the trajectory types as specified 
 * in the type value of the trajectory message
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/trajectory.md 
 * 
 * @author Andreas Rothfuss
 *
 */
public enum TRAJ_TYPE{
    UNUSED(0x0),
    /** Trajectory with only entry point */
    ENTRY_ONLY(0x1), 
    /** Trajectory with only target point */
    TARGET_ONLY(0x2),	
    /** Trajectory with entry and target point */
    ENTRY_TARGET(0x3); 	

	/** code for the exponent as specified in 
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/trajectory.md */
	public final byte code;
	
    TRAJ_TYPE(int code){
    	if (code == ordinal())
    		this.code = (byte) ordinal();
    	else
    		throw new IllegalArgumentException();
    }

	/**
	 * To get the {@link TRAJ_TYPE} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the trajectory type from
	 * @return
	 * 		the {@link TRAJ_TYPE}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known trajectory type
	 */
    public static TRAJ_TYPE fromValue(int value) throws IllegalArgumentException {
        try{
            return TRAJ_TYPE.values()[value];
        }catch( ArrayIndexOutOfBoundsException e ) {
            throw new IllegalArgumentException("Unknown enum value :"+ value);
        }
    }
}