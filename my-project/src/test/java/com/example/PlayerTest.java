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

    // @Test
    // public void testMoveWorker() {
    //     player.workerInit(validator, board, 0, 0, 1, 1);
    //     player.chooseWorker(0);

    //     assertFalse(player.moveWorker(validator, board, -1, -1)); // not in boundary
    //     assertFalse(player.moveWorker(validator, board, rows, cols)); // not in boundary
    //     assertFalse(player.moveWorker(validator, board, 1, 1)); // Occupied
    //     assertFalse(player.moveWorker(validator, board, 1, 2)); // No adjacent

    //     Grid targetGrid = board.getGrid(1, 0);
    //     Grid workerGrid = board.getGrid(0, 0);
    //     assertTrue(player.moveWorker(validator, board, 1, 0));
    //     assertFalse(workerGrid.getOccupy());
    //     assertTrue(targetGrid.getOccupy());

    // }

    // @Test
    // public void testBuild() {
    //     player.workerInit(validator, board, 0, 0, 1, 1);
    //     player.chooseWorker(0);

    //     assertFalse(player.build(validator, board, -1, -1)); // not in boundary
    //     assertFalse(player.build(validator, board, rows, cols)); // not in boundary
    //     assertFalse(player.build(validator, board, 1, 1)); // Occupied
    //     assertFalse(player.build(validator, board, 1, 2)); // No adjacent

    //     Grid grid = board.getGrid(0, 1);
    //     for(int i = 0; i <= 3; i++){
    //         assertEquals(grid.getHeight(), i);
    //         assertTrue(player.build(validator, board, 0, 1));
    //     }
    //     assertFalse(player.build(validator, board, 0, 1));
    // }

    @Test
    public void testMoveWorker_NotInBoundary_NegativeCoordinates() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Worker should not move to negative coordinates.", player.moveWorker(validator, board, -1, -1));
    }

    @Test
    public void testMoveWorker_NotInBoundary_ExceedsGrid() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Worker should not move outside grid boundaries.", player.moveWorker(validator, board, rows, cols));
    }

    @Test
    public void testMoveWorker_OccupiedTarget() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Worker should not move to an occupied grid.", player.moveWorker(validator, board, 1, 1));
    }

    @Test
    public void testMoveWorker_NotAdjacent() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Worker should only move to adjacent grids.", player.moveWorker(validator, board, 2, 2));
    }

    @Test
    public void testMoveWorker_SuccessfulMove() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertTrue("Worker should move successfully to the target grid.", player.moveWorker(validator, board, 0, 1));
    }

    @Test
    public void testBuild_NotInBoundary_NegativeCoordinates() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Cannot build on grid with negative coordinates.", player.build(validator, board, -1, -1));
    }

    @Test
    public void testBuild_NotInBoundary_ExceedsGrid() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Cannot build outside of grid boundaries.", player.build(validator, board, rows, cols));
    }

    @Test
    public void testBuild_OccupiedTarget() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Cannot build on an occupied grid.", player.build(validator, board, 1, 1));
    }

    @Test
    public void testBuild_NotAdjacent() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertFalse("Can only build on adjacent grids.", player.build(validator, board, 2, 2));
    }

    @Test
    public void testBuild_SuccessfulBuild_FirstLevel() {
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertTrue("Should successfully build on the target grid.", player.build(validator, board, 0, 1));
    }

    @Test
    public void testBuild_SuccessfulBuild_SecondLevel() {
        // Assuming a building was already initiated in the setup or a previous test
        Grid grid = board.getGrid(0, 1);
        grid.addBrick(); // Simulate a build action to reach the first level
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertTrue("Should successfully build the second level on the target grid.", player.build(validator, board, 0, 1));
    }

    @Test
    public void testBuild_MaxHeight() {
        Grid grid = board.getGrid(0, 1);
        // Manually set the grid to max height minus one to simulate building to max height
        while (grid.getHeight() < 3) {
            grid.addBrick();
        }
        player.workerInit(validator, board, 0, 0, 1, 1);
        player.chooseWorker(0);
        assertTrue("Should successfully build the max height on the target grid.", player.build(validator, board, 0, 1));
        assertFalse("Should not be able to build beyond max height.", player.build(validator, board, 0, 1));
    }

}
