/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientTest {

	static final int SERVER_PORT = 30020;

	static Server server;
	Client client;
	TestMessageHandler clientMessageHandler;

	private Logger log;
	static TestMessageHandler serverMessageHandler;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
		//server.start();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		log = LoggerFactory.getLogger(this.getClass());
		serverMessageHandler = new TestMessageHandler();
		server = new Server(SERVER_PORT, serverMessageHandler);
		clientMessageHandler = new TestMessageHandler();
		client = new Client("127.0.0.1", SERVER_PORT, clientMessageHandler, true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@After
	public void tearDown() throws Exception {		
		client.stop();
		client = null;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
		server.stop();
	}

	@Test
	public void testClientReconnect() {
		
		server.stop();	
	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
		
		assertFalse(client.isConnected());
		
		server = new Server(SERVER_PORT, serverMessageHandler);
	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
		
		assertTrue(client.isConnected());
	}

	@Test
	public void testClientStop() {
		
		server.stop();	
		
		client.stop();
	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
		
		assertEquals("Test active threads", 2, Thread.activeCount());
		log.info("done");
	}

}
