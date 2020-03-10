/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network.stream;

import network.IOpenIGTMessageSender;

/**
 * Class representing a {@link Thread} handled by a streaming nocde 
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class StreamRunner implements Runnable {

	/** device name for messages */
	public final String deviceName;
	/** target for streaming messages */
	public final IOpenIGTMessageSender replyTo;

	/**
	 * Constructor to create a new {@link StreamRunner}
	 * 
	 * @param deviceName
	 * 		device name for streaming messages
	 * @param replyTo
	 * 		target for streaming messages
	 */
	public StreamRunner(String deviceName, IOpenIGTMessageSender replyTo){
		this.deviceName = deviceName;
		this.replyTo = replyTo;
	}

	@Override
	public final void run(){
		sendReply();
	}
	
	/** 
	 * Prototype method for replying
	 * 
	 *  Should look like this:
	 *  protected void sendReply() {
	 *		try {
	 *			replyTo.sendMessage(new OIGTL_DataMessage(deviceName));
	 *		} catch (IOException e) {e.printStackTrace();
	 *	}
	 * 
	 */
	protected abstract void sendReply();
}
