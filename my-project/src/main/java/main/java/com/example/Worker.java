package main.java.com.example;


public class Worker {
    private int x;
    private int y;
    private int workerID;

    public Worker(int workerID) {
        this.x = -1;
        this.y = -1;
        this.workerID = workerID;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean move(int x, int y) {
        this.x = x;
        this.y = y;
        return true;
    }
}
