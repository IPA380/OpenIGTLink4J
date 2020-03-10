/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.position;

import util.MessageTestHelper;

public class PositionMessageTestData {

	static byte[] test_message_header = MessageTestHelper.convertCharToByte(new char[]{
		  /*-------- OpenIGTLink message header -------*/
			
		  0x00, 0x01,                                     /* Version number */
		  0x50, 0x4f, 0x53, 0x49, 0x54, 0x49, 0x4f, 0x4e,
		  0x00, 0x00, 0x00, 0x00,                         /* POSITION */ 
		  0x44, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4e, 0x61,
		  0x6d, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		  0x00, 0x00, 0x00, 0x00,                         /* Device name */
		  0x00, 0x00, 0x00, 0x00, 0x49, 0x96, 0x02, 0xd4, /* Time stamp */
		  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1c, /* Body size */
		  0x6b, 0xc8, 0xd6, 0x28, 0x2b, 0x79, 0xa3, 0xa1, /* CRC */

    });

	  /*---------- POINT message body ------------*/
    
	static byte[] test_message_body = MessageTestHelper.convertCharToByte(new char[]{
		  /*-------- POSITION message body -------*/
		  
		  0x42, 0x38, 0x36, 0x60, 0x41, 0x9b, 0xc4, 0x67, /* px, py */
		  0x42, 0x38, 0x36, 0x60, 0x00, 0x00, 0x00, 0x00, /* pz, ox */
		  0x3f, 0x13, 0xcd, 0x3a, 0x3f, 0x13, 0xcd, 0x3a, /* oy, oz */
		  0x3e, 0xaa, 0xaa, 0xab,                         /* ow     */
    });
    
    static byte[] test_message = MessageTestHelper.join(test_message_header, test_message_body);

	static long VERSION = 1L;

   static long timestamp = 1234567892;
   static String deviceName = "DeviceName";
   static float[] position = new float[]{46.0531f, 19.4709f, 46.0531f};
   static float[] quaternion = new float[]{0.0f, 0.5773502691f, 0.5773502692f, 0.3333333333f};

}
