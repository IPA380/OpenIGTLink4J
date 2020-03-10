/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package network;

import java.io.IOException;
import java.io.InputStream;
import msg.RawOpenIGTMessage;

import util.Header;
/**
 * Class representing a {@link Thread} that handles the receiving
 * of incoming messages 
 * 
 * @author Andreas Rothfuss
 *
 */
public class ReceiveRunner extends NetManagerRunner{

	private static final int MAX_RCV_SIZE = 60000;
	private static final int MAX_MESSAGE_WAIT_TIME = 5;

	/**
	 * Destination constructor to create a new {@link ReceiveRunner}
	 * 
	 * @param netManager
	 * 		the {@link NetManager} managing this {@link MessageRunner}
	 */
	public ReceiveRunner(NetManager netManager) {
		super("ReceiveRunner", netManager);
	}

	@Override
	protected void update() {
		try {
			InputStream inStream = netManager.getInStream();

			/* read the header */
			byte[] headerBytes = new byte[Header.LENGTH];
			
			int readBytes = inStream.read(headerBytes);
			if(readBytes == -1) {
				/* the function was called, but the input stream is empty */
				netManager.report(new IOException());
			}

			/* create the header */
			Header header = new Header(headerBytes);

			/** read the body */
			/* byte array for the body*/
			int bodySize = (int) header.getBodySize();
			boolean error;
			if (bodySize < 1e8 && bodySize >= 0) {		
				error = false;			
				byte[] bytes = new byte[bodySize];
				
				/* read the body in blocks of MAX_RCV_SIZE*/
				int destPos = 0;
				for (int startIdx = 0; startIdx < bytes.length; startIdx = startIdx + MAX_RCV_SIZE) {
					byte[] byteArray = new byte[Math.min(MAX_RCV_SIZE, bytes.length-startIdx)];
					
					/* wait for bytes to be available*/
					long blockStartTime = System.currentTimeMillis();
					while (inStream.available() < byteArray.length 
							&& System.currentTimeMillis() - blockStartTime < MAX_MESSAGE_WAIT_TIME) {
						/* wait for MAX_MESSAGE_WAIT_TIME ms for message to arrive completely*/
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {}
					}
					/* read the bytes*/
					readBytes = inStream.read(byteArray);
					if (readBytes < byteArray.length) {
						error = true;
						log.warn("Expected to read " + bytes.length + " while reading only " + readBytes + ".");
					}
					/* copy bytes from block into body bytes array*/
					System.arraycopy(byteArray, 0, bytes, destPos, byteArray.length);
					destPos = destPos + byteArray.length;
				}
	
				/* create the message */
				RawOpenIGTMessage msg = new RawOpenIGTMessage(headerBytes, bytes);
				
				if (msg != null) {
					netManager.addToReceiveQueue(msg);
				}
			}
			else {
				error = true;
			}
			if (error) {
				byte[] byteArray = new byte[inStream.available()];
				readBytes = inStream.read(byteArray);	
				log.warn("Skipped " + readBytes + " bytes.");				
			}
		} 
		catch (IOException e) {
			netManager.report(e);
		}
	}
}
