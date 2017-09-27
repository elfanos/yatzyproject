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
        scoreKeeper = new ScoreKeeper(scores);
        this.name = name;
        round = 1;
    }

    public boolean isScoreIsSet() {
        return scoreIsSet;
    }
    public void setScoreIsSet(boolean scoreIsSet) {
        this.scoreIsSet = scoreIsSet;
    }
    public void incrementRound(){
        round++;
    }
    public int getRound() {
        return round;
    }
    public int getNumberOfThrows() {
        return numberOfThrows;
    }
    public void setNumberOfThrows(int numberOfThrows) {
        this.numberOfThrows = numberOfThrows;
    }
    public void increseNumberOfThrows(){
        this.numberOfThrows++;
    }
    public String getName(){
        return name;
    }
    public ScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }
    public int getColumnPosition() {
        return columnPosition;
    }
    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }
    public boolean isCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
