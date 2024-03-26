package main.java.com.example;

import java.util.ArrayList;

public class Grid {
    private int x;
    private int y;
    private int height;
    private boolean occupy;
    private static final int MAX_HEIGHT = 4;

    /**
    * Initialize the grid in specified location with height = 0 and occupy = false
    * @param x - the x location
    * @param y - the y location
    */
    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 0;
        this.occupy = false;
    }

    /**
    * Get the position of a specified grid
    * @returns an arraylist that demonstrates the position of the grid
    */
    public ArrayList<Integer> getPosition() {
        ArrayList<Integer> position = new ArrayList<>();
        position.add(this.x);
        position.add(this.y);
        return position;
    }

    /**
    * Get the height of a specified grid
    * @returns the height of the grid
    */
    public int getHeight() {
        return this.height;
    }

    /**
    * Plus 1 to height of a specified grid
    * @returns boolean that demonstrates whether the height of grid increases successfully
    */
    public boolean addBrick() {
        if(this.height >= MAX_HEIGHT){
            return false;
        }
        else{
            this.height += 1;
            return true;
        }
    }

    /**
    * Get the occupid status of a specified grid
    * @returns the occupid status of the grid
    */
    public boolean getOccupy() {
        return this.occupy;
    }
    
    /**
    * Set the occupid status of a specified grid after it has been occupied by a new worker
    * @returns boolean that demonstrates whether the occupied status of grid changes successfully
    */
    public boolean setOccupy() {
        if(this.occupy) {
            return false;
        }
        else{
            this.occupy =true;
            return true;
        }
    }

    /**
    * Set the occupid status of a specified grid to false after the worker on it move to a other location
    * @returns boolean that demonstrates whether the occupied status of grid clears successfully
    */
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
