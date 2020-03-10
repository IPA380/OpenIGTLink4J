/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import msg.OIGTL_GetMessage;
import msg.OpenIGTMessage;
import msg.capability.GetCapabilityMessage;
import msg.image.GetImageMessage;
import msg.point.GetPointMessage;
import msg.position.GetPositionMessage;
import msg.status.GetStatusMessage;
import msg.track.tdata.GetTrackingDataMessage;
import msg.trajectory.GetTrajectoryMessage;
import msg.transform.GetTransformMessage;
import network.Client;
import network.TestMessageHandler;

public class QueryMechanismTest {

	static final int SERVER_PORT = 30020;

	static Server server;
	Client client;
	TestMessageHandler clientMessageHandler;

	private Logger log;
	static TestMessageHandler serverMessageHandler;
			
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
		serverMessageHandler = new TestMessageHandler();
		server = new Server(SERVER_PORT, serverMessageHandler);
//		server.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		server.stop();
	}

	@Before
	public void setUp() throws Exception {
		log = LoggerFactory.getLogger(this.getClass());
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
	}

	@Test
	public void testTransfrom() throws UnknownHostException, IOException {
		int minResponseTime = Integer.MAX_VALUE;
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		int i = 1;
		for (i = 1; i < 20000; i += 1) {
			receivedMessages.clear();
			client.send(new GetTransformMessage("TEST"));
			try {
				Thread.sleep(i);
			} catch (InterruptedException e) {}
			if (!receivedMessages.isEmpty() &&
					receivedMessages.lastElement().getDeviceName().equals("TEST")){
				minResponseTime = i;
				log.info("Fastest reply to GET_TRANS after " + i + " ms");
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
		assertTrue("Testing reply reception after " + i + " ms", 
				minResponseTime <= i);
	}

	@Test
	public void testImage() throws UnknownHostException, IOException {
		String deviceName = "TEST_IMAGE";
		OIGTL_GetMessage message = new GetImageMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

	@Test
	public void testTransform() throws UnknownHostException, IOException {
		String deviceName = "TEST_TRANSFORM";
		OIGTL_GetMessage message = new GetTransformMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

	@Test
	public void testPosition() throws UnknownHostException, IOException {
		String deviceName = "TEST_POSITION";
		OIGTL_GetMessage message = new GetPositionMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

	@Test
	public void testCapability() throws UnknownHostException, IOException {
		String deviceName = "TEST_CAPABILITY";
		OIGTL_GetMessage message = new GetCapabilityMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

	@Test
	public void testStatus() throws UnknownHostException, IOException {
		String deviceName = "TEST_STATUS";
		OIGTL_GetMessage message = new GetStatusMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

	@Test
	public void testTData() throws UnknownHostException, IOException {
		String deviceName = "TEST_TDATA";
		OIGTL_GetMessage message = new GetTrackingDataMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

	@Test
	public void testPoint() throws UnknownHostException, IOException {
		String deviceName = "TEST_POINT";
		OIGTL_GetMessage message = new GetPointMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

	@Test
	public void testTrajectory() throws UnknownHostException, IOException {
		String deviceName = "TEST_TRAJ";
		OIGTL_GetMessage message = new GetTrajectoryMessage(deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(message);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + message.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(deviceName));
	}

//	@Test
//	public void testNDArray() throws UnknownHostException, IOException {
//		String deviceName = "TEST_NDARRAY";
//		OIGTL_GetMessage message = new GetNDArrayMessage(deviceName);
//		
//		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
//		receivedMessages.clear();
//		client.send(message);
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {}
//		assertTrue("Testing reply reception after sending " + message.getClass(), 
//				receivedMessages.lastElement().getDeviceName().equals(deviceName));
//	}

//	@Test
//	public void testImageMeta() throws UnknownHostException, IOException {
//		String deviceName = "TEST_IMAGE_META";
//		OIGTL_GetMessage message = new GetImageMetaMNessage(deviceName);
//		
//		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
//		receivedMessages.clear();
//		client.send(message);
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {}
//		assertTrue("Testing reply reception after sending " + message.getClass(), 
//				receivedMessages.lastElement().getDeviceName().equals(deviceName));
//	}

//	@Test
//	public void testLB_Meta() throws UnknownHostException, IOException {
//		String deviceName = "TEST_LB_Meta";
//		OIGTL_GetMessage message = new GetLBMeta(deviceName);
//		
//		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
//		receivedMessages.clear();
//		client.send(message);
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {}
//		assertTrue("Testing reply reception after sending " + message.getClass(), 
//				receivedMessages.lastElement().getDeviceName().equals(deviceName));
//	}

}
