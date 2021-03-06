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
import java.net.UnknownHostException;

import javax.net.SocketFactory;
import msg.OpenIGTMessage;
import protocol.MessageParser;

/**
 *** This class represents an automatically reconnecting client
 * 
 * @author Andreas Rothfuss
 * 
 */
public class ClientThread extends MyLoopedRunnable implements IOpenIGTMessageSender, IOpenIGTNetworkNode{

//	private static final String[] protocols = new String[] {"TLSv1.1"};
//	private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};

	public static SocketFactory DEFAULT_SOCKET_FACTORY = SocketFactory.getDefault();
	
	private SocketFactory socketFactory;

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
		this(client, ip, port, connectionRetry, messageParser, null);
	}
	
	/**
     * Constructor to create a new {@link ClientThread} and start it
     * 
     * @param client
     * 		the {@link OpenITGNode} that will handle messages
     * @param ip
     * 		the ip the {@link ClientThread} will connect to
     * @param port
     * 		the port the {@link ClientThread} will connect to
	 * @param socketFactory 
	 * 		the {@link SocketFactory} to be used to create sockets
     */
	public ClientThread(OpenITGNode client, String ip, int port, boolean connectionRetry, 
			MessageParser messageParser, SocketFactory socketFactory) {
		super("ClientThread");
		this.client = client;		
		this.ip = ip;
		this.port = port;
		this.connectionRetry = connectionRetry;
		this.messageParser = messageParser;
		if (socketFactory != null) {
			this.socketFactory = socketFactory;
		}
		else {
			this.socketFactory = DEFAULT_SOCKET_FACTORY;
		}
	}

	@Override
	public void update() {
		/* if we are not connected */
		if (netManager == null) {
			/* connect to the server */
			try {
				netManager = new NetManager(
						createSocket(), 
						client, this, messageParser);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	private Socket createSocket() throws IOException, UnknownHostException {
		Socket retVal = socketFactory.createSocket(ip, port);
//		if (retVal instanceof SSLSocket) {
//			((SSLSocket)retVal).setEnabledProtocols(protocols);
//			((SSLSocket)retVal).setEnabledCipherSuites(cipher_suites);
//		}
		return retVal;
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
		if (netManager == null) {
			throw new IOException("Not Connected");
		}
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
