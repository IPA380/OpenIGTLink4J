/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import protocol.MessageParser;

public abstract class AbstractMessageRunner extends NetManagerRunner {

	/** The {@link MessageParser} */
	public static MessageParser messageParser;

	public AbstractMessageRunner(String name, NetManager netManager) {
		super(name, netManager);
		if (messageParser == null){
			messageParser = new MessageParser();
		}
	}

}