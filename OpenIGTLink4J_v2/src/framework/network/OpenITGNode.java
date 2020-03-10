/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import protocol.IOpenIGTLinkMessageListener;
import protocol.MessageHandler;

/**
 * Class implementing a OpenIGTLink network node
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class OpenITGNode implements IContainsNetworkedRunnabel{

	/**	List of all the message handlers of this node */
	protected LinkedList<IOpenIGTLinkMessageListener> messageHandlers;
	protected Logger log;

	/**
	 * Constructor to create a new {@link OpenITGNode}
	 * 
	 * @param messageHandler
	 * 		the {@link MessageHandler} for the node
	 */
	public OpenITGNode(MessageHandler messageHandler){
		this.messageHandlers = new LinkedList<IOpenIGTLinkMessageListener>();
		this.messageHandlers.add(messageHandler);

		log = LoggerFactory.getLogger(this.getClass());
	}

}
