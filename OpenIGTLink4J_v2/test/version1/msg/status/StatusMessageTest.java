/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.status;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import msg.status.StatusMessage;
import msg.trajectory.TrajectoryMessage;
import util.Header;

/**
 * @author Andreas Rothfuss
 *
 */
public class StatusMessageTest {
	
    StatusMessage underTest;
    
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
        underTest = new StatusMessage("DeviceName");
        underTest.getHeader().setTimeStamp(0, 1234567890);
        underTest.setStatus(StatusMessageTestData.testElement);
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
  	    		Arrays.copyOfRange(StatusMessageTestData.test_message, 0, 34),  Arrays.copyOfRange(toTest, 0, 34));
  	    assertArrayEquals("Testing packed message against gold standard: header timestamp", 
  	    		Arrays.copyOfRange(StatusMessageTestData.test_message, 35, 41),  Arrays.copyOfRange(toTest, 35, 41));
  	    assertArrayEquals("Testing packed message against gold standard: header bodySize", 
  	    		Arrays.copyOfRange(StatusMessageTestData.test_message, 42, 49),  Arrays.copyOfRange(toTest, 42, 49));
//  	    assertArrayEquals("Testing packed message against gold standard: header crc", 
//  	    		Arrays.copyOfRange(test_message, 50, 57),  Arrays.copyOfRange(toTest, 50, 57));
  	    assertArrayEquals("Testing packed message against gold standard: body", 
  	    		Arrays.copyOfRange(StatusMessageTestData.test_message, 58, StatusMessageTestData.test_message.length),
  	    		Arrays.copyOfRange(toTest, 58, toTest.length));
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testUnpack() throws Exception {
    	underTest =  new StatusMessage(new Header(StatusMessageTestData.test_message_header),
    			StatusMessageTestData.test_message_body);
		assertEquals("testing unpack of gold standard: matching status code ", 
				StatusMessageTestData.testElement.getCode(), underTest.getCode());
		assertEquals("testing unpack of gold standard: matching status subCode ", 
				StatusMessageTestData.testElement.getSubCode(), underTest.getSubCode());
		assertEquals("testing unpack of gold standard: matching status errorName ", 
				StatusMessageTestData.testElement.getErrorName(), underTest.getErrorName());
		assertEquals("testing unpack of gold standard: matching status errorMessage ", 
				StatusMessageTestData.testElement.getStatusString(), underTest.getStatusString());
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testPackUnpack() throws Exception {
  	    byte[] bytes = underTest.getBody();
        underTest.UnpackBody(bytes);
        assertEquals("testing unpack of gold standard: matching status code ",
        		StatusMessageTestData.testElement.getCode(), underTest.getCode());
		assertEquals("testing unpack of gold standard: matching status subCode ",
				StatusMessageTestData.testElement.getSubCode(), underTest.getSubCode());
		assertEquals("testing unpack of gold standard: matching status errorName ",
				StatusMessageTestData.testElement.getErrorName(), underTest.getErrorName());
		assertEquals("testing unpack of gold standard: matching status errorMessage ", 
				StatusMessageTestData.testElement.getStatusString(), underTest.getStatusString());
    }
    
    @Test
    public void testSend() throws Exception {
    	underTest.getBytes();
    	assertTrue("No Null Pointer", true);
    }
}
