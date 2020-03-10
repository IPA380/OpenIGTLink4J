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

/**
 * Interface defining methods all OpenIGTLink network node need to implement
 * 
 * @author Andreas Rothfuss
 *
 */
public interface IOpenIGTNetworkNode {
	
	/**
	 * Method called to report {@link IOException}s 
	 * 
	 * @param e
	 * 		the {@link IOException} to report
	 * @param netManager
	 * 		the {@link NetManager} reporting the exception
	 */
	public void report(IOException e, NetManager netManager);

}
