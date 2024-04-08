package main.java.com.example;

public class Worker {
    private int x, y; 
    private final int playerId;
    private boolean isMoved;

    /**
     * Default Constructor
     * 
     * @param x The x position for the worker.
     * @param y The y position for the worker.
     * @param playerId The playerId the worker belongs to.
     */
    public Worker(int x, int y, int playerId) {
        this.x = x;
        this.y = y;
        this.playerId = playerId;
        this.isMoved = false;
    }

    /**
     * Get the x position of the worker.
     *
     * @return The x position value of the worker.
     */
    public int getX() {
        return x;
    }


    /**
     * Get the y position of the worker.
     *
     * @return The y position value of the worker.
     */
    public int getY() {
        return y;
    }

    /**
     * Move the worker to a specified position(x,y)
     * 
     *  @param x The new x position of the worker.
     * @param y The new y position of the worker.
     */
    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Get the playerId this worker belongs to.
     *
     * @return The playerId this worker belongs to.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Indicates whether the worker has moved or not.
     *
     * @return {@code}true if the worker has moved, otherwise false.
     */
    public boolean getIsMoved() {
        return isMoved;
    }

    /**
     * Sets the isMoved status of the work.
     *
     * @param moved The new isMoved status. True indicates the worker has moved in this round, and false indicates the opposite.
     */
    public void setIsMoved(boolean moved) {
        isMoved = moved;
    }
}


