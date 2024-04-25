 package com.example;

 import org.junit.jupiter.api.Test;

 import main.java.com.example.Worker;

 import static org.junit.jupiter.api.Assertions.*;

 public class WorkerTest {

     /**
      * Test for the getX method.
      */
     @Test
     public void testGetX() {
         Worker worker = new Worker(0, 0, 1);
         assertEquals(0, worker.getX());
     }

     /**
      * Test for the getY method.
      */
     @Test
     public void testGetY() {
         Worker worker = new Worker(0, 0, 1);
         assertEquals(0, worker.getY());
     }

     /**
      * Test for the move method.
      */
     @Test
     public void testMove() {
         Worker worker = new Worker(0, 0, 1);
         worker.move(1, 1);
         assertEquals(1, worker.getX());
         assertEquals(1, worker.getY());
     }

     /**
      * Test for the getPlayerId method.
      */
     @Test
     public void testGetPlayerId() {
         Worker worker = new Worker(0, 0, 1);
         assertEquals(1, worker.getPlayerId());
     }

     /**
      * Test for the getIsMoved method.
      */
     @Test
     public void testGetIsMoved() {
         Worker worker = new Worker(0, 0, 1);
         assertFalse(worker.getIsMoved());
     }

     /**
      * Test for the setIsMoved method.
      */
     @Test
     public void testSetIsMoved() {
         Worker worker = new Worker(0, 0, 1);
         worker.setIsMoved(true);
         assertTrue(worker.getIsMoved());
     }
 }
