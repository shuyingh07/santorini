package com.example.GodCardTest;

import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.GodClass.BuildStrategy;
import main.java.com.example.GodClass.Demeter;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestDemeter {
    private Board board;
    private BuildStrategy buildStrategy;
    private Player[] players = new Player[2];

    @Before
    public void setUp() {
        initializeGame();
    }

    private void initializeGame() {
        board = new Board();
        buildStrategy = new Demeter();
        players[0] = new Player(0);
        players[1] = new Player(1);
        players[0].setBuildStrategy(buildStrategy);
        players[1].setBuildStrategy(buildStrategy);
        players[0].initializeWorker(board, 0, 0, 1, 1);
        players[1].initializeWorker(board, 2, 2, 3, 3);
        players[0].move(board, 0, 0, 0, 1); // Player can only build after they moved their worker
    }

    private void initializeGameWithoutMove() {
        board = new Board();
        buildStrategy = new Demeter();
        players[0] = new Player(0);
        players[1] = new Player(1);
        players[0].setBuildStrategy(buildStrategy);
        players[1].setBuildStrategy(buildStrategy);
        players[0].initializeWorker(board, 0, 0, 1, 1);
        players[1].initializeWorker(board, 2, 2, 3, 3);
    }

    // Test to evaluate the correct handling of building based on differences in height
    @Test
    public void testValidBuild() {
        initializeGame();
        assertTrue("Should allow building on a block of height 1", buildStrategy.isValidBuild(board, 1, 0));
    }

    // Test to ensure Demeter cannot build twice on the same exact spot during a single turn
    @Test
    public void testBuildingTwiceNotOnSameSpot() {
        initializeGame();
        players[0].build(board, 0, 0);
        assertFalse("Should not allow second build on the same spot", players[0].build(board, 0, 0));
    }

    // Test to ensure that builds outside of board boundaries are correctly invalidated
    @Test
    public void testBuildingOutOfBounds() {
        initializeGame();
        assertFalse("Should not allow building out of bounds", players[0].build(board, -1, 0));
    }

    // Test to confirm that building on already occupied spaces is not permitted
    @Test
    public void testBuildingOnOccupiedSpace() {
        initializeGame();
        players[0].build(board, 1, 1); // Assume this space becomes occupied
        assertFalse("Should not allow building on an occupied space", players[0].build(board, 1, 1));
    }

    // Test to confirm that building on too far spaces is not permitted
    @Test
    public void testBuildingTooFar() {
        initializeGame();
        assertFalse("Should not allow building on an occupied space", players[0].build(board, 4, 4));
    }

    // Test to ensure that Demeter's special build rule is not applied when not in 'build' phase
    @Test
    public void testBuildingInWrongStatus() {
        initializeGameWithoutMove();
        assertFalse("Should not allow building when status is not 'build'", players[0].build(board, 0, 2));
    }

    // Test to ensure that Demeter cannot build on a space that has a dome (height of four)
    @Test
    public void testBuildingOnDome() {
        initializeGame();
        for (int i = 0; i < 4; i++) {
            board.buildBlock(0, 1);
        }
        assertFalse("Should not allow building on a space with a dome", buildStrategy.isValidBuild(board, 0, 1));
    }

    // Test to ensure that passAction correctly resets internal state for a new action
    @Test
    public void testPassAction() {
        initializeGame();
        players[0].build(board, 0, 0);  // First build
        buildStrategy.passAction();     // Reset the state
        assertTrue("Should allow building again on the same spot after passAction", players[0].build(board, 0, 0));
    }
}
