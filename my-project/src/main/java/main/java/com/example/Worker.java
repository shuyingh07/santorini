package main.java.com.example;


public class Worker {
    private int x;
    private int y;
    private int workerID;

    /**
    * Init worker with specified ID
    * @param workerID - specified ID that refers to a worker
    */
    public Worker(int workerID) {
        this.x = -1;
        this.y = -1;
        this.workerID = workerID;
    }

    /**
    * Get x location of the worker
    * @return the x location of the worker
    */
    public int getX() {
        return this.x;
    }

    /**
    * Get y location of the worker
    * @return the y location of the worker
    */
    public int getY() {
        return this.y;
    }

    /**
    * Move worker to a specified location
    * @param x - specified x location
    * @param y - specified y location
    * @return boolean that demonstrates a successful move
    */
    public boolean move(int x, int y) {
        this.x = x;
        this.y = y;
        return true;
    }

    /**
    * Build in a specified location
    * @param board - the game board
    * @param x - specified x location
    * @param y - specified y location
    * @return boolean that demonstrates a successful build
    */
    public boolean build(Board board, int x, int y) {
        Grid targetGrid = board.getGrid(x, y);
        targetGrid.addBrick();
        return true;
    }
}
