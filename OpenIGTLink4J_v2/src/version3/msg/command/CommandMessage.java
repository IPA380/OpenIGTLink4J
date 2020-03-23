/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.command;

import java.io.UnsupportedEncodingException;

import msg.OIGTL_DataMessage;
import util.BytesArray;
import util.Header;
import util.MIBenum;

/**
 * Class representing a command message
 * 
 * @see https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/command.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class CommandMessage extends OIGTL_DataMessage {

    /** The messages data type */
	public static final String DATA_TYPE = "COMMAND";

	/** Size of the command name in bytes */
	public static final int COMMAND_NAME_SIZE = 128;

	/** The unique ID of the command */
	long commandId;
	/** The name of the command */
	String commandName;
	/** The encoding of the command string.
  	 *  The value is defined in IANA Character Sets 
  	 *  (http://www.iana.org/assignments/character-sets). 
  	 */
	MIBenum encoding;
	/** The command stored as an XML encoded string. */
	String command;

    /**
     *** Constructor to be used to create message to be sent
     * 
	 * @param deviceName	device name of message
	 * @param command_id 	id of the command of the message
	 * @param commandName	name of the command of the message
	 * @param command		command of the message
	 * */
	public CommandMessage(String deviceName, long command_id, 
			String commandName, String command) {
		super(DATA_TYPE, deviceName, 4 + COMMAND_NAME_SIZE + 2 + 4 + command.length());
		this.commandId = command_id;
		if (commandName.length() > COMMAND_NAME_SIZE) {
			throw new IllegalArgumentException("Command name cannot be longer than " +
					COMMAND_NAME_SIZE);
		}
		this.commandName = commandName;
		this.encoding = MIBenum.US_ASCII;		
		this.command = command;
	}

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
	public CommandMessage(Header header, byte[] bodyBytes) {
		super(header, bodyBytes);
	}

	@Override
	protected boolean UnpackBody(byte[] bodyBytes) {
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(bodyBytes);
		
		commandId = bytesArray.getLong(4);
		commandName = bytesArray.getString(COMMAND_NAME_SIZE);
		encoding = MIBenum.from((int) bytesArray.getLong(2));
		
		long length = bytesArray.getLong(4);

		try {
			command = encoding.decode(bytesArray.getBytes((int) length));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("The encoding " + 
					encoding + " is not supported.");
		}
		
		return true;
	}

	@Override
	public byte[] getBody() {
		BytesArray bytesArray = new BytesArray();
		
		bytesArray.putLong(commandId, 4);
		bytesArray.putString(commandName, COMMAND_NAME_SIZE);
		/*Encoding: US-ASCII */
		bytesArray.putLong(3, 2); 
		bytesArray.putLong(command.length(), 4);
		bytesArray.putString(command);
		
		return bytesArray.getBytes();
	}
	
	public long getCommandId() {
		return commandId;
	}
	
	public String getCommandName() {
		return commandName;
	}

	public String getCommand() {
		return command;
	}
	
	

}
