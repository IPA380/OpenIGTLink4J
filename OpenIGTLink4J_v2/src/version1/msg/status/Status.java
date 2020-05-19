/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.status;

/**
 * Class representing a device status as defined in
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/status.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class Status {

	/**
	 * Status codes as defined in
	 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/status.md
	 */
	public enum STATUS {
		INVALID(0),
		OK(1),
		UNKNOWN_ERROR(2),
		/** emergency */
		PANICK_MODE(3),  
		/** file, configuration, device etc */			
		NOT_FOUND(4),  				
		ACCESS_DENIED(5),
		BUSY(6),
		/** Time out / Connection lost */
		TIME_OUT(7),  	
		/** Overflow / Can't be reached */
		OVERFLOW(8), 			
		/** Checksum error */
		CHECKSUM_ERROR(9),  		
		/** Configuration error */
		CONFIG_ERROR(10), 		
		/** Not enough resource (memory, storage etc) */
		RESOURCE_ERROR(11), 
		/** Illegal/Unknown instruction */
		UNKNOWN_INSTRUCTION(12), 
		/** Device not ready (starting up)*/
		NOT_READY(13), 			
		/** Manual mode (device does not accept commands) */
		MANUAL_MODE(14), 		
		/** Device disabled */
		DISABLED(15), 		
		/** Device not present */
		NOT_PRESENT(16), 	
		/** Device version not known */
		UNKNOWN_VERSION(17), 		
		/** Hardware failure */
		HARDWARE_FAILURE(18), 	
		/** Exiting / shut down in progress */
		SHUT_DOWN(19), 
		NUM_TYPES(20);
		
		public final int code;

		STATUS(int code) {
			if (code == ordinal())
				this.code = (byte) ordinal();
			else
				throw new IllegalArgumentException();
		}

		/**
		 * To get the {@link STATUS} from (serialized) value
		 * 
		 * @param value
		 * 		value to get the exponent from
		 * @return
		 * 		the {@link STATUS}
		 * @throws IllegalArgumentException
		 * 		if value doesn't correspond to any known unit
		 */
		public static STATUS fromValue(int value) throws IllegalArgumentException {
		    try{
		        return STATUS.values()[value];
		    }catch( ArrayIndexOutOfBoundsException e ) {
		        throw new IllegalArgumentException("Unknown enum value :"+ value);
		    }
		}
	}

	STATUS code;
	long subCode;
	String errorName;
	String statusString;

	/**
	 * Constructs a status object with default value STATUS_OK
	 */
	public Status() {
		this.code = STATUS.OK;
		this.setStatusString(STATUS.OK.name());
	}
	
	/**
	 * Constructs a a status object with the specified status code
	 *
	 * @param code the status code
	 */
	public Status(STATUS status) {
		this.code = status;
		this.setStatusString(status.name());
		if(getStatusString().length() > 19)
			setStatusString(getStatusString().substring(0,19));
	}

	/**
	 * Constructs a a status object with the specified status code
	 *
	 * @param code the status code
	 */
	public Status(int code) {
		this(STATUS.fromValue(code));
	}
	
	/**
	 * Constructs a a status object with the specified status code
	 *
	 * @param code
	 * @param subCode
	 * @param errorName
	 * @param statusString
	 */
	public Status(STATUS code, long subCode, String errorName, String statusString) {
		this.code = code;
		this.subCode = subCode;
		this.errorName = errorName + '\0';
		this.statusString = statusString + '\0';
	}

	/**
	 * Constructs a a status object with the specified status code
	 *
	 * @param code
	 * @param subCode
	 * @param errorName
	 * @param statusString
	 */
	public Status(int code, long subCode, String errorName, String statusString) { 
		this.code = STATUS.fromValue(code);
		this.subCode = subCode;
		this.errorName = errorName + '\0';
		this.statusString = statusString + '\0';
	}
	
	/**
	 *** To set client or server status code
	 * @param code
	 *** 
	 */
	public void setCode(STATUS code) {
		this.code = code;
	}

	/**
	 *** To set client or server status code
	 * @param code
	 *** 
	 */
	public void setCode(int code) {
		this.code = STATUS.fromValue(code);
	}
	
	/**
	 *** To get client or server status code
	 *** 
	 * @return the status code
	 */
	public STATUS getCodeEnum() {
		return this.code;
	}

	/**
	 *** To get client or server status code
	 *** 
	 * @return the status code
	 */
	public int getCode() {
		return this.code.code;
	}

	/**
	 *** To set client or server status subCode
	 * @param subCode
	 *** 
	 */
	public void setSubCode(long subCode) {
		this.subCode = subCode;
	}

	/**
	 *** To get client or server status code
	 *** 
	 * @return the status subCode
	 */
	public long getSubCode() {
		return this.subCode;
	}

	/**
	 *** To set client or server status errorName
	 * @param errorName
	 *** 
	 */
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	/**
	 *** To get client or server status code
	 *** 
	 * @return the status code
	 */
	public String getErrorName() {
		return this.errorName;
	}

	/**
	 *** To set client or server status statusString
	 * @param statusString
	 *** 
	 */
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	/**
	 *** To get client or server status code
	 *** 
	 * @return the status code
	 */
	public String getStatusString() {
		return this.statusString;
	}
}

