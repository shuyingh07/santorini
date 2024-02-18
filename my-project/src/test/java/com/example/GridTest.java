package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.java.com.example.Grid;

public class GridTest {
    private ArrayList<Grid> grids;

    @Before
    public void setUp() {
        grids = new ArrayList<>();
        grids.add(new Grid(0, 0));
        grids.add(new Grid(1, 1));
        grids.add(new Grid(2, 2));
    }

    @Test
    public void testGetPosition() {
        for (int i = 0; i < grids.size(); i++) {
            Grid grid = grids.get(i);
            assertNotNull(grid.getPosition());
            assertEquals(grid.getPosition().get(0).intValue(), i);
            assertEquals(grid.getPosition().get(1).intValue(), i);
        }
    }

    @Test
    public void testGetHeight() {
        Grid grid = grids.get(0);
        assertEquals(grid.getHeight(), 0);
    }

    @Test
    public void testAddBrick() {
        Grid grid = grids.get(0);
        
        for(int i = 0; i <= 3; i++){
            assertEquals(grid.getHeight(), i);
            assertTrue(grid.addBrick());
        }

        assertEquals(grid.getHeight(), 4);
        assertFalse(grid.addBrick());
    }

    @Test
    public void testOccupy() {
        Grid grid = grids.get(0);

        assertFalse(grid.getOccupy());

        grid.setOccupy();
        assertTrue(grid.getOccupy());

        grid.clearOccupy();
        assertFalse(grid.getOccupy());
    }
}
