/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.image;

import util.BytesArray;
import util.Endian;

/**
 * Class representing the header part of an image as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/image.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class ImageHeader {

	/** Size of the image header in bytes */
	public static int SIZE = 72;
	/** version number */
	public static int VERSION = 1;
	/** Default Number of Image Components (1:Scalar) */
	public static int DEFAULT_IMAGE_TYPE = 1;
	/** Default image scalar type */
	public static ImageScalarType DEFAULT_IMAGE_SCALR_TYPE = ImageScalarType.TYPE_UINT8;
	/** Default endian */
	public static Endian DEFAULT_ENDIAN = Endian.ENDIAN_LITTLE;
	/** Default coordinate system */
	public static CoordinateConvention DEFAULT_COS = CoordinateConvention.RAS;
	
	/** V: version number */
	int version;

	/** T: Number of Image Components (1:Scalar, >1:Vector). (NOTE: Vector data is stored fully interleaved.) */
	int imageType;
	
	/** S: Scalar type (2:int8 3:uint8 4:int16 5:uint16 6:int32 7:uint32 10:float32 11:float64) */
	ImageScalarType scalarType;
	
	/** E: Endian for image data (1:BIG 2:LITTLE) (NOTE: values in image header is fixed to BIG endian) */
	Endian endian;
	
	/** O: Image coordinate (1:RAS 2:LPS) */
	CoordinateConvention imageCOSType;
	
	/** RI, RJ, RK: Number of pixels in each direction */
	int[] dimensions;
	
	/** TX, TY, TZ: Transverse vector (direction for 'i' index) / The length represents pixel size in 'i' direction in millimeter */
	float[] normals_i;
	
	/** SX, SY, SZ: Transverse vector (direction for 'j' index) / The length represents pixel size in 'j' direction in millimeter */
	float[] normals_j;
	
	/** NX, NY, NZ: 	Normal vector of image plane(direction for 'k' index) / The length represents pixel size in 'z' direction or slice thickness in millimeter */
	float[] normals_k;
	
	/** PX, PY, PZ: Center position of the image (in millimeter) (*) */
	float[] origin;
	
	/** DI, DJ, DK: Starting index of subvolume */
	int[] subOffset;
	
	/** DRI, DRJ, DRK: Number of pixels of subvolume */
	int[] subDimensions;


	/**
	 * Constructor to create a fully specified {@link ImageHeader}
	 * imageType ,imageScalarType, endian and imageCOSType will be set to default values
	 * 
	 * @param dimensions
	 * 		Number of pixels in each direction
	 * @param normals_i
	 * 		Transverse vector (direction for 'i' index) / The length represents pixel size in 'i' direction in millimeter
	 * @param normals_j
	 * 		Transverse vector (direction for 'j' index) / The length represents pixel size in 'j' direction in millimeter
	 * @param normals_k
	 * 		Normal vector of image plane(direction for 'k' index) / The length represents pixel size in 'z' direction or slice thickness in millimeter
	 * @param origin
	 * 		Center position of the image (in millimeter) (*)
	 * @param subOffset
	 * 		Starting index of subvolume
	 * @param subDimensions
	 * 		Number of pixels of subvolume
	 */
	public ImageHeader( int[] dimensions, float[] normals_i, float[] normals_j, float[] normals_k, float[] origin, int[] subOffset, int[] subDimensions){
		this(DEFAULT_IMAGE_TYPE, DEFAULT_IMAGE_SCALR_TYPE, DEFAULT_ENDIAN, 
				DEFAULT_COS, dimensions, normals_i, normals_j, normals_k, 
				origin, subOffset, subDimensions);
	}

	/**
	 * Constructor to create a fully specified {@link ImageHeader}
	 * 
	 * @param imageType
	 * 		Number of Image Components (1:Scalar, >1:Vector). (NOTE: Vector data is stored fully interleaved.)
	 * @param imageScalarType
	 * 		Scalar type (2:int8 3:uint8 4:int16 5:uint16 6:int32 7:uint32 10:float32 11:float64)
	 * @param endian
	 * 		Endian for image data (1:BIG 2:LITTLE) (NOTE: values in image header is fixed to BIG endian)
	 * @param imageCOSType
	 * 		Image coordinate (1:RAS 2:LPS)
	 * @param dimensions
	 * 		Number of pixels in each direction
	 * @param normals_i
	 * 		Transverse vector (direction for 'i' index) / The length represents pixel size in 'i' direction in millimeter
	 * @param normals_j
	 * 		Transverse vector (direction for 'j' index) / The length represents pixel size in 'j' direction in millimeter
	 * @param normals_k
	 * 		Normal vector of image plane(direction for 'k' index) / The length represents pixel size in 'z' direction or slice thickness in millimeter
	 * @param origin
	 * 		Center position of the image (in millimeter) (*)
	 * @param subOffset
	 * 		Starting index of subvolume
	 * @param subDimensions
	 * 		Number of pixels of subvolume
	 */
	public ImageHeader(int imageType, ImageScalarType imageScalarType, 
			Endian endian, CoordinateConvention imageCOSType, int[] dimensions, 
			float[] normals_i, float[] normals_j, float[] normals_k, float[] origin,
			int[] subOffset, int[] subDimensions){
		version = VERSION;
		this.imageType = imageType;
		this.scalarType = imageScalarType;
		this.endian = endian;
		this.imageCOSType = imageCOSType;
		this.dimensions = dimensions;
		this.normals_i = normals_i;
		this.normals_j = normals_j;
		this.normals_k = normals_k;
		this.origin = origin;
		this.subOffset = subOffset;
		this.subDimensions = subDimensions;
	}

	/**
	 * Constructor to de-serialize a {@link ImageHeader} from bytes
	 * 
	 * @param bytes
	 * 		the serialized form of the {@link ImageHeader}
	 */
	public ImageHeader(byte[] headerBytes){
		if (headerBytes.length == SIZE){
			BytesArray bytesArray = new BytesArray();
			bytesArray.putBytes(headerBytes);

			version = (int) bytesArray.getLong(2);
			imageType = (int)bytesArray.getLong(1);
			scalarType = ImageScalarType.fromValue((int)bytesArray.getLong(1));
			endian = Endian.fromValue((int)bytesArray.getLong(1));
			imageCOSType = CoordinateConvention.fromValue((int)bytesArray.getLong(1));
			dimensions = new int[]{
					(int) bytesArray.getLong(2), 
					(int) bytesArray.getLong(2), 
					(int) bytesArray.getLong(2)};
			normals_i = new float[]{
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4)};
			normals_j = new float[]{
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4)};
			normals_k = new float[]{
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4)};
			origin = new float[]{
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4), 
					(float) bytesArray.getDouble(4)};
			subOffset = new int[]{
					(int) bytesArray.getLong(2), 
					(int) bytesArray.getLong(2), 
					(int) bytesArray.getLong(2)};
			subDimensions = new int[]{
					(int) bytesArray.getLong(2), 
					(int) bytesArray.getLong(2), 
					(int) bytesArray.getLong(2)};
		}
		else {
			throw new IllegalArgumentException("byte[] length must be " + SIZE);
		}
	}

	/**
	 * To serialize this
	 * @return
	 */
	public byte[] getBytes(){
		BytesArray bytesArray = new BytesArray();
		
		bytesArray.putLong(VERSION, 2);
		bytesArray.putLong(imageType, 1);
		bytesArray.putByte(scalarType.code);
		bytesArray.putByte(endian.code);
		bytesArray.putByte(imageCOSType.code);
		
		bytesArray.putLong(dimensions[0], 2);
		bytesArray.putLong(dimensions[1], 2);
		bytesArray.putLong(dimensions[2], 2);

		bytesArray.putDouble(normals_i[0], 4);
		bytesArray.putDouble(normals_i[1], 4);
		bytesArray.putDouble(normals_i[2], 4);

		bytesArray.putDouble(normals_j[0], 4);
		bytesArray.putDouble(normals_j[1], 4);
		bytesArray.putDouble(normals_j[2], 4);

		bytesArray.putDouble(normals_k[0], 4);
		bytesArray.putDouble(normals_k[1], 4);
		bytesArray.putDouble(normals_k[2], 4);

		bytesArray.putDouble(origin[0], 4);
		bytesArray.putDouble(origin[1], 4);
		bytesArray.putDouble(origin[2], 4);
		
		bytesArray.putLong(subOffset[0], 2);
		bytesArray.putLong(subOffset[1], 2);
		bytesArray.putLong(subOffset[2], 2);
		
		bytesArray.putLong(subDimensions[0], 2);
		bytesArray.putLong(subDimensions[1], 2);
		bytesArray.putLong(subDimensions[2], 2);
		
		return bytesArray.getBytes();
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the imageType
	 */
	public int getImageType() {
		return imageType;
	}

	/**
	 * @param imageType the imageType to set
	 */
	void setImageType(int imageType) {
		this.imageType = imageType;
	}

	/**
	 * @return the scalarType
	 */
	public ImageScalarType getScalarType() {
		return scalarType;
	}

	/**
	 * @param scalarType the scalarType to set
	 */
	void setScalarType(ImageScalarType scalarType) {
		this.scalarType = scalarType;
	}

	/**
	 * @return the endian
	 */
	public Endian getEndian() {
		return endian;
	}

	/**
	 * @param endian the endian to set
	 */
	void setEndian(Endian endian) {
		this.endian = endian;
	}

	/**
	 * @return the imageCOSType
	 */
	public CoordinateConvention getImageCOSType() {
		return imageCOSType;
	}

	/**
	 * @param imageCOSType the imageCOSType to set
	 */
	void setImageCOSType(CoordinateConvention imageCOSType) {
		this.imageCOSType = imageCOSType;
	}

	/**
	 * @return the dimensions
	 */
	public int[] getDimensions() {
		return dimensions;
	}

	/**
	 * @param dimensions the dimensions to set
	 */
	void setDimensions(int[] dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * @return the normals_i
	 */
	public float[] getNormals_i() {
		return normals_i;
	}

	/**
	 * @param normals_i the normals_i to set
	 */
	void setNormals_i(float[] normals_i) {
		this.normals_i = normals_i;
	}

	/**
	 * @return the normals_j
	 */
	public float[] getNormals_j() {
		return normals_j;
	}

	/**
	 * @param normals_j the normals_j to set
	 */
	void setNormals_j(float[] normals_j) {
		this.normals_j = normals_j;
	}

	/**
	 * @return the normals_k
	 */
	public float[] getNormals_k() {
		return normals_k;
	}

	/**
	 * @param normals_k the normals_k to set
	 */
	void setNormals_k(float[] normals_k) {
		this.normals_k = normals_k;
	}

	/**
	 * @return the origin
	 */
	public float[] getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	void setOrigin(float[] origin) {
		this.origin = origin;
	}

	/**
	 * @return the subOffset
	 */
	public int[] getSubOffset() {
		return subOffset;
	}

	/**
	 * @param subOffset the subOffset to set
	 */
	void setSubOffset(int[] subOffset) {
		this.subOffset = subOffset;
	}

	/**
	 * @return the subDimensions
	 */
	public int[] getSubDimensions() {
		return subDimensions;
	}

	/**
	 * @param subDimensions the subDimensions to set
	 */
	void setSubDimensions(int[] subDimensions) {
		this.subDimensions = subDimensions;
	}

}
