/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;

import util.Header;
import util.RTSMessageStatus;

/**
 * Abstract base class representing a reply message for streaming 
 * messages 
 * 
 * @author Andreas Rothfuss
 *
 */
public class OIGTL_RTSMessage extends OpenIGTMessage {

	/** body size of a RTS message */
	public static final int BODY_SIZE = 1;

	/** status of the request for which this message represents the reply */
	protected RTSMessageStatus status;

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public OIGTL_RTSMessage(Header header, byte[] body)  {
		super(header, body);
	}

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param dataType		data type of the message
	 * @param deviceName	device name of the request for which this represents the reply
	 * @param status		status of the request for which this represents the reply
	 */
	public OIGTL_RTSMessage(String dataType, String deviceName, RTSMessageStatus status){
		super(dataType, deviceName, BODY_SIZE);
		this.status = status;
	}

	/**
	 * To get the status
	 * 
	 * @return the status of the request
	 */
	public RTSMessageStatus getStatus() {
		return status;
	}
	
	@Override
	public byte[] getBody() {
		return new byte[]{status.getCode()};
	}

	@Override
	public boolean UnpackBody(byte[] body) {
		this.status = RTSMessageStatus.fromValue(body[0]);
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ", Status = " + status;
	}

}
