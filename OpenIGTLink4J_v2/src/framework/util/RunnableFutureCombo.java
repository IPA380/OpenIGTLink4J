/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

import java.util.concurrent.ScheduledFuture;

import network.IOpenIGTMessageSender;
import network.stream.StreamRunner;

/**
 * This class is used to create an object to store a {@link StreamRunner}
 * in combination with its {@link ScheduledFuture}
 * 
 * @author Andreas Rothfuss
 *
 */
public class RunnableFutureCombo {

	/** The {@link StreamRunner} */
	StreamRunner runnable;
	public StreamRunner getRunnable() {
		return runnable;
	}

	public ScheduledFuture<?> getFuture() {
		return future;
	}


	/** The {@link ScheduledFuture} */
	ScheduledFuture<?> future;

	/**
	 * Constructor to create an object
	 */
	public RunnableFutureCombo(StreamRunner runnable, ScheduledFuture<?> future) {
		this.runnable = runnable;
		this.future = future;
	}

	/**
	 * Method to compare the runnable with deviceName and streaming message destiantion
	 * 
	 * @return 
	 * 		true, if deviceName and reply to both equal the stored ones and
	 * 		false otherwise
	 */
	public boolean equals(String deviceName, IOpenIGTMessageSender replyTo) {
		return runnable.deviceName.equals(deviceName) && runnable.replyTo.equals(replyTo);
	}

	
	/**
	 * Attempts to cancel execution of this task. This attempt will fail if the task 
	 * has already completed, has already been cancelled, or could not be cancelled 
	 * for some other reason. If successful, and this task has not started when 
	 * cancel is called, this task should never run. If the task has already started, 
	 * then the mayInterruptIfRunning parameter determines whether the thread executing 
	 * this task should be interrupted in an attempt to stop the task. 
	 * 
	 * After this method returns, subsequent calls to isDone will always return 
	 * true. Subsequent calls to isCancelled will always return true if this method 
	 * returned true.
	 * 
	 * @returns false if the task could not be cancelled, typically because it has 
	 * already completed normally; true otherwise
	 */
	public void cancel() {
		future.cancel(false);
	}

}
