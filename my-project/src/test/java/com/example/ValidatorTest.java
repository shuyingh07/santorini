 package com.example;

 import org.junit.jupiter.api.Test;

 import main.java.com.example.Board;
 import main.java.com.example.Validator;

 import static org.junit.jupiter.api.Assertions.*;

 public class ValidatorTest {

     /**
      * Test for isValidInitial method with valid initial placements.
      */
     @Test
     public void testIsValidInitialValidPlacement() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test a valid initial placement
         assertTrue(validator.isValidInitial(board, 0, 0));
     }

     /**
      * Test for isValidInitial method with out-of-bounds position.
      */
     @Test
     public void testIsValidInitialOutOfBounds() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test an invalid initial placement (out of bounds)
         assertFalse(validator.isValidInitial(board, -1, 0));
     }

     /**
      * Test for isValidInitial method with position that has been occupied.
      */
     @Test
     public void testIsValidInitialIsOccupied() {
         Board board = new Board();
         Validator validator = new Validator();

         // Test an invalid initial placement (grid occupied)
         board.initializeWorker(0, 0, 1, 1, 1);
         assertFalse(validator.isValidInitial(board, 0, 0));
     }

     /**
      * Test for isValidMove method with valid moves.
      */
     @Test
     public void testIsValidMoveValidMove() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test a valid move
         board.initializeWorker(0, 0, 1, 1, 1);
         assertTrue(validator.isValidMove(board, 0, 0, 1, 0, 1));
     }

     /**
      * Test for isValidMove method with out-of-bounds position.
      */
     @Test
     public void testIsValidMoveOutOfBounds() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test an invalid move (out of bounds)
         assertFalse(validator.isValidMove(board, 0, 0, -1, 0, 1));
     }

     /**
      * Test for isValidMove method with occupied position.
      */
     @Test
     public void testIsValidMoveIsOccupied() {
         Board board = new Board();
         Validator validator = new Validator();

         // Test an invalid move (grid occupied)
         board.initializeWorker(2, 2, 3, 3, 2);
         assertFalse(validator.isValidMove(board, 2, 2, 3, 3, 2));
     }

     /**
      * Test for isValidMove method with far position.
      */
     @Test
     public void testIsValidMoveTooFar() {
         Board board = new Board();
         Validator validator = new Validator();

         // Test an invalid move (grid too far)
         board.initializeWorker(0, 0, 1, 0, 2);
         assertFalse(validator.isValidMove(board, 0, 0, 4, 4, 2));
     }

     /**
      * Test for isValidBuild method with valid builds.
      */
     @Test
     public void testIsValidBuildValidBuild() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test a valid build
         board.initializeWorker(0, 0, 1, 1, 1);
         board.findAndPlaceWorker(1, 1, 1, 0, 1); // Move worker to (1, 0)
         assertTrue(validator.isValidBuild(board, 1, 1));
     }

     /**
      * Test for isValidBuild method with out-of-bounds builds.
      */
     @Test
     public void testIsValidBuildInvalidBuildOutOfBounds() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test an invalid build (out of bounds)
         assertFalse(validator.isValidBuild(board, -1, 0));

     }

     /**
      * Test for isValidBuild method with occupied grid.
      */
     @Test
     public void testIsValidBuildInvalidBuildIsOccupied() {
         Board board = new Board();
         Validator validator = new Validator();

         board.initializeWorker(0, 0, 1, 1, 1);

         // Test an invalid build (grid occupied)
         assertFalse(validator.isValidBuild(board, 0, 0));
     }

     /**
      * Test for isValidBuild method with a far grid.
      */
     @Test
     public void testIsValidBuildInvalidBuildTooFar() {
         Board board = new Board();
         Validator validator = new Validator();

         board.initializeWorker(0, 0, 1, 1, 1);

         assertFalse(validator.isValidBuild(board, 4, 4));
     }

     /**
      * Test for isValidBuild method with max Height.
      */
     @Test
     public void testIsValidBuildInvalidBuildMaxHeight() {
         Board board = new Board();
         Validator validator = new Validator();

         // Test a valid build
         board.initializeWorker(0, 0, 1, 1, 1);
         board.buildBlock(0, 1);
         board.buildBlock(0, 1);
         board.buildBlock(0, 1);
         board.buildBlock(0, 1);

         // Test an invalid build (grid occupied)
         assertFalse(validator.isValidBuild(board, 0, 1));
     }

     /**
      * Test for isWinningMove method with a wining move.
      */
     @Test
     public void testIsWinningMoveWin() {
         Board board = new Board();
         Validator validator = new Validator();
         board.initializeWorker(0, 0, 1, 1, 1);
        
         // Test a winning move
         board.buildBlock(0, 1);
         board.buildBlock(0, 1);
         board.buildBlock(0, 1);
         board.findAndPlaceWorker(0, 0, 0, 1, 1); // Move worker to (0, 1)
         assertTrue(validator.isWinningMove(board));
     }

     /**
      * Test for isWinningMove method with a non-winning move.
      */
     @Test
     public void testIsWinningMoveNotWin() {
         Board board = new Board();
         Validator validator = new Validator();

         // Test a non-winning move
         board.initializeWorker(0, 0, 1, 1, 1);
         assertFalse(validator.isWinningMove(board));
     }

     /**
      * Test for isWithinBounds method within bounds.
      */
     @Test
     public void testIsWithinBoundsSatisfied() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test within bounds
         assertTrue(validator.isWithinBounds(board, 0, 0));
     }

     /**
      * Test for isWithinBounds method out of bounds.
      */
     @Test
     public void testIsWithinBoundsFailed() {
         Board board = new Board();
         Validator validator = new Validator();

         // Test out of bounds
         assertFalse(validator.isWithinBounds(board, -1, 0));
     }

     /**
      * Test for gridIsFree method.
      */
     @Test
     public void testGridIsFree() {
         Board board = new Board();
         Validator validator = new Validator();
        
         // Test free grid
         assertTrue(validator.gridIsFree(board, 0, 0));
        
         // Test occupied grid
         board.initializeWorker(0, 0, 1, 1, 1);
         assertFalse(validator.gridIsFree(board, 0, 0));
     }
 }
