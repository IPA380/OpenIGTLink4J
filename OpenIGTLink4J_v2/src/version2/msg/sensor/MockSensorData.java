/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

import java.io.File;
import org.jblas.DoubleMatrix;

import mock.MockMessageData;

/** Class representing mocked sensor objects to be read from file
 * 
 * each line in the file base should contain any number of floats
 * seperated by by spaces
 * 
 * a0 a1 a2 a3 a4 a5
 * a6 a7 a8
 * 
 * additionally the sensor unit and exponent can be set in a commented 
 * out line (starting with a #) by use of the keywords unit and exponent
 * @author Andreas Rothfuss
 *
 */
public class MockSensorData extends MockMessageData<double[]> {
	
	SI_UNIT unit = SI_UNIT.BASE_NONE;
	SI_EXP exp = SI_EXP.PLUS0;
	
	public MockSensorData(String deviceName) {
		super(deviceName);
	}
	
	public MockSensorData(File file) {
		super(file);
	}

	@Override
	protected void parseLine(String string) {
		if (!string.startsWith("#")) {
			DoubleMatrix mat = DoubleMatrix.valueOf(string);
			add(mat.toArray());
		}
		else {
			if (string.contains("unit")) {
				unit = SI_UNIT.fromValue(parseIntegerString(string));
			}
			if (string.contains("exponent")) {
				exp = SI_EXP.fromValue(parseIntegerString(string));
			}
		}
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return new Unit(unit, exp);
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(SI_UNIT unit, SI_EXP exp) {
		this.unit = unit;
		this.exp = exp;
	}

}
