package main.java.com.example;


public class Validator {

    private static final int MAX_HEIGHT = 4;

    public Validator(){
    
    }

    /**
    * Check whether a location is in the boundary of the board
    * @param board - game board that has boundary
    * @param x - specified x location
    * @param y - specified y location
    * @return boolean that demonstrates whether the specified location is in the boundary
    */
    public boolean isInBoundary(Board board, int x, int y) {
        if(x < board.getRowLen() && y < board.getColLen() && x >= 0 && y >= 0) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
    * Check whether a location is adjacent to a worker
    * @param Worker - the worker that player chooses this turn
    * @param x - specified x location
    * @param y - specified y location
    * @return boolean that demonstrates whether the specified location is adjacent to the worker
    */
    public boolean isAdjacentGrid(Worker worker, int x, int y) {
        int workerX = worker.getX();
        int workerY = worker.getY();

        if((Math.abs(workerX-x) <= 1) && (Math.abs(workerY-y) <= 1)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
    * Check whether a worker can move successfully
    * @param board - game board that has boundary
    * @param Worker - the worker that player chooses this turn
    * @param x - specified x location
    * @param y - specified y location
    * @return boolean that demonstrates whether the worker can move to a specified location successfully
    */
    public boolean isValidMove(Board board, Worker worker, int x, int y) {
        if(!isInBoundary(board, x, y)){
            return false;
        }

        if(!isAdjacentGrid(worker, x, y)){
            return false;
        }

        Grid targetGrid = board.getGrid(x, y);
        int workerX = worker.getX();
        int workerY = worker.getY();
        Grid workeGrid = board.getGrid(workerX, workerY);
        if(targetGrid.getOccupy()){
            return false;
        }
        if(targetGrid.getHeight() >= MAX_HEIGHT){
            return false;
        }
        if((targetGrid.getHeight() - workeGrid.getHeight()) > 1){
            return false;
        }
        return true;
    }

    /**
    * Check whether a worker can build successfully
    * @param board - game board that has boundary
    * @param Worker - the worker that player chooses this turn
    * @param x - specified x location
    * @param y - specified y location
    * @return boolean that demonstrates whether the worker can build in a specified location successfully
    */
    public boolean isValidBuild(Board board, Worker worker, int x, int y) {
        if(!isInBoundary(board, x, y)){
            return false;
        }

        if(!isAdjacentGrid(worker, x, y)){
            return false;
        }

        Grid grid = board.getGrid(x, y);
        if(grid.getOccupy()){
            return false;
        }
        if(grid.getHeight() >= MAX_HEIGHT){
            return false;
        }
        return true;
    }

    /**
    * Check whether the game has a winner
    * @param board - game board that has boundary
    * @param Worker - the worker that player chooses this turn
    * @return boolean that demonstrates whether the game has a winner
    */
    public boolean isWin(Board board, Worker worker) {
        int workerX = worker.getX();
        int workerY = worker.getY();

        Grid grid = board.getGrid(workerX, workerY);

        if(grid.getHeight() == MAX_HEIGHT - 1) {
            return true;
        }
        else{
            return false;
        }
    }
    
}
