package main.java.com.example;

/**
 * This class contains methods to validate actions in the game of Santorini.
 */
public class Validator {
    private int maxHeight;

    Validator(){
        this.maxHeight =4;
    }

    /**
     * Checks if the initial placement of a worker is valid.
     * @param board board of the current game
     * @param toX initial position x of worker
     * @param toY initial position y of worker
     * @return {@code true} if the placement is valid, {@code false} otherwise.
     */
    public boolean isValidInitial(Board board, int toX, int toY) {
        if (!isWithinBounds(board, toX, toY)) {
            return false;
        }
        if (!gridIsFree(board, toX, toY)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a move of a worker is valid.
     * @param board board of the current game
     * @param fromX position x of the original worker
     * @param fromY position y of the original worker
     * @param toX position x of the moved worker
     * @param toY position y of the moved worker
     * @param playerId the player id of current player
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */
    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY, int playerId) {
        boolean workerBelongPlayer = false;
        for (Worker worker : board.getWorkers()) {
            if (fromX == worker.getX() && fromY == worker.getY() && playerId == worker.getPlayerId()) {
                workerBelongPlayer = true;
                break;
            }
        }

        if (!isWithinBounds(board, toX, toY)) {
            return false;
        }
        if (!workerBelongPlayer) {
            return false;
        }
        if (board.getTowerHeight(toX, toY) > maxHeight-1 || !gridIsFree(board, toX, toY) || board.getTowerHeight(toX, toY) - board.getTowerHeight(fromX, fromY) > 1 || Math.abs(fromX - toX) > 1 || Math.abs(fromY - toY) > 1) {
            return false;
        }
        return true;
    }

    /**
     * Checks if building at a position is valid.
     * @param board board of the current game
     * @param x position x to build
     * @param y position y to build
     * @return {@code true} if building is valid, {@code false} otherwise.
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

        if (!isWithinBounds(board, x, y)) {
            return false;
        }
        if (board.getTowerHeight(x, y) >= maxHeight || Math.abs(x - workerX) > 1 || Math.abs(y - workerY) > 1 || !gridIsFree(board, x, y) || !hasMovedWorker) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a move results in a win.
     * @param board The game board.
     * @return {@code true} if the move results in a win, {@code false} otherwise.
     */
    public boolean isWinningMove(Board board) {
        for (Worker worker : board.getWorkers()) {
            if (worker.getIsMoved() && board.getTowerHeight(worker.getX(), worker.getY()) == maxHeight - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a cell is within the board boundaries.
     * @param board The game board.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return {@code true} if the cell is within bounds, {@code false} otherwise.
     */
    public boolean isWithinBounds(Board board, int x, int y) {
        if (x >= 0 && x < board.getROW() && y >= 0 && y < board.getCOL()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a cell is unoccupied by a worker.
     * @param board The game board.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return {@code true} if the cell is unoccupied, {@code false} otherwise.
     */
    public boolean gridIsFree(Board board, int x, int y) {
        if (!board.hasWorker(x, y)) {
            return true;
        } else {
            return false;
        }
    }
}
