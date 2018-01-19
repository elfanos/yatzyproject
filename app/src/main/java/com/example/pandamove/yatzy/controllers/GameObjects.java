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
    private ArrayList<Integer> glScore;
    private ArrayList<GlActive> glActive;

    /**
     * Constructor
     * @param players list of players
     * */
    public GameObjects(ArrayList<Player> players, ArrayList<Integer> glScore){
        this.players = players;
        this.glScore = glScore;
        glActive = new ArrayList<>();
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


    /**
     * Empty the glScore array and
     * and declare a new one
     * */
    public void clearGlInfo(){
        glScore = null;
        glScore = new ArrayList<>();
    }

    /**
     * Empty the glActive array
     * and declare a new one
     * */
    public void clearGlActive(){
        glActive = null;
        glActive = new ArrayList<>();
    }

    /**
     * @return glScore as a list of integer
     * */
    public ArrayList<Integer> getGlScore(){
        return glScore;
    }

    /**
     * Add a new value to the glScore array
     *
     * @param score the value of the integer which is added to the array
     * */
    public void setGlScore(int score){
        this.glScore.add(score);
    }

    /**
     * Add a new GlActive object to the glActive array
     *
     * @param index as a integer
     * @param bool the state given when called
     * */
    public synchronized void setGlActive(int index, boolean bool){
        GlActive glActive = new GlActive(index,bool);
        this.glActive.add(glActive);
    }

    /**
     * Remove an object inside the glActive array
     *
     * @param index the index of the object which is being removed
     * */
    public synchronized void removeGlActive(int index){
        for(int i = 0; i < glActive.size(); i+=1){
            if(glActive.get(i).getIndex() == index){
                this.glActive.remove(i);
            }
        }
    }

    /**
     * @return glActive as an array of GlActive objects
     * */
    public ArrayList<GlActive> getGlActive(){
        return glActive;
    }

    /**
     * Class which keep selected surface index when user click
     * on a open gl surface
     * */
    public class GlActive implements Serializable{
        int index;
        boolean active;

        /**
         * Constructor of the GlActive class
         *
         * @param index gives an index as integer
         * @param active gives an state
         * */
        public GlActive(int index, boolean active){
            this.index = index;
            this.active = active;
        }
        /**
         * @return the index of the object
         * */
        public int getIndex() {
            return index;
        }

        /**
         * @return the state of the object
         * */
        public boolean isActive() {
            return active;
        }


    }
}
