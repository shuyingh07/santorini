package main.java.com.example;

/**
 * Represents the game board in the board game.
 * This class is responsible for managing the state and interactions of the game's physical components,
 * including the grid that represents the playing area, the workers on the board, and any other elements specific to the game's design.
 * The Board class also collaborates with various strategy classes (e.g., move, build, win strategies) to enforce game rules
 * and validate player actions according to the current game state.
 */
public class Board {
    private final int ROW = 5;
    private final int COL = 5;
    private final int WOKERNUM = 4;

    private final Grid[][] grids = new Grid[ROW][COL]; // A 5x5 grid representing the game board. Each cell's value represents the height of towers.

    private final Worker[] workers = new Worker[WOKERNUM]; // An array to store up to four workers on the board.

    Board(){
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                grids[i][j] = new Grid();  // 假设Grid有无参构造器
            }
        }
    }

    /**
     * Find the worker, place it to a new position and set isMoved attribute of this worker to true
     * @param fromX position x of the original worker
     * @param fromY position y of the original worker
     * @param toX position x of the moved worker
     * @param toY position y of the moved worker
     * @param playerId the player id of current player
     */
    public void findAndPlaceWorker(int fromX, int fromY, int toX, int toY,  int playerId) {
        for(Worker worker : workers) {
            if (fromX == worker.getX() && fromY == worker.getY() && worker.getPlayerId() == playerId) {
                worker.setX(toX);
                worker.setY(toY);
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
        int counter1 = 0;
        int counter2 = 0;
        for(Worker worker : workers) {
            if(worker == null) {
                if (counter2 == 0){
                    workers[counter1] = new Worker(x1, y1, playerId);
                    counter2 += 1;
                } else {
                    workers[counter1] = new Worker(x2, y2, playerId);
                    break;
                }
            }
            counter1 += 1;
        }
    }

    /**
     * Build a block or dome on the board by adding one on the grid in position x,y
     * @param x position x
     * @param y position y
     */
    public void buildBlock(int x, int y) {
        grids[x][y].addHeight();
    }

    /**
     * Gets the height of the tower located at a specific position on the grid.
     *
     * @param x The x-coordinate of the tower's location.
     * @param y The y-coordinate of the tower's location.
     * @return The height of the tower at the specified position.
     */
    public int getTowerHeight(int x, int y) {
        return grids[x][y].getHeight();
    }

    /**
     * Retrieves the array of workers.
     * @return An array of Worker objects.
     */
    public Worker[] getWorkers() {
        return this.workers;
    }

    /**
     * Retrieves the grid.
     * @return The grid.
     */
    public Grid[][] getGrids() {
        return this.grids;
    }

    public int getROW() {
        return this.ROW;
    }

    public int getCOL() {
        return this.COL;
    }
}
