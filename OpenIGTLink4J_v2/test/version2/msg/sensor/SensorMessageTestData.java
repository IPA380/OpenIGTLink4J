/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.sensor;

import msg.sensor.SI_EXP;
import msg.sensor.SI_UNIT;
import msg.sensor.Unit;
import util.MessageTestHelper;

public class SensorMessageTestData {

	static byte[] test_message_header = MessageTestHelper.convertCharToByte(new char[]{
			  /*------- OpenIGTLink message header --------*/
			  0x00, 0x01,                                     /* Version number */
			  0x53, 0x45, 0x4e, 0x53, 0x4f, 0x52, 0x00, 0x00,
			  0x00, 0x00, 0x00, 0x00,                         /* SENSOR */ 
			  0x44, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4e, 0x61,
			  0x6d, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			  0x00, 0x00, 0x00, 0x00,                         /* Device name */
			  0x00, 0x00, 0x00, 0x00, 0x49, 0x96, 0x02, 0xd4, /* Time stamp */
			  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3a, /* Body size */
			  0x63, 0x7b, 0x80, 0x08, 0x66, 0x20, 0x20, 0xe7, /* CRC */
    });

	/*---------- String message body ------------*/
    
    static byte[] test_message_body_header = MessageTestHelper.convertCharToByte(new char[]{

    		  0x06,                                           /* larray */
    		  0x00,                                           /* status */
    		  0x00, 0x44, 0x3E, 0x00, 0x00, 0x00, 0x00, 0x00, /* unit */
    });
    
    static byte[] test_message_body_data = MessageTestHelper.convertCharToByte(new char[]{
    		  0x40, 0xfe, 0x24, 0x0c, 0x7a, 0xe1, 0x47, 0xae, /* value: sensor #0 */
    		  0x40, 0xc8, 0x1c, 0xd6, 0xc8, 0xb4, 0x39, 0x58, /* value: sensor #1 */
    		  0x40, 0x93, 0x4a, 0x45, 0x6d, 0x5c, 0xfa, 0xad, /* value: sensor #2 */
    		  0x40, 0x5e, 0xdd, 0x3b, 0xe2, 0x2e, 0x5d, 0xe1, /* value: sensor #3 */
    		  0x40, 0x28, 0xb0, 0xfc, 0xb4, 0xf1, 0xe4, 0xb4, /* value: sensor #4 */
    		  0x3f, 0xf3, 0xc0, 0xca, 0x2a, 0x5b, 0x1d, 0x5d, /* value: sensor #4 */
    });

    static byte[] test_message_body = MessageTestHelper.join(test_message_body_header, 
    		test_message_body_data);
    static byte[] test_message = MessageTestHelper.join(test_message_header, 
    		test_message_body);

	static long VERSION = 1L;
    static long timestamp = 1234567892;
    static String deviceName = "DeviceName";

    static double[] sensorValues = new double[]{123456.78,12345.678,1234.5678,123.45678,12.345678,1.2345678};
	static Unit unit = Unit.of(SI_UNIT.BASE_METER, SI_EXP.PLUS1, 
			SI_UNIT.BASE_SECOND, SI_EXP.MINUS2);
}
