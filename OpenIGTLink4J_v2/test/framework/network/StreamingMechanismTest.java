/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import msg.OIGTL_STPMessage;
import msg.OIGTL_STTMessage;
import msg.OpenIGTMessage;
import msg.image.RTSImageMessage;
import msg.image.STPImageMessage;
import msg.image.STTImageMessage;
import msg.position.RTSPositionMessage;
import msg.position.STPPositionMessage;
import msg.position.STTPositionMessage;
import msg.track.tdata.RTSTrackingDataMessage;
import msg.track.tdata.STPTrackingDataMessage;
import msg.track.tdata.STTTrackingDataMessage;
import msg.transform.RTSTransformMessage;
import msg.transform.STPTransformMessage;
import msg.transform.STTTransformMessage;

public class StreamingMechanismTest {

	static final int SERVER_PORT = 30020;

	static Server server;
	Client client;
	TestMessageHandler clientMessageHandler;
	static TestMessageHandler serverMessageHandler;
			
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
		serverMessageHandler = new TestMessageHandler();
		server = new Server(SERVER_PORT, serverMessageHandler, null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		server.stop();
	}

	@Before
	public void setUp() throws Exception {
		clientMessageHandler = new TestMessageHandler();
		client = new Client("127.0.0.1", SERVER_PORT, clientMessageHandler, true, null);
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

	/*
	 * @Test public void testTransfrom() throws UnknownHostException, IOException {
	 * int minResponseTime = Integer.MAX_VALUE; Vector<OpenIGTMessage>
	 * receivedMessages = clientMessageHandler.getReceivedMessages(); int i = 1; for
	 * (i = 1; i < 20000; i += 1) { receivedMessages.clear(); client.send(new
	 * GetTransformMessage("TEST")); try { Thread.sleep(i); } catch
	 * (InterruptedException e) {} if (!receivedMessages.isEmpty() &&
	 * receivedMessages.lastElement().getDeviceName().equals("TEST")){
	 * minResponseTime = i; Log.info("Fastest reply to GET_TRANS after " + i +
	 * " ms"); break; } try { Thread.sleep(100); } catch (InterruptedException e) {}
	 * } assertTrue("Testing reply reception after " + i + " ms", minResponseTime <=
	 * i); }
	 */

	@Test
	public void testImage() throws UnknownHostException, IOException {
		String deviceName = "_IMAGE";
		OIGTL_STTMessage msgStart = new STTImageMessage("START" + deviceName);
		OIGTL_STPMessage msgStop = new STPImageMessage("STOP" + deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(msgStart);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		client.send(msgStop);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + msgStart.getClass(), 
				receivedMessages.firstElement() instanceof RTSImageMessage);
		assertTrue("Testing reply reception after sending " + msgStart.getClass(), 
				receivedMessages.firstElement().getDeviceName().equals(msgStart.getDeviceName()));
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement() instanceof RTSImageMessage);
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(msgStop.getDeviceName()));
	}

	@Test
	public void testTransfrom() throws UnknownHostException, IOException {
		String deviceName = "_TRANSFORM";
		OIGTL_STTMessage msgStart = new STTTransformMessage("START" + deviceName);
		OIGTL_STPMessage msgStop = new STPTransformMessage("STOP" + deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(msgStart);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		client.send(msgStop);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + msgStart.getClass(), 
				receivedMessages.firstElement() instanceof RTSTransformMessage);
		assertTrue("Testing reply reception after sending " + msgStart.getClass(), 
				receivedMessages.firstElement().getDeviceName().equals(msgStart.getDeviceName()));
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement() instanceof RTSTransformMessage);
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(msgStop.getDeviceName()));
	}

	@Test
	public void testPosition() throws UnknownHostException, IOException {
		String deviceName = "_POSITION";
		OIGTL_STTMessage msgStart = new STTPositionMessage("START" + deviceName);
		OIGTL_STPMessage msgStop = new STPPositionMessage("STOP" + deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(msgStart);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		client.send(msgStop);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + msgStart.getClass(), 
				receivedMessages.firstElement() instanceof RTSPositionMessage);
		assertEquals("Testing reply reception after sending " + msgStart.getClass(), 
				msgStart.getDeviceName(), receivedMessages.firstElement().getDeviceName());
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement() instanceof RTSPositionMessage);
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(msgStop.getDeviceName()));
	}

	@Test
	public void testTrackingData() throws UnknownHostException, IOException {
		String deviceName = "_TDATA";
		OIGTL_STTMessage msgStart = new STTTrackingDataMessage("START" + deviceName);
		OIGTL_STPMessage msgStop = new STPTrackingDataMessage("STOP" + deviceName);
		
		Vector<OpenIGTMessage> receivedMessages = clientMessageHandler.getReceivedMessages();
		receivedMessages.clear();
		client.send(msgStart);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		client.send(msgStop);
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		assertTrue("Testing reply reception after sending " + msgStart.getClass(), 
				receivedMessages.firstElement() instanceof RTSTrackingDataMessage);
		assertTrue("Testing reply reception after sending " + msgStart.getClass(), 
				receivedMessages.firstElement().getDeviceName().equals(msgStart.getDeviceName()));
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement() instanceof RTSTrackingDataMessage);
		assertTrue("Testing reply reception after sending " + msgStop.getClass(), 
				receivedMessages.lastElement().getDeviceName().equals(msgStop.getDeviceName()));
	}

	/*
	 * @Test public void testNDArray() throws UnknownHostException, IOException {
	 * String deviceName = "_NDArray"; OIGTL_STTMessage msgStart = new
	 * STTNDArrayMessage("START" + deviceName); OIGTL_STPMessage msgStop = new
	 * STPNDArrayMessage("STOP" + deviceName);
	 * 
	 * Vector<OpenIGTMessage> receivedMessages =
	 * clientMessageHandler.getReceivedMessages(); receivedMessages.clear();
	 * client.send(msgStart); try {Thread.sleep(100); } catch (InterruptedException
	 * e) {} client.send(msgStop); try {Thread.sleep(100); } catch
	 * (InterruptedException e) {}
	 * assertTrue("Testing reply reception after sending " + msgStart.getClass(),
	 * receivedMessages.firstElement() instanceof RTSNDArrayMessage);
	 * assertTrue("Testing reply reception after sending " + msgStart.getClass(),
	 * receivedMessages.firstElement().getDeviceName().equals(msgStart.getDeviceName
	 * ())); assertTrue("Testing reply reception after sending " +
	 * msgStop.getClass(), receivedMessages.lastElement() instanceof
	 * RTSNDArrayMessage); assertTrue("Testing reply reception after sending " +
	 * msgStop.getClass(),
	 * receivedMessages.lastElement().getDeviceName().equals(msgStop.getDeviceName()
	 * )); }
	 */
}
