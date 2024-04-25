package main.java.com.example.GodClass;

import main.java.com.example.Board;
import main.java.com.example.Player;

/**
 * Interface defining customized building strategies for different god cards in the game.
 * Implementations provide specific rules and logic for building structures on the game board,
 * allowing for varied and unique building behaviors beyond standard game rules.
 */
public interface BuildStrategy {
    /**
     * Determines if a build action is valid at the specified location on the board.
     * @param board The game board.
     * @param x The x-coordinate of the build action.
     * @param y The y-coordinate of the build action.
     * @return {@code true} if the build action is valid, {@code false} otherwise.
     */
    boolean isValidBuild(Board board, int x, int y);

    /**
     * Checks if a player can take another action after a build action.
     * @param player The player performing the build action.
     * @return {@code true} if another action can be taken, {@code false} otherwise.
     */
    boolean canTakeAnotherAction(Player player);

    /**
     * Passes an extra build action, possibly resetting parameters.
     */
    void passAction();
}

