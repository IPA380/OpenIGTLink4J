/*=========================================================================
  Program:   OpenIGTLink4J Library
  Language:  java
  
  Copyright (c) Fraunhofer IPA. All rights reserved.
  
  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.
=========================================================================*/ 
 
package msg.point;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PointsTest {

    Points points;
	PointElement elem_1;
	PointElement elem_2;
	PointElement elem_3;
	PointElement elem_4;

	/**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
		elem_1 = new PointElement(3, 0, 0);
		elem_2 = new PointElement(1, 0, 0);
		elem_3 = new PointElement(-5, 0, 0);
		elem_4 = new PointElement(0, 0, 0);
		elem_1.setRadius((float) 5);
		elem_2.setRadius((float) 7.5);
		elem_3.setRadius((float) 10);
		elem_4.setRadius((float) 12);
		
		points = new Points();
		points.addElement(elem_1);
		points.addElement(elem_2);
		points.addElement(elem_3);
		points.addElement(elem_4);
	}

	@Test
	public void testSortByRadius() {
		points.sortByRadius();
		
		assertEquals(elem_4, points.getElement(0));
		assertEquals(elem_3, points.getElement(1));
		assertEquals(elem_2, points.getElement(2));
		assertEquals(elem_1, points.getElement(3));
	}

	@Test
	public void testSortByDistToLargest() {
		points.sortByDistToLargest();
		
		assertEquals(elem_4, points.getElement(0));
		assertEquals(elem_2, points.getElement(1));
		assertEquals(elem_1, points.getElement(2));
		assertEquals(elem_3, points.getElement(3));
	}

	@Test
	public void testCentroid() {		
		assertArrayEquals(new float[]{-0.25f, 0, 0}, points.getCentroid(), 1e-6f);
	}
	
	@Test
	public void testSortByCentroidDist() {
		points.sortByCentroidDist();
		
		assertEquals(elem_4, points.getElement(0));
		assertEquals(elem_2, points.getElement(1));
		assertEquals(elem_1, points.getElement(2));
		assertEquals(elem_3, points.getElement(3));
	}

}
