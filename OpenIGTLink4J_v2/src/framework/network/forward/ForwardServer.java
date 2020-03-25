/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network.forward;

import network.Server;
import protocol.MessageHandler;
import protocol.MessageParser;

public class ForwardServer extends Server {

	public final String forwardIP;
	public final int forwardPort;

	public ForwardServer(int serverPort, MessageHandler messageHandler, int maxNumClients, 
			String forwardIP, int forwardPort, MessageParser messageParser) {
		super(serverPort, messageHandler, maxNumClients, messageParser);
		this.forwardIP = forwardIP;
		this.forwardPort = forwardPort;
	}

	protected void initServerThread(int serverPort, int maxNumClients) {
		serverThread = new ForwardServerThread(this, serverPort, maxNumClients, 
				forwardIP, forwardPort, messageParser);
	}

}
