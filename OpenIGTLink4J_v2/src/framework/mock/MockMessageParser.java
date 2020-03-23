/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package mock;

import java.io.File;
import java.util.ArrayList;

import msg.point.MockPoints;
import msg.sensor.MockSensorData;
import msg.status.MockStati;
import msg.trajectory.MockTrajectories;
import msg.transform.MockTransfroms;

public class MockMessageParser {
	
	public static ArrayList<MockStati> parseStatusFiles(File[] files){ 
		if (files != null && files.length != 0) {
			ArrayList<MockStati> stati = 
					new ArrayList<MockStati>();
			for (int i = 0; i < files.length; i++) {
				stati.add(new MockStati(files[i]));
			}
			return stati;
		}
		return null;
	}
	
	public static ArrayList<MockSensorData> parseSensorFiles(File[] files){ 
		if (files != null && files.length != 0) {
			ArrayList<MockSensorData> sensors = 
					new ArrayList<MockSensorData>();
			for (int i = 0; i < files.length; i++) {
				sensors.add(new MockSensorData(files[i]));
			}
			return sensors;
		}
		return null;
	}

	public static ArrayList<MockTransfroms> parseTransfromFiles(File[] files){ 
		if (files != null && files.length != 0) {
			ArrayList<MockTransfroms> transfroms = 
					new ArrayList<MockTransfroms>();
			for (int i = 0; i < files.length; i++) {
				transfroms.add(new MockTransfroms(files[i]));
			}
			return transfroms;
		}
		return null;
	}

	public static ArrayList<MockPoints> parsePointsFiles(File[] files){ 
		if (files != null && files.length != 0) {
			ArrayList<MockPoints> points = 
					new ArrayList<MockPoints>();
			for (int i = 0; i < files.length; i++) {
				points.add(new MockPoints(files[i]));
			}
			return points;
		}
		return null;
	}


	public static ArrayList<MockTrajectories> parseTrajectorieFiles(File[] files){ 
		if (files != null && files.length != 0) {
			ArrayList<MockTrajectories> trajectories = 
					new ArrayList<MockTrajectories>();
			for (int i = 0; i < files.length; i++) {
				trajectories.add(new MockTrajectories(files[i]));
			}
			return trajectories;
		}
		return null;
	}	

}
