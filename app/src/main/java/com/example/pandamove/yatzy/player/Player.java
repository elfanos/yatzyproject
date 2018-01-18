package com.example.pandamove.yatzy.player;

import com.example.pandamove.yatzy.score.ScoreKeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class Player implements Serializable{
    private String name;
    private ScoreKeeper scoreKeeper;
    private int columnPosition;
    private boolean currentPlayer;
    private int round;
    private int numberOfThrows;
    private boolean scoreIsSet;

    /**
     * Constructor player
     * every player has one scoreKeeper
     * set to score Zero first.
     * And one name of the player
     * @param name
     * */
    public Player(String name, String[] scores){

        // Set a score keeper for the player to keep information
        // about the player score during the game
        scoreKeeper = new ScoreKeeper(scores);

        this.name = name;
        round = 1;
    }

    /**
     * @return scoreIsSet if scores is set or not
     * */
    public boolean isScoreIsSet() {
        return scoreIsSet;
    }

    /**
     * Set if score is set or not
     *
     * @param scoreIsSet boolean value if set or not
     * */
    public void setScoreIsSet(boolean scoreIsSet) {
        this.scoreIsSet = scoreIsSet;
    }

    /**
     * Increment rounds of the current player, but it is rolls
     * */
    public void incrementRound(){
        System.out.println("le rounds??");
        round = 5;
        //round++;
    }
    public int getRound() {
        return round;
    }

    /**
     * @return the number of throws by the player numberOfThrows
     * */
    public int getNumberOfThrows() {
        return numberOfThrows;
    }

    /**
     * Set the number of throws for the player
     *
     * @param numberOfThrows which number of throws that is current
     * */
    public void setNumberOfThrows(int numberOfThrows) {
        this.numberOfThrows = numberOfThrows;
    }

    /**
     * Increase the number of throws for the player
     * */
    public void increseNumberOfThrows(){
        this.numberOfThrows++;
    }

    /**
     * @return name of the player
     * */
    public String getName(){
        return name;
    }

    /**
     * @return scoreKeeper of the player, contains all the information
     *          about the players score in the game
     * */
    public ScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }

    /**
     * Get the columnposition for the player between 0-3
     *
     * @return columnPosition as an integer between 0-3
     * */
    public int getColumnPosition() {
        return columnPosition;
    }

    /**
     * Set the columnposition for the player as integer
     *
     * @param columnPosition which position between 0-3
     * */
    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    /**
     * @return currePlayer as boolean if it is true the player is current player
     * */
    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set if the player is current or not
     *
     * @param currentPlayer a boolean true or false if the player is current
     * */
    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
