/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;

public class NoReplyDataMessage extends OIGTL_DataMessage implements INoReplyMessage{

	public NoReplyDataMessage() {
		super("", "");
	}
	
	@Override
	public byte[] getBody() {
		return null;
	}

	@Override
	public boolean UnpackBody(byte[] body) {
		return false;
	}

}
