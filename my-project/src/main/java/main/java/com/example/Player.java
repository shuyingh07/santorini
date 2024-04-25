package main.java.com.example;

import main.java.com.example.GodClass.BuildStrategy;
import main.java.com.example.GodClass.MoveStrategy;
import main.java.com.example.GodClass.WinStrategy;

/**
 * Represents a player in the game.
 * This class manages the plauer status, including its playerID, initialize and move workers, build in a specified position.
 */
public class Player {
    private final int playerId;
    private String status;
    private final Validator defaultStrategy = new Validator();
    private MoveStrategy moveStrategy = null;
    private BuildStrategy buildStrategy = null;
    private WinStrategy winStrategy = null;
    private boolean isExecuted = false;

    /**
     * Constructs a player with a unique identifier.
     * @param playerId Id that represents a player
     */
    public Player(int playerId) {
        this.playerId = playerId;
        this.status = "initialize";
    }

    /**
     * Initializes two workers for the player.
     * 
     * @param board board of the current game
     * @param x1 position x of worker one
     * @param y1 position y of worker two
     * @param x2 position x of worker two
     * @param y2 position y of worker two
     * @return {@code true} if workers are successfully initialized, {@code false} otherwise.
     */
    public boolean initializeWorker(Board board, int x1, int y1, int x2, int y2) {
        if (status.equals("initialize") && defaultStrategy.isValidInitial(board, x1, y1) && defaultStrategy.isValidInitial(board, x2, y2) && !(x1 == x2 && y1 == y2)) {
            this.status = "move";
            board.initializeWorker(x1, y1, x2, y2, playerId);
            return true;
        } else {
            System.out.println("Invalid initialization. Try again.");
            return false;
        }
    }

    /**
     * Moves a worker (settele at fromX, fromY) to a specified position (toX, toY).
     *
     * @param board board of the current game
     * @param fromX position x of the original worker
     * @param fromY position y of the original worker
     * @param toX position x of the moved worker
     * @param toY position y of the moved worker
     * @return {@code true} if the worker is moved successfully
     */
    public boolean move(Board board, int fromX, int fromY, int toX, int toY) {
        if ((moveStrategy == null && status.equals("move") && defaultStrategy.isValidMove(board, fromX, fromY, toX, toY, playerId))) {
            board.findAndPlaceWorker(fromX, fromY, toX, toY, playerId);
            this.status = "build";
            return true;
        } else if ((moveStrategy != null && status.equals("move") && moveStrategy.isValidMove(board, fromX, fromY, toX, toY, playerId))) {
            // Handle move actions under a specific move strategy
            if(moveStrategy.canTakeAnotherAction(this)) {
                // Execute the first or second build action based on the player's execution state
                if(!this.isExecuted) {
                    this.isExecuted = true;
                    board.findAndPlaceWorker(fromX, fromY, toX, toY, playerId);
                } else {
                    board.findAndPlaceWorker(fromX, fromY, toX, toY, playerId);
                    this.isExecuted = false;
                    this.status = "build";
                }
            } else {
                // Execute a single move action if the strategy does not allow a second action
                board.findAndPlaceWorker(fromX, fromY, toX, toY, playerId);
                this.status = "build";
            }

            return true;
        } else {
            // Output error message if the move is invalid
            System.out.println("Invalid move. Try again.");
            return false;
        }
    }

    /**
     * Builds a block or dome at a specified position (buildX, buildY).
     * 
     * @param board board of the current game
     * @param buildX the X position required to build a block or dome
     * @param buildY the Y position required to build a block or dome
     * @return {@code true} if the block or dome is successfully built
     */
    public boolean build(Board board, int buildX, int buildY) {
        // Check if the build is valid under standard rules or a specific build strategy
        if((buildStrategy == null && status.equals("build") && defaultStrategy.isValidBuild(board, buildX, buildY))) {
            // Execute the build action and update the status
            this.status = "move";
            board.buildBlock(buildX, buildY);
            resetIsMoved(board);
            return true;
        } else if(buildStrategy != null && status.equals("build") && buildStrategy.isValidBuild(board, buildX, buildY)) {
            // Handle build actions under a specific build strategy
            if(buildStrategy.canTakeAnotherAction(this)) {
                // Execute the first or second build action based on the player's execution state
                if(!this.isExecuted) {
                    this.isExecuted = true;
                    board.buildBlock(buildX, buildY);
                } else {
                    board.buildBlock(buildX, buildY);
                    this.isExecuted = false;
                    this.status = "move";
                    resetIsMoved(board);
                }
            } else {
                // Execute a single build action if the strategy does not allow a second action
                this.status = "move";
                board.buildBlock(buildX, buildY);
                resetIsMoved(board);
            }

            return true;
        } else {
            // Output error message if the build action is invalid
            System.out.println("Invalid build. Try again.");
            return false;
        }
    }

    /**
     * Check if a move atcion leads to win.
     * Both check default winning strategy (no god class) and special winning strategy (with god class)
     *
     * @param board The game board on which the move is being made.
     * @param fromX The original X coordinate of the worker.
     * @param fromY The original Y coordinate of the worker.
     * @param toX The target X coordinate for the worker.
     * @param toY The target Y coordinate for the worker.
     * @return {@code true} if the move results in a win, otherwise {@code false}.
     */
    public boolean isWinningMove(Board board, int fromX, int fromY, int toX, int toY) {
        // Check if the default winning condition is met
        boolean isDefaultWin = defaultStrategy.isWinningMove(board);
        
        // Check if a custom winning strategy is set and its conditions are met
        boolean isGodWin = false;
        if (winStrategy != null) {
            isGodWin = winStrategy.isWinningMove(board, fromX, fromY, toX, toY);
        }
    
        // Return true if either the default or custom winning conditions are met
        return isDefaultWin || isGodWin;
    }

    /**
     * Resets the movement state of all workers on the board.

     * @param board The game board whose workers' movement states are to be reset.
     */
    public void resetIsMoved(Board board) {
        // Iterate through all workers on the board
        for(Worker worker : board.getWorkers()) {
            // Reset the 'isMoved' state to false if it's currently true
            if(worker.getIsMoved()) {
                worker.setIsMoved(false);
            }
        }
    }

    /**
     * Get the status of player.
     * @return The status of player.
     */
    public String getStatus() {
        return status;
    }
    

    /**
     * Sets the current status of the player.
     * This status represents the current phase or action the player is in.
     *
     * @param status The status to be set for the player.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the movement strategy according to the god card.
     *
     * @param strategy The move strategy to be set.
     */
    public void setMoveStrategy(MoveStrategy strategy) { 
        this.moveStrategy = strategy; 
    }

    /**
     * Sets the building strategy according to the god card.
     *
     * @param strategy The build strategy to be set.
     */
    public void setBuildStrategy(BuildStrategy strategy) { 
        this.buildStrategy = strategy; 
    }

    /**
     * Sets the winning strategy according to the god card.
     *
     * @param strategy The win strategy to be set.
     */
    public void setWinStrategy(WinStrategy strategy) { 
        this.winStrategy = strategy; 
    }

    /**
     * Gets the current building strategy of the player.
     * @return The current build strategy.
     */
    public BuildStrategy getBuildStrategy() {
        return buildStrategy;
    }

    /**
     * Gets the current moving strategy of the player.
     * @return The current move strategy.
     */
    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    /**
     * Checks if a specific action has been executed by the player.
     * @return {@code true} if the action has been executed, otherwise {@code false}.
     */
    public boolean getIsExecuted() {
        return isExecuted;
    }
    
    /**
     * Sets the executed state for a specific action.
     *
     * @param executed The executed state to be set.
     */
    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }
}
