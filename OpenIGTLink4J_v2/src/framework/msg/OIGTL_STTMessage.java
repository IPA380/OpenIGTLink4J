/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;

import util.BytesArray;
import util.Header;

/**
 * Abstract base class representing all {@link OpenIGTMessage} implementations that
 * implement a STT message from the streaming mechanism
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class OIGTL_STTMessage extends OpenIGTMessage {
	
	/**
	 * The body size of the {@link OIGTL_STTMessage}
	 */
	protected static int BODY_SIZE = 8;
	
	/**
	 * Minimum interval between message (milliseconds)
	 */
	protected long resolution;

	/**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param dataType
     * 			message data type
     * @param deviceName
     *          message device Name
     **/
    public OIGTL_STTMessage(String dataType, String deviceName) {
            this(dataType, deviceName, 0);
    }
    
    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method CreateBody and then
     * getBytes to send them
     *** 
     * @param dataType
     * 			message data type
     * @param deviceName
     *          message device Name
     * @param resol
     * 			minimum update interval between messages in milliseconds,
     * 			use 0 to request messages as fast as possible
     **/
    public OIGTL_STTMessage(String deviceName, String dataType, long resol) {
            super(deviceName, dataType, BODY_SIZE);
            this.resolution = resol;
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header
     * @param body
     */
    public OIGTL_STTMessage(Header header, byte body[]) {
            super(header, body);
    }
    
    /**
     * To set the message resolution (milliseconds)
     * 
     * @param resolution
     */
    public void setResolution(long resolution) {
    	this.resolution = resolution;
    }

	/**
	 * To get the message resolution
	 * @return
	 * 		The message resolution (milliseconds)
	 */
	public long getResolution() {
		return resolution;
	}

    @Override
    public byte[] getBody() {
    	BytesArray bytesArray = new BytesArray();
    	bytesArray.putLong(resolution, 8);
        return bytesArray.getBytes();
    }
    
    @Override
    public boolean UnpackBody(byte[] body) {
    	BytesArray bytesArray = new BytesArray();
    	bytesArray.putBytes(body);
    	this.resolution = bytesArray.getLong(8);
        return true;
    }
}

