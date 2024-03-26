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

    /**
    * Initialize two workers
    * @param validator - check whether the location that player choose is validated
    * @param board - game board that has boundary
    * @param x1 - the x location of the worker1
    * @param y1 - the y location of the worker1
    * @param x2 - the x location of the worker2
    * @param y2 - the y location of the worker2
    * @return boolean that demonstrates whether the player intializes workers successfuly
    */
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
    
    /**
    * Choose worker to do operations in this turn
    * @param workerID - the worker that need to operate this turn
    * @return boolean that demonstrates whether the player choose the worker successfully
    */
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

    /**
    * Get the chosen worker in this turn
    * @return worker that has been chosen this turn
    */
    public Worker getCurrentWorker() {
        return this.currentWorker;
    }

     /**
    * Move the chosen worker to a specified location
    * @param validator - check whether the location that player choose is validated
    * @param board - game board that has boundary
    * @param x - the targeted x location
    * @param y - the targeted y location
    * @return boolean that demonstrates whether the player move the worker successfully
    */
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

            if(validator.isWin(board, currentWorker)){
                System.out.println("You win the game");
                System.exit(0);
            }
            return true;
        }
    }

    /**
    * Build brick in a specified location
    * @param validator - check whether the location that player choose is validated
    * @param board - game board that has boundary
    * @param x - the targeted x location
    * @param y - the targeted y location
    * @return boolean that demonstrates whether the player successfully build
    */
    public boolean build(Validator validator, Board board, int x, int y){
        if(!(validator.isValidBuild(board, currentWorker, x, y))) {
            return false;
        }

        else{
            return this.currentWorker.build(board, x, y);
        }
    }
}