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
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import msg.point.GetPointMessage;
import msg.status.GetStatusMessage;
import protocol.MessageHandler;

public class MitigateTestClient {
	
	Client client;
	MessageHandler clientMessageHandler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		clientMessageHandler = new TestMessageHandler();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws UnknownHostException, IOException {
		client = new Client("127.0.0.1", 30010, clientMessageHandler, true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
		
		try {
			for (int i = 0; i < 1000; i++) {
				client.send(new GetPointMessage("CalibrationTool"));
				client.send(new GetStatusMessage("test"));
				Thread.sleep(500);
			}
		} catch (IOException e1) {e1.printStackTrace();
		} catch (InterruptedException e) {e.printStackTrace();}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

}
