package main.java.com.example;

import java.util.ArrayList;

public class Board {
    private int rows;
    private int cols;
    private ArrayList<ArrayList<Grid>> grids;

    /**
    * Initialize board with specified row and column length
    * @param row - specified row length of the board
    * @param col - specified column length of the board
    */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        grids = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<Grid> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(new Grid(i, j));
            }
            grids.add(row);
        }

    }

    /**
    * Get the grid in a specified location
    * @param x - specified x location
    * @param y - specified y location
    * @returns the specified grid
    */
    public Grid getGrid(int x,  int y) {
        if (x < 0 || y < 0){
            return null;
        }
        if(x >= this.rows || y >= this.cols){
            return null;
        }
        else{
            ArrayList<Grid> row = this.grids.get(x);
            return row.get(y);
        }
    }

    /**
    * Get the row length of the board
    * @returns row length of the board
    */
    public int getRowLen() {
        return this.rows;
    }

    /**
    * Get the column length of the board
    * @returns column length of the board
    */
    public int getColLen() {
        return this.cols;
    }

}
