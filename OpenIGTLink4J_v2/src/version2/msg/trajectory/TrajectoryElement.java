/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.trajectory;

import java.util.Arrays;

import util.AbstractElement;
import util.BytesArray;

/**
* Class representing a {@link TrajectoryElement} as specified in 
* https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/trajectory.md
* 
* @author Andreas Rothfuss
*
*/
public class TrajectoryElement extends AbstractElement{

	/** Size of the serialized form of a {@link TrajectoryElement} in bytes */
	public static final int IGTL_TRAJECTORY_ELEMENT_SIZE = 150;

	/** Type of the {@link TrajectoryElement}, default value is TARGET_ONLY */
	TRAJ_TYPE type = TRAJ_TYPE.TARGET_ONLY;
	/** Position of the entry point of the {@link TrajectoryElement} */
	float[] entryPosition = new float[3];
	/** Position of the target point of the {@link TrajectoryElement} */
	float[] targetPosition = new float[3];
	
	/**
	 * Constructor to create an empty trajectory
	 */
	public TrajectoryElement() {
		super();
	}
	
	/**
	 * Constructor to create a fully specified trajectory
	 * 
	 * @param name
	 * 		the trajectories name
	 * @param groupName
	 * 		the trajectories group name
	 * @param type
	 * 		the trajectories type
	 * @param entry
	 * 		the trajectories entry point
	 * @param target
	 * 		the trajectories target point
	 * @param rgba
	 * 		the trajectory color in RGBA
	 * @param diameter
	 * 		the trajectories diameter
	 * @param owner
	 * 		the Id of the owner image/sliceset. Trajectories 
	 * 		from different slicesets can be sent if slicesets are fused.
	 */
	public TrajectoryElement(String name, String groupName, 
			TRAJ_TYPE type, float[] entry, float[] target, 
			byte[] rgba, float diameter, String owner){
		super(name, groupName, rgba, diameter, owner);
		this.type = type;
		this.entryPosition = entry;
		this.targetPosition = target;
	}
	
	@Override
	public boolean equals(Object otherObj){
	    TrajectoryElement other = (TrajectoryElement) otherObj;
	    
	    
	    boolean retVal = true;
	    
	    retVal &= super.equals(otherObj);
	    retVal &= type.code == other.getType();
	    retVal &= Arrays.equals(entryPosition, other.getEntryPosition());
	    retVal &= Arrays.equals(targetPosition, other.getTargetPosition());
	    
	    return retVal;
	}

	/**
	 * To set the trajectory type
	 * 
	 * @param type
	 * 		can be TARGET_ONLY, ENTRY_ONLY or ENTRY_TARGET
	 */
	public void setType(TRAJ_TYPE type)
	{
	    if (type != TRAJ_TYPE.UNUSED)
	    	this.type = type;
	    else 
	    	throw new IllegalArgumentException();
	}

	/**
	 * To set the trajectory type from byte value
	 * 
	 * @param type
	 * 		can be TARGET_ONLY(2), ENTRY_ONLY(1) or ENTRY_TARGET(3)
	 */
	public void setType(byte type) {
	    this.type = TRAJ_TYPE.fromValue(type);
	}

	/**
	 * @return the trajectory type as byte
	 */
	public byte getType() {
	    return (byte) this.type.code;
	}

	/**
	 * To set the entry position
	 * 
	 * @param position accepts only float arrays with 3 elements
	 */
	public void setEntryPosition(float[] position){
	    if (position.length == 3){
	        this.entryPosition[0] = position[0];
	        this.entryPosition[1] = position[1];
	        this.entryPosition[2] = position[2];
	    }
	    else{
	        throw new IllegalArgumentException();
	    }
	}

	/**
	 * To set the entry position
	 */
	public void setEntryPosition(float x, float y, float z){
	    this.entryPosition[0] = x;
	    this.entryPosition[1] = y;
	    this.entryPosition[2] = z;
	}

	/**
	 * @return the entry position
	 */
	public float[] getEntryPosition(){
	    return entryPosition;
	}

	/**
	 * To set the target position
	 * 
	 * @param position accepts only float arrays with 3 elements
	 */
	public void setTargetPosition(float[] position){
	    if (position.length == 3){
	        this.targetPosition[0] = position[0];
	        this.targetPosition[1] = position[1];
	        this.targetPosition[2] = position[2];
	    }
	    else{
	        throw new IllegalArgumentException();
	    }
	}

	/**
	 * To set the target position
	 */
	public void setTargetPosition(float x, float y, float z){
	    this.targetPosition[0] = x;
	    this.targetPosition[1] = y;
	    this.targetPosition[2] = z;
	}

	/**
	 * @return the target position
	 */
	public float[] getTargetPosition()
	{
	    return targetPosition;
	}

	@Override
	public byte[] convertToBytes() {
	    BytesArray bytesArray = new BytesArray();
	    
	    bytesArray.putString(this.getName(), IGTL_LEN_NAME);
	
	    bytesArray.putString(this.getGroupName(), IGTL_LEN_GROUP_NAME);
	
	    bytesArray.putByte(this.getType());
	    
	    /*put reserved byte */
	    bytesArray.putByte((byte) 0x00);
	    
	    bytesArray.putBytes(this.getRGBA());
	    
	    bytesArray.putDouble(this.getEntryPosition()[0], 4);
	    bytesArray.putDouble(this.getEntryPosition()[1], 4);
	    bytesArray.putDouble(this.getEntryPosition()[2], 4);
	    
	    bytesArray.putDouble(this.getTargetPosition()[0], 4);
	    bytesArray.putDouble(this.getTargetPosition()[1], 4);
	    bytesArray.putDouble(this.getTargetPosition()[2], 4);
	
	    bytesArray.putDouble(this.getRadius(), 4);
	
	    bytesArray.putString(this.getOwner(), IGTL_LEN_OWNER);
	    
	    return bytesArray.getBytes();
	}


	/**
	 * To de-serialize a {@link TrajectoryElement} from bytes
	 * 
	 * @param bytes
	 * 		the serialized form of the {@link TrajectoryElement}
	 * @return
	 * 		the de-serialized {@link TrajectoryElement}
	 */
	public static TrajectoryElement fromBytes(byte[] bytes){
		TrajectoryElement retVal = new TrajectoryElement();
		
	    BytesArray bytesArray = new BytesArray();
	    bytesArray.putBytes(bytes);
	
	    retVal.setName(bytesArray.getString(IGTL_LEN_NAME));
	    retVal.setGroupName(bytesArray.getString(IGTL_LEN_GROUP_NAME));
	    retVal.setType(bytesArray.getBytes(1)[0]);
	    
	    /* remove reserved byte */
	    bytesArray.getBytes(1);
	    
	    retVal.setRGBA(bytesArray.getBytes(4));
	    retVal.setEntryPosition((float)bytesArray.getDouble(4),
	    (float)bytesArray.getDouble(4), (float)bytesArray.getDouble(4));
	    retVal.setTargetPosition((float)bytesArray.getDouble(4),
	    (float)bytesArray.getDouble(4), (float)bytesArray.getDouble(4));
	    retVal.setRadius((float)bytesArray.getDouble(4));
	    
	    retVal.setOwner(bytesArray.getString(IGTL_LEN_OWNER));
	    
		return retVal;    	
	}
	
	@Override
	public String toString(){
		String retVal = super.toString() + "\n";
	    retVal += " Type: " + getType();
	    retVal += " EntryPosition: " + Arrays.toString(getEntryPosition());
	    retVal += " TargetPosition: " + Arrays.toString(getTargetPosition());
	    retVal += " RGBA-Value: " + new String(rgba);
	    return retVal;
	}
    
}
