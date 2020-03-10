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
import java.io.OutputStream;

/**
 * Class representing a {@link Thread} that handles the sending
 * of outgoing messages 
 * 
 * @author Andreas Rothfuss
 *
 */
public class SendRunner extends NetManagerRunner{

	private static final int MAX_TRANSMISSION_UNIT = 1500;

	/**
	 * Destination constructor to create a new {@link SendRunner}
	 * 
	 * @param netManager
	 * 		the {@link NetManager} managing this {@link MessageRunner}
	 */
	public SendRunner(NetManager netManager) {
		super("SendRunner", netManager);
	}

	@Override
	protected void update() {
		if (!netManager.responseQueueIsEmpty()){
			byte[] msg = netManager.pollResponseQueue();
			try {
				OutputStream outStream = netManager.getOutStream();
				/* send the message, MAX_TRANSMISSION_UNIT bytes at a time*/
				for (int i = 0; i < msg.length; i=i+MAX_TRANSMISSION_UNIT) {
					try {
						outStream.write(msg, i, Math.min(MAX_TRANSMISSION_UNIT, msg.length-i));
					}
					catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
					outStream.flush();
				}
			} catch (IOException e) {netManager.report(e);} 
		}
		else{
			synchronized (this) {
				try {
					this.wait(20);
				} catch (InterruptedException e) {}	
			}
		}
	}

}
