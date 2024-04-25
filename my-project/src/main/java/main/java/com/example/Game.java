package main.java.com.example;
import java.util.Objects;


import main.java.com.example.GodClass.BuildStrategy;
import main.java.com.example.GodClass.Demeter;
import main.java.com.example.GodClass.Hephaestus;
import main.java.com.example.GodClass.Minotaur;
import main.java.com.example.GodClass.MoveStrategy;
import main.java.com.example.GodClass.Pan;
import main.java.com.example.GodClass.WinStrategy;

/**
 * This class manages the entire game flow, including the board, players, and validator to check player actions.
 */
public class Game {

    private Board board; // The game board

    private final Player[] players = new Player[2];

    private int currentPlayerId = 0;

    private boolean isWinning = false; 

    private String message; // A message related to the current state of the game.

    /**
     * Default constructor.
     */
    public Game() {
        this(new Board(), new Player(0), new Player(1), 0, false, "");
    }

    /**
     * Constructor for the Game with specified status.
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
     * Initializes a worker on the board at a specified position.
     *
     * @param fromX The original X position.
     * @param fromY The original Y position.
     * @param toX The targetd X position for the worker.
     * @param toY The targetd Y position for the worker.
     * @return A new instance of Game reflecting the updated status.
     */
    public Game initializeWorker(int fromX, int fromY, int toX, int toY) {
        boolean actionSuccess = this.players[currentPlayerId].initializeWorker(this.board, fromX, fromY, toX, toY);
        if(actionSuccess) {
            this.message = "Player " + this.getCurrentPlayerId() + " initialize Successfully!";
            changeCurrentPlayer();
        } else {
            this.message = "Error: Invalid initialization, please try again!";
        }
        return new Game(this.board, this.players[0], this.players[1], this.currentPlayerId, this.isWinning, this.message);
    }

    /**
     * Moves a worker on the board to the targeted position.
     * If the move is successful and it is a wining move, the winning status is set and send a winning message.
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

        this.isWinning = this.players[currentPlayerId].isWinningMove(board, fromX, fromY, toX, toY);

        if (this.isWinning) {
            this.message = "Player " + this.getCurrentPlayerId() + " win!";
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
            changeCurrentPlayer();
        } else {
            this.message = "Error: Invalid building, please try again!";
        }
        return new Game(this.board, this.players[0], this.players[1], this.currentPlayerId, this.isWinning, this.message);
    }

    /**
     * Change current player.
     */
    private void changeCurrentPlayer() {
        this.currentPlayerId = (currentPlayerId + 1) % 2;
    }

    /**
     * Retrieves the current game board.
     * @return The current game board.
     */
    public Board getBoard() {
        return this.board;
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
     * Checks if one player has won the game.
     * @return {@code true} if one player won the game, otherwise {@code false}.
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

    /**
    * This method sets the god class for the current player based on the given god class name.
    * It initializes the corresponding god card strategy for the player, such as Demeter, Hephaestus, Minotaur, or Pan.
    * After setting the god class, it switches to the next player.
    * If the given god class name does not match any known god cards, it returns the current state without changing the player.
    *
    * @param godClassName The name of the god class to be set for the player.
    * @return A new instance of Game reflecting the updated state after setting the god class.
    */
    public Game setGodClass(String godClassName) {
        System.out.println(godClassName);
    
        switch (godClassName) {
            case "Demeter":
                BuildStrategy demeter = new Demeter();
                this.players[currentPlayerId].setBuildStrategy(demeter);
                this.message = "Player " + currentPlayerId + " set Demeter as god card successfully!";
                break;
            case "Hephaestus":
                BuildStrategy hephaestus = new Hephaestus();
                this.players[currentPlayerId].setBuildStrategy(hephaestus);
                this.message = "Player " + currentPlayerId + " set Hephaestus as god card successfully!";
                break;
            case "Minotaur":
                MoveStrategy minotaur = new Minotaur();
                this.players[currentPlayerId].setMoveStrategy(minotaur);
                this.message = "Player " + currentPlayerId + " set Minotaur as god card successfully!";
                break;
            case "Pan":
                WinStrategy pan = new Pan();
                this.players[currentPlayerId].setWinStrategy(pan);
                this.message = "Player " + currentPlayerId + " set Pan as god card successfully!";
                break;
            default:
                // Set error message if the god card name does not match any known god cards
                this.message = "Error: Cannot set god card, please try again!";
                return this; // Return the current state without changing the player
        }
    
        // Switch to the next player after successfully setting the god card
        changeCurrentPlayer();
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

            if (this.players[currentPlayerId].getBuildStrategy() != null) {
                // pass build action
                this.players[currentPlayerId].getBuildStrategy().passAction();

                // Reset move state and switch to the next player
                this.players[currentPlayerId].resetIsMoved(board);
                changeCurrentPlayer();

                // Set success message
                this.message = "Pass Success! Current build action has been passed!";
            } else if (this.players[currentPlayerId].getMoveStrategy() != null) {
                // pass move action
                this.players[currentPlayerId].getMoveStrategy().passAction();

                // Set success message
                this.message = "Pass Success! Current move action has been passed!";
            }
        } else {
            // Set error message if pass action is not allowed
            this.message = "Error: Cannot pass this action!";
        }
        return new Game(this.board, this.players[0], this.players[1], this.currentPlayerId, this.isWinning, this.message);
    }
}