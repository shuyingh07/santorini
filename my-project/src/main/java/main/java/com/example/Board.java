package main.java.com.example;

import java.util.ArrayList;

public class Board {
    private int rows;
    private int cols;
    private ArrayList<ArrayList<Grid>> grids;

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

    public int getRowLen() {
        return this.rows;
    }

    public int getColLen() {
        return this.cols;
    }

}
