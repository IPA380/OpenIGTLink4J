/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.trajectory;

import java.util.Vector;

import util.BytesArray;

/**
 * Class representing a {@link Trajectory} as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/trajectory.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class Trajectory {	

    /** List of all {@link TrajectoryElement}s  contained in this */
	Vector<TrajectoryElement> trajectoryList = new Vector<TrajectoryElement>();
	
	/**
	 * Method to add a {@link Trajectory} to this
	 * 
	 * @param elem
	 * 		the {@link TrajectoryElement} to be added
	 * @return
	 * 		the number of {@link TrajectoryElement}s contained in this
	 */
	public int addTrajectoryElement(TrajectoryElement elem){
	    trajectoryList.add(elem);
	    return trajectoryList.size();
	}
	
	/**
	 * Method to clear all {@link TrajectoryElement}s contained in this
	 */
	public void clearTrajectryElements() {
		trajectoryList.clear();
	}

	/**
	 * Method to get the number of {@link TrajectoryElement}s in this
	 * 
	 * @return
	 * 		the number of {@link TrajectoryElement}s contained in this
	 */
	public int getNumberOfTrajectoryElements(){
	    return trajectoryList.size();
	}

	/**
	 * Method to get the ith {@link TrajectoryElement} contained in this trajectory
	 * 
	 * @param i
	 * 		the position from which to get the {@link TrajectoryElement}
	 * @return
	 * 		the {@link TrajectoryElement} at position i
	 */
	public TrajectoryElement getTrajectoryElement(int i){
		return trajectoryList.get(i);
	}

	/**
	 * Method to generate the serialized representation of the {@link Trajectory}
	 * 
	 * @return
	 * 		serialized {@link Trajectory}
	 */
	public byte[] convertToBytes(){
	
		BytesArray bytesArray = new BytesArray();
	    TrajectoryElement element;
	    
	    for (int iter = 0; iter < getNumberOfTrajectoryElements(); iter ++){
	        element = getTrajectoryElement(iter);
	        bytesArray.putBytes(element.convertToBytes());
	    }
	    return bytesArray.getBytes();
	}

	/**
	 * Method to de-serialize a {@link Trajectory}
	 * 
	 * @param nElement
	 * 		the number of {@link TrajectoryElement}s contained in trajectory_data
	 * @param
	 * 		the serialized representation of the {@link Trajectory}
	 * @return
	 * 		the de-serialized {@link Trajectory}
	 */
	public static Trajectory fromBytes(long nElement, byte[] trajectory_data){
	
		Trajectory retVal = new Trajectory();
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(trajectory_data);
		  
		/* TODO: have a look at little / big endian conversion */
		for (int iter = 0; iter < nElement; iter ++){
			TrajectoryElement element = TrajectoryElement.fromBytes(bytesArray.getBytes(TrajectoryElement.IGTL_TRAJECTORY_ELEMENT_SIZE));
		    retVal.addTrajectoryElement(element);
		}
		
		return retVal;
	}
	
	@Override
	public String toString() {        
		String trajectoryString = "";
		
	    for ( int index = 0; index < trajectoryList.size(); index++){
	    	trajectoryString = trajectoryString + "============================\n";
	    	trajectoryString += trajectoryList.get(index).toString() + "\n";
	    	trajectoryString = trajectoryString + "============================\n";
	    }
	    
	    return trajectoryString;
	}

}
