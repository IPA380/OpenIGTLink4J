/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.status;

import java.io.File;

import mock.MockMessageData;

/** Class representing mocked status objects to be read from file
 * 
 * each line in the file base should contain a code, subcode, error-name
 * and error description seperated by semicolons
 * 
 * 0x01; 0x00; Test1; first test state
 * @author Andreas Rothfuss
 *
 */
public class MockStati extends MockMessageData<Status> {
	
	public MockStati(String deviceName) {
		super(deviceName);
	}
	
	public MockStati(File file) {
		super(file);
	}

	@Override
	protected void parseLine(String string) {
		if (!string.startsWith("#")) {
			String[] values = string.split(";");
			int code = parseIntegerString(values[0]);
			int subCode = parseIntegerString(values[1]);
			add(new Status(code, subCode, values[2], values[3]));
		}
	}

}
