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
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.net.ServerSocketFactory;

import msg.OpenIGTMessage;
import protocol.MessageParser;

/**
 *** This class represents an server that accepts clients automatically
 * 
 * @author Andreas Rothfuss
 * 
 */
public class ServerThread extends MyLoopedRunnable implements IOpenIGTMessageSender, IOpenIGTNetworkNode{

	public static ServerSocketFactory DEFAULT_SOCKET_FACTORY = ServerSocketFactory.getDefault();
	
	private ServerSocketFactory socketFactory;
	
	/** The {@link MessageParser} */
	public final MessageParser messageParser;

	/** the port the {@link ServerThread} will listen to */
	protected int serverPort;
	/** the {@link ServerSocket} */
	protected ServerSocket serverSocket = null;
	/** Queue with all {@link NetManager}s connected to clients */
	protected ConcurrentLinkedQueue<NetManager> netManagers;
	/** maximum number of client connections*/
	protected int maxNumClients;
	/** pointer to the current running {@link Thread} */
	protected Thread runningThread = null;
	/** the {@link OpenITGNode} that handles messages */
	protected OpenITGNode server;

	/**
     * Constructor to create a new {@link ServerThread} and start it
     * 
     * @param client
     * 		the {@link OpenITGNode} that will handle messages
     * @param port
     * 		the port the {@link ServerThread} will lsiten to
     */
	public ServerThread(OpenITGNode server, int port, int maxNumClients, 
			MessageParser messageParser){
		this(server, port, maxNumClients, messageParser, null);
	}

	/**
     * Constructor to create a new {@link ServerThread} and start it
     * 
     * @param client
     * 		the {@link OpenITGNode} that will handle messages
     * @param ip
     * 		the ip the {@link ClientThread} will connect to
     * @param port
     * 		the port the {@link ClientThread} will connect to
	 * @param socketFactory 
	 * 		the {@link ServerSocketFactory} to be used to create sockets
     */
	public ServerThread(OpenITGNode server, int port, int maxNumClients, 
			MessageParser messageParser, ServerSocketFactory socketFactory){
		super("ServerThread");
		this.server = server;
	    this.serverPort = port;
	    this.maxNumClients = maxNumClients;
	    this.messageParser = messageParser;
		if (socketFactory != null) {
			this.socketFactory = socketFactory;
		}
		else {
			this.socketFactory = DEFAULT_SOCKET_FACTORY;
		}
	    
	    netManagers = new ConcurrentLinkedQueue<NetManager>();

        openServerSocket();
	}
	
	@Override
    public void update(){
    	/* if another client is allowed */
    		/* wait for new connection */
            Socket clientSocket = null;
            Socket additionalSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            	if (netManagers.size() < maxNumClients){
            		log.info("Client " + clientSocket.getInetAddress().toString().replace("/", "") 
                		+ ":" + clientSocket.getPort() + " connected to server " + 
                		clientSocket.getLocalSocketAddress().toString().replace("/",  ""));
	 	           	additionalSocket = handleClientConnection();
            	}
            	else {
            		log.info("Client " + clientSocket.getInetAddress().toString().replace("/", "") 
                    		+ ":" + clientSocket.getPort() + " rejected. Maximum number of client "
                    				+ "connections exceeded");
            		clientSocket.close();
            	}
            } catch (IOException e) {
            	log.trace("Caught an " + e.getClass());
                if(isAlive()) {
	                if (e instanceof ConnectException) {
						try {
							clientSocket.close();
							clientSocket = null;
						} catch (IOException e1) {log.warn("Caught an " + e1.getClass());}
					}
	                else{
	                	throw new RuntimeException("Error accepting client connection", e);
	                }
                }
            }
            if (clientSocket != null) {
            	/* add new NetManager */
	            NetManager[] newNetManagers = createNewNetManager(clientSocket, additionalSocket);
	            for (int i = 0; i < newNetManagers.length; i++) {
		            netManagers.add(newNetManagers[i]);
				}
			}	  
    	else{
    		try {
				Thread.sleep(20);
			} catch (InterruptedException e) {log.warn("Sleep interrupted");}
    	}
	}
	
	protected Socket handleClientConnection() throws UnknownHostException, IOException {
		return null;
	}

	protected NetManager[] createNewNetManager(Socket clientSocket, 
			Socket additionalSocket) {
		return new NetManager[] {new NetManager(clientSocket, server, this, messageParser)};
	}
	
	@Override
	protected String purposeString(){
		return " for OpenIGTLink server listenting to port " + serverPort;
	}

	/**
	 *	to stop a running {@link ServerThread}
	 */
	public void stop(){
		super.stop();
	    /* once stopped, close all client connections */
	    for (NetManager netManager : netManagers) {
			netManager.stop();
		}
	    try {
	        this.serverSocket.close();
	    } catch (IOException e) {
	        throw new RuntimeException("Error stopping server", e);
	    }
	}

	/**
	 * to open the {@link ServerSocket} and start waiting for {@link Client}
	 * connections
	 */
	void openServerSocket() {
	    try {
	        this.serverSocket = socketFactory.createServerSocket(this.serverPort);
	    } catch (IOException e) {
	        throw new RuntimeException("Cannot open port " + serverPort, e);
	    }
	}

	@Override
	public void send(OpenIGTMessage message) throws IOException {
		for (NetManager netManager : netManagers) {
			netManager.send(message);
		}
	}
	
	@Override
	public void report(IOException e, NetManager netManager) {
		log.info("Client " + netManager.getSocketInetAdressString() + " disconnected. Cause: " + e);
		netManager.stop();
		netManagers.remove(netManager);
	}

	/**
	 * @return whether the {@link ClientThread} is connected or not
	 */
	public boolean isConnected() {
		return !netManagers.isEmpty();
	}

	public String getConnectedAdress() {
		return netManagers.peek().getSocketInetAdressString();
	}
}
