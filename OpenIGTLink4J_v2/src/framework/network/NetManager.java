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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import msg.OpenIGTMessage;
import msg.RawOpenIGTMessage;
import protocol.IOpenIGTLinkMessageListener;
import protocol.MessageHandler;
import protocol.MessageParser;

/**
 * Class managing the components needed to implement the 
 * OpenIGTLink messaging protocol
 * 
 * @author Andreas Rothfuss
 *
 */
public class NetManager implements IOpenIGTMessageSender{

	/** The {@link MessageParser} */
	public final MessageParser messageParser;

	/** The {@link ExecutorService} to execute the different runners */
	protected ExecutorService threadPool;	
	/** The {@link Runnable} to handle incoming of messages */
	protected ReceiveRunner receiveRunner;
	/** The {@link Runnable} to handle outgiong of messages */
	protected SendRunner sendRunner;
	/** The {@link Runnable} to handle the protocol for messages */
	protected NetManagerRunner messageRunner;
	/** The Socket handling the network connection */
	protected NetManagerRunner socketRunner;
	/** The Socket handling the network connection */
	protected Socket socket;

	/** The {@link OpenITGNode} holding the {@link MessageHandler}s */
	protected OpenITGNode node;	
	/** The network node */
	protected IOpenIGTNetworkNode networkNode;
	/** Queue holding incoming messages */
	protected ConcurrentLinkedQueue<RawOpenIGTMessage> receiveQueue;
	/** Queue holding outgoing messages */
	protected ConcurrentLinkedQueue<byte[]> responseQueue;
	private Logger log;

	/**
	 * Constructor to create a new {@link NetManager}
	 * 
	 * @param socket
	 * 		the {@link Socket} handling the network connection
	 * @param node
	 * 		the {@link OpenITGNode} holding the {@link MessageHandler}s
	 * @param networkNode
	 * 		the network node
	 */
	public NetManager(Socket socket, OpenITGNode node, IOpenIGTNetworkNode networkNode, 
			MessageParser messageParser){
		log = LoggerFactory.getLogger(this.getClass());
		
		if (messageParser == null){
			this.messageParser = new MessageParser(false);
		}
		else {
			this.messageParser = messageParser;
		}
		
		this.socket = socket;
		this.node = node;
		this.networkNode = networkNode;

		receiveQueue = new ConcurrentLinkedQueue<RawOpenIGTMessage>();
		responseQueue = new ConcurrentLinkedQueue<byte[]>();

		receiveRunner = new ReceiveRunner(this);
		sendRunner = new SendRunner(this);
		initMessageRunner();
		socketRunner = new NetManagerRunner("SocketRunner", this) {
			
			@Override
			protected void update() {
				if (!netManager.isConnected()) {
					netManager.report(new IOException("Socket closed"));
				}
				else {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {}
				}
			}
		};

		threadPool = Executors.newFixedThreadPool(4);

		threadPool.execute(receiveRunner);	
		threadPool.execute(messageRunner);	
		threadPool.execute(sendRunner);		
		threadPool.execute(socketRunner);
	}

	protected void initMessageRunner() {
		messageRunner = new MessageRunner(this);
	}

	/**
	 * Method to stop the {@link NetManager}
	 */
	public void stop() {
		receiveRunner.stop();
		sendRunner.stop();
		messageRunner.stop();
		socketRunner.stop();
		
		threadPool.shutdownNow();

		if(!socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				log.error("Could not stop the socket of the " + NetManager.class.getName() + 
						" instance." + e.getCause());
			}
		}
	}
	
	@Override
	protected void finalize() {
		stop();
	}

	/**
	 * Method to report an {@link IOException} occurring in one of the {@link Runnable}s
	 * @param e
	 * 		the {@link IOException} that occured
	 */
	public void report(IOException e) {
		stop();
		networkNode.report(e, this);
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
		return socket.getOutputStream();
	}

	/**
	 * Method to get the {@link InputStream}
	 * @return
	 * 		the {@link InputStream}
	 * @throws IOException
	 * 		if an I/O error occurs when creating the output stream or if the socket 
	 * 		is not connected
	 */
	public InputStream getInStream() throws IOException {
		return socket.getInputStream();
	}

	/**
	 * @return whether the outgoing message queue is empty or not
	 */
	public boolean responseQueueIsEmpty() {
		return responseQueue.isEmpty();
	}

	/**
	 * Method to poll the outgoing message queue
	 * 
	 * @return the polled message
	 */
	public synchronized byte[] pollResponseQueue() {
		return responseQueue.poll();
	}

	/**
	 * @return whether the is a message handler available
	 */
	public boolean listenersIsEmpty() {
		return node.messageHandlers.isEmpty();
	}

	/**
	 * @return whether the incoming message queue is empty or not
	 */
	public boolean receiveQueueisEmpty() {
		return receiveQueue.isEmpty();
	}

	/**
	 * Method to poll the incoming message queue
	 * 
	 * @return the polled message
	 */
	public RawOpenIGTMessage pollReceiveQueue() {
		return receiveQueue.poll();
	}

	/**
	 * Method to get the available message handlers
	 * 
	 * @return the polled message
	 */
	public LinkedList<IOpenIGTLinkMessageListener> getListeners(){
		return node.messageHandlers;
	}

	/**
	 * Method to add a message to the incoming messages queue
	 * 
	 * @param msg
	 * 		the message to be added to the incoming messages queue
	 */
	public void addToReceiveQueue(RawOpenIGTMessage msg) {
		receiveQueue.add(msg);
		synchronized (messageRunner) {
			messageRunner.notify();
		}
	}

	@Override
	public void send(OpenIGTMessage msg) throws IOException {
		log.trace("Sending message: " + msg.toString(), Byte.MAX_VALUE);
		
		messageParser.modifyBeforeSend(msg);
		
		byte[] msgBytes = msg.getBytes();
		send(msgBytes);
	}

	public void send(byte[] msgBytes) throws IOException {
		if (sendRunner.alive) {
			if (msgBytes != null && msgBytes.length > 0) {
				responseQueue.add(msgBytes);
				synchronized (sendRunner) {
					sendRunner.notify();
				}
			}
		}
		else {
			IOException toThrow = new IOException("Not Connected any more.");
			report(toThrow);
			throw toThrow;
		}
	}

	/**
	 * Method to get the address the {@link Socket} is connected to
	 * @return
	 */
	public String getSocketInetAdressString() {
		String iNetAdress = "null";
		try{
			iNetAdress = socket.getInetAddress().toString().replace("/", "") + 
					":" + socket.getPort();
		}
		catch(NullPointerException e) {}
		return iNetAdress;
	}

	/**
	 * @return the commection state of the {@link Socket}
	 */
	public boolean isConnected() {
		return (socket != null && !socket.isClosed() && socket.isConnected());
	}
}
