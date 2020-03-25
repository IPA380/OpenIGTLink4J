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

import msg.OpenIGTMessage;
import protocol.MessageHandler;
import protocol.MessageParser;

/**
 * Class implementing an abstract client for an OpenIGTLink node
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class OpenIGTLinkClient extends MessageHandler 
	implements IOpenIGTMessageSender, IContainsNetworkedRunnabel {

	/** The {@link MessageParser} */
	public final MessageParser messageParser;
	
	/** The {@link Client} */
	Client client;
	/** The ip address the client will try to connect to */
	protected String ipAdress;
	/** The port the client will try to connect to */
	protected int port;

	/**
	 * Destination constructor
	 * 
	 * @param ipAdress
	 * 		IP-address, the client will try to connect to
	 * @param port
	 * 		port, the client will try to connect to
	 */
	public OpenIGTLinkClient(String ipAdress, int port, 
			MessageParser messageParser) {
		this.ipAdress = ipAdress;
		this.port = port;
		this.messageParser = messageParser;
	}

	/**
	 * to start the client
	 * 
	 * @throws UnknownHostException
	 * 		if the IP address of the host could not be determined
	 * @throws IOException
	 * 		if an I/O error occurs when creating the socket
	 **/
	public void start() throws UnknownHostException, IOException {
		client = new Client(ipAdress, port, this, true, messageParser);
	}

	/**
	 * to start the client
	 * 
	 * @param automaticConnectionRetry
     * 		flag indicating whether the client will attempt an automatic
     * 		reconnect after a disconnect
	 * @throws UnknownHostException
	 * 		if the IP address of the host could not be determined
	 * @throws IOException
	 * 		if an I/O error occurs when creating the socket
	 **/
	public void start(boolean automaticConnectionRetry) throws UnknownHostException, IOException {
		client = new Client(ipAdress, port, this, automaticConnectionRetry, 
				messageParser);
	}

	/**
	 * to stop the client
	 **/
	public void stop() {
		if (client != null) {
			client.stop();
		}
	}

	@Override
	public void send(OpenIGTMessage message) throws IOException {
		client.send(message);
	}


	/**
	 * to send a message if the client is connected
	 * 
	 * @param message
	 * 		the message to be sent
	 * @throws IOException
	 * 		if the client is not connected
	 */
	public void sendIfConnected(OpenIGTMessage msg) throws IOException {
		if (isConnected())
			send(msg);
	}

	@Override
	public boolean isConnected() {
		return client.isConnected();
	}

	@Override
	public boolean isRunning() {
		return client.isRunning();
	}

}