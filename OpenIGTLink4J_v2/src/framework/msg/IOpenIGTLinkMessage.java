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

/**
 * Interface defining an OpenIGTLink message
 * 
 * @author Andreas Rothfuss
 *
 */
public interface IOpenIGTLinkMessage{

	/**
	 *** To create body from body byte array
	 * 
	 * @return byte [] representing the message body
	 * 
	 * @throws IllegalAccessException if the method is accessed while 
	 * the body is not ready to be a packed or the method is not 
	 * implemented for the class
	 */
	public byte[] getBody();
	
	/**
	 *** Unique device name.
	 *** 
	 * @return The name of the device
	 **/
	public String getDeviceName();
	
	/**
	 *** Message data type.
	 *** 
	 * @return The data type of the message
	 **/
	public String getDataType();
	
	/**
	 *** header.
	 *** 
	 * @return bytes array containing the header of the message
	 **/
	public Header getHeader();
	
	/**
	 *** To get message String
	 *** 
	 * @return the message String
	 */
	public abstract String toString();

}