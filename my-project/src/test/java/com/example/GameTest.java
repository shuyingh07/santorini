package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.com.example.Game;
import main.java.com.example.Player;

public class GameTest {
    private Game game;
    Player player1;
    Player player2;
    ArrayList<Player> playerList;

    @Before
    public void setUp() {
        game = new Game();
        player1 = new Player(0);
        player2 = new Player(1);
        playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
    }

    @Test
    public void testGameStart() {
        assertTrue(game.gameStart(player1, playerList));

        Player player3 = new Player(3);
        playerList.add(player3);
        assertFalse(game.gameStart(player1, playerList));
    }

    @Test
    public void testCurrentPlayer() {        
        game.gameStart(player1, playerList);
        assertEquals(game.getCurrentPlayer(), player1);
        game.changeCurrentPlayer();
        assertEquals(game.getCurrentPlayer(), player2);
    }

    @Test
    public void testWinner(){
        game.gameStart(player1, playerList);
        assertNull(game.getWinner());

        game.setWinner(player1);
        assertEquals(player1, game.getWinner());
    }

}
