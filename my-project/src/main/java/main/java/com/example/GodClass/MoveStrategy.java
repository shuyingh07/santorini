package main.java.com.example.GodClass;

import main.java.com.example.Board;
import main.java.com.example.Player;

/**
 * Interface defining customized movement strategies for different god cards in the game.
 */
public interface MoveStrategy {
    /**
     * Checks if the move is valid according to specific requirements.
     * @param board The game board.
     * @param fromX The x-coordinate of the original worker's position.
     * @param fromY The y-coordinate of the original worker's position.
     * @param toX The x-coordinate of the target position for the worker.
     * @param toY The y-coordinate of the target position for the worker.
     * @param playerId The player ID.
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */
    boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY, int playerId);

    /**
     * Checks if a player can take another action following a move action.
     * @param player The player performing the move action.
     * @return {@code true} if another action can be taken, {@code false} otherwise.
     */
    boolean canTakeAnotherAction(Player player);

    /**
     * Passes an extra move action, possibly resetting parameters.
     */
    void passAction();
}

