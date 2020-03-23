/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.command;

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
import util.ExtendedHeader;
import util.Header;
import util.MetaData;

/**
 * @author Andreas Rothfuss
 *
 */
public class CommandMessageTest2 {
	
	CommandMessage underTest;    

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
        underTest = new CommandMessage(CommandMessageTestData2.deviceName,
        		CommandMessageTestData2.coommandID,
        		CommandMessageTestData2.commandName,
        		CommandMessageTestData2.command);
        underTest.getHeader().setTimeStamp(-1, -1);
        
        MetaData metaData = new MetaData();
        metaData.addKeyValuePair(CommandMessageTestData2.metaData0key, CommandMessageTestData2.metaData0value);
        metaData.addKeyValuePair(CommandMessageTestData2.metaData1key, CommandMessageTestData2.metaData1value);
        
        underTest.setMetaData(metaData, CommandMessageTestData2.msgId);
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
  	    		Arrays.copyOfRange(CommandMessageTestData2.test_message, 0, 34),  Arrays.copyOfRange(toTest, 0, 34));
  	    assertArrayEquals("Testing packed message against gold standard: header timestamp", 
  	    		Arrays.copyOfRange(CommandMessageTestData2.test_message, 35, 41),  Arrays.copyOfRange(toTest, 35, 41));
  	    assertArrayEquals("Testing packed message against gold standard: header bodySize", 
  	    		Arrays.copyOfRange(CommandMessageTestData2.test_message, 42, 49),  Arrays.copyOfRange(toTest, 42, 49));
  	    assertArrayEquals("Testing packed message against gold standard: header crc", 
  	    		Arrays.copyOfRange(CommandMessageTestData2.test_message, 50, 57),  Arrays.copyOfRange(toTest, 50, 57));
  	    int currentCount = 58;
  	    byte[] testArray = Arrays.copyOfRange(toTest, currentCount, currentCount += ExtendedHeader.LENGTH);
  	    assertArrayEquals("Testing packed message against gold standard: extended header", 
  	    		CommandMessageTestData2.test_message_extended_header, testArray);
  	    testArray = Arrays.copyOfRange(toTest, currentCount, currentCount += CommandMessageTestData2.test_message_body_data.length);
  	    assertArrayEquals("Testing packed message against gold standard: body data", 
  	    		CommandMessageTestData2.test_message_body_data, testArray);
  	    testArray =	Arrays.copyOfRange(toTest, currentCount, currentCount += CommandMessageTestData2.test_message_meta_data.length);
  	    assertArrayEquals("Testing packed message against gold standard: metaData", 
  	    		CommandMessageTestData2.test_message_meta_data, testArray);
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testUnpack() throws Exception {
    	underTest =  new CommandMessage(new Header(CommandMessageTestData2.test_message_header),
    			CommandMessageTestData2.test_message_body);
    	
    	Header msgHeader = underTest.getHeader();
    	assertEquals("Test message header version", CommandMessageTestData2.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", CommandMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", CommandMessageTestData2.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", 0, msgHeader.getTimeStamp());
    	assertEquals("Test message header timestamp fraction", 0, msgHeader.getTimeStampFrac());
    	assertEquals("Test message header body size", CommandMessageTestData2.test_message_body.length, msgHeader.getBodySize());

    	assertEquals("testing unpack of gold standard: matching command id",
    			CommandMessageTestData2.coommandID, underTest.commandId);
    	assertEquals("testing unpack of gold standard: matching command name",
    			CommandMessageTestData2.commandName, underTest.commandName);
    	assertEquals("testing unpack of gold standard: matching command encoding",
    			CommandMessageTestData2.encoding, underTest.encoding);
    	assertEquals("testing unpack of gold standard: matching command",
    			CommandMessageTestData2.command, underTest.command);
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
    	assertEquals("Test message header version", CommandMessageTestData2.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", CommandMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", CommandMessageTestData2.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", -1, msgHeader.getTimeStamp());
    	assertEquals("Test message header timestamp fraction", -1, msgHeader.getTimeStampFrac());
    	assertEquals("Test message body size", CommandMessageTestData2.test_message_body.length, msgHeader.getBodySize());

    	assertEquals("testing unpack of gold standard: matching command id",
    			CommandMessageTestData2.coommandID, underTest.commandId);
    	assertEquals("testing unpack of gold standard: matching command name",
    			CommandMessageTestData2.commandName, underTest.commandName);
    	assertEquals("testing unpack of gold standard: matching command encoding",
    			CommandMessageTestData2.encoding, underTest.encoding);
    	assertEquals("testing unpack of gold standard: matching command",
    			CommandMessageTestData2.command, underTest.command);
    }
    
    @Test
    public void testSend() throws Exception {
    	underTest.getBytes();
    	assertTrue("No Null Pointer", true);
    }
}
