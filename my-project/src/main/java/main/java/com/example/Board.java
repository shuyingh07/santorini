package main.java.com.example;

import java.util.List;
import java.util.ArrayList;


/**
 * The board for a game
 * This class manages the board status, including its size, grid list and worker list.
 */
public class Board {
    private final int row = 5;
    private final int col = 5;
    private final int workerNum = 4;

    private final List<List<Grid>> grids = new ArrayList<>(row); // A 5x5 grid representing the game board.

    private final List<Worker> workers = new ArrayList<>(workerNum); // Store the 4 workers status.

    public Board(){
        for (int i = 0; i < row; i++) {
            List<Grid> rowList = new ArrayList<>(col);
            for (int j = 0; j < col; j++) {
                rowList.add(new Grid());
            }
            grids.add(rowList);
        }
    }

    /**
     * Find the worker, place it to a new position and set isMoved attribute of this worker to true
     * @param fromX original x position of the worker
     * @param fromY original y position of the worker
     * @param toX targeted x position of the worker
     * @param toY targeted y position of worker
     * @param playerId the player id of current player
     */
    public void findAndPlaceWorker(int fromX, int fromY, int toX, int toY,  int playerId) {
        for(Worker worker : workers) {
            if (fromX == worker.getX() && fromY == worker.getY() && worker.getPlayerId() == playerId) {
                worker.move(toX, toY);
                worker.setIsMoved(true);
            }
        }
    }

    /**
     * Place two workers of specified player
     * @param x1 targeted x position of worker 1
     * @param y1 targeted y position of worker 1
     * @param x2 targeted x position of worker 2
     * @param y2 targeted y position  of worker 2
     * @param playerId the player id of current player
     */
    public void initializeWorker(int x1, int y1, int x2, int y2, int playerId) {
        workers.add(new Worker(x1, y1, playerId));
        workers.add(new Worker(x2, y2, playerId));
    }

    /**
     * Building at specified position, the height of this grid will plus 1.
     * @param x position x
     * @param y position y
     */
    public void buildBlock(int x, int y) {
        grids.get(x).get(y).addHeight();
    }

    /**
     * Retrieves the grid.
     * 
     * @param x position x
     * @param y position y
     * @return The grid.
     */
    public Grid getGrid(int x, int y) {
        return grids.get(x).get(y);
    }

    /**
     * Check whether the grid has a dome.
     * @param x position x
     * @param y position y
     * @return {@code true} if the grid has a dome, {@code false} otherwise
     */
    public Boolean getGridDome(int x, int y) {
        Grid grid = grids.get(x).get(y);
        return grid.isHasDome();
    }

    /**
     * Gets the height of the tower located at a specific position on the grid.
     *
     * @param x The x location of the grid.
     * @param y The y location of the grid.
     * @return The height of the tower at the specified grid.
     */
    public int getTowerHeight(int x, int y) {
        return grids.get(x).get(y).getHeight();
    }

    /**
     * Retrieves the array of workers.
     * @return An array of Worker objects.
     */
    public List<Worker> getWorkers() {
        return this.workers;
    }

    /**
     * Retrieves the grid list.
     * @return The grid list.
     */
    public List<List<Grid>> getGrids() {
        return this.grids;
    }


    /**
     * Retrieves the row length of the board.
     * @return The row length of the board.
     */
    public int getROW() {
        return this.row;
    }

    /**
     * Retrieves the column length of the board.
     * @return The column length of the board.
     */
    public int getCOL() {
        return this.col;
    }

    /**
     * Check whether the specified grid is occupied by a worker.
     * @param x The x location of the grid.
     * @param y The y location of the grid.
     * @return True if the grid is occupied by a worker.
     */
    public boolean hasWorker(int x, int y){
        for (Worker worker : workers) {
            if (worker != null && worker.getX() == x && worker.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
