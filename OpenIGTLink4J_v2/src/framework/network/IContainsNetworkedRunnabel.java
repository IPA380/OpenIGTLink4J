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
 * Interface defining methods all classes containing a networked 
 * runnable need to implement
 * 
 * @author Andreas Rothfuss
 *
 */
public interface IContainsNetworkedRunnabel {
	
	/**
	 * @return the client connection state
	 */
	public boolean isConnected();

	/**
	 * @return whether the client is running or not
	 */
	public boolean isRunning();

}
