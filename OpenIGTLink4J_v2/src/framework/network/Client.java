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
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import msg.OpenIGTMessage;
import protocol.MessageHandler;

/**
 *** This class represents a client that implements an
 * {@link OpenITGNode}
 * 
 * @author Andreas Rothfuss
 * 
 */
public class Client extends OpenITGNode {

	/** The {@link ClientThread} */
	protected ClientThread clientThread;
	/** The {@link ExecutorService} to execute the {@link ClientThread} */
    protected ExecutorService threadPool;

    /**
     * Constructor to create a new {@link Client} and start it
     * 
     * @param ip
     * 		the ip the client will connect to
     * @param port
     * 		the port the client will connect to
     * @param messageHandler
     * 		the message handler that will be called to handle new messages
     * @param automaticConnectionRetry
     * 		flag indicating whether the client will attempt an automatic
     * 		reconnect after a disconnect
     **/
	public Client(String ip, int port, MessageHandler messageHandler, boolean automaticConnectionRetry) throws UnknownHostException, IOException {
		super(messageHandler);
		this.start(ip, port, automaticConnectionRetry);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
	}

	/**
	 * to start the client
	 * 
	 * @param automaticConnectionRetry
     * 		flag indicating weather the client will attempt an automatic
     * 		reconnect after a disconnect
	 * @throws UnknownHostException
	 * 		if the IP address of the host could not be determined
	 * @throws IOException
	 * 		if an I/O error occurs when creating the socket
	 **/
	protected void start(String ip, int port, boolean automaticConnectionRetry) throws UnknownHostException, IOException {
		if (clientThread == null) {
			clientThread = new ClientThread(this, ip, port, automaticConnectionRetry);
		}
		if (!clientThread.isConnected()){
			threadPool = Executors.newFixedThreadPool(1);
			threadPool.execute(clientThread);
		}
		else{
			log.warn("ClientThread is already running");
		}
	}
	
	/**
	 * to stop the client
	 **/
	public void stop() {
		clientThread.stop();
		if (threadPool != null)
			threadPool.shutdown();
	}

	/**
	 * to send a message
	 * 
	 * @param message
	 * 		the message to be sent
	 * @throws IOException
	 * 		if the client is not connected
	 */
	public void send(OpenIGTMessage message) throws IOException {
		clientThread.send(message);
	}

	@Override
	public boolean isConnected() {
		return clientThread != null && clientThread.isConnected();
	}
	
	public String getConnectedAdress() {
		return clientThread.getConnectedAdress();
	}

	@Override
	public boolean isRunning() {
		return clientThread.isAlive();
	}

}
