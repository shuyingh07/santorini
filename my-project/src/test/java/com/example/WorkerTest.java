package com.example;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.java.com.example.Worker;

public class WorkerTest {
    Worker worker;

    @Before
    public void setUp() {
        worker = new Worker(0);
    }

    @Test
    public void testMover() {
        assertEquals(worker.getX(), -1);
        assertEquals(worker.getY(), -1);

        worker.move(1, 2);
        assertEquals(worker.getX(), 1);
        assertEquals(worker.getY(), 2);
    }
}