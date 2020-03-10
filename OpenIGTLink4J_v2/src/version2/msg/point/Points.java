/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.jblas.FloatMatrix;

import util.BytesArray;

/**
 * Class representing {@link Points} as specified in 
 * https://github.com/openigtlink/OpenIGTLink/blob/master/Documents/Protocol/point.md
 * 
 * @author Andreas Rothfuss
 *
 */
public class Points {	

    /** List of all {@link PointElement}s contained in this */
	ArrayList<PointElement> list = new ArrayList<PointElement>();

	/**
	 * Method to add a {@link PointElement} to this
	 * 
	 * @param elem
	 * 		the {@link PointElement} to be added
	 * @return
	 * 		the number of {@link PointElement}s contained in this 
	 */
	public int addElement(PointElement elem){
	    list.add(elem);
	    return list.size();
	}

	/**
	 * Method to clear all {@link PointElement}s contained in this
	 */
	public void clearElements() {
		list.clear();
	}

	/**
	 * Method to get the number of {@link PointElement}s in this
	 * 
	 * @return
	 * 		the number of {@link PointElement}s contained in this
	 */
	public int getNumberOfElements(){
	    return list.size();
	}

	/**
	 * Method to get the ith {@link PointElement} contained in this
	 *  
	 * @param i
	 * 		the position from which to get the {@link PointElement}
	 * @return
	 * 		the {@link PointElement} at position i
	 */
	public PointElement getElement(int i){
		return list.get(i);
	}

	/**
	 * Method to sort the {@link PointElement}s according to their radius
	 */
	public void sortByRadius(){
		Collections.sort(list, new DoubleComparator<PointElement>(SortingOrder.DESCENDING){
			@Override double getCriteria(PointElement object) {return object.getRadius();}
		});
	}

	/**
	 * Method to sort the {@link PointElement}s according to their distance to 
	 * the centroid of all points
	 */
	public void sortByCentroidDist(){
		final float[] centroid = getCentroid();
		Collections.sort(list, new DoubleComparator<PointElement>(){
			@Override 
			double getCriteria(PointElement object) {
				FloatMatrix toCentroid = new FloatMatrix(centroid).sub(
						new FloatMatrix(object.getPosition()));
				return toCentroid.norm2();
			}
		});
	}

	/**
	 * Method to sort the {@link PointElement}s according to their distance to 
	 * the largest {@link PointElement} (by radius)
	 */
	public void sortByDistToLargest(){
		sortByRadius();
		final float[] largest = list.get(0).getPosition();
		Collections.sort(list, new DoubleComparator<PointElement>(){
			@Override 
			double getCriteria(PointElement object) {
				FloatMatrix toLargest = new FloatMatrix(largest).sub(
						new FloatMatrix(object.getPosition()));
				return toLargest.norm2();
			}
		});
	}


	/**
	 * Method to get the {@link PointElement}s centroid
	 *   
	 * @return
	 * 		the centroid as a float[]
	 */
	float[] getCentroid() {
		FloatMatrix mat = FloatMatrix.zeros(3, 0);
		for (PointElement elem : list) {
			mat = FloatMatrix.concatHorizontally(mat, new FloatMatrix(elem.getPosition()));
		}
		return mat.rowMeans().toArray();
	}

	/**
	 * Enumeration for sorting orders
	 */
	enum SortingOrder{
		ASCENDING,
		DESCENDING;
	}


	/**
	 * Comperator to compare doubles
	 */
	abstract class DoubleComparator<T> implements Comparator<T>{
		SortingOrder order;

		public DoubleComparator() {
			this.order = SortingOrder.ASCENDING;
		}
		
		public DoubleComparator(SortingOrder order) {
			this.order = order;
		}

		abstract double getCriteria(T object);

		@Override
		public int compare(T o1, T o2) {
	        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
	        int criteriaAscending = getCriteria(o1) > getCriteria(o2) ? 1 : 
	        	(getCriteria(o1) < getCriteria(o2)) ? -1 : 0;
	        if (order == SortingOrder.DESCENDING) {
				criteriaAscending = -criteriaAscending;
			}
	        return criteriaAscending;
		}
	}

	/**
	 * Method to generate the serialized representation of the {@link Points}
	 * 
	 * @return
	 * 		serialized {@link Points}
	 */
	public byte[] convertToBytes(){
	
		BytesArray bytesArray = new BytesArray();
		PointElement element;
	    
	    for (int iter = 0; iter < getNumberOfElements(); iter ++){
	        element = getElement(iter);
	        bytesArray.putBytes(element.convertToBytes());
	    }
	    return bytesArray.getBytes();
	}

	/**
	 * Method to de-serialize {@link Points}
	 * 
	 * @param nElement
	 * 		the number of {@link PointElement}s contained in data
	 * @param data
	 * 		the serialized representation of the {@link Points}
	 * @return
	 * 		the de-serialized {@link Points}
	 */
	public static Points fromBytes(long nElement, byte[] data){
	
		Points retVal = new Points();
		BytesArray bytesArray = new BytesArray();
		bytesArray.putBytes(data);
		  
		// TODO: have a look at little / big endian conversion
		for (int iter = 0; iter < nElement; iter ++){
			PointElement element = PointElement.fromBytes(bytesArray.getBytes(PointElement.IGTL_POINT_ELEMENT_SIZE));
		    retVal.addElement(element);
		}
		
		return retVal;
	}
	
	@Override
	public String toString() {        
		String retVal = "\n";
		
	    for ( int index = 0; index < list.size(); index++){
	    	retVal += "============================\n";
	    	retVal += list.get(index).toString() + "\n";
	    	retVal += "============================\n";
	    }
	    
	    return retVal;
	}

}
