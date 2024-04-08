package main.java.com.example;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class manages the status of current game, including board, grids, players, workers.
 * It transfer current status to Json for frontend to use.
 */
public class GameState {
    private List<List<Grid>> grid;

    private int currentPlayer;

    private String status;

    private boolean isWinning;

    private String message;

    private static final int MaxHeight = 4;

    public GameState() {
        this.grid = new ArrayList<>();
        this.currentPlayer = 0;
        this.status = "";
        this.isWinning = false;
        this.message = "";
    }

    /**
     * Updates the GameState based on the information from a Game object.
     * @param game The new Game instance to update the GameState.
     */
    public void updateFromGame(Game game) {
        Board board = game.getBoard();
        Validator validator = new Validator();

        this.currentPlayer = game.getCurrentPlayerId();
        this.status = game.getPlayers(game.getCurrentPlayerId()).getStatus();
        this.isWinning = game.getIsWinning();
        this.message = game.getMessage();

        // Reconstruct the grid representation of the game board
        this.grid = new ArrayList<>();
        for (int x = 0; x < board.getROW(); x++) {
            List<Grid> row = new ArrayList<>();
            for (int y = 0; y < board.getCOL(); y++) {

                Grid grid = new Grid();
                grid.setHeight(board.getTowerHeight(x, y));
                grid.setHasDome(!(board.getTowerHeight(x, y) < MaxHeight));
                if(validator.gridIsFree(board, x, y)) {
                    grid.setOccupyStatus(-1);
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
     * @return The grid of the game state.
     */
    public List<List<Grid>> getGrid() {
        return grid;
    }

    /**
     * Sets the grid of the game state.
     * @param grid The grid to set for the game state.
     */
    public void setGrid(List<List<Grid>> grid) {
        this.grid = grid;
    }

    /**
     * Retrieves the current player's ID.
     * @return The current player's ID.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player's ID.
     * @param currentPlayer The player ID to set as current.
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Retrieves the current status of the game.
     * @return The current status of the game.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the game.
     * @param status The status to set for the game.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Checks if the game is in a winning state.
     * @return {@code true} if the game is in a winning state, otherwise {@code false}.
     */
    public boolean getIsWinning() {
        return isWinning;
    }

    /**
     * Sets the winning status of the game.
     * @param isWinning {@code true} if the game is in a winning state, otherwise {@code false}.
     */
    public void setIsWinning(boolean isWinning) {
        this.isWinning = isWinning;
    }

    /**
     * Retrieves the message associated with the game state.
     * @return The message associated with the game state.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message associated with the game state.

     * @param message The message to set for the game state.
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
