package main.java.com.example;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents the state of the game specifically for frontend representation.
 * This class encapsulates all essential information required to display the current state of the game on the frontend.
 * It includes the game grid, the ID of the current player, the current status of the game, whether the game is in a winning state, and any relevant messages.
 * The class provides methods to update its state from a Game object, which store the game state at backend,
 * facilitating synchronization between the game logic and frontend display.
 * The class also utilize jackson package to convert this GameState object to a JSON string to communicate with the frontend.
 */
public class GameState {
    private List<List<Grid>> grid; // A 2D list representing the game board grid, where each cell represented as CellData holds data about the cell's state.

    private int currentPlayer; // The ID of the current player. This is used to track whose turn it is.

    private String status; // The current status of the game, indicating the phase or state of the current turn (e.g., 'move', 'build').

    private boolean isWinning; // A flag indicating whether the current game state meets a winning condition.

    private String message; // A message related to the current state of the game, used for providing feedback or instructions to players.

    /**
     * Default constructor for GameState.
     * Initializes the game state with default values.
     */
    public GameState() {
        this.grid = new ArrayList<>();
        this.currentPlayer = 0;
        this.status = "";
        this.isWinning = false;
        this.message = "";
    }

    /**
     * Updates the GameState based on the information from a Game object.
     * This method extracts and sets the current game state, including the board grid, player information, game status, and winning state.
     * It is intended for synchronizing the GameState，required by frontend， with the current state of the Game instance from backend.
     *
     * @param game The Game instance from which to update the GameState.
     */
    public void updateFromGame(Game game) {
        Board board = game.getBoard();
        Validator validator = new Validator();

        // Update the current player, game status, winning condition, and message from the current game instance
        this.currentPlayer = game.getCurrentPlayerId();
        this.status = game.getPlayers(game.getCurrentPlayerId()).getStatus();
        this.isWinning = game.getIsWinning();
        this.message = game.getMessage();

        // Reconstruct the grid representation of the game board
        this.grid = new ArrayList<>();
        for (int x = 0; x < board.getROW(); x++) {
            List<Grid> row = new ArrayList<>();
            for (int y = 0; y < board.getCOL(); y++) {
                // Create a new Grid object for each cell
                Grid grid = new Grid();
                // Set the height and dome presence based on the board's state
                grid.setHeight(board.getTowerHeight(x, y));
                // Set if this position has a dome
                grid.setHasDome(!(board.getTowerHeight(x, y) < 4));
                // Check if the cell is free or occupied by a worker
                if(validator.gridHasWorker(board, x, y)) {
                    grid.setOccupyStatus(-1); // 2 indicates a free cel
                } else {
                    // Identify which player's worker occupies the cell
                    for (Worker worker : board.getWorkers()) {
                        if (worker != null && worker.getX() == x && worker.getY() == y) {
                            grid.setOccupyStatus(worker.getPlayerId());
                        }
                    }
                }
                row.add(grid);
            }
            this.grid.add(row);
        }
    }


    /**
     * Converts this GameState object to a JSON string.
     * @return JSON representation of the GameState.
     */
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    // Getters and setters for GameState, this is required by jackson
    /**
     * Retrieves the grid of the game state.
     * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @return The grid of the game state.
     */
    public List<List<Grid>> getGrid() {
        return grid;
    }

    /**
     * Sets the grid of the game state.
     * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @param grid The grid to set for the game state.
     */
    public void setGrid(List<List<Grid>> grid) {
        this.grid = grid;
    }

    /**
     * Retrieves the current player's ID.
     * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @return The current player's ID.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player's ID.
     * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @param currentPlayer The player ID to set as current.
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Retrieves the current status of the game.
     * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @return The current status of the game.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the game.
     * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @param status The status to set for the game.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Checks if the game is in a winning state.
     * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @return {@code true} if the game is in a winning state, otherwise {@code false}.
     */
    public boolean getIsWinning() {
        return isWinning;
    }

    /**
     * Sets the winning status of the game.
     * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @param isWinning {@code true} if the game is in a winning state, otherwise {@code false}.
     */
    public void setIsWinning(boolean isWinning) {
        this.isWinning = isWinning;
    }

    /**
     * Retrieves the message associated with the game state.
     * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @return The message associated with the game state.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message associated with the game state.
     * * (This method is not a dead method, it is required by Jackson to be present in order to convert the GameState object to a JSON string.)
     * @param message The message to set for the game state.
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
