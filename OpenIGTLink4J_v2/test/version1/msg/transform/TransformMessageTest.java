/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.transform;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import msg.trajectory.TrajectoryMessage;
import util.Header;

/**
 * @author Andreas Rothfuss
 *
 */
public class TransformMessageTest {
	
	TransformMessage underTest;    

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
        underTest = new TransformMessage(TransformMessageTestData.deviceName, 
        		TransformMessageTestData.inOrigin, 
        		TransformMessageTestData.rotationMatrix);
        underTest.getHeader().setTimeStamp(0, TransformMessageTestData.timestamp);
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
  	    		Arrays.copyOfRange(TransformMessageTestData.test_message, 0, 34),  Arrays.copyOfRange(toTest, 0, 34));
  	    assertArrayEquals("Testing packed message against gold standard: header timestamp", 
  	    		Arrays.copyOfRange(TransformMessageTestData.test_message, 35, 41),  Arrays.copyOfRange(toTest, 35, 41));
  	    assertArrayEquals("Testing packed message against gold standard: header bodySize", 
  	    		Arrays.copyOfRange(TransformMessageTestData.test_message, 42, 49),  Arrays.copyOfRange(toTest, 42, 49));
  	    assertArrayEquals("Testing packed message against gold standard: header crc", 
  	    		Arrays.copyOfRange(TransformMessageTestData.test_message, 50, 57),  Arrays.copyOfRange(toTest, 50, 57));
  	    assertArrayEquals("Testing packed message against gold standard: from timestamp", 
  	    		Arrays.copyOfRange(TransformMessageTestData.test_message, 58, TransformMessageTestData.test_message.length),
  	    		Arrays.copyOfRange(toTest, 58, toTest.length));
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testUnpack() throws Exception {
    	underTest =  new TransformMessage(new Header(TransformMessageTestData.test_message_header),
    			TransformMessageTestData.test_message_body);
    	
    	Header msgHeader = underTest.getHeader();
    	assertEquals("Test message header version", TransformMessageTestData.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", TransformMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", TransformMessageTestData.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", 0, msgHeader.getTimeStamp());
    	assertEquals("Test message header timestamp fraction", TransformMessageTestData.timestamp, msgHeader.getTimeStampFrac());
    	assertEquals("Test message header body size", TransformMessageTestData.test_message_body.length, msgHeader.getBodySize());
    	
		assertArrayEquals("testing unpack of gold standard: matching translation ", 
			TransformMessageTestData.inOrigin, underTest.getPositionVector(), 1e-6f);
		for (int i = 0; i < 3; i++) {
			assertArrayEquals("testing unpack of gold standard: matching rotation matrix row " + i, 
					TransformMessageTestData.rotationMatrix[i], underTest.getRotationMatrixArray()[i], 1e-6f);
		}
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
    	assertEquals("Test message header version", TransformMessageTestData.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", TransformMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", TransformMessageTestData.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", 0, msgHeader.getTimeStamp());
    	assertEquals("Test message header timestamp fraction", TransformMessageTestData.timestamp, msgHeader.getTimeStampFrac());
    	assertEquals("Test message body size", TransformMessageTestData.test_message_body.length, msgHeader.getBodySize());

		assertArrayEquals("testing unpack of gold standard: matching translation ", 
			TransformMessageTestData.inOrigin, underTest.getPositionVector(), 1e-6f);
		for (int i = 0; i < 3; i++) {
			assertArrayEquals("testing unpack of gold standard: matching rotation matrix row " + i, 
					TransformMessageTestData.rotationMatrix[i], underTest.getRotationMatrixArray()[i], 1e-6f);
		}
    }
    
    @Test
    public void testSend() throws Exception {
    	underTest.getBytes();
    	assertTrue("No Null Pointer", true);
    }
}
