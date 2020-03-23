/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.point;

import msg.OIGTL_DataMessage;
import util.Header;

/**
 *** This class create an TrajectoryMessage object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andreas Rothfuss
 * 
 */
public class PointMessage extends OIGTL_DataMessage{

    /** The messages data type */
	public static final String DATA_TYPE = "POINT";
    
    /**
     *  A list of pointers to the trajectories.
     */
	Points points;

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of message
	 * */
    public PointMessage(String deviceName) {
		super(DATA_TYPE, deviceName, 0);
		points = new Points();
    }

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	
	 * 		device name of message
	 * @param points
	 * 		the {@link Points}
	 * */
    private PointMessage(String deviceName, Points points) {
		super(DATA_TYPE, deviceName, 
				getBodyPackSize(points.getNumberOfElements()));
		this.points = points;
	}

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */ 
    public PointMessage(Header header, byte[] body) {
		super(header, body);
	}

    /**
     * To set the {@link Points} of this message
     */
    public void setPoints(Points points) {
		this.points = points;
	}

	/**
	 * Method to add a {@link PointElement} to this message
	 * 
	 * @param elem
	 * 		the {@link PointElement} to be added
	 * @return
	 * 		the number of {@link PointElement}s contained in this message
	 */
	@Deprecated /*(2018-04-09)*/
	public int AddPointElement(PointElement elem) {
		return addPointElement(elem);
	}
	/**
	 * Method to add a {@link PointElement} to this message
	 * 
	 * @param elem
	 * 		the {@link PointElement} to be added
	 * @return
	 * 		the number of {@link PointElement}s contained in this message
	 */
    public int addPointElement(PointElement elem){
        return points.addElement(elem);
    }

	/**
	 * @return the {@link Points} 
	 */
    public Points getPoints(){
        return points;
    }

	/**
	 * To get the packed size of {@link Points}´
	 * 
	 * @return the packed body size
	 */
    static int getBodyPackSize(int nbOfElements){
        /* The body size sum of the header size and status message size. */
        return PointElement.IGTL_POINT_ELEMENT_SIZE * nbOfElements;
    }
	
	/**
	 * To get the packed size of {@link Points}´
	 * 
	 * @return the packed body size
	 */
    int getBodyPackSize(){
        /* The body size sum of the header size and status message size. */
        return getBodyPackSize(points.getNumberOfElements());
    }
    
    @Override
    public byte[] getBody() {
    	if (points != null)
    		return points.convertToBytes();
    	else
    		return new byte[0];
    }
    
    @Override
    public boolean UnpackBody(byte[] body){
    	
		long nElement = (body.length / PointElement.IGTL_POINT_ELEMENT_SIZE);
		
		points = Points.fromBytes(nElement, body);
        
        return true;
    }
    
    @Override
    public String toString() {
        String retVal = "POINT Device Name           : " + getDeviceName() + "\n";
        if (points != null)
        	retVal += points.toString();
        else
        	retVal += "POINTS: null";        
        return retVal;
    }
    
}
