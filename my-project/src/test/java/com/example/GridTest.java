 package com.example;

 import org.junit.jupiter.api.Test;

 import main.java.com.example.Grid;

 import static org.junit.jupiter.api.Assertions.*;

 public class GridTest {

     /**
      * Test for the getHeight method.
      */
     @Test
     public void testGetHeight() {
         Grid grid = new Grid();
         assertEquals(0, grid.getHeight());
     }

     /**
      * Test for the addHeight method.
      */
     @Test
     public void testAddHeight() {
         Grid grid = new Grid();
         grid.addHeight();
         assertEquals(1, grid.getHeight());
     }

     /**
      * Test for the setHeight method.
      */
     @Test
     public void testSetHeight() {
         Grid grid = new Grid();
         grid.setHeight(3);
         assertEquals(3, grid.getHeight());
     }

     /**
      * Test for the getOccupyStatus method.
      */
     @Test
     public void testGetOccupyStatus() {
         Grid grid = new Grid();
         assertEquals(-1, grid.getOccupyStatus());
     }

     /**
      * Test for the setOccupyStatus method.
      */
     @Test
     public void testSetOccupyStatus() {
         Grid grid = new Grid();
         grid.setOccupyStatus(1);
         assertEquals(1, grid.getOccupyStatus());
     }

     /**
      * Test for the isHasDome method.
      */
     @Test
     public void testIsHasDome() {
         Grid grid = new Grid();
         assertFalse(grid.isHasDome());
     }

     /**
      * Test for the setHasDome method.
      */
     @Test
     public void testSetHasDome() {
         Grid grid = new Grid();
         grid.setHasDome(true);
         assertTrue(grid.isHasDome());
     }
 }
