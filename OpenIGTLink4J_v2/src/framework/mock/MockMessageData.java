/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.LoggerFactory;


public abstract class MockMessageData<T> {

	public final String deviceName;
	
	protected ArrayList<T> values = new ArrayList<T>();
	
	protected int counter = 0;

	public MockMessageData(String deviceName) {
		this.deviceName = deviceName;
	}

	public MockMessageData(File file) {
		this(getFilenameWithoutExtension(file));

		List<String> readString = readFileLineByLine(file);
		
		for (String string : readString) {
			parseLine(string);
		}
		LoggerFactory.getLogger(this.getClass()).info("File " + file.toString() + " parsed.");
	}

	protected abstract void parseLine(String string);

	/**
	 * @param sensorValues the sensorValues to set
	 */
	public void add(T value) {
		this.values.add(value);
	}


	/**
	 * @return 
	 * @return the sensorValues
	 */
	public T getNext() {
		if (counter >= values.size()) {
			counter = 0;
		}
		return values.get(counter++);
	}

	protected static String getFilenameWithoutExtension(File file) {
		String fileNameWithOutExt = file.getName().
				replaceFirst("[.][^.]+$", "");
		return fileNameWithOutExt;
	}

	protected static int parseIntegerString(String string) {
		if (string.contains("0x")) {
			int beginIndex = string.indexOf("0x");
			String hexVal = string.substring(beginIndex+2, beginIndex+4);
			return Integer.parseInt(hexVal, 16);
		}
		else
			return Integer.parseInt(string);
	}

	protected static List<String> readFileLineByLine(File file){
		LinkedList<String> retVal = new LinkedList<String>();
        BufferedReader buffReader = null;
        try{
            buffReader = new BufferedReader (new FileReader(file));
            String line = buffReader.readLine();
            while(line != null){
                retVal.add(line);
                line = buffReader.readLine();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try{
                buffReader.close();
            }catch(IOException ioe1){
                //Leave It
            }
        }
        return retVal;
    }
}