/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.point;

import java.io.File;
import org.jblas.FloatMatrix;

import mock.MockMessageData;

/** Class representing mocked point objects to be read from file
 * 
 * each line in the file base should contain any number of points
 * seperated by semicolons with three floats each separated by spaces
 * 
 * x1 y1 z1, r1 g1 b1 a1, rad1; x2 y2 z2; x3 y3 z3; 
 * @author Andreas Rothfuss
 *
 */
public class MockPoints extends MockMessageData<Points> {
	
	
	public MockPoints(String deviceName) {
		super(deviceName);
	}
	
	public MockPoints(File file) {
		super(file);
	}

	@Override
	protected void parseLine(String string) {
		if (!string.startsWith("#")) {
			String[] values = string.split(";");
			Points points = new Points();
			for (int i = 0; i < values.length; i++) {
				String[] parts = values[i].split(",");
				FloatMatrix mat = FloatMatrix.valueOf(parts[0]);
				PointElement elem = new PointElement(mat.get(0), mat.get(1), mat.get(2));
				if (parts.length > 1) {
					FloatMatrix rgba = FloatMatrix.valueOf(parts[1]);
					elem.setRGBA((byte)rgba.get(0), (byte)rgba.get(1), (byte)rgba.get(2), (byte)rgba.get(3));
				}
				if (parts.length > 2) {
					FloatMatrix rad = FloatMatrix.valueOf(parts[2]);
					elem.setRadius(rad.get(0));
				}
				points.addElement(elem);
			}
			add(points);
		}
	}

}
