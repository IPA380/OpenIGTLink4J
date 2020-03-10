/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.capability;

import msg.image.GetImageMessage;
import msg.image.ImageMessage;
import msg.transform.GetTransformMessage;
import msg.transform.TransformMessage;
import util.MessageTestHelper;

public class CapabilityMessageTestData {

	static byte[] test_message_header = MessageTestHelper.convertCharToByte(new char[]{
		  /*------- OpenIGTLink message header --------*/

			  0x00, 0x01,                                     /* Version number */
			  0x43, 0x41, 0x50, 0x41, 0x42, 0x49, 0x4c, 0x49,
			  0x54, 0x59, 0x00, 0x00,                         /* CAPABILITY */
			  0x44, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4e, 0x61,
			  0x6d, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			  0x00, 0x00, 0x00, 0x00,                         /* Device name */
			  0x00, 0x00, 0x00, 0x00, 0x49, 0x96, 0x02, 0xd4, /* Time stamp */
			  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x30, /* Body size */

			  0x69, 0x57, 0xe2, 0x9e, 0x2b, 0x35, 0xea, 0x1e, /* CRC */
    });

	  /*---------- POINT message body ------------*/
    
	static byte[] test_message_Body = MessageTestHelper.convertCharToByte(new char[]{
    	  /* body - capabitlity list */

		  0x49, 0x4d, 0x41, 0x47, 0x45, 0x00, 0x00, 0x00,
		  0x00, 0x00, 0x00, 0x00,                         /* IMAGE */
		  0x47, 0x45, 0x54, 0x5f, 0x49, 0x4d, 0x41, 0x47, 
		  0x45, 0x00, 0x00, 0x00,                         /* GET_IMAGE */
		  0x54, 0x52, 0x41, 0x4e, 0x53, 0x46, 0x4f, 0x52, 
		  0x4d, 0x00, 0x00, 0x00,                         /* TRANSFORM */
		  0x47, 0x45, 0x54, 0x5f, 0x54, 0x52, 0x41, 0x4e, 
		  0x53, 0x00, 0x00, 0x00,                         /* GET_TRANS */
    });
    
    static byte[] test_message = MessageTestHelper.join(test_message_header, test_message_Body);
    
    static String[] testElement = new String[]{
    		ImageMessage.DATA_TYPE,
            GetImageMessage.DATA_TYPE,
            TransformMessage.DATA_TYPE,
            GetTransformMessage.DATA_TYPE
    };

}
