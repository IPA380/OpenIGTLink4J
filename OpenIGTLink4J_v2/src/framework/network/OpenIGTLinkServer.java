/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import java.io.IOException;

import org.slf4j.LoggerFactory;

import msg.OpenIGTMessage;
import protocol.MessageHandler;
import protocol.MessageParser;

public abstract class OpenIGTLinkServer extends MessageHandler
		implements IOpenIGTMessageSender, IContainsNetworkedRunnabel {
	
	/** The {@link Server} */
	Server server;
	/** The port the server will listen to */
	protected int port;

	/**
	 * Destination constructor
	 * 
	 * @param port
	 *            port, the server will be listening to
	 */
	@Deprecated
	public OpenIGTLinkServer(int port) {
		this.port = port;
		log = LoggerFactory.getLogger(this.getClass());
		server = new Server(port, this, null);
	}

	/**
	 * Destination constructor
	 * 
	 * @param port
	 *            port, the server will be listening to
	 */
	public OpenIGTLinkServer(int port, MessageParser messageParser) {
		this.port = port;
		log = LoggerFactory.getLogger(this.getClass());
		server = new Server(port, this, messageParser);
	}
	
	/**
	 * Destination constructor
	 * 
	 * @param port
	 *            port, the server will be listening to
     * @param maxNumClients
     * 		maximum number of clients allowed to conntect to the server
	 */
	public OpenIGTLinkServer(int port, int maxNumClients, MessageParser messageParser) {
		this.port = port;
		log = LoggerFactory.getLogger(this.getClass());
		server = new Server(port, this, maxNumClients, messageParser);
	}

	/**
	 * to start the server
	 **/
	protected void start(int serverPort, int maxNumClients) {
		server.start(serverPort, maxNumClients);
	}

	/**
	 * to stop the client
	 **/
	public void stop() {
		server.stop();
	}

	@Override
	public void send(OpenIGTMessage message) throws IOException {
		server.send(message);
	}

	@Override
	public boolean isRunning() {
		if (server != null)
			return server.isRunning();
		else
			return false;
	}

	@Override
	public boolean isConnected() {
		return server.isConnected();
	}

}