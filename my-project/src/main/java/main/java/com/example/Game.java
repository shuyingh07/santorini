package main.java.com.example;

import java.util.ArrayList;

public class Game {
    private Player currentPlayer;
    private Player winner;

    private Player player1;
    private Player player2;
    private ArrayList<Player> playerList;

    public Game() {
        this.currentPlayer = null;
        this.winner = null;

        player1 = new Player(0);
        player2 = new Player(1);
        playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        this.currentPlayer = player1;
    }

    /**
    * Start the game with chosen currentPlayer, and get the playerlist
    * @param currentPlayer - the player that play game at first
    * @param playerlist - a list that contains two players who play the game
    * @return boolean that demonstrates whether the game starts successfully
    */
    public void gameStart() {
        ;
    }

    /**
    * Get the player in current turn
    * @return player that need to operate in this turn
    */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    
    /**
    * Change the player that need to operate their worker
    * @return boolean that demonstrates whether player changes successfully
    */
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

    /**
    * Get the winner of the game
    * @return the player that wins the game
    */
    public Player getWinner() {
        return this.winner;
    }

    /**
    * Set current player as winner
    * @return boolean that demonstrates whether the winner sets successfully
    */
    public boolean setWinner() {
        if(this.winner != null){
            return false;
        }
        else{
            this.winner = this.currentPlayer;
            return true;
        }
    }
}
