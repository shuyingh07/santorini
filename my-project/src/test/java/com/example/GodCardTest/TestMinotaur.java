package com.example.GodCardTest;

import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.GodClass.MoveStrategy;
import main.java.com.example.GodClass.Minotaur;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestMinotaur {
    private Board board;
    private MoveStrategy moveStrategy;
    private Player[] players = new Player[2];

    @Before
    public void setUp() {
        initializeGame();
    }

    private void initializeGame() {
        // This situation is for testing push destination has another worker
        board = new Board();
        moveStrategy = new Minotaur();
        players[0] = new Player(0);
        players[1] = new Player(1);
        players[0].setMoveStrategy(moveStrategy);
        players[1].setMoveStrategy(moveStrategy);
        players[0].initializeWorker(board, 0, 0, 1, 1);
        players[1].initializeWorker(board, 2, 2, 3, 3);
    }

    private void initializeGame2() {
        // This situation is for testing push destination doesn't have another worker
        board = new Board();
        moveStrategy = new Minotaur();
        players[0] = new Player(0);
        players[1] = new Player(1);
        players[0].setMoveStrategy(moveStrategy);
        players[1].setMoveStrategy(moveStrategy);
        players[0].initializeWorker(board, 0, 0, 1, 1);
        players[1].initializeWorker(board, 2, 1, 3, 3);
    }

    // Test to verify that a move to an occupied spot by the opponent's worker where the push is valid
    @Test
    public void testValidPushMove() {
        initializeGame2();
        assertTrue("Should allow pushing an opponent's worker when the space behind is free",
                moveStrategy.isValidMove(board, 1, 1, 2, 1, 0));
    }

    // Test to verify that a push move is invalid if the destination for the pushed worker is occupied
    @Test
    public void testPushIntoOccupiedSpace() {
        initializeGame();
        assertFalse("Should not allow pushing into an occupied space",
                moveStrategy.isValidMove(board, 1, 1, 2, 2, 0));
    }

    // Test to verify that a push move is invalid if it tries to push the worker outside of the board boundaries
    @Test
    public void testPushOutOfBounds() {
        initializeGame();
        assertFalse("Should not allow pushing a worker out of bounds",
                moveStrategy.isValidMove(board, 0, 0, 0, -1, 0));
    }

    // Test to verify that a push move is invalid if the pushing move is too far
    @Test
    public void testPushTooFar() {
        initializeGame();
        assertFalse("Should not allow moves greater than one space in any direction",
                moveStrategy.isValidMove(board, 0, 0, 2, 0, 0));
    }

    // Test to verify that a move is invalid if it would result in pushing a worker onto a space with a dome
    @Test
    public void testPushOntoDome() {
        initializeGame();
        // Setup a dome at the destination of the push
        for (int i = 0; i < 4; i++) {
            board.buildBlock(2, 2);
        }
        assertFalse("Should not allow pushing onto a dome",
                moveStrategy.isValidMove(board, 1, 1, 2, 2, 0));
    }

    // Test to ensure the Minotaur cannot push its own workers
    @Test
    public void testPushOwnWorker() {
        initializeGame();
        assertFalse("Should not allow pushing one's own worker",
                moveStrategy.isValidMove(board, 0, 0, 1, 1, 0));
    }

    // Test to ensure moves to positions with a height difference greater than one are invalid
    @Test
    public void testInvalidHeightDifference() {
        initializeGame();
        board.buildBlock(1, 1); // Height = 1
        board.buildBlock(1, 1); // Height = 2
        assertFalse("Should not allow moving to a place with a height difference greater than one",
                moveStrategy.isValidMove(board, 0, 0, 1, 1, 0));
    }

    // Test to ensure the strategy adheres to basic move validation (e.g., within bounds, correct height, etc.)
    @Test
    public void testBasicMoveValidation() {
        initializeGame();
        assertTrue("Should allow a basic valid move without pushing",
                moveStrategy.isValidMove(board, 0, 0, 0, 1, 0));
    }

    // Test to check if the player is in the 'move' phase, and should not perform another action if not
    @Test
    public void testMovePhaseValidation() {
        initializeGame();
        assertTrue(players[0].move(board, 0, 0, 1, 0));
        assertFalse(players[0].move(board, 1, 0, 2, 0));
    }
}