/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;

public class RawOpenIGTMessage {
	
	public final byte[] headerBytes;
	public final byte[] body;

	public RawOpenIGTMessage(byte[] header, byte[] body) {
		this.headerBytes = header;
		this.body = body;
	}
	
	public byte[] getMessageBytes() {
		byte[] msgBytes = new byte[headerBytes.length + 
		                           body.length];
		System.arraycopy(headerBytes, 0, msgBytes, 0, headerBytes.length);
		System.arraycopy(body, 0, msgBytes, headerBytes.length, body.length);
		return msgBytes;
	}

}
