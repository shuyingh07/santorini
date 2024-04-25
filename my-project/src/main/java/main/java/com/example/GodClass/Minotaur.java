package main.java.com.example.GodClass;

import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.Validator;
import main.java.com.example.Worker;

/**
 * Represents the Minotaur move strategy in the game.
 * This class allows a player's worker to move into an opponent worker’s space,
 * if the opponent's worker can be forced one space straight backwards to an unoccupied space at any level.
 * Implements the MoveStrategy interface, altering standard movement rules.
 */
public class Minotaur implements MoveStrategy{
    private Validator validator = new Validator();
    /**
     * Determines if a move action is valid for a player with the Minotaur god card.
     * The Minotaur can move into an opponent's square if and only if the opponent can be pushed to the next space.
     *
     * @param board The game board.
     * @param fromX The x-coordinate of the original position of the worker.
     * @param fromY The y-coordinate of the original position of the worker.
     * @param toX The x-coordinate of the target position for the worker.
     * @param toY The y-coordinate of the target position for the worker.
     * @param playerId The ID of the player attempting the move.
     * @return {@code true} if the move is valid according to Minotaur's rules, otherwise {@code false}.
     */
    @Override
    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY, int playerId) {
        // Check if the worker belongs to the current player
        boolean workerBelongPlayer = false;
        for (Worker worker : board.getWorkers()) {
            if (fromX == worker.getX() && fromY == worker.getY() && playerId == worker.getPlayerId()) {
                workerBelongPlayer = true;
                break;
            }
        }

        // Check basic move conditions
        boolean isValid = isWithinBounds(toX, toY)
                && workerBelongPlayer
                && board.getTowerHeight(toX, toY) <= 3
                && Math.abs(board.getTowerHeight(toX, toY) - board.getTowerHeight(fromX, fromY)) <= 1
                && Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1;

        // If the destination is free and the movement is valid, return true.
        if(isValid && isCellFree(board, toX, toY)) {
            return true;
        } else if(isValid && !isCellFree(board, toX, toY)) {
            // check if the target position has opponent's worker
            for (Worker worker : board.getWorkers()) {
                if (toX == worker.getX() && toY == worker.getY() &&
                        worker.getPlayerId() != playerId) {

                    // Calculate the position of the opponent's worker's back
                    int pushX = toX + (toX - fromX);
                    int pushY = toY + (toY - fromY);

                    // Check that the rear square is on the board and not occupied
                    if (isWithinBounds(pushX, pushY) && isCellFree(board, pushX, pushY)) {
                        // Pushing opponent worker
                        worker.move(pushX, pushY);
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the player can take another move.
     * Always returns false for Minotaur as it cannot build twice.
     *
     * @param player The player performing the action.
     * @return Always {@code false}.
     */
    @Override
    public boolean canTakeAnotherAction(Player player) {
        return false;
    }

    /**
     * Leave it empty since this god card does not allow additional action
     */
    @Override
    public void passAction() {
    }

    /**
     * Check if the cell is within boundary
     * @param x position x of the checked cell
     * @param y position y of the checked cell
     * @return {@code true} if the position within boundary
     */
    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

    /**
     * Check if the cell is free
     * @param x position x of the checked cell
     * @param y position y of the checked cell
     * @return {@code true} if the position is free
     */
    public boolean isCellFree(Board board, int x, int y) {
        for (Worker worker : board.getWorkers()) {
            if (worker != null && worker.getX() == x && worker.getY() == y) {
                return false;
            }
        }
        return true;
    }
}
