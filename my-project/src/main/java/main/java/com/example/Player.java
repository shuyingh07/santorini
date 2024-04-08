package main.java.com.example;


/**
 * Represents a player in the game.
 * This class manages the plauer status, including its playerID, initialize and move workers, build in a specified position.
 */
public class Player {
    private final int playerId;
    private String status;
    private Validator validator;

    /**
     * Constructs a player with a unique identifier.
     * @param playerId Id that represents a player
     */
    public Player(int playerId) {
        this.playerId = playerId;
        this.status = "initialize";
        this.validator = new Validator();
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
        if (status.equals("initialize") && validator.isValidInitial(board, x1, y1) && validator.isValidInitial(board, x2, y2) && !(x1 == x2 && y1 == y2)) {
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
        if ((status.equals("move") && validator.isValidMove(board, fromX, fromY, toX, toY, playerId))) {
            board.findAndPlaceWorker(fromX, fromY, toX, toY, playerId);
            this.status = "build";
            return true;
        } else {
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
        if((status.equals("build") && validator.isValidBuild(board, buildX, buildY))) {
            // Execute the build action and update the status
            this.status = "move";
            board.buildBlock(buildX, buildY);
            resetIsMoved(board);
            return true;
        } else {
            System.out.println("Invalid build. Try again.");
            return false;
        }
    }

    /**
     * Determines if a move results in a winning condition.
     * This method checks for standard winning conditions and any additional conditions specified by a win strategy.
     * If any worker has moved to a position meeting the winning criteria, or if the win strategy's conditions are met, the move is considered a winning move.
     *
     * @param board The game board on which the move is being made.
     * @param fromX The original X coordinate of the worker.
     * @param fromY The original Y coordinate of the worker.
     * @param toX The target X coordinate for the worker.
     * @param toY The target Y coordinate for the worker.
     * @return {@code true} if the move results in a win, otherwise {@code false}.
     */
    public boolean isWinningMove(Board board, int fromX, int fromY, int toX, int toY) {
        boolean isWin = validator.isWinningMove(board);

        return isWin;
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
}
