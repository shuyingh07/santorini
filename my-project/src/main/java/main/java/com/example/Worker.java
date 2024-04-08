package main.java.com.example;

public class Worker {
    private int x, y; 
    private final int playerId;
    private boolean isMoved = false; // determine if this worker is moved in this round

    public Worker(int x, int y, int playerId) {
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }

    /**
     * Retrieves the x-coordinate of the worker.
     *
     * @return The x-coordinate value of the worker.
     */
    public int getX() {
        return x;
    }


    /**
     * Retrieves the y-coordinate of the worker.
     *
     * @return The y-coordinate value of the worker.
     */
    public int getY() {
        return y;
    }

    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Get the unique identifier for the player this worker belongs to.
     *
     * @return The unique identifier for the corresponding player.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Indicates whether the player has moved or not.
     *
     * @return True if the player has moved, otherwise false.
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


