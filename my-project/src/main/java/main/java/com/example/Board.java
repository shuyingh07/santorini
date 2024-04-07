package main.java.com.example;

import java.util.List;
import java.util.ArrayList;

public class Board {
    private final int row = 5;
    private final int col = 5;
    private final int workerNum = 4;

    private final List<List<Grid>> grids = new ArrayList<>(row); // A 5x5 grid representing the game board.

    private final List<Worker> workers = new ArrayList<>(workerNum); // Store the 4 workers status.

    Board(){
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
     * Place the two worker to the required position
     * @param x1 position x of desired position of worker 1
     * @param y1 position y of desired position of worker 1
     * @param x2 position x of desired position of worker 2
     * @param y2 position y of desired position of worker 2
     * @param playerId the player id of current player
     */
    public void initializeWorker(int x1, int y1, int x2, int y2, int playerId) {
        workers.add(new Worker(x1, y1, playerId));
        workers.add(new Worker(x2, y2, playerId));
    }

    /**
     * Build a block or dome on the board by adding one on the grid in position x,y
     * @param x position x
     * @param y position y
     */
    public void buildBlock(int x, int y) {
        grids.get(x).get(y).addHeight();
    }

    /**
     * Gets the height of the tower located at a specific position on the grid.
     *
     * @param x The x-coordinate of the tower's location.
     * @param y The y-coordinate of the tower's location.
     * @return The height of the tower at the specified position.
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
     * Retrieves the grid.
     * @return The grid.
     */
    public List<List<Grid>> getGrids() {
        return this.grids;
    }

    public int getROW() {
        return this.row;
    }

    public int getCOL() {
        return this.col;
    }

    public boolean hasWorker(int x, int y){
        for (Worker worker : workers) {
            if (worker != null && worker.getX() == x && worker.getY() == y) {
                return false;
            }
        }
        return true;
    }
}
