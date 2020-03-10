/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;

/**
 * Representation of the different {@link OpenIGTMessage} types 
 * mainly driven by the prefixes
 * 
 * @author Andreas Rothfuss
 *
 */
public enum OIGTLMessageType {
	GET("GET"),
	STT("STT"),
	STP("STP"),
	RTS("RTS"),
	OTHER("");
	
	String dataType;

	/**
	 * Destination constructor
	 * 
	 * @param dataType
	 * 		Prefix defining the message type
	 */
	OIGTLMessageType(String dataType){
		this.dataType = dataType;
	}

	/**
	 * To get the {@link OIGTLMessageType} for a {@link OpenIGTMessage}
	 *  
	 * @param message
	 * 		the message to get the type for
	 * @return
	 * 		the messages message type
	 */
	public static OIGTLMessageType from(OpenIGTMessage message) {        
		if (message instanceof OIGTL_GetMessage)
            return GET;
        if (message instanceof OIGTL_STTMessage)
            return STT;
        if (message instanceof OIGTL_STPMessage)
            return STP;
        if (message instanceof OIGTL_RTSMessage)
            return RTS;
        
        return OTHER;
	}

	/**
	 * To get the {@link OIGTLMessageType} for the data type of 
	 * a {@link OpenIGTMessage}
	 *  
	 * @param dataType
	 * 		the message data type to get the type for
	 * @return
	 * 		the messages message type
	 */
	public static OIGTLMessageType from(String dataType) {
		OIGTLMessageType[] values = OIGTLMessageType.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].dataType.equals(dataType.substring(0,3))) {
				return values[i];
			}
		}
		return OTHER;
	}

}
