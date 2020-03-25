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

import msg.string.GetStringMessage;
import protocol.MessageHandler;

public class TestServer {
	
	Server server;
	MessageHandler serverMessageHandler;
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
		serverMessageHandler = new TestMessageHandler();
		clientMessageHandler = new TestMessageHandler();
		server = new Server(30010, serverMessageHandler, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws UnknownHostException, IOException {
		client = new Client("127.0.0.1", 30010, clientMessageHandler, true, null);
		sleep(1000);
		
		try {
			client.send(new GetStringMessage("test1"));
			client.send(new GetStringMessage("test2"));
		} catch (IOException e1) {e1.printStackTrace();}
		
		sleep(1000);
	}

	void sleep(int time_ms) {
		try {
			Thread.sleep(time_ms);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

}
