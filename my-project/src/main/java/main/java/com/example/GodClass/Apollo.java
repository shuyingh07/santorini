package main.java.com.example.GodClass;

import main.java.com.example.Worker;
import main.java.com.example.Board;
import main.java.com.example.Player;
import main.java.com.example.Validator;

public class Apollo implements MoveStrategy {
    private Validator validator = new Validator();

    @Override
    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY, int playerId) {
        // Check if the worker belongs to the current player and basic move conditions
        if (!validator.godCardSeizeValidMove(board, fromX, fromY, toX, toY, playerId)) {
            return false;
        }

        // If there's an opponent's worker in the target cell, move it to the original position
        for (Worker worker : board.getWorkers()) {
            if (toX == worker.getX() && toY == worker.getY() && worker.getPlayerId() == playerId) {
                return false;
            }
            if (toX == worker.getX() && toY == worker.getY() && worker.getPlayerId() != playerId) {
                worker.move(fromX, fromY);
                return true;
            }
        }

        return true; // If the target cell is free and within bounds, the move is valid
    }

    @Override
    public boolean canTakeAnotherAction(Player player) {
        return false;
    }

    @Override
    public void passAction() {
        // No additional action allowed for Apollo
    }
}
