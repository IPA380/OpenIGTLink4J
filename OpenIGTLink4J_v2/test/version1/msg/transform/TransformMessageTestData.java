/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.transform;

import util.MessageTestHelper;

public class TransformMessageTestData {

	static byte[] test_message_header = MessageTestHelper.convertCharToByte(new char[]{
		  /*-------- OpenIGTLink message header -------*/
		  0x00, 0x01,                                     /* Version number */
		  0x54, 0x52, 0x41, 0x4e, 0x53, 0x46, 0x4f, 0x52,
		  0x4d, 0x00, 0x00, 0x00,                         /* TRANSFORM */ 
		  0x44, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4e, 0x61,
		  0x6d, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		  0x00, 0x00, 0x00, 0x00,                         /* Device name */
		  0x00, 0x00, 0x00, 0x00, 0x49, 0x96, 0x02, 0xd4, /* Time stamp */
		  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x30, /* Body size */
		  0xf6, 0xdd, 0x2b, 0x8e, 0xb4, 0xdf, 0x6d, 0xd2, /* CRC */

    });

	  /*---------- POINT message body ------------*/
    
	static byte[] test_message_body = MessageTestHelper.convertCharToByte(new char[]{
		  /*-------- POSITION message body -------*/
		  0xBF, 0x74, 0x73, 0xCD, 0x3E, 0x49, 0x59, 0xE6, /* tx, ty */
		  0xBE, 0x63, 0xDD, 0x98, 0xBE, 0x49, 0x59, 0xE6, /* tz, sx */
		  0x3E, 0x12, 0x49, 0x1B, 0x3F, 0x78, 0x52, 0xD6, /* sy, sz */
		  0x3E, 0x63, 0xDD, 0x98, 0x3F, 0x78, 0x52, 0xD6, /* nx, ny */
		  0xBD, 0xC8, 0x30, 0xAE, 0x42, 0x38, 0x36, 0x60, /* nz, px */
		  0x41, 0x9B, 0xC4, 0x67, 0x42, 0x38, 0x36, 0x60, /* py, pz */
    });
    
    static byte[] test_message = MessageTestHelper.join(test_message_header, test_message_body);

    static long VERSION = 1L;

   static long timestamp = 1234567892;
   static String deviceName = "DeviceName";

   	static float[] inT = new float[]{-0.954892f, 0.196632f, -0.222525f};
	static float[] inS = new float[]{-0.196632f, 0.142857f, 0.970014f};
	static float[] inN = new float[]{0.222525f, 0.970014f, -0.0977491f};
	static float[] inOrigin = new float[]{46.0531f,19.4709f,46.0531f};
	static float[][] rotationMatrix = {{inT[0],inS[0],inN[0]},
	                            {inT[1],inS[1],inN[1]},
	                            {inT[2],inS[2],inN[2]}};

}
