package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.com.example.Board;
import main.java.com.example.Grid;
import main.java.com.example.Player;
import main.java.com.example.Validator;


public class PlayerTest {
    int rows;
    int cols;
    Player player;
    Validator validator;
    Board board;

    @Before
    public void setUp() {
        player = new Player(0);
        validator = new Validator();
        
        rows = cols = 5;
        board = new Board(rows, cols);
    }

    @Test
    public void testWorkerInit() {
        assertFalse(player.workerInit(validator, board, 0, 0, 0, 0));
        assertFalse(player.workerInit(validator, board, -1, -1, -1, -1));
        assertFalse(player.workerInit(validator, board, rows, cols, 0, 0));
        assertTrue(player.workerInit(validator, board, 0, 0, 1, 1));

        Grid grid1 = board.getGrid(0, 0);
        Grid grid2 = board.getGrid(1, 1);
        assertTrue(grid1.getOccupy());
        assertTrue(grid2.getOccupy());
        assertFalse(player.workerInit(validator, board, 0, 0, 1, 1));
        grid1.clearOccupy();
        grid2.clearOccupy();
        assertTrue(player.workerInit(validator, board, 0, 0, 1, 1));
    }

    @Test
    public void testChooseWorker() {
        assertFalse(player.chooseWorker(-1));
        assertTrue(player.chooseWorker(0));
        assertTrue(player.chooseWorker(1));
        assertFalse(player.chooseWorker(2));
    }

    @Test
    public void testMoveWorker() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);

        assertFalse(player.moveWorker(validator, board, -1, -1)); // not in boundary
        assertFalse(player.moveWorker(validator, board, rows, cols)); // not in boundary
        assertFalse(player.moveWorker(validator, board, 1, 1)); // Occupied
        assertFalse(player.moveWorker(validator, board, 1, 2)); // No adjacent

        Grid targetGrid = board.getGrid(1, 0);
        Grid workerGrid = board.getGrid(0, 0);
        assertTrue(player.moveWorker(validator, board, 1, 0));
        assertFalse(workerGrid.getOccupy());
        assertTrue(targetGrid.getOccupy());

    }

    @Test
    public void testBuild() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);

        assertFalse(player.build(validator, board, -1, -1)); // not in boundary
        assertFalse(player.build(validator, board, rows, cols)); // not in boundary
        assertFalse(player.build(validator, board, 1, 1)); // Occupied
        assertFalse(player.build(validator, board, 1, 2)); // No adjacent

        Grid grid = board.getGrid(0, 1);
        for(int i = 0; i <= 3; i++){
            assertEquals(grid.getHeight(), i);
            assertTrue(player.build(validator, board, 0, 1));
        }
        assertFalse(player.build(validator, board, 0, 1));
    }
}
