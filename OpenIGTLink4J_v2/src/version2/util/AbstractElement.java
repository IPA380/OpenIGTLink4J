/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

import java.util.Arrays;

import msg.point.PointMessage;
import msg.trajectory.TrajectoryElement;
import msg.trajectory.TrajectoryMessage;

/**
 * Class representing an Abstract Elements as used in {@link PointMessage} and {@link TrajectoryMessage}
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class AbstractElement {

	/** Lenght of the name of a {@link AbstractElement} in bytes */
	public static final int IGTL_LEN_NAME = 64;
	/** Lenght of the group name of a {@link AbstractElement} in bytes */
	public static final int IGTL_LEN_GROUP_NAME = 32;
	/** Lenght of the owner of a {@link AbstractElement} in bytes */
	public static final int IGTL_LEN_OWNER = 20;

	/** Name of a {@link AbstractElement} */
	protected String name = "";
	/** Group name of a {@link AbstractElement} */
	protected String groupName = "";
	/** RGBA value of a {@link AbstractElement} */
	protected byte[] rgba = new byte[4];
	/** Radius of a {@link AbstractElement} */
	protected float radius = 0;
	/** Owner of a {@link AbstractElement} */
	protected String owner = "";

	/**
	 * Destination Constructor for a {@link AbstractElement}
	 * 
	 * @param name
	 * 		Name of the new {@link AbstractElement}
	 * @param groupName
	 * 		Group name of the new {@link AbstractElement}
	 * @param rgba
	 * 		RGBA of the new {@link AbstractElement}
	 * @param radius
	 * 		Radius of the new {@link AbstractElement}
	 * @param owner
	 * 		Owner of the new {@link AbstractElement}
	 */
	public AbstractElement(String name, String groupName,
			byte[] rgba, float radius, String owner) {
		this.name = name;
		this.groupName = groupName;
		this.rgba = rgba;
		this.radius = radius;
		this.owner = owner;
	}

	/**
	 * Destination Constructor for a empty {@link AbstractElement}
	 */
	public AbstractElement() {
		this("", "", new byte[4], 0, "");
	}
	
	@Override
	public boolean equals(Object otherObj){
		if (otherObj instanceof AbstractElement) {
		    AbstractElement other = (AbstractElement) otherObj;
		    
		    boolean retVal = true;
		    
		    retVal &= name.equals(other.getName());
		    retVal &= groupName.equals(other.getGroupName());
		    retVal &= Arrays.equals(rgba, other.getRGBA());
		    retVal &= radius == other.getRadius();
		    retVal &= owner.equals(other.getOwner());
		    
		    return retVal;
		}
		else {
			return false;
		}
	}
	
	/**
	 * To set the name
	 * 
	 * @param name accepts only names of max IGTL_LEN_NAME characters
	 */
	public void setName(String name){
	    if (name.length() <= IGTL_LEN_NAME){
	        this.name = name;
	    }
	    else {
	        throw new IllegalArgumentException();
	    }
	}

	/**
	 * @return the name
	 */
	public String getName(){
	    return this.name;
	}

	/**
	 * To set the group name
	 * 
	 * @param name accepts only names of max IGTL_LEN_GROUP_NAME characters
	 */
	public void setGroupName(String grpname){
	    if (grpname.length() <= IGTL_LEN_GROUP_NAME){
	        this.groupName = grpname;
	    }
	    else{
	        throw new IllegalArgumentException();
	    }
	}

	/**
	 * @return the group name
	 */
	public String getGroupName(){
	    return this.groupName;
	}

	/**
	 * To set the RGBA value
	 * 
	 * @param name accepts only byte arrays with 4 elements
	 */
	public void setRGBA(byte[] rgba){
	    if (rgba.length == 4){
	        this.rgba[0] = rgba[0];
	        this.rgba[1] = rgba[1];
	        this.rgba[2] = rgba[2];
	        this.rgba[3] = rgba[3];
	    }
	    else{
	        throw new IllegalArgumentException();
	    }
	}
	
	/**
	 *  To set the RGBA value
	 *  
	 * @param r R value in RGBA
	 * @param g G value in RGBA
	 * @param b B value in RGBA
	 * @param a A value in RGBA
	 */
	public void setRGBA(byte r, byte g, byte b, byte a){
	    this.rgba[0] = r;
	    this.rgba[1] = g;
	    this.rgba[2] = b;
	    this.rgba[3] = a;
	}
	
	/**
	 * @return the RGBA value
	 */
	public byte[] getRGBA(){
	    return rgba;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
	    return radius;
	}

	/**
	 * To set the radius
	 * 
	 * @param m_Radius
	 */
	public void setRadius(float m_Radius) {
	    this.radius = m_Radius;
	}

	/**
	 * To set the owner
	 * 
	 * @param name accepts only names of max IGTL_LEN_OWNER characters
	 */
	public void setOwner(String owner){
	    if (owner.length() <= IGTL_LEN_OWNER){
	        this.owner = owner;
	    }
	    else{
	        throw new IllegalArgumentException();
	    }
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
	    return owner;
	}
	    
	/**
	 * To serialize this
	 * @return
	 */
	public abstract byte[] convertToBytes();
	
	
	@Override
	public String toString(){
		String retVal = new String();
	    retVal += " Name: " + getName();
	    retVal += " GroupName: " + getGroupName();
	    retVal += " RGBA: " + Arrays.toString(getRGBA());
	    retVal += " Radius: " + getRadius();
	    retVal += " Owner: " + getOwner();
	    return retVal;
	}
    
}
