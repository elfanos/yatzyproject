package com.example.pandamove.yatzy.player;

import com.example.pandamove.yatzy.score.ScoreKeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class Player {
    private String name;
    private ScoreKeeper scoreKeeper;
    private int columnPosition;
    private boolean currentPlayer;
    private HashMap<String,Integer> scoresInRow;

    /**
     * Constructor player
     * every player has one scoreKeeper
     * set to score Zero first.
     * And one name of the player
     * @param name
     * */
    public Player(String name, String[] scores){
        scoreKeeper = new ScoreKeeper(scores);
        scoresInRow = new HashMap<>();
        this.name = name;
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
    public void setRowValueAdded(){
        Iterator iterator = scoresInRow.entrySet().iterator();
    }

}
