package main.java.com.example.GodClass;

import main.java.com.example.Board;

/**
 * Represents Pan's win strategy in the game.
 * Pan wins if a worker moves down two or more levels.
 */
public class Pan implements WinStrategy{
    private static final int WINNINGDOWN = 2;
    private static final int WINNINGUP = 3;

    /**
     * Determines if a move action constitutes a winning move according to Pan's win condition.
     *
     * @param board The game board.
     * @param fromX The original x-coordinate of the worker.
     * @param fromY The original y-coordinate of the worker.
     * @param toX The target x-coordinate of the worker.
     * @param toY The target y-coordinate of the worker.
     * @return {@code true} if the move is a winning move according to Pan's rules, otherwise {@code false}.
     */
    @Override
    public boolean isWinningMove(Board board, int fromX, int fromY, int toX, int toY) {
        return (board.getTowerHeight(fromX, fromY) - board.getTowerHeight(toX, toY)) >= WINNINGDOWN || board.getTowerHeight(toX, toY) == WINNINGUP;
    }
}
