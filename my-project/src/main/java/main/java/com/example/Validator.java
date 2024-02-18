package main.java.com.example;


public class Validator {

    public Validator(){
    
    }

    public boolean isInBoundary(Board board, int x, int y) {
        if(x < board.getRowLen() && y < board.getColLen() && x >= 0 && y >= 0) {
            return true;
        }
        else{
            return false;
        }
    }

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
        if(targetGrid.getHeight() >= 4){
            return false;
        }
        if((targetGrid.getHeight() - workeGrid.getHeight()) > 1){
            return false;
        }
        return true;
    }

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
        if(grid.getHeight() >= 4){
            return false;
        }
        return true;
    }

    public boolean isWin(Board board, Worker worker) {
        int workerX = worker.getX();
        int workerY = worker.getY();

        Grid grid = board.getGrid(workerX, workerY);

        if(grid.getHeight() == 3) {
            return true;
        }
        else{
            return false;
        }
    }
    
}
