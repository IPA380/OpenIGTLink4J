/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;



/**
 * Class representing a {@link Thread} handled by a {@link NetManager} 
 * 
 * @author Andreas Rothfuss
 *
 */
public abstract class NetManagerRunner extends MyLoopedRunnable{

	/** the {@link NetManager} to be executed*/
	protected NetManager netManager;
	/**
	 * Constructor to create a new {@link NetManagerRunner}
	 */
	public NetManagerRunner(String name, NetManager netManager){
		super(name);
		this.netManager = netManager;
	}
	
	@Override
	protected String purposeString(){
		String iNetAdress = "null";
		try{
			iNetAdress = netManager.getSocketInetAdressString();
		}
		catch (NullPointerException e) {}
		return " for NetManger " + iNetAdress;
	}
}
