package main.java.com.example;

import java.util.ArrayList;

public class Game {
    private Player currentPlayer;
    private Player winner;
    private ArrayList<Player> playerList;

    public Game() {
        this.currentPlayer = null;
        this.winner = null;
        this.playerList = null;
    }

    public boolean gameStart(Player currenPlayer, ArrayList<Player> playerlist) {
        if(currenPlayer == null){
            return false;
        }
        if(playerlist.size() != 2){
            return false;
        }

        this.currentPlayer = currenPlayer;
        this.playerList = playerlist;
        return true;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    
    public boolean changeCurrentPlayer() {
        if(this.currentPlayer == null){
            return false;
        }

        if(this.currentPlayer == playerList.get(0)) {
            this.currentPlayer = playerList.get(1);
        }
        else{
            this.currentPlayer = playerList.get(0);
        }
        return true;
    }

    public Player getWinner() {
        return this.winner;
    }

    public boolean setWinner(Player player) {
        if(this.winner != null){
            return false;
        }
        else{
            this.winner = player;
            return true;
        }
    }
}
