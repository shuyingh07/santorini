package main.java.com.example.GodClass;

import main.java.com.example.Board;

/**
 * Interface defining customized win strategies for different god cards in the game.
 * Implementations provide specific conditions and logic to determine winning scenarios,
 * allowing for a variety of winning conditions beyond standard game rules.
 */
public interface WinStrategy {
    /**
     * Determines if a move action constitutes a winning move according to a specific god card's win condition.
     * @param board The game board.
     * @param fromX The x-coordinate of the worker's original position.
     * @param fromY The y-coordinate of the worker's original position.
     * @param toX The x-coordinate of the worker's target position.
     * @param toY The y-coordinate of the worker's target position.
     * @return {@code true} if the move is a winning move according to the specific god card's rules, {@code false} otherwise.
     */
    boolean isWinningMove(Board board, int fromX, int fromY, int toX, int toY);
}

