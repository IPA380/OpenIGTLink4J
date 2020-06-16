/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

import msg.OpenIGTMessage;

/**
 * Helper class for {@link OpenIGTMessage} unit tests
 * 
 * @author Andreas Rothfuss
 *
 */
public class MessageTestHelper {
	private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	/**
	 * To convert char array to byte array, useful when porting {@link OpenIGTMessage} 
	 * unit tests from c++ implementation to java implementation
	 * 
	 * @param toConvert
	 * 		the array of chars to convert
	 * @return
	 * 		the array of bytes converted from toConvert
	 */
	public static byte[] convertCharToByte(char[] toConvert) {
		byte[] retVal = new byte[toConvert.length];
		for (int i = 0; i < retVal.length; i++)
			retVal[i] = (byte) toConvert[i];
		return retVal;
	}
	
	/**
	 * To join two byte arrays, useful when porting {@link OpenIGTMessage} 
	 * unit tests from c++ implementation to java implementation
	 * 
	 * @param first
	 * 		the first byte array
	 * @param second
	 * 		the second byte array
	 * @return
	 *  	the combined byte array
	 */
	public static byte[] join(byte[] first, byte[] second) {
		byte[] retVal = new byte[first.length + second.length];
		for (int i = 0; i < retVal.length; i++){
			if (i < first.length)
				retVal[i] = first[i];
			else
				retVal[i] = second[i-first.length];
		}
		return retVal;
	} 
}
