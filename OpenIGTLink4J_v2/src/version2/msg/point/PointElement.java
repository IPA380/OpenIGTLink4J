/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.point;

import java.util.Arrays;

import msg.trajectory.TrajectoryElement;
import util.AbstractElement;
import util.BytesArray;

/**
* Class representing a {@link TrajectoryElement} as specified in 
* https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/point.md
* 
* @author Andreas Rothfuss
*
*/
public class PointElement extends AbstractElement{

	/** Size of the serialized form of a {@link PointElement} in bytes */
	public static final int IGTL_POINT_ELEMENT_SIZE = 136;
	/** Position of the {@link PointElement} */
	protected float[] position = new float[3];

	/**
	 * Constructor to create an empty {@link PointElement}
	 */
	public PointElement() {};
	
	/**
	 * Constructor to create a fully specified {@link PointElement}
	 * 
	 * @param name
	 * 		the {@link PointElement}s name
	 * @param groupName
	 * 		the {@link PointElement}s  group name
	 * @param rgba
	 * 		the trajectory color in RGBA
	 * @param position
	 * 		the position of the {@link PointElement}
	 * @param diameter
	 * 		the trajectories diameter
	 * @param owner
	 * 		the Id of the owner image/sliceset. Points 
	 * 		from different slicesets can be sent if slicesets are fused.
	 */
	public PointElement(String name, String groupName,
    		byte[] rgba, float[] position, float radius, String owner){
		super(name, groupName, rgba, radius, owner);
		this.setPosition(position);
	}
	
	/**
	 * Constructor to create a fully specified {@link PointElement}
	 * 
	 * @param name
	 * 		the {@link PointElement}s name
	 * @param groupName
	 * 		the {@link PointElement}s  group name
	 * @param rgba
	 * 		the trajectory color in RGBA
	 * @param x
	 * 		the x-coordinate of the {@link PointElement}
	 * @param y
	 * 		the y-coordinate of the {@link PointElement}
	 * @param z
	 * 		the z-coordinate of the {@link PointElement}
	 * @param diameter
	 * 		the trajectories diameter
	 * @param owner
	 * 		the Id of the owner image/sliceset. Points 
	 * 		from different slicesets can be sent if slicesets are fused.
	 */
	public PointElement(String name, String groupName,
    		byte[] rgba, float x, float y, float z, float radius, String owner){
		super(name, groupName, rgba, radius, owner);
		this.setPosition(x, y, z);
	}
	
	/**
	 * Constructor to create a partially specified {@link PointElement}
	 * 
	 * @param x
	 * 		the x-coordinate of the {@link PointElement}
	 * @param y
	 * 		the y-coordinate of the {@link PointElement}
	 * @param z
	 * 		the z-coordinate of the {@link PointElement}
	 */
	public PointElement(float x, float y, float z) {
        this.position[0] = x;
        this.position[1] = y;
        this.position[2] = z;		
	}
	
	@Override
    public boolean equals(Object otherObj){
        PointElement other = (PointElement) otherObj;
        
        boolean retVal = true;
        
        retVal &= super.equals(otherObj);
        retVal &= Arrays.equals(position, other.getPosition());
        
        return retVal;
    }


	/**
	 * To set the position
	 * 
	 * @param position accepts only float arrays with 3 elements
	 */
    public void setPosition(float[] position){
        if (position.length == 3){
            this.position[0] = position[0];
            this.position[1] = position[1];
            this.position[2] = position[2];
        }
        else{
            throw new IllegalArgumentException();
        }
    }

	/**
	 * To set the entry position
	 * 
	 * @param x
	 * 		the x-coordinate of the {@link PointElement}
	 * @param y
	 * 		the y-coordinate of the {@link PointElement}
	 * @param z
	 * 		the z-coordinate of the {@link PointElement}
	 */
    public void setPosition(float x, float y, float z){
        this.position[0] = x;
        this.position[1] = y;
        this.position[2] = z;
    }

	/**
	 * @return the position
	 */
    public float[] getPosition(){
        return position;
    }

	@Override
    public byte[] convertToBytes() {
        BytesArray bytesArray = new BytesArray();
        
        bytesArray.putString(this.getName(), IGTL_LEN_NAME);

        bytesArray.putString(this.getGroupName(), IGTL_LEN_GROUP_NAME);
     	    
	    bytesArray.putBytes(this.getRGBA());
	    
	    bytesArray.putDouble(this.getPosition()[0], 4);
	    bytesArray.putDouble(this.getPosition()[1], 4);
	    bytesArray.putDouble(this.getPosition()[2], 4);
	
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
	public static PointElement fromBytes(byte[] bytes){
		PointElement retVal = new PointElement();
		
	    BytesArray bytesArray = new BytesArray();
	    bytesArray.putBytes(bytes);
	
	    retVal.setName(bytesArray.getString(IGTL_LEN_NAME));
	    retVal.setGroupName(bytesArray.getString(IGTL_LEN_GROUP_NAME));
	    	    
	    retVal.setRGBA(bytesArray.getBytes(4));
	    
	    retVal.setPosition((float)bytesArray.getDouble(4),
	    (float)bytesArray.getDouble(4), (float)bytesArray.getDouble(4));
	    
	    retVal.setRadius((float)bytesArray.getDouble(4));
	    
	    retVal.setOwner(bytesArray.getString(IGTL_LEN_OWNER));
	    
		return retVal;    	
	}

	@Override
	public String toString(){
		String retVal = super.toString() + "\n";
		retVal +=  " Position	: " + Arrays.toString(getPosition());
	    return retVal;
	}
}
