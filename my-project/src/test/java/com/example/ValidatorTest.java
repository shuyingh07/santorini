package com.example;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.com.example.Board;
import main.java.com.example.Grid;
import main.java.com.example.Player;
import main.java.com.example.Validator;
import main.java.com.example.Worker;

public class ValidatorTest {
    int rows;
    int cols;
    Player player;
    Worker worker;
    Validator validator;
    Board board;

    @Before
    public void setUp() {
        player = new Player(0);
        validator = new Validator();
        rows = cols = 5;
        board = new Board(rows, cols);

        player.workerInit(validator, board, 0, 0, 3, 3);
        player.chooseWorker(1);
        worker = player.getCurrentWorker();
    }

    @Test
    public void testIsInBoundary() {
        assertFalse(validator.isInBoundary(board, -1, -1));

        assertTrue(validator.isInBoundary(board, 0, 0));
        assertTrue(validator.isInBoundary(board, rows-1, cols-1));

        assertFalse(validator.isInBoundary(board, rows, cols));
    }

    @Test
    public void testIsAdjacentGrid() {
        assertTrue(validator.isAdjacentGrid(worker, 2, 2));
        assertTrue(validator.isAdjacentGrid(worker, 2, 3));
        assertTrue(validator.isAdjacentGrid(worker, 2, 4));
        assertTrue(validator.isAdjacentGrid(worker, 3, 2));
        assertTrue(validator.isAdjacentGrid(worker, 3, 4));
        assertTrue(validator.isAdjacentGrid(worker, 4, 2));
        assertTrue(validator.isAdjacentGrid(worker, 4, 3));
        assertTrue(validator.isAdjacentGrid(worker, 4, 4));

        assertFalse(validator.isAdjacentGrid(worker, -1, -1));
        assertFalse(validator.isAdjacentGrid(worker, 0, 0));
    }

    @Test
    public void isValidMove() {
        assertFalse(validator.isValidMove(board, worker, -1, -1)); // not in boundary
        assertFalse(validator.isValidMove(board, worker, rows, cols)); // not in boundary
        assertFalse(validator.isValidMove(board, worker, 0, 0)); // No adjacent
    }

    @Test
    public void testIsWin() {
        int workerX = worker.getX();
        int workerY = worker.getY();
        Grid grid = board.getGrid(workerX, workerY);

        assertFalse(validator.isWin(board, worker));
        for(int i = 0; i < 3; i++){
            grid.addBrick();
        }
        assertTrue(validator.isWin(board, worker));
    }
}
