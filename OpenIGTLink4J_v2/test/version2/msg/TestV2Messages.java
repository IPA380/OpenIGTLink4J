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

import msg.point.PointMessageTest;
import msg.sensor.SensorMessageTest;
import msg.string.StringMessageTest;
import msg.tdata.TrackingDataMessageTest;
import msg.trajectory.TrajectoryMessageTest;

@RunWith(Suite.class)

@SuiteClasses({  
	
	/* Version 2 */
	PointMessageTest.class,
	SensorMessageTest.class,
	StringMessageTest.class,
	TrackingDataMessageTest.class,
	TrajectoryMessageTest.class
	
})

public class TestV2Messages {

}
