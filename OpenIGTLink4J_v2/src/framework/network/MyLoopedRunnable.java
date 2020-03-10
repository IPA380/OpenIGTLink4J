/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class MyLoopedRunnable implements Runnable{

	/** alive flag */
	public boolean alive;
	/** universal unique id for identification */
	private UUID uuid;
	
	protected String name;
	protected Logger log;

	public MyLoopedRunnable(String name) {
		this.name = name;
		log = LoggerFactory.getLogger(this.getClass());
		
		uuid = UUID.randomUUID();
		alive = true;
	}

	@Override
	public final void run() {
        final String orgName = Thread.currentThread().getName();
        Thread.currentThread().setName(name);
        try {
        	log.trace(this.getClass().getSimpleName() + purposeString() + " started. UUID: " + uuid);
			while(alive){
				update();
			}
			log.trace(this.getClass().getSimpleName() + purposeString() + " stopped. UUID: " + uuid);
        } finally {
            Thread.currentThread().setName(orgName);
        }
	}
	
	protected String purposeString(){return "";}

	/**
	 * Method that will be called by the {@link NetManagerRunner}
	 * This method will be called repeatedly by {@link MyLoopedRunnable}.
	 * In order for the Thread to be able to exit, update should only 
	 * block for limited times.
	 * If the Thread needs to be closed from inside the thread call 
	 * {@link MyLoopedRunnable}.stop() 
	 */
	protected abstract void update();

	/**
	 * @return whether the is alive or not
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 *	to stop a running {@link MyLoopedRunnable}
	 */
	public synchronized void stop(){
		alive = false;
	}

}