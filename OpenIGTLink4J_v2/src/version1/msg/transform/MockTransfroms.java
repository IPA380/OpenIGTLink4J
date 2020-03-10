/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.transform;

import java.io.File;

import org.jblas.FloatMatrix;

import mock.MockMessageData;

/** Class representing mocked transform objects to be read from file
 * 
 * each line in the file base should contain a transform cosisting of three
 * float arrays of four elements each separated by semicolons
 * the three float array represent the the first three lines of the homogenous 
 * transformation
 * 
 * 1 0 0 -10; 0 1 0 -20; 0 0 1 -30
 * @author Andreas Rothfuss
 *
 */
public class MockTransfroms extends MockMessageData<Transform> {
	
	public MockTransfroms(String deviceName) {
		super(deviceName);
	}
	
	public MockTransfroms(File file) {
		super(file);
	}

	@Override
	protected void parseLine(String string) {
		if (!string.startsWith("#")) {
			FloatMatrix mat = FloatMatrix.valueOf(string);
			add(new Transform(
					mat.getRange(0, 3, 3, 4).toArray(), 
					mat.getRange(0, 3, 0, 3).toArray2()));
		}
	}

}
