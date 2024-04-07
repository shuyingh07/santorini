package main.java.com.example;


/**
 * Represents a worker in the game.
 * Workers are the game pieces controlled by players, capable of moving around the board and performing actions like building.
 * This class holds the positional data of the worker and tracks its movements and association with a specific player.
 */
public class Worker {
    private int x, y; // position coordinate
    private final int playerId; // corresponding playerId to the player
    private boolean isMoved = false; // determine if this worker is moved in this round

    public Worker(int x, int y, int playerId) {
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }

    /**
     * Sets the x-coordinate of the worker.
     *
     * @param x The new x-coordinate to set.
     */
    public void setX(int x) {
        this.x = x;
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
     * Sets the y-coordinate of the worker.
     *
     * @param y The new y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retrieves the y-coordinate of the worker.
     *
     * @return The y-coordinate value of the worker.
     */
    public int getY() {
        return y;
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


