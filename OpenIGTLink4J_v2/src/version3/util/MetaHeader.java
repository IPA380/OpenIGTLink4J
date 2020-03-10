/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package util;

/**
 * Class representing an Metadata header part of an OpenIGTLink V3 message
 * for one metadata set as specified in http://openigtlink.org/developers/spec
 * 
 * @author Andreas Rothfuss
 *
 */
public class MetaHeader{
	
	/** Size of the key*/
	public final int keySize;
	/** Encoding of the value*/
	public final int valueEncoding;
	/** Size of the value */
	public final long valueSize;

    /**
     * Destination Constructor 
     * 
     * @param keySize
     * 		size of the key
     * @param valueEncoding
     * 		encoding of the value
     * @param valueSize
     * 		size of the value
     **/
	public MetaHeader(int keySize, int valueEncoding, long valueSize) {
		this.keySize = keySize;
		this.valueEncoding = valueEncoding;
		this.valueSize = valueSize;
	}

}
