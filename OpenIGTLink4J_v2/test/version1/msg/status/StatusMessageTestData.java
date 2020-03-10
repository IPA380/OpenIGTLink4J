/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.status;

import msg.status.Status;
import msg.status.Status.STATUS;
import util.MessageTestHelper;

public class StatusMessageTestData {

	static byte[] test_message_header = MessageTestHelper.convertCharToByte(new char[]{
			  /*------- OpenIGTLink message header --------*/

			  0x00, 0x01,                                     /* Version number */
			  0x53, 0x54, 0x41, 0x54, 0x55, 0x53, 0x00, 0x00,
			  0x00, 0x00, 0x00, 0x00,                         /* STATUS */ 
			  0x44, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4e, 0x61,
			  0x6d, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			  0x00, 0x00, 0x00, 0x00,                         /* Device name */
			  0x00, 0x00, 0x00, 0x00, 0x49, 0x96, 0x02, 0xd4, /* Time stamp */
			  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x36, /* Body size */
			  0x98, 0xee, 0x43, 0xee, 0xd8, 0xe4, 0x31, 0xcf, /* CRC */
    });

	  /*---------- POINT message body ------------*/
    
	static byte[] test_message_body = MessageTestHelper.convertCharToByte(new char[]{
	    	  /* body - status */
			  0x00, 0x0f,                                     /* Status code */
			  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0a, /* Sub code */
			  0x41, 0x43, 0x54, 0x55, 0x41, 0x54, 0x4f, 0x52,
			  0x5f, 0x44, 0x49, 0x53, 0x41, 0x42, 0x4c, 0x45,
			  0x44, 0x00, 0x00, 0x00,                         /* Status name */
			  0x41, 0x63, 0x74, 0x75, 0x61, 0x74, 0x6f, 0x72,
			  0x20, 0x41, 0x20, 0x69, 0x73, 0x20, 0x64, 0x69,
			  0x73, 0x61, 0x62, 0x6c, 0x65, 0x64, 0x2e, 0x00,
    });
    
    static byte[] test_message = MessageTestHelper.join(test_message_header, test_message_body);
    

   static long timestamp = 1234567892;
   static String deviceName = "DeviceName";
   
   static String STR_ERROR_NAME = "ACTUATOR_DISABLED";
   static String STR_ERROR_MESSAGE = "Actuator A is disabled.";
   static Status testElement = new Status(
   		STATUS.DISABLED.code, 
   		0x0a, 
   		STR_ERROR_NAME, 
   		STR_ERROR_MESSAGE);
}
