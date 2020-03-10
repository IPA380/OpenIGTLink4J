/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.image;

import msg.OIGTL_DataMessage;
import util.Endian;
import util.Header;

/**
 * This class creates a ImageMessage object from bytes received or help to generate
 * bytes to send from it as specified in
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/image.md
 * 
 * The IMAGE format supports 2D or 3D images with metric information including image
 * matrix size, voxel size, coordinate system type, position, and orientation. The 
 * body section of the IMAGE data consists of two parts: image header to transfer 
 * the metric information and image body to transfer the array of pixel or voxel 
 * values. The data type of pixel or voxel can be either scalar or vector, and 
 * numerical values can be 8-, 16-, 32-bit integer, or 32- or 64-bit floating point. 
 * The pixel values can be either big-endian or little-endian, since the sender 
 * software can specify the byte order in the image header. The format also supports 
 * "partial image transfer", in which a region of the image is transferred instead 
 * of the whole image. This mechanism is suitable for real-time applications, in 
 * which images are updated region-by-region. The sub-volume must be box-shaped and 
 * defined by 6 parameters consisting of the indices for the corner voxel of the 
 * sub-volume and matrix size of the sub-volume.
 * 
 * @author Andreas Rothfuss
 * 
 */
public class ImageMessage extends OIGTL_DataMessage {

	/** The messages data type */
	public static final String DATA_TYPE = "IMAGE";

	/** The header part of the message */
	ImageHeader imageHeader;
	/** Binary image data () Image data (Endian is determined by "E" field) */
	byte[] imageData;

    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     **/
    public ImageMessage(String deviceName) {
        super(DATA_TYPE, deviceName, ImageHeader.SIZE);
    }


    /**
     *** Constructor to be used to create message to send them with this
     * constructor you must use method SetImageHeader, then CreateBody and then
     * getBytes to send them
     *** 
     * @param deviceName
     *            Device Name
     * @param imageHeader
     * @param imageData
     **/
    public ImageMessage(String deviceName, ImageHeader imageHeader, byte[] imageData) {
        super(DATA_TYPE, deviceName, ImageHeader.SIZE + imageData.length);
        this.imageHeader = imageHeader;
        this.imageData = imageData;
    }

    /**
     *** Constructor to be used to create message from received data
     * 
     * @param header	header object
     * @param body		byte array representing the message body
     */
    public ImageMessage(Header header, byte body[]){
        super(header, body);
    }

	@Override
	public byte[] getBody() {
		byte[] retVal = new byte[ImageHeader.SIZE + imageData.length];
		
		System.arraycopy(imageHeader.getBytes(), 0, retVal, 0, ImageHeader.SIZE);
		System.arraycopy(imageData, 0, retVal, ImageHeader.SIZE, imageData.length);
		
		return retVal;
	}

	@Override
	protected boolean UnpackBody(byte[] body) {
		byte[] headerBytes = new byte[ImageHeader.SIZE];
		System.arraycopy(body, 0, headerBytes, 0, ImageHeader.SIZE);
		
		imageHeader = new ImageHeader(headerBytes);
		
		imageData = new byte[body.length - ImageHeader.SIZE];
		System.arraycopy(body, ImageHeader.SIZE, imageData, 0, imageData.length);
		
		return true;
	}
	
	/**
	 * @return the version
	 */
	public int getVersion() {
		return imageHeader.getVersion();
	}

	/**
	 * @return the imageType
	 */
	public int getImageType() {
		return imageHeader.getImageType();
	}

	/**
	 * @return the scalarType
	 */
	public ImageScalarType getScalarType() {
		return imageHeader.getScalarType();
	}

	/**
	 * @return the endian
	 */
	public Endian getEndian() {
		return imageHeader.getEndian();
	}

	/**
	 * @return the imageCOSType
	 */
	public CoordinateConvention getImageCOSType() {
		return imageHeader.getImageCOSType();
	}

	/**
	 * @return the dimensions
	 */
	public int[] getDimensions() {
		return imageHeader.getDimensions();
	}

	/**
	 * @return the normals_i
	 */
	public float[] getNormals_i() {
		return imageHeader.getNormals_i();
	}

	/**
	 * @return the normals_j
	 */
	public float[] getNormals_j() {
		return imageHeader.getNormals_j();
	}

	/**
	 * @return the normals_k
	 */
	public float[] getNormals_k() {
		return imageHeader.getNormals_k();
	}

	/**
	 * @return the origin
	 */
	public float[] getOrigin() {
		return imageHeader.getOrigin();
	}

	/**
	 * @return the subOffset
	 */
	public int[] getSubOffset() {
		return imageHeader.getSubOffset();
	}

	/**
	 * @return the subDimensions
	 */
	public int[] getSubDimensions() {
		return imageHeader.getSubDimensions();
	}
}
