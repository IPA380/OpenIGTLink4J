/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.trajectory;

import java.io.File;

import mock.MockMessageData;

/** Class representing mocked trajectory objects to be read from file
 * 
 * each line in the file base should contain any number of trajectories
 * seperated by semicolons
 * each trajectory should consist of its name, group name, entry-  and 
 * target position, radius and owner separated by kommata
 * 
 * testTraj, testTrajs, 0, 0, 0, 0, 0, 0, 0, none
 * @author Andreas Rothfuss
 *
 */
public class MockTrajectories extends MockMessageData<Trajectory> {
	
	
	public MockTrajectories(String deviceName) {
		super(deviceName);
	}
	
	public MockTrajectories(File file) {
		super(file);
	}

	@Override
	protected void parseLine(String string) {
		if (!string.startsWith("#")) {
			String[] values = string.split(";");
			Trajectory trajectorie = new Trajectory();
			for (int i = 0; i < values.length; i++) {
				String[] parts = values[i].split(",");
				trajectorie.addTrajectoryElement(new TrajectoryElement(
						parts[0], parts[1], TRAJ_TYPE.ENTRY_TARGET, 
						new float[] {Float.valueOf(parts[2]), Float.valueOf(parts[3]), Float.valueOf(parts[4])}, 
						new float[] {Float.valueOf(parts[5]), Float.valueOf(parts[6]), Float.valueOf(parts[7])}, 
						new byte[4], Float.valueOf(parts[8]), parts[9]));
			}
			add(trajectorie);
		}
	}

}
