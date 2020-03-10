/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import msg.capability.CapabilityMessageTest;
import msg.image.ImageMessageTest;
import msg.position.PositionMessageTest;
import msg.status.StatusMessageTest;
import msg.transform.TransformMessageTest;

@RunWith(Suite.class)

@SuiteClasses({  
	/* Version 1 */
	CapabilityMessageTest.class,
	ImageMessageTest.class,
	PositionMessageTest.class,
	StatusMessageTest.class,
	TransformMessageTest.class
	
})

public class TestV1Messages {

}
