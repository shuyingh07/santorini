package main.java.com.example.GodClass;

import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.Validator;

/**
 * Represents the Hephaestus build strategy in the game.
 * According to Hephaestus's ability, this class enables a player to build one additional block (not dome)
 * on top of their first block.
 * Implements the BuildStrategy interface, providing a specific building rule modification.
 */
public class Hephaestus implements BuildStrategy {
    private int lastBuildX = -1; // The X coordinate of the last build action. Initialized to -1 to indicate no build has occurred yet.
    private int lastBuildY = -1; // The Y coordinate of the last build action. Initialized to -1 to indicate no build has occurred yet.
    private boolean hasBuiltOnce = false; // A flag to track if the player has already built once during their turn.
    private Validator validator = new Validator(); 
    /**
     * Determines if a build action is valid for player with Hephaestus god card at a specified location on the board.
     * Hephaestus can build an additional time on the same space, but only under 3 tower height.
     *
     * @param board The game board.
     * @param x The x-coordinate of the build location.
     * @param y The y-coordinate of the build location.
     * @return {@code true} if the build is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValidBuild(Board board, int x, int y) {
        // Ensure we build on the same space for the second build, and validate boundaries using Validator
        if ((hasBuiltOnce && (x != lastBuildX || y != lastBuildY)) || !validator.isWithinBounds(board, x, y)) {
            return false;
        }
        
        // Check if the space is free (no dome) and within height limits
        boolean canBuildAgain = hasBuiltOnce ? board.getTowerHeight(x, y) < Validator.getMaxHeight()-1 && !board.getGridDome(x, y) : true;
        boolean isValid = canBuildAgain && validator.gridIsFree(board, x, y) && validator.isWithinBounds(board, x, y);

        // If valid, update the build state
        if (isValid) {
            if (!hasBuiltOnce) {
                lastBuildX = x;
                lastBuildY = y;
                hasBuiltOnce = true;
            } else {
                resetBuildState();
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if the player can take another action.
     * Always returns true for Hephaestus as they can build twice.
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
     * Invoked to signal completion of Hephaestus's building actions.
     */
    @Override
    public void passAction() {
        resetBuildState();
    }

    /**
     * Resets the internal state tracking the last build action.
     * Clears the coordinates and reset the hasBuiltOnce flag.
     */
    public void resetBuildState() {
        lastBuildX = -1;
        lastBuildY = -1;
        hasBuiltOnce = false;
    }


}
