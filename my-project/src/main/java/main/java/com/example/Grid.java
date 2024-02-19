package main.java.com.example;

import java.util.ArrayList;

public class Grid {
    private int x;
    private int y;
    private int height;
    private boolean occupy;
    private static final int MAX_HEIGHT = 4;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 0;
        this.occupy = false;
    }

    public ArrayList<Integer> getPosition() {
        ArrayList<Integer> position = new ArrayList<>();
        position.add(this.x);
        position.add(this.y);
        return position;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean addBrick() {
        if(this.height >= MAX_HEIGHT){
            return false;
        }
        else{
            this.height += 1;
            return true;
        }
    }

    public boolean getOccupy() {
        return this.occupy;
    }

    public boolean setOccupy() {
        if(this.occupy) {
            return false;
        }
        else{
            this.occupy =true;
            return true;
        }
    }

    public boolean clearOccupy() {
        if(!this.occupy) {
            return false;
        }
        else{
            this.occupy = false;
            return true;
        }
    }

}
