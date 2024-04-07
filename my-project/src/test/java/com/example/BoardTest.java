// package com.example;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;
// import static org.junit.Assert.assertNull;


// import org.junit.Before;
// import org.junit.Test;

// import main.java.com.example.Board;

// public class BoardTest {
//     int rows;
//     int cols;
//     private Board board;

//     @Before
//     public void setUp() {
//         rows = 5;
//         cols = 5;
//         board = new Board(rows, cols);
//     }

//     @Test
//     public void testGetRowLen() {
//         assertEquals(board.getRowLen(), rows);
//     }

//     @Test
//     public void testGetColLen() {
//         assertEquals(board.getColLen(), cols);
//     }

//     @Test
//     public void testGetGrid() {
//         assertNull(board.getGrid(-1, -1));
//         assertNull(board.getGrid(rows, cols));

//         for(int i = 0; i < rows; i++){
//             for(int j = 0; j < cols; j++){
//                 assertNotNull(board.getGrid(i, j));
//             }
//         }
//     }
// }