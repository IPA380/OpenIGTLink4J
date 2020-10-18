/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import msg.trajectory.TrajectoryMessage;
import network.Client;
import network.Server;
import network.TestMessageHandler;
import util.Header;

/**
 * @author Andreas Rothfuss
 *
 */
public class PointMessageTest {
	
	PointMessage underTest;
	private Logger log;    

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	
    }

	/**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
		log = LoggerFactory.getLogger(this.getClass());
		
        underTest = new PointMessage(PointMessageTestData.deviceName);
        
        underTest.addPointElement(PointMessageTestData.testElement0);
        underTest.addPointElement(PointMessageTestData.testElement1);
        underTest.addPointElement(PointMessageTestData.testElement2);
        
        underTest.getHeader().setTimeStamp(0, PointMessageTestData.timestamp);
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        underTest = null;
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testPack() throws Exception {
  	    byte[] toTest = underTest.getBytes();
  	    assertArrayEquals("Testing packed message against gold standard: header till device name", 
  	    		Arrays.copyOfRange(PointMessageTestData.test_message, 0, 34),  Arrays.copyOfRange(toTest, 0, 34));
  	    assertArrayEquals("Testing packed message against gold standard: header timestamp", 
  	    		Arrays.copyOfRange(PointMessageTestData.test_message, 35, 41),  Arrays.copyOfRange(toTest, 35, 41));
  	    assertArrayEquals("Testing packed message against gold standard: header bodySize", 
  	    		Arrays.copyOfRange(PointMessageTestData.test_message, 42, 49),  Arrays.copyOfRange(toTest, 42, 49));
  	    assertArrayEquals("Testing packed message against gold standard: header crc", 
  	    		Arrays.copyOfRange(PointMessageTestData.test_message, 50, 57),  Arrays.copyOfRange(toTest, 50, 57));
  	    assertArrayEquals("Testing packed message against gold standard: from timestamp", 
  	    		Arrays.copyOfRange(PointMessageTestData.test_message, 58, PointMessageTestData.test_message.length),
  	    		Arrays.copyOfRange(toTest, 58, toTest.length));
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testUnpack() throws Exception {
    	underTest =  new PointMessage(new Header(PointMessageTestData.test_message_header),
    			PointMessageTestData.test_message_body);
    	
    	Header msgHeader = underTest.getHeader();
    	assertEquals("Test message header version", PointMessageTestData.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", PointMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", PointMessageTestData.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", 0, msgHeader.getTimeStamp());
    	assertEquals("Test message header timestamp fraction", PointMessageTestData.timestamp, msgHeader.getTimeStampFrac());
    	assertEquals("Test message header body size", PointMessageTestData.test_message_body.length, msgHeader.getBodySize());
    	
    	assertEquals("testing unpack of gold standard: matching testElement 0",
    			PointMessageTestData.testElement0, underTest.getPoints().getElement(0));
        assertEquals("testing unpack of gold standard: matching testElement 1", 
        		PointMessageTestData.testElement1, underTest.getPoints().getElement(1));
        assertEquals("testing unpack of gold standard: matching testElement 2", 
        		PointMessageTestData.testElement2, underTest.getPoints().getElement(2));
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testPackUnpack() throws Exception {
    	underTest.getBytes();
  	    byte[] bytes = underTest.getBody();
        underTest.UnpackBody(bytes);
    	
    	Header msgHeader = underTest.getHeader();
    	assertEquals("Test message header version", PointMessageTestData.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", PointMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", PointMessageTestData.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", 0, msgHeader.getTimeStamp());
    	assertEquals("Test message header timestamp fraction", PointMessageTestData.timestamp, msgHeader.getTimeStampFrac());
    	assertEquals("Test message body size", PointMessageTestData.test_message_body.length, msgHeader.getBodySize());


    	assertEquals("testing unpack of gold standard: matching testElement 0",
    			PointMessageTestData.testElement0, underTest.getPoints().getElement(0));
        assertEquals("testing unpack of gold standard: matching testElement 1", 
        		PointMessageTestData.testElement1, underTest.getPoints().getElement(1));
        assertEquals("testing unpack of gold standard: matching testElement 2", 
        		PointMessageTestData.testElement2, underTest.getPoints().getElement(2));
    }
    
    @Test
    public void testMuliplePoints() throws UnknownHostException, IOException, InterruptedException {
    	Server server = new Server(30010, new TestMessageHandler(), null);
		TimeUnit.SECONDS.sleep(2);
		TestMessageHandler clientMessageHandler = new TestMessageHandler(false);
    	new Client("127.0.0.1", 30010, clientMessageHandler, false, null);	
		TimeUnit.MILLISECONDS.sleep(10);
    	PointMessage testMessage;
		Scanner s = new Scanner(System.in);
    	for(int i = 1; i<=1000; i=i+100) {
    		long start = System.currentTimeMillis();
    		testMessage = new PointMessage("TEST");
    		PointElement testPoint = null;
    		for(int j = 0; j<i; j++) {
    			testPoint = new PointElement((float)(j+1.1), (float)(j+1.2), (float)(j+1.3));
    			testMessage.addPointElement(testPoint);
    		}
    		System.out.println("Test message contains " 
    		+ testMessage.getPoints().getNumberOfElements()	+ " points");
    		server.send(testMessage);
    		
    		TimeUnit.MILLISECONDS.sleep(500);
    		if(clientMessageHandler.getReceivedMessages().size() != 0) {
    			PointMessage msg = (PointMessage) clientMessageHandler.getReceivedMessages().get(0);
    			log.info("Client received a " + PointMessage.class.getSimpleName() + " with " 
    			+ msg.getPoints().getNumberOfElements() + " points");
    			log.info("Body size is: " + msg.getBodyPackSize());
    			clientMessageHandler.getReceivedMessages().clear();
    			PointElement validPoint = msg.getPoints().getElement(msg.getPoints().getNumberOfElements()-1);
    			assertEquals("Testing last point for " + i + " points", testPoint, validPoint);
    			log.info("Last point is: " + validPoint);
    			log.info("This step took " + new Long(System.currentTimeMillis()-start).toString() + " ms.");
    		}
    		else {
    			log.info("No message received");
    			fail("No message received for " + i + " points.");
    		}
    	}
    	s.close();
    }
    
    @Test
    public void testSend() throws Exception {
    	underTest.getBytes();
    	assertTrue("No Null Pointer", true);
    }
}
