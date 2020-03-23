/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.trajectory;

import msg.OIGTL_DataMessage;
import util.Header;

/**
 *** This class create an TrajectoryMessage object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andreas Rothfuss
 * 
 */
public class TrajectoryMessage extends OIGTL_DataMessage{

    /** The messages data type */
	public static final String DATA_TYPE = "TRAJ";
	

	/** The {@link Trajectory} */
	Trajectory trajectory;


    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of message
	 * */
	public TrajectoryMessage(String deviceName) {
		this(deviceName, new Trajectory());
	}

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName
	 * 		the device name of the message
	 * @param trajectory
	 * 		the {@link Trajectory}
	 * */
    private TrajectoryMessage(String deviceName, Trajectory trajectory) {
		super(DATA_TYPE, deviceName, getBodyPackSize(trajectory));
		this.trajectory = trajectory;
	}

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public TrajectoryMessage(Header header, byte[] body){
		super(header, body);
	}

	/**
	 * Method to add a {@link Trajectory} to this message
	 * 
	 * @param elem
	 * 		the {@link TrajectoryElement} to be added
	 * @return
	 * 		the number of {@link TrajectoryElement}s contained in this message
	 */
	@Deprecated /*(2018-04-09)*/
	public int AddTrajectoryElement(TrajectoryElement elem) {
		return addTrajectoryElement(elem);
	}
	/**
	 * Method to add a {@link Trajectory} to this message
	 * 
	 * @param elem
	 * 		the {@link TrajectoryElement} to be added
	 * @return
	 * 		the number of {@link TrajectoryElement}s contained in this message
	 */
	public int addTrajectoryElement(TrajectoryElement elem)
	{
	    int retVal = trajectory.addTrajectoryElement(elem);
	    return retVal;
	}

	/**
	 * @return the {@link Trajectory}
	 */
	public Trajectory getTrajectory()
	{
	    return trajectory;
	}

	/**
	 * To get the packed size of a {@link Trajectory}´
	 * 
	 * @return the packed body size
	 * @deprecated use getBodyPackSize(trajectory) instead
	 */
	@Deprecated /*(2018-04-09)*/
	static int GetBodyPackSize(Trajectory trajectory) {
	    /* The body size sum of the header size and status message size. */
	    return getBodyPackSize(trajectory);
	}
	
	/**
	 * To get the packed size of a {@link Trajectory}´
	 * 
	 * @return the packed body size
	 */
	static int getBodyPackSize(Trajectory trajectory) {
	    /* The body size sum of the header size and status message size. */
	    return TrajectoryElement.IGTL_TRAJECTORY_ELEMENT_SIZE * trajectory.getNumberOfTrajectoryElements();
	}

	/**
	 * To get the packed size of the {@link Trajectory}´of this message
	 * 
	 * @return the packed body size
	 * @deprecated use getBodyPackSize() instead
	 */
	@Deprecated /*(2018-04-09)*/
	int GetBodyPackSize() {
	    return getBodyPackSize();
	}
	
	/**
	 * To get the packed size of the {@link Trajectory}´of this message
	 * 
	 * @return the packed body size
	 */
	int getBodyPackSize() {
	    /* The body size sum of the header size and status message size. */
	    return getBodyPackSize(trajectory);
	}
	
	@Override
	public byte[] getBody() {
		byte[] body = trajectory.convertToBytes();
	    return body;
	}
	
	@Override
	public boolean UnpackBody(byte[] body) {
		long nElement = (body.length / TrajectoryElement.IGTL_TRAJECTORY_ELEMENT_SIZE);
		
		trajectory = Trajectory.fromBytes(nElement, body);
	    
	    return true;
	}
	
	@Override
	public String toString() {
	    String trajectoryMessageString = "TRAJ Device Name: " + getDeviceName();
	    if (trajectory != null)
	    	trajectoryMessageString = trajectoryMessageString + trajectory.toString();
	    else
	    	trajectoryMessageString += "Trajcetory is null"; 
	    return trajectoryMessageString;
	}
    
}
