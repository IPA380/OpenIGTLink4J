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

import network.AbstractMessageRunner;
import network.NetManager;

public class ForwardMessageRunner extends AbstractMessageRunner{

	public ForwardMessageRunner(NetManager netManager) {
		super("ForwardMessageRunner", netManager);
	}

	@Override
	protected void update() {
		if (!netManager.receiveQueueisEmpty()){
			/* there is a new message in the receive queue*/

			/* get the message */
			byte[] msgBytes = netManager.pollReceiveQueue().getMessageBytes();
			try {
				netManager.send(msgBytes);
			} catch (IOException e) {
				netManager.report(e);
			}
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
