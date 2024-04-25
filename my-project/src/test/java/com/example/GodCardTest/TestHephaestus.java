package com.example.GodCardTest;

import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.GodClass.BuildStrategy;
import main.java.com.example.GodClass.Hephaestus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestHephaestus {
    private Board board;
    private BuildStrategy buildStrategy;
    private Player[] players = new Player[2];

    @Before
    public void setUp() {
        board = new Board();
        buildStrategy = new Hephaestus();
        players[0] = new Player(0);
        players[1] = new Player(1);
        players[0].setBuildStrategy(buildStrategy);
        players[1].setBuildStrategy(buildStrategy);
        players[0].initializeWorker(board, 0, 0, 1, 1);
        players[1].initializeWorker(board, 2, 2, 3, 3);
    }

    // Test initial valid build is successful
    @Test
    public void initialBuildIsValid() {
        assertTrue("Initial valid build should succeed", buildStrategy.isValidBuild(board, 0, 1));
    }

    // Test subsequent build on the same spot within height restrictions
    @Test
    public void subsequentBuildOnSameSpotIsValid() {
        assertTrue("First build on spot should succeed", buildStrategy.isValidBuild(board, 0, 1));
        assertTrue("Second build on the same spot should succeed", buildStrategy.isValidBuild(board, 0, 1));
    }

    // Test subsequent build on a different spot is invalid
    @Test
    public void subsequentBuildOnDifferentSpotIsInvalid() {
        assertTrue("First build should succeed", buildStrategy.isValidBuild(board, 0, 1));
        assertFalse("Second build on a different spot should fail", buildStrategy.isValidBuild(board, 1, 0));
    }

    // Test builds outside board boundaries are invalid
    @Test
    public void buildOutsideBoundariesIsInvalid() {
        assertFalse("Building outside the board should fail", buildStrategy.isValidBuild(board, -1, 1));
    }

    // Test builds on an already dome-capped spot are invalid
    @Test
    public void buildOnDomeIsInvalid() {
        for (int i = 0; i < 4; i++) {
            board.buildBlock(0, 1); // Creating a dome
        }
        assertFalse("Building on a dome should fail", buildStrategy.isValidBuild(board, 0, 1));
    }

    // Test build is invalid if attempted beyond max tower height (height 4)
    @Test
    public void buildBeyondMaxHeightIsInvalid() {
        for (int i = 0; i < 3; i++) {
            board.buildBlock(0, 1); // Building up to height 3
        }
        assertTrue("Building at height 3 should succeed", buildStrategy.isValidBuild(board, 0, 1));
        assertFalse("Building at height 4 should fail", buildStrategy.isValidBuild(board, 0, 1));
    }

    // Test reset build state allows for new build location and height
    @Test
    public void resetBuildStateAllowsNewBuild() {
        assertTrue("Initial build should succeed", buildStrategy.isValidBuild(board, 0, 1));
        buildStrategy.passAction(); // Reset the build state
        assertTrue("New build after reset should succeed", buildStrategy.isValidBuild(board, 1, 0));
    }

    // Ensure that player cannot build if their status is not 'build'
    @Test
    public void statusNotBuildPreventsBuilding() {
        players[0].move(board, 0, 0, 1, 0); // Changing status from build to move
        assertFalse("Should not allow build when status is not 'build'", buildStrategy.isValidBuild(board, 1, 0));
    }
}

