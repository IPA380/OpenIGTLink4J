/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network.stream;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import msg.OIGTL_RTSMessage;
import msg.OIGTL_STPMessage;
import msg.sensor.RTSSensorMessage;
import msg.sensor.STPSensorMessage;
import network.IOpenIGTMessageSender;
import network.OpenIGTLinkClient;
import protocol.MessageParser;
import util.RTSMessageStatus;
import util.RunnableFutureCombo;

/**
 * Class implementing an abstract client for an OpenIGTLink node that implements 
 * the basic mechanisms for the OpenIGTLink streaming meachanism
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class OpenIGTLinkStreamingClient extends OpenIGTLinkClient {

	/** the default size of the {@link ScheduledThreadPoolExecutor}  */
	protected static final int DEFAULT_THREADPOOL_SIZE = 10;

	/** the shortest allowed streaming period in milliseconds */
	protected static final int MINIMUM_STREAMING_PERIOD = 50;

	/** the actual size of the {@link ScheduledThreadPoolExecutor}  */
	protected int threadPoolSize;
	/** the {@link ScheduledThreadPoolExecutor} to execute the {@link Thread}s */
	protected ScheduledExecutorService threadPool;
	/** list of all running {@link Thread}s  */
	LinkedList<RunnableFutureCombo> runnables;

	/**
	 * Destination constructor to create a new {@link OpenIGTLinkStreamingClient}
	 * with a thread pool size defined by DEFAULT_THREADPOOL_SIZE
	 * 
	 * @param ipAdress
	 * 		IP-address, the client will try to connect to
	 * @param port
	 * 		port, the client will try to connect to
	 */
	@Deprecated
	public OpenIGTLinkStreamingClient(String ipAdress, int port) {
		this(ipAdress, port, DEFAULT_THREADPOOL_SIZE, null);
	}

	/**
	 * Destination constructor to create a new {@link OpenIGTLinkStreamingClient}
	 * with a thread pool size defined by DEFAULT_THREADPOOL_SIZE
	 * 
	 * @param ipAdress
	 * 		IP-address, the client will try to connect to
	 * @param port
	 * 		port, the client will try to connect to
	 */
	public OpenIGTLinkStreamingClient(String ipAdress, int port, 
			MessageParser messageParser) {
		this(ipAdress, port, DEFAULT_THREADPOOL_SIZE, messageParser);
	}

	/**
	 * Destination constructor  to create a new {@link OpenIGTLinkStreamingClient}
	 * with a thread pool size defined by a paramenter
	 * 
	 * @param ipAdress
	 * 		IP-address, the client will try to connect to
	 * @param port
	 * 		port, the client will try to connect to
	 * @param threadPoolSize
	 * 		the desired size of the threadpool
	 */
	public OpenIGTLinkStreamingClient(String ipAdress, int port, int threadPoolSize, 
			MessageParser messageParser) {
		super(ipAdress, port, messageParser);
		this.threadPoolSize = threadPoolSize;
		runnables = new LinkedList<RunnableFutureCombo>();
	}
	
	@Override
	public void start() throws UnknownHostException, IOException {
		super.start();
		/* create thread pool */
		threadPool = Executors.newScheduledThreadPool(threadPoolSize);
	}

	@Override
	public void stop() {
		/* stop the thread pool*/
		threadPool.shutdownNow();
		super.stop();
	}

	/**
	 * Method called to schedule a {@link StreamRunner} with a fixed update interval
	 * Should be called by sttMessageReceived of any child class to schedule a new {@link StreamRunner}
	 * 
	 * @param fixedRate
	 * 		the update rate for the {@link StreamRunner}, must be above MINIMUM_STREAMING_PERIOD
	 * @param streamRunner
	 * 		the {@link StreamRunner} to be scheduled
	 */
	protected void scheduleStreamRunner(long fixedRate, StreamRunner streamRunner) {
		long streamingPeriod = Math.max(MINIMUM_STREAMING_PERIOD, fixedRate);
		long initialDelay = (long) Math.max(streamingPeriod*0.1, 1);
		if (threadPool == null){
			/* create thread pool */
			threadPool = Executors.newScheduledThreadPool(threadPoolSize);
		}
		ScheduledFuture<?> future = threadPool.scheduleAtFixedRate(streamRunner,
				initialDelay, streamingPeriod, TimeUnit.MILLISECONDS);
		
		runnables.add(new RunnableFutureCombo(streamRunner, future));
	}

	/**
	 * Method called to unschedule a {@link StreamRunner}
	 * Should be called in case an {@link IOExceptio} occurs during the sendReply method of the 
	 * {@link StreamRunner}
	 * 
	 * @param streamRunner
	 * 		the {@link StreamRunner} to be scheduled
	 */
	protected void stopExecution(StreamRunner streamRunner) {
		for (RunnableFutureCombo runnableFutureCombo : runnables) {
			if ( runnableFutureCombo.getRunnable().equals(streamRunner) ) {
				runnableFutureCombo.cancel();
				runnables.remove(runnableFutureCombo);
			}
		}
	}

	@Override
	public OIGTL_RTSMessage stpMessageReceived(OIGTL_STPMessage message, IOpenIGTMessageSender replyTo) {
		/* this instance of stpMessageReceived will look for running streams that
		 * match the message and stop them*/
		log.debug("Message received: " + message.toString());
		
		if ( message instanceof STPSensorMessage) {
			for (RunnableFutureCombo runnableFutureCombo : runnables) {
				if ( runnableFutureCombo.equals(message.getDeviceName(), replyTo)) {
					runnableFutureCombo.cancel();
					runnables.remove(runnableFutureCombo);
					return new OIGTL_RTSMessage(RTSSensorMessage.DATA_TYPE, 
							message.getDeviceName(), RTSMessageStatus.Success);	
				}
			}
		}
		return null;
	}

}
