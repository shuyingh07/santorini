package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.java.com.example.Board;

import java.util.List;

public class BoardTest {

    /**
     * Test for the findAndPlaceWorker method.
     */
    @Test
    public void testFindAndPlaceWorker() {
        Board board = new Board();
        board.initializeWorker(0, 0, 1, 1, 1);
        board.findAndPlaceWorker(0, 0, 1, 2, 1);
        List<main.java.com.example.Worker> workers = board.getWorkers();
        boolean isMoved = false;
        for (main.java.com.example.Worker worker : workers) {
            if (worker.getX() == 1 && worker.getY() == 2) {
                isMoved = true;
                break;
            }
        }
        assertTrue(isMoved);
    }

    /**
     * Test for the initializeWorker method.
     */
    @Test
    public void testInitializeWorker() {
        Board board = new Board();
        board.initializeWorker(0, 0, 1, 1, 1);
        List<main.java.com.example.Worker> workers = board.getWorkers();
        assertEquals(2, workers.size());
        assertEquals(0, workers.get(0).getX());
        assertEquals(0, workers.get(0).getY());
        assertEquals(1, workers.get(1).getX());
        assertEquals(1, workers.get(1).getY());
    }

    /**
     * Test for the buildBlock method.
     */
    @Test
    public void testBuildBlock() {
        Board board = new Board();
        board.buildBlock(2, 2);
        assertEquals(1, board.getTowerHeight(2, 2));
    }

    /**
     * Test for the getGrid method.
     */
    @Test
    public void testGetGrid() {
        Board board = new Board();
        assertNotNull(board.getGrid(0, 0));
    }

    /**
     * Test for the getGridDome method.
     */
    @Test
    public void testGetGridDome() {
        Board board = new Board();
        assertFalse(board.getGridDome(0, 0));
    }

    /**
     * Test for the getTowerHeight method.
     */
    @Test
    public void testGetTowerHeight() {
        Board board = new Board();
        assertEquals(0, board.getTowerHeight(0, 0));
    }

    /**
     * Test for the getWorkers method.
     */
    @Test
    public void testGetWorkers() {
        Board board = new Board();
        assertNotNull(board.getWorkers());
    }

    /**
     * Test for the getGrids method.
     */
    @Test
    public void testGetGrids() {
        Board board = new Board();
        assertNotNull(board.getGrids());
    }

    /**
     * Test for the getROW method.
     */
    @Test
    public void testGetROW() {
        Board board = new Board();
        assertEquals(5, board.getROW());
    }

    /**
     * Test for the getCOL method.
     */
    @Test
    public void testGetCOL() {
        Board board = new Board();
        assertEquals(5, board.getCOL());
    }

    /**
     * Test for the hasWorker method.
     */
    @Test
    public void testHasWorker() {
        Board board = new Board();
        board.initializeWorker(0, 0, 1, 1, 1);
        assertTrue(board.hasWorker(0, 0));
        assertFalse(board.hasWorker(2, 2));
    }
}
