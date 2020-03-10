/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.capability;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import msg.capability.CapabilityMessage;
import msg.trajectory.TrajectoryMessage;
import util.Header;

/**
 * @author Andreas Rothfuss
 *
 */
public class CapabilityMessageTest {
	
	CapabilityMessage underTest;    

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
        underTest = new CapabilityMessage("DeviceName");
        underTest.getHeader().setTimeStamp(0, 1234567890);
        underTest.addCapability(CapabilityMessageTestData.testElement[0]);
        underTest.addCapability(CapabilityMessageTestData.testElement[1]);
        underTest.addCapability(CapabilityMessageTestData.testElement[2]);
        underTest.addCapability(CapabilityMessageTestData.testElement[3]);
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
  	    		Arrays.copyOfRange(CapabilityMessageTestData.test_message, 0, 34),  Arrays.copyOfRange(toTest, 0, 34));
  	    assertArrayEquals("Testing packed message against gold standard: header timestamp", 
  	    		Arrays.copyOfRange(CapabilityMessageTestData.test_message, 35, 41),  Arrays.copyOfRange(toTest, 35, 41));
  	    assertArrayEquals("Testing packed message against gold standard: header bodySize", 
  	    		Arrays.copyOfRange(CapabilityMessageTestData.test_message, 42, 49),  Arrays.copyOfRange(toTest, 42, 49));
  	    assertArrayEquals("Testing packed message against gold standard: header crc", 
  	    		Arrays.copyOfRange(CapabilityMessageTestData.test_message, 50, 57),  Arrays.copyOfRange(toTest, 50, 57));
  	    assertArrayEquals("Testing packed message against gold standard: from timestamp", 
  	    		Arrays.copyOfRange(CapabilityMessageTestData.test_message, 58, 
  	    				CapabilityMessageTestData.test_message.length),
  	    		Arrays.copyOfRange(toTest, 58, toTest.length));
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testUnpack() throws Exception {
    	underTest =  new CapabilityMessage(new Header(CapabilityMessageTestData.test_message_header),
    			CapabilityMessageTestData.test_message_Body);
    	for (int i = 0; i < CapabilityMessageTestData.testElement.length; i++) {
    		assertEquals("testing unpack of gold standard: matching element " + i, 
    				CapabilityMessageTestData.testElement[i], underTest.GetCapabilityList().get(i));
		}
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testPackUnpack() throws Exception {
  	    byte[] bytes = underTest.getBody();
        underTest.UnpackBody(bytes);
    	for (int i = 0; i < CapabilityMessageTestData.testElement.length; i++) {
    		assertEquals("testing unpack of gold standard: matching element " + i, 
    				CapabilityMessageTestData.testElement[i], underTest.GetCapabilityList().get(i));
		}
    }
    
    @Test
    public void testSend() throws Exception {
    	underTest.getBytes();
    	assertTrue("No Null Pointer", true);
    }
}
