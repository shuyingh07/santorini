package main.java.com.example.GodClass;

import main.java.com.example.Worker;
import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.Validator;

/**
 * Represents the Demeter build strategy in the game.
 * As per Demeter's unique ability, this class allows a player to perform an additional build action,
 * but not on the same space as the first build in a turn.
 * Implements the BuildStrategy interface, defining customized building behavior.
 */
public class Demeter implements BuildStrategy {
    private int lastBuildX = -1; // The X coordinate of the last build action. Initialized to -1 to indicate no build has occurred yet.
    private int lastBuildY = -1; // The Y coordinate of the last build action. Initialized to -1 to indicate no build has occurred yet.
    private boolean hasBuiltOnce = false; // A flag to track if the player has already built once during their turn.
    private Validator validator = new Validator();

    /**
     * Determines if a build action is valid for player with Demeter god card at a specified location on the board.
     * This method allows player to build one additional time, but not on the same space
     * in addition to the default 'isValidBuild' method.
     * This method overrides the method in BuildStrategy interface.
     * @param board The game board.
     * @param x The x-coordinate where the build action is attempted.
     * @param y The y-coordinate where the build action is attempted.
     * @return {@code true} if the build action is valid and follows Demeter's building rules, otherwise {@code false}.
     */
    @Override
    public boolean isValidBuild(Board board, int x, int y) {
        // Rebuild in the same place is not allowed for second build
        if (hasBuiltOnce && x == lastBuildX && y == lastBuildY) {
            return false;
        }

        // Use Validator to check move validity
        boolean isValid = validator.isValidBuild(board, x, y);

        // Update build state if valid
        if (isValid) {
            if (!hasBuiltOnce) {
                lastBuildX = x;
                lastBuildY = y;
                hasBuiltOnce = true;
            } else {
                resetBuildState();
            }
        }

        return isValid;
    }


    /**
     * Checks if the player can take another action.
     * Always returns true for Demeter as they can build twice.
     *
     * @param player The player performing the action.
     * @return Always {@code true}.
     */
    @Override
    public boolean canTakeAnotherAction(Player player) {
        return true;
    }

    /**
     * Resets the build state for the next action.
     * Invoked to signal completion of Demeter's building actions.
     */
    @Override
    public void passAction() {
        resetBuildState();
    }

    /**
     * Resets the internal state tracking the last build action.
     * Clears the coordinates and reset the hasBuiltOnce flag.
     */
    private void resetBuildState() {
        lastBuildX = -1;
        lastBuildY = -1;
        hasBuiltOnce = false;
    }

}
