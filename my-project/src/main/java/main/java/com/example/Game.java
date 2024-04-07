package main.java.com.example;
import java.util.Objects;

/**
 * Represents the game controller for the board game.
 * This class manages the game state at the backend, including the board, players, and game rules.
 * The front-end will call methods in this class to control the state of the game.
 */
public class Game {

    private Board board; // The game board object, managing the grid, workers, and their interactions.

    private final Player[] players = new Player[2]; // An array to hold the two players participating in the game.

    private int currentPlayerId = 0; // The ID of the current player. This is used to track whose turn it is, toggling between 0 and 1.

    private boolean isWinning = false; // A flag to indicate whether the current game state meets a winning condition.

    private String message; // A message related to the current state of the game, used to provide feedback, instructions, or game results to players.

    /**
     * Default constructor for the Game class.
     * Initializes the game with default board and players.
     */
    public Game() {
        this(new Board(), new Player(0), new Player(1), 0, false, "");
    }

    /**
     * Constructor for the Game class.
     * @param board The game board.
     * @param player1 The first player.
     * @param player2 The second player.
     * @param currentPlayerId The ID of the current player.
     * @param isWinning The winning status of the game.
     * @param message A message related to the game state.
     */
    public Game(Board board, Player player1, Player player2, int currentPlayerId, boolean isWinning, String message) {
        this.board = board;
        this.players[0] = player1;
        this.players[1] = player2;
        this.currentPlayerId = currentPlayerId;
        this.isWinning = isWinning;
        this.message = message;
    }

    /**
     * Initializes a worker on the board at a specified location.
     * This method is responsible for placing a player's worker on the board at the beginning of the game.
     * It validates the initial position and updates the game state accordingly.
     *
     * @param fromX The original X position.
     * @param fromY The original Y position.
     * @param toX The target X position for the worker.
     * @param toY The target Y position for the worker.
     * @return A new instance of Game reflecting the updated state.
     */
    public Game initializeWorker(int fromX, int fromY, int toX, int toY) {
        boolean actionSuccess = this.players[currentPlayerId].initializeWorker(this.board, fromX, fromY, toX, toY);
        if(actionSuccess) {
            this.message = "Player " + this.getCurrentPlayerId() + " initialization Successful!";
            switchPlayer();
        } else {
            this.message = "Error: Invalid initialization, please try again!";
        }
        return new Game(this.board, this.players[0], this.players[1], this.currentPlayerId, this.isWinning, this.message);
    }

    /**
     * Moves a worker on the board from one position to another.
     * This method checks the validity of the move, updates the game state, and determines if the move results in a winning condition.
     * If the move is successful and results in a win, the winning status is set and a victory message is assigned.
     * Otherwise, a success or error message is set based on the move's validity.
     *
     * @param fromX The original X position.
     * @param fromY The original Y position.
     * @param toX The target X position for the worker.
     * @param toY The target Y position for the worker.
     * @return A new instance of Game reflecting the updated state.
     */
    public Game move(int fromX, int fromY, int toX, int toY) {
        boolean actionSuccess = this.players[currentPlayerId].move(this.board, fromX, fromY, toX, toY);

        // Check if the move results in a winning condition
        this.isWinning = this.players[currentPlayerId].isWinningMove(board, fromX, fromY, toX, toY);

        // Update message based on game state
        if (this.isWinning) {
            this.message = "Player " + this.getCurrentPlayerId() + " win this game!!!";
        } else if(actionSuccess) {
            this.message = "Player " + this.getCurrentPlayerId() + " successfully move the worker!";
        } else {
            this.message = "Error: Invalid movement, please try again!";
        }

        return new Game(this.board, this.players[0], this.players[1], this.currentPlayerId, this.isWinning, this.message);
    }

    /**
     * Builds a structure on the board at a specified position.
     * This method enables a player to construct a block or dome on the game board.
     * It checks the validity of the build location and updates the game state to reflect the new structure.
     * A success or error message is set based on the build action's outcome.
     *
     * @param toX The X position to build.
     * @param toY The Y position to build.
     * @return A new instance of Game reflecting the updated state.
     */
    public Game build(int toX, int toY) {
        boolean actionSuccess = this.players[currentPlayerId].build(this.board, toX, toY);
        if(actionSuccess && Objects.equals(this.players[currentPlayerId].getStatus(), "build")) {
            this.message = "Player " + this.getCurrentPlayerId() + " building Success! Please continue to build!";
        } else if (actionSuccess && Objects.equals(this.players[currentPlayerId].getStatus(), "move")) {
            this.message = "Player " + this.getCurrentPlayerId() + " building Success! Switch player!";
            switchPlayer();
        } else {
            this.message = "Error: Invalid building, please try again!";
        }
        return new Game(this.board, this.players[0], this.players[1], this.currentPlayerId, this.isWinning, this.message);
    }


    /**
     * Handles the passing of an action by the current player.
     * This method allows a player to skip their current action (move or build) if certain conditions are met.
     * It checks the current player's status and action execution state, then updates the status accordingly.
     * The method also resets certain states of the player and switches to the next player.
     * A success message is set if the action is successfully passed, otherwise, an error message is generated.
     *
     * @return A new instance of Game reflecting the updated state after the action is passed.
     */
    public Game passAction() {
        // Check if the current player has executed an action and has a build strategy
        if(this.players[currentPlayerId].getIsExecuted()) {
            // Update the player's status based on the current action
            if(Objects.equals(this.players[currentPlayerId].getStatus(), "move")) {
                this.players[currentPlayerId].setStatus("build");
            } else if(Objects.equals(this.players[currentPlayerId].getStatus(), "build")) {
                this.players[currentPlayerId].setStatus("move");
            }

            // Reset executed state and perform pass action on the build strategy
            this.players[currentPlayerId].setExecuted(false);
        } else {
            // Set error message if pass action is not allowed
            this.message = "Error: Cannot pass this action!";
        }
        return new Game(this.board, this.players[0], this.players[1], this.currentPlayerId, this.isWinning, this.message);
    }


    /**
     * Switches the current player to next player.
     */
    private void switchPlayer() {
        this.currentPlayerId = (currentPlayerId + 1) % 2;
    }

    /**
     * Retrieves the current game board.
     * @return The current game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the game board.
     * @param board The board to set.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Retrieves a player by ID.
     * @param currentPlayerId The ID of the player to retrieve.
     * @return The player with the specified ID.
     */
    public Player getPlayers(int currentPlayerId) {
        return players[currentPlayerId];
    }

    /**
     * Retrieves the ID of the current player.
     * @return The ID of the current player.
     */
    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    /**
     * Checks if the game is in a winning state.
     * @return {@code true} if the game is in a winning state, otherwise {@code false}.
     */
    public boolean getIsWinning() {
        return this.isWinning;
    }

    /**
     * Retrieves the current game message.
     * @return The current game message.
     */
    public String getMessage() {
        return message;
    }
}