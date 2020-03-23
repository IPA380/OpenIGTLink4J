/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.image;

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
public class ImageMessageTest {
    
    ImageMessage underTest;
    
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
    	  underTest = new ImageMessage(ImageMessageTestData.deviceName, 
    			  ImageMessageTestData.testHeader, ImageMessageTestData.test_message_body_data);
    	  underTest.getHeader().setTimeStamp(0, ImageMessageTestData.timestamp);
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
  	    		Arrays.copyOfRange(ImageMessageTestData.test_message, 0, 34),  Arrays.copyOfRange(toTest, 0, 34));
  	    assertArrayEquals("Testing packed message against gold standard: header timestamp", 
  	    		Arrays.copyOfRange(ImageMessageTestData.test_message, 35, 41),  Arrays.copyOfRange(toTest, 35, 41));
  	    assertArrayEquals("Testing packed message against gold standard: header bodySize", 
  	    		Arrays.copyOfRange(ImageMessageTestData.test_message, 42, 49),  Arrays.copyOfRange(toTest, 42, 49));
  	    assertArrayEquals("Testing packed message against gold standard: header crc", 
  	    		Arrays.copyOfRange(ImageMessageTestData.test_message, 50, 57),  Arrays.copyOfRange(toTest, 50, 57));
  	    assertArrayEquals("Testing packed message against gold standard: body header", 
  	    		Arrays.copyOfRange(ImageMessageTestData.test_message, 58, 128),  Arrays.copyOfRange(toTest, 58, 128));
  	    assertArrayEquals("Testing packed message against gold standard: body data", 
	    		Arrays.copyOfRange(ImageMessageTestData.test_message, 129, ImageMessageTestData.test_message.length),  
	    		Arrays.copyOfRange(toTest, 129, toTest.length));
    }
    
    /**
     * Test method for {@link messages.TrajectoryMessage#UnpackBody()} and {@link TrajectoryMessage#PackBody()}.
     * @throws Exception
     */
    @Test
    public void testUnpack() throws Exception {
    	underTest =  new ImageMessage(new Header(ImageMessageTestData.test_message_header), 
    			ImageMessageTestData.test_message_body);

    	Header msgHeader = underTest.getHeader();
    	assertEquals("Test message header version", ImageMessageTestData.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", ImageMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", ImageMessageTestData.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", ImageMessageTestData.timestamp, msgHeader.getTimeStampFrac());
    	assertEquals("Test message header body size", ImageMessageTestData.test_message_body_header.length +
    			ImageMessageTestData.test_message_body_data.length, msgHeader.getBodySize());

    	assertEquals("Testing image header: version", ImageHeader.VERSION, underTest.getVersion());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_IMAGE_TYPE, underTest.getImageType());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_IMAGE_SCALR_TYPE, underTest.getScalarType());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_ENDIAN, underTest.getEndian());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_COS, underTest.getImageCOSType());

    	assertArrayEquals("Testing image header: dimensions", ImageMessageTestData.size, underTest.getDimensions());
    	assertArrayEquals("Testing image header: normals i", ImageMessageTestData.inT, underTest.getNormals_i(), (float) 1e-6);
    	assertArrayEquals("Testing image header: normals i", ImageMessageTestData.inS, underTest.getNormals_j(), (float) 1e-6);
    	assertArrayEquals("Testing image header: normals i", ImageMessageTestData.inN, underTest.getNormals_k(), (float) 1e-6);
    	assertArrayEquals("Testing image header: subvolume offset", ImageMessageTestData.svoffset, underTest.getSubOffset());
    	assertArrayEquals("Testing image header: subvolume dimensions", ImageMessageTestData.svsize, underTest.getSubDimensions());
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
    	assertEquals("Test message header version", ImageMessageTestData.VERSION, msgHeader.getVersion());
    	assertEquals("Test message header device type", ImageMessage.DATA_TYPE, msgHeader.getDataType());
    	assertEquals("Test message header device name", ImageMessageTestData.deviceName, msgHeader.getDeviceName());
    	assertEquals("Test message header timestamp", ImageMessageTestData.timestamp, msgHeader.getTimeStampFrac());
    	assertEquals("Test message header body size", ImageMessageTestData.test_message_body_header.length +
    			ImageMessageTestData.test_message_body_data.length, msgHeader.getBodySize());

    	assertEquals("Testing image header: version", ImageHeader.VERSION, underTest.getVersion());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_IMAGE_TYPE, underTest.getImageType());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_IMAGE_SCALR_TYPE, underTest.getScalarType());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_ENDIAN, underTest.getEndian());
    	assertEquals("Testing image header: image type", ImageHeader.DEFAULT_COS, underTest.getImageCOSType());

    	assertArrayEquals("Testing image header: dimensions", ImageMessageTestData.size, underTest.getDimensions());
    	assertArrayEquals("Testing image header: normals i", ImageMessageTestData.inT, underTest.getNormals_i(), (float) 1e-6);
    	assertArrayEquals("Testing image header: normals i", ImageMessageTestData.inS, underTest.getNormals_j(), (float) 1e-6);
    	assertArrayEquals("Testing image header: normals i", ImageMessageTestData.inN, underTest.getNormals_k(), (float) 1e-6);
    	assertArrayEquals("Testing image header: subvolume offset", ImageMessageTestData.svoffset, underTest.getSubOffset());
    	assertArrayEquals("Testing image header: subvolume dimensions", ImageMessageTestData.svsize, underTest.getSubDimensions());
     }
    
    @Test
    public void testSend() throws Exception {
    	underTest.getBytes();
    	assertTrue("No Null Pointer", true);
    }
}
