package main.java.com.example;


/**
 * Represents a player in the game.
 * This class manages the plauer status, including its playerID, initialize and move workers, build in a specified position.
 */
public class Player {
    private final int playerId;

    private String status = "initialize";  // The current status of the player, including "initialize", "move", "build".

    private final Validator validator = new Validator(); // Default validation strategy for initialization, building and moving.

    /**
     * Constructor for the Player class.
     * Initializes a new player with a unique identifier.
     * This ID is used to distinguish the player throughout the game.
     *
     * @param playerId The unique identifier for the player.
     */

    public Player(int playerId) {
        this.playerId = playerId;
    }

    /**
     * First, it allows player to choose the initial position for two workers
     * Second, check if the position is valid and status is "initialize". If not, it will return false and end.
     * If it is valid, place the two workers.
     * @param board board of the current game
     * @param x1 position x of worker one
     * @param y1 position y of worker two
     * @param x2 position x of worker two
     * @param y2 position y of worker two
     * @return {@code true} if successfully initialized the two workers
     */
    public boolean initializeWorker(Board board, int x1, int y1, int x2, int y2) {
        // Check if the current status is 'initialize' and the first position is valid
        // Validate the second position and ensure it's not the same as the first
        if (status.equals("initialize") && validator.isValidInitial(board, x1, y1) && validator.isValidInitial(board, x2, y2) && !(x1 == x2 && y1 == y2)) {
            // Update status to 'move' and initialize the two workers
            this.status = "move";
            board.initializeWorker(x1, y1, x2, y2, playerId);
            return true;
        } else {
            // Output error message if the first initialization is invalid
            System.out.println("Invalid initialization. Try again.");
            return false;
        }
    }

    /**
     * First, it allows player to choose the worker to move according to the position.
     * Second, it allows player to choose the desired position to move.
     * Third, it will check if this player has god card that implements move strategy
     * if true, validation obey the god card rule will be executed, otherwise, the default validation will be executed.
     * (The extra action for god card like pushing other workers will be implemented directly in god card validator)
     * Forth, without god card, it will check if the steps above is valid and if the status is "move". If not, return false and end.
     * With god card allows second move, it will check if this god card allows second move.
     * If it is valid, move the worker to the desired place.
     *
     * @param board board of the current game
     * @param fromX position x of the original worker
     * @param fromY position y of the original worker
     * @param toX position x of the moved worker
     * @param toY position y of the moved worker
     * @return {@code true} if the worker is moved successfully
     */
    public boolean move(Board board, int fromX, int fromY, int toX, int toY) {
        // Check the validity of the move, either through the standard rules or a specific move strategy
        if ((status.equals("move") && validator.isValidMove(board, fromX, fromY, toX, toY, playerId))) {
            // Update the worker's position on the board and change the status to 'build'
            board.findAndPlaceWorker(fromX, fromY, toX, toY, playerId);
            this.status = "build";
            return true;
        } else {
            // Output error message if the move is invalid
            System.out.println("Invalid move. Try again.");
            return false;
        }
    }

    /**
     * First, it allows player to choose the position to build a block or dome.
     * Second, it will check if this player has god card that implements build strategy
     * if true, validation obey the god card rule will be executed,
     * if false, the default validation will be executed.
     * Third, it will check if the position is valid and the status is "build". If not, return false.
     * Forth, without god card, it will build a block or dome.
     * With god card allows second build, it will check if this god card allows second build.
     * If true, check if this player has built once or not to decide if the status needs to be changed to 'move', then build a block or dome.
     * if not, change the status to 'move' and build a block or dome.
     * @param board board of the current game
     * @param buildX the X position required to build a block or dome
     * @param buildY the Y position required to build a block or dome
     * @return {@code true} if the block or dome is successfully built
     */
    public boolean build(Board board, int buildX, int buildY) {
        // Check if the build is valid under standard rules or a specific build strategy
        if((status.equals("build") && validator.isValidBuild(board, buildX, buildY))) {
            // Execute the build action and update the status
            this.status = "move";
            board.buildBlock(buildX, buildY);
            resetIsMoved(board);
            return true;
        } else {
            // Output error message if the build action is invalid
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
     * This method iterates through all workers on the board and sets their 'isMoved' state to false.
     * It is used to reset the state at the end of a turn or after a specific action has been completed,
     * ensuring that the movement state of each worker is accurately reflected for the next turn.
     *
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
     * Retrieves the status of player.
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
