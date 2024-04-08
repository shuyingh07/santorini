package main.java.com.example;

/**
 * This class contains methods to check whether an action is valid.
 */
public class Validator {
    private int maxHeight = 4;
    /**
     * Check if the initial place is within the board boundaries, the cell is unoccupied
     * @param board board of the current game
     * @param toX initial position x of worker
     * @param toY initial position y of worker
     * @return {@code true} if the position is valid to place the worker
     */
    public boolean isValidInitial(Board board, int toX, int toY) {
        return isWithinBounds(board, toX, toY) && gridIsFree(board, toX, toY);
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
                && board.getTowerHeight(toX, toY) <= maxHeight-1 && gridIsFree(board, toX, toY)
                && board.getTowerHeight(toX, toY) - board.getTowerHeight(fromX, fromY) <= 1
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

        return isWithinBounds(board, x, y) && board.getTowerHeight(x, y) < maxHeight &&
                Math.abs(x - workerX) <= 1 && Math.abs(y - workerY) <= 1 &&
                gridIsFree(board, x, y) && hasMovedWorker;
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
            if (worker.getIsMoved() && board.getTowerHeight(worker.getX(), worker.getY()) == maxHeight - 1) {
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
    public boolean isWithinBounds(Board board, int x, int y) {
        return x >= 0 && x < board.getROW() && y >= 0 && y < board.getCOL();
    }

    /**
     * Check if the cell is free
     * @param x position x of the checked cell
     * @param y position y of the checked cell
     * @return {@code true} if the position is free
     */
    public boolean gridIsFree(Board board, int x, int y) {
        
        return !board.hasWorker(x, y);
    }
}
