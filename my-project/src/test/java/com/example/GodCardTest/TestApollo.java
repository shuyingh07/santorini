package com.example.GodCardTest;

import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.GodClass.MoveStrategy;
import main.java.com.example.GodClass.Apollo;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestApollo {
    private Board board;
    private MoveStrategy moveStrategy;
    private Player[] players = new Player[2];

    @Before
    public void setUp() {
        board = new Board();
        moveStrategy = new Apollo();
        players[0] = new Player(0);
        players[1] = new Player(1);
        players[0].setMoveStrategy(moveStrategy);
        players[1].setMoveStrategy(moveStrategy);
        players[0].initializeWorker(board, 0, 0, 1, 1);
        players[1].initializeWorker(board, 2, 2, 3, 3);
    }

    // Test when trying to swap with own worker, which should be invalid
    @Test
    public void testSwapWithOwnWorker() {
        assertFalse("Moving onto own worker should be invalid", moveStrategy.isValidMove(board, 0, 0, 1, 1, 0));
    }

    // Test valid swap with opponent's worker
    @Test
    public void testValidSwapWithOpponent() {
        assertTrue("Swapping with opponent's worker should be valid", moveStrategy.isValidMove(board, 1, 1, 2, 2, 0));
    }

    // Test move to an unoccupied space within boundaries
    @Test
    public void testMoveToUnoccupiedSpace() {
        assertTrue("Moving to an unoccupied space within bounds should be valid", moveStrategy.isValidMove(board, 0, 0, 0, 1, 0));
    }

    // Test move to an out of boundary position
    @Test
    public void testMoveOutOfBoundary() {
        assertFalse("Moving out of bounds should be invalid", moveStrategy.isValidMove(board, 0, 0, -1, -1, 0));
    }

    // Test move where target cell is blocked by a dome
    @Test
    public void testMoveToDomeBlockedCell() {
        for (int i = 0; i < 4; i++) {
            board.buildBlock(0, 1); // Build dome at (0, 1)
        }
        assertFalse("Moving to a dome-blocked cell should be invalid", moveStrategy.isValidMove(board, 0, 0, 0, 1, 0));
    }

    // Test move beyond maximum allowed distance
    @Test
    public void testMoveBeyondMaxDistance() {
        assertFalse("Moving beyond maximum allowed distance should be invalid", moveStrategy.isValidMove(board, 0, 0, 2, 0, 0));
    }

    // Test move resulting in height difference more than allowed
    @Test
    public void testMoveWithInvalidHeightDifference() {
        board.buildBlock(0, 1); // Height 1
        board.buildBlock(0, 1); // Height 2
        board.buildBlock(0, 1); // Height 3
        assertFalse("Moving to a cell with too much height difference should be invalid", moveStrategy.isValidMove(board, 0, 0, 0, 1, 0));
    }

}
