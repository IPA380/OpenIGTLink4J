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
import java.net.Socket;

import msg.OpenIGTMessage;
import protocol.MessageParser;

/**
 *** This class represents an automatically reconnecting client
 * 
 * @author Andreas Rothfuss
 * 
 */
public class ClientThread extends MyLoopedRunnable implements IOpenIGTMessageSender, IOpenIGTNetworkNode{

	/** The {@link MessageParser} */
	public final MessageParser messageParser;

	/** the ip the {@link ClientThread} will connect to */
	protected String ip;
	/** the port the {@link ClientThread} will connect to */
	protected int port;
	/** the {@link NetManager} that handles the messaging */
	protected NetManager netManager;
	/** the {@link OpenITGNode} that handles messages */
	protected OpenITGNode client;
	private boolean connectionRetry;
	
	/**
     * Constructor to create a new {@link ClientThread} and start it
     * 
     * @param client
     * 		the {@link OpenITGNode} that will handle messages
     * @param ip
     * 		the ip the {@link ClientThread} will connect to
     * @param port
     * 		the port the {@link ClientThread} will connect to
     */
	public ClientThread(OpenITGNode client, String ip, int port, boolean connectionRetry, 
			MessageParser messageParser) {
		super("ClientThread");
		this.client = client;		
		this.ip = ip;
		this.port = port;
		this.connectionRetry = connectionRetry;
		this.messageParser = messageParser;
	}

	@Override
	public void update() {
		/* if we are not connected */
		if (netManager == null) {
			/* connect to the server */
			try {
				netManager = new NetManager(new Socket(ip, port), client, this, messageParser);
			} catch (IOException e) {}
		}
		else {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
		if (connectionRetry == false){
			alive = false;
		}
	}
	
	@Override
	protected String purposeString(){
		return " for OpenIGTLink client connecting to" + ip + ":" + port;
	}
	
	@Override
	public synchronized void stop(){
		super.stop();
		
		/* once stopped, close the client connection */        
		if (netManager != null) {
			netManager.stop();
			netManager = null;
        }
		log.info("Client Stopped.") ;
	}
	
	/**
	 * @return whether the {@link ClientThread} is connected or not
	 */
	public boolean isConnected() {
		return netManager != null && netManager.isConnected();
	}
	
	public String getConnectedAdress() {
		return netManager.getSocketInetAdressString();
	}

	@Override
	public void send(OpenIGTMessage message) throws IOException {
		netManager.send(message);
	}

	@Override
	public void report(IOException e, NetManager netManager) {
		if (this.netManager != null){
			this.netManager.stop();
			this.netManager = null;
		}
	}
	
	public void disableConnectionRetry(){
		this.connectionRetry = false;
	}
	
	public boolean getConnectionRetry(){
		return this.connectionRetry;
	}

}
