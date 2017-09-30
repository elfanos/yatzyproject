package com.example.pandamove.yatzy.controllers;

import com.example.pandamove.yatzy.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class with handle game object and rounds during the
 * yatzy game
 * @author Rasmus Dahlkvist
 */

public class GameObjects implements Serializable{
    private int round;
    private int halfScore;
    private static final int NUMBEROFROUNDS = 14;
    private ArrayList<Player> players;

    /**
     * Constructor
     * @param players list of players
     * */
    public GameObjects(ArrayList<Player> players){
        this.players = players;
        round = 1;
    }

    /**
     * @return the next player as an int to
     *          get the right column position of the player
     * */
    public int getNextPlayer(int playerPos){
        if(players.size() > 1) {
            switch (playerPos) {
                case 0:
                    return 1;
                case 1:
                    if(players.size() > 2) {
                        return 2;
                    }
                case 2:
                    if(players.size() > 3) {
                        return 3;
                    }
                case 3:
                    if(players.size() > 4) {
                        return 0;
                    }
                default:
            }
        }
        return 0;
    }

    /**
     * Called when a new round is initialized
     * */
    public void initializeNextRound(){
        if(!this.checkIfLastRound()){
            this.setRound();
        }
    }

    /**
     * Check if it is last round
     *
     * @return false or true
     * */
    private boolean checkIfLastRound(){
        if(this.getRound() < NUMBEROFROUNDS){
            return false;
        }else{
            return true;
        }
    }
    /**
     * Refresh scores is setted for all the players in the game
     * */
    public void refreshScoreIsSetted(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isScoreIsSet()){
                players.get(i).setScoreIsSet(false);
            }
        }
    }

    /**
     * Check if it is last player after each score is setted
     *
     *  @return true or false
     * */
    public boolean checkIfLastPlayer(){
        int counter = 0;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isScoreIsSet()){
                counter++;
            }
        }
        if(counter >= 4){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return number of rounds
     * */
    public int getRound() {
        return round;
    }

    /**
     * Increment rounds
     * */
    public void setRound() {
       this.round++;
    }

    /**
     * Set round for testing
     * @param test which round to start on
     * */
    public void setRoundTest(int test){
        this.round = test;
    }

    /**
     * @return players get the players
     * */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @param players set the players
     * */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
