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
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

import network.NetManager;
import network.OpenITGNode;
import network.stream.ServerThread;

public class ForwardServerThread extends ServerThread {

	private String forwardIP;
	private int forwardPort;

	public ForwardServerThread(OpenITGNode server, int port, int maxNumClients, 
			String forwardIP, int forwardPort) {
		super(server, port, maxNumClients);
		this.forwardIP = forwardIP;
		this.forwardPort = forwardPort;
	}

	protected Socket handleClientConnection() throws UnknownHostException, IOException {
		Socket socket = null;
		try {
			socket = new Socket(forwardIP, forwardPort);
			Thread.sleep(500);
			log.info("Gateway connected to " + forwardIP + ":" + forwardPort + " through " + 
					socket.getLocalAddress().toString().replace("/", "") + ":" + socket.getLocalPort() + ".");
		} catch (InterruptedException e) {}
		catch (ConnectException e) {
			log.error("Socket " + forwardIP + ":" + forwardPort + " refused the connection.");
			throw e;
		}
		return socket;
	}

	protected NetManager[] createNewNetManager(Socket clientSocket, 
			Socket additionalSocket) {
		return new NetManager[] {
				new ForwardManager(clientSocket,
						additionalSocket, server, this),
				new ForwardManager(additionalSocket, 
						clientSocket, server, this)};
	}
	
	@Override
	public void report(IOException e, NetManager netManager) {
		log.info("Client " + netManager.getSocketInetAdressString() + " disconnected");
		Iterator<NetManager> iterator = netManagers.iterator();
		while (iterator.hasNext()) {
			NetManager currentNetManager = iterator.next();
			if (currentNetManager == netManager) {
				currentNetManager.stop();
				iterator.remove();
				iterator.next().stop();
				iterator.remove();
			}
		}
	}

}
