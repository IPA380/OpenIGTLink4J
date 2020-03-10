/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

/**
 * RTS message status
 * @author Andreas Rothfuss
 *
 */
public enum RTSMessageStatus{
	Success(0),
	Error(1);

	/** code for the RTS message status */
	public final byte code;

	RTSMessageStatus(int code){
		if (code == ordinal())
			this.code = (byte) ordinal();
		else
			throw new IllegalArgumentException();
	}

	/**
	 * To get the {@link RTSMessageStatus} from (serialized) value
	 * 
	 * @param value
	 * 		value to get the exponent from
	 * @return
	 * 		the {@link RTSMessageStatus}
	 * @throws IllegalArgumentException
	 * 		if value doesn't correspond to any known unit
	 */
	public static RTSMessageStatus fromValue(byte code) throws IllegalArgumentException {
		for(int i = 0; i < RTSMessageStatus.values().length; i++){
			if (RTSMessageStatus.values()[i].code == code){
				return RTSMessageStatus.values()[i];
			}
		}
		throw new IllegalArgumentException("Unknown enum value :" + code);
		}
	
		public byte getCode(){
			return code;
	}
}