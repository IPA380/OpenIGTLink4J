/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network.forward;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import network.IOpenIGTNetworkNode;
import network.NetManager;
import network.OpenITGNode;
import protocol.MessageHandler;

/**
 * Class managing the components needed to implement the 
 * OpenIGTLink messaging protocol
 * 
 * @author Andreas Rothfuss
 *
 */
public class ForwardManager extends NetManager{

	Socket socket1;

	/**
	 * Constructor to create a new {@link ForwardManager}
	 * 
	 * @param socket
	 * 		the {@link Socket} handling the network connection
	 * @param node
	 * 		the {@link OpenITGNode} holding the {@link MessageHandler}s
	 * @param networkNode
	 * 		the network node
	 */
	public ForwardManager(Socket socket, Socket socket1, 
			OpenITGNode node, IOpenIGTNetworkNode networkNode){
		super(socket, node, networkNode);
		this.socket1 = socket1;	
	}
	
	@Override
	protected void initMessageRunner() {
		messageRunner = new ForwardMessageRunner(this);
	}
	
	/**
	 * Method to get the {@link OutputStream}
	 * @return
	 * 		the {@link OutputStream}
	 * @throws IOException
	 * 		if an I/O error occurs when creating the output stream or if the socket 
	 * 		is not connected
	 */
	public OutputStream getOutStream() throws IOException {
		return socket1.getOutputStream();
	}


	/**
	 * Method to get the address the {@link Socket} is connected to
	 * @return
	 */
	public String getSocket1InetAdressString() {
		return socket1.getInetAddress().toString().replace("/", "");
	}
	
	/**
	 * @return the commection state of the {@link Socket}
	 */
	public boolean isConnected() {
		return (socket != null && !socket.isClosed() && socket.isConnected() &&
				socket1 != null && !socket1.isClosed() && socket1.isConnected());
	}
}
