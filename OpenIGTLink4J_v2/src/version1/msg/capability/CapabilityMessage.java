/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Absynt Technologies Ltd, Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.capability;

import java.util.ArrayList;
import java.util.Iterator;

import msg.OIGTL_DataMessage;
import util.BytesArray;
import util.Header;

/**
 *** This class create an Capability object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andre Charles Legendre
 * @author Kevin Harrington
 * @author Andreas Rothfuss
 * 
 */
public class CapabilityMessage extends OIGTL_DataMessage {

    /** The messages data type */
    public static final String DATA_TYPE = "CAPABILITY";

	/** The length of the capabilty type in bytes */
	public static int CAPABILITY_TYPE_LENGTH = 12;

    /** Field to store the capabilities */
    ArrayList<String> capabilityList;

    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public CapabilityMessage(String deviceName) {
        super(DATA_TYPE, deviceName, 0);
        capabilityList = new ArrayList<String>();
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header
     * @param body
     */
    public CapabilityMessage(Header header, byte body[]) {
        super(header, body);
    }

    @Override
    public boolean UnpackBody(byte[] body) {
        capabilityList = new ArrayList<String>();
    	BytesArray bytesArray = new BytesArray();
        bytesArray.putBytes(body);
        for (int i = 0; i < body.length / CAPABILITY_TYPE_LENGTH; i++) {
        	capabilityList.add(bytesArray.getString(CAPABILITY_TYPE_LENGTH));
        }
    	return true;
    }

    @Override
    public byte[] getBody() {
    	BytesArray bytesArray = new BytesArray();
		for (String string : capabilityList) {
			bytesArray.putString(string, CAPABILITY_TYPE_LENGTH);
		}
        return bytesArray.getBytes();
    }

    /**
     *** To set Capability ArrayList this ArrayList can be created by MessageHandler or by ResponseHandler
     * @param capabilityList
     *** 
     */
    public void setCapabilityList(ArrayList<String> capabilityList) {
        this.capabilityList = capabilityList;
    }

	/**
	 * To set the capabilities transferred with this message
	 * 
	 * @param capability
	 * 		List of capabilities, a capability in capabilities will be 
	 * 		rejected if it has more than 12 characters
	 */
	public void setCapability(String[] capability) {
		capabilityList.clear();
		for (int i = 0; i < capability.length; i++) {
			addCapability(capability[i]);
		}
	}

	/**
	 * To add a capability to the capabilities transferred with this message
	 * 
	 * @param capability
	 * 		Capability to be added, a capability will be 
	 * 		rejected if it has more than 12 characters
	 */
	public void addCapability(String capability) {
		if (capability.length() <= CAPABILITY_TYPE_LENGTH)
			capabilityList.add(capability);
		else
			log.warn("Capabilty " + capability + " was rejected because it " +
					"is longer than " + CAPABILITY_TYPE_LENGTH);
	}

    /**
     *** To get capabilityList ArrayList
     *** 
     * @return the capabilityList
     */
    public ArrayList<String> GetCapabilityList() {
        return capabilityList;
    }

    @Override
    public String toString() {
        String capabilityString = "CAPABILITY Device Name           : " + getDeviceName();
        if (capabilityList != null) {
	        Iterator<String> it = capabilityList.iterator();
	        while (it.hasNext()) {
	        	capabilityString = capabilityString.concat(it.next());
	        }
        }
        else
        	capabilityString += " Capability is null";
        return capabilityString;
    }
}

