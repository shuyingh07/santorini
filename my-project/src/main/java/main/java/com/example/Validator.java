package main.java.com.example;

/**
 * Provides default strategy for validating various actions in the game.
 * This class contains methods to ensure that actions such as initializing a worker, moving a worker, and building on the board comply with the game's rules.
 */
public class Validator {
    private final static int MAX_HEIGHT = 4;
    /**
     * Check if the initial place is within the board boundaries, the cell is unoccupied
     * @param board board of the current game
     * @param toX initial position x of worker
     * @param toY initial position y of worker
     * @return {@code true} if the position is valid to place the worker
     */
    public boolean isValidInitial(Board board, int toX, int toY) {
        return isWithinBounds(board, toX, toY) && isCellFree(board, toX, toY);
    }

    /**
     * Check if the moving position is within the board boundaries, the cell is unoccupied, the height is valid, etc.
     * @param board board of the current game
     * @param fromX position x of the original worker
     * @param fromY position y of the original worker
     * @param toX position x of the moved worker
     * @param toY position y of the moved worker
     * @param playerId the player id of current player
     * @return {@code true} if the position is valid to move the worker from the original position
     */
    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY, int playerId) {
        boolean workerBelongPlayer = false;
        for (Worker worker : board.getWorkers()) {
            if (fromX == worker.getX() && fromY == worker.getY() && playerId == worker.getPlayerId()) {
                workerBelongPlayer = true;
                break;
            }
        }

        return isWithinBounds(board, toX, toY)
                && workerBelongPlayer
                && board.getTowerHeight(toX, toY) <= 3 && isCellFree(board, toX, toY)
                && Math.abs(board.getTowerHeight(toX, toY) - board.getTowerHeight(fromX, fromY)) <= 1
                && Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1;
    }

    /**
     * Check if the build position is within the board boundaries, the height is less than 4, the cell neighbored with moved worker, etc.
     * @param board board of the current game
     * @param x position x of the block or dome
     * @param y position y of the block or dome
     * @return {@code true} if the position is valid to build block or dome
     */
    public boolean isValidBuild(Board board, int x, int y) {
        int workerX = -1;
        int workerY = -1;
        boolean hasMovedWorker = false;
        for(Worker worker : board.getWorkers()) {
            if(worker.getIsMoved()) {
                workerX = worker.getX();
                workerY = worker.getY();
                hasMovedWorker = true;
                break;
            }
        }

        return isWithinBounds(board, x, y) && board.getTowerHeight(x, y) < MAX_HEIGHT &&
                Math.abs(x - workerX) <= 1 && Math.abs(y - workerY) <= 1 &&
                isCellFree(board, x, y) && hasMovedWorker &&
                Math.abs(board.getTowerHeight(x, y) - board.getTowerHeight(workerX, workerY)) <= 1;
    }

    /**
     * Determines if a move results in a winning condition.
     * This method checks for default winning conditions (reaching a level 3 tower on the board).
     * If any worker belongs to current player has moved to a position meeting the winning criteria, the move is considered a winning move.
     *
     * @param board The game board on which the move is being made.
     * @return {@code true} if the move results in a win, otherwise {@code false}.
     */
    public boolean isWinningMove(Board board) {
        boolean ifWin = false;

        // Check standard winning condition: any worker reaching the third level
        for (Worker worker : board.getWorkers()) {
            if (worker.getIsMoved() && board.getTowerHeight(worker.getX(), worker.getY()) == 3) {
                ifWin = true;
                break;
            }
        }
        return ifWin;
    }

    /**
     * Check if the cell is within boundary
     * @param x position x of the checked cell
     * @param y position y of the checked cell
     * @return {@code true} if the position within boundary
     */
    private boolean isWithinBounds(Board board, int x, int y) {
        return x >= 0 && x < board.getROW() && y >= 0 && y < board.getCOL();
    }

    /**
     * Check if the cell is free
     * @param x position x of the checked cell
     * @param y position y of the checked cell
     * @return {@code true} if the position is free
     */
    private boolean isCellFree(Board board, int x, int y) {
        for (Worker worker : board.getWorkers()) {
            if (worker != null && worker.getX() == x && worker.getY() == y) {
                return false;
            }
        }
        return true;
    }
}
