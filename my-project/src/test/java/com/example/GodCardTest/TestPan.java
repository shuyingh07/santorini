package com.example.GodCardTest;

import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.GodClass.Pan;
import main.java.com.example.GodClass.WinStrategy;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestPan {
    private Board board;
    private WinStrategy pan;

    @Before
    public void setUp() {
        board = new Board();
        pan = new Pan();
    }

    // Test to check if moving down from the first level (height = 1) does not result in a win
    @Test
    public void testMoveDownFromFirstLevel() {
        board.buildBlock(0, 0); // Build one block, height = 1
        assertFalse("Moving down from the first level should not result in a win",
                pan.isWinningMove(board, 0, 0, 0, 1));
    }

    // Test to check if moving down from the second level (height = 2) results in a win
    @Test
    public void testMoveDownFromSecondLevel() {
        board.buildBlock(1, 0);
        board.buildBlock(1, 0); // Build two blocks, height = 2
        assertTrue("Moving down from the second level should result in a win",
                pan.isWinningMove(board, 1, 0, 1, 1));
    }

    // Test to check if moving down from the third level (height = 3) results in a win
    @Test
    public void testMoveDownFromThirdLevel() {
        for (int i = 0; i < 3; i++) {
            board.buildBlock(2, 0); // Build three blocks, height = 3
        }
        assertTrue("Moving down from the third level should result in a win",
                pan.isWinningMove(board, 2, 0, 2, 1));
    }

    // Test to verify winning by ascending to the third level
    @Test
    public void testAscendingToThirdLevel() {
        for (int i = 0; i < 2; i++) {
            board.buildBlock(3, 1); // Build two blocks, height = 2
        }
        board.buildBlock(3, 1); // Third level reached
        assertTrue("Ascending to the third level should result in a win",
                pan.isWinningMove(board, 3, 0, 3, 1));
    }

}

