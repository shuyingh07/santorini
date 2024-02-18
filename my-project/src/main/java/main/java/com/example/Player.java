package main.java.com.example;

import java.util.ArrayList;

public class Player {
    private int playerID;
    private ArrayList<Worker> workerList;
    private Worker currentWorker;

    public Player(int playerID) {
        this.playerID = playerID;
        this.currentWorker = null;
        this.workerList = new ArrayList<>();
        this.workerList.add(new Worker(0));
        this.workerList.add(new Worker(1));
    }

    public boolean workerInit(Validator validator, Board board, int x1, int y1, int x2, int y2) {
        if(x1 == x2 && y1 ==y2){
            return false;
        }
        
        if(!(validator.isInBoundary(board, x1, y1))){
            return false;
        }

        if(!(validator.isInBoundary(board, x2, y2))){
            return false;
        }
        Grid grid1 = board.getGrid(x1, y1);
        Grid grid2 = board.getGrid(x2, y2);
        if(grid1.getOccupy() || grid2.getOccupy()){
            return false;
        }

        Worker worker1 = workerList.get(0);
        worker1.move(x1, y1);
        grid1.setOccupy();

        Worker worker2 = workerList.get(1);
        worker2.move(x2, y2);
        grid2.setOccupy();
        return true;

    }
    
    public boolean chooseWorker(int workerID) {
        if(workerID < 0 || workerID >= workerList.size()){
            return false;
        }

        else{
            Worker worker = workerList.get(workerID);
            this.currentWorker = worker;
            return true;
        }
    }

    public Worker getCurrentWorker() {
        return this.currentWorker;
    }

    public boolean moveWorker(Validator validator, Board board, int x, int y) {
        if(!(validator.isValidMove(board, currentWorker, x, y))) {
            return false;
        }

        else{
            Grid workeGrid = board.getGrid(currentWorker.getX(), currentWorker.getY());
            Grid targetGrid = board.getGrid(x, y);
            workeGrid.clearOccupy();
            targetGrid.setOccupy();
            this.currentWorker.move(x, y);
            return true;
        }
    }

    public boolean build(Validator validator, Board board, int x, int y){
        if(!(validator.isValidBuild(board, currentWorker, x, y))) {
            return false;
        }

        else{
            Grid targetGrid = board.getGrid(x, y);
            targetGrid.addBrick();
            return true;
        }
    }
}