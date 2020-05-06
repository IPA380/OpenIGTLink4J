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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import msg.OpenIGTMessage;
import protocol.MessageHandler;
import protocol.MessageParser;

/**
 *** This class represents a server that implements an
 * {@link OpenITGNode}
 * 
 * @author Andreas Rothfuss
 * 
 */
public class Server extends OpenITGNode{

	/** The {@link MessageParser} */
	public final MessageParser messageParser;

	/** The {@link ServerThread} */
	protected ServerThread serverThread;
	/** The {@link ExecutorService} to execute the {@link ServerThread} */
	protected ExecutorService threadPool;

    /**
     * Constructor to create a new {@link Server} and start it
     * 
     * @param serverPort
     * 		the port the server will listen to
     * @param messageHandler
     * 		the message handler that will be called to handle new messages
     * @param maxNumClients
     * 		maximum number of clients allowed to conntect to the server
     **/
	public Server(int serverPort, MessageHandler messageHandler, int maxNumClients, 
			MessageParser messageParser) {
		super(messageHandler);	
		this.messageParser = messageParser;
		this.start(serverPort, maxNumClients);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
	}

    /**
     * Constructor to create a new {@link Server} that will allow connections
     * by up to 256 clients and start it
     * 
     * @param serverPort
     * 		the port the server will listen to
     * @param messageHandler
     * 		the message handler that will be called to handle new messages
     **/
	public Server(int serverPort, MessageHandler messageHandler, 
			MessageParser messageParser) {
		this(serverPort, messageHandler, 256, messageParser);
	}

	protected void initServerThread(int serverPort, int maxNumClients) {
		serverThread = new ServerThread(this, serverPort, maxNumClients, 
				messageParser);
	}

	/**
	 * to start the server
	 **/
	protected void start(int serverPort, int maxNumClients){
		if (serverThread == null) {
			initServerThread(serverPort, maxNumClients);
			threadPool = Executors.newFixedThreadPool(1);
			threadPool.execute(serverThread);			
		}
		else {
			log.warn("serverThread is already running");
		}
	}

	/**
	 * to stop the server
	 **/
	public void stop(){
		serverThread.stop();
		if (threadPool != null)
			threadPool.shutdown();
	}

	/**
	 * to send a message
	 * 
	 * @param message
	 * 		the message to be sent
	 * @throws IOException
	 * 		if the server is not connected
	 */
	public void send(OpenIGTMessage message) throws IOException {
		serverThread.send(message);
	}

	@Override
	public boolean isConnected() {
		if (serverThread != null) {
			return serverThread.isConnected();
		}
		return false;
	}

	@Override
	public boolean isRunning() {
		return serverThread.isAlive();
	}
	
	public String getConnectedAdress() {
		return serverThread.getConnectedAdress();
	}
}
