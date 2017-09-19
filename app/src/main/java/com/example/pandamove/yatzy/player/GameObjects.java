package com.example.pandamove.yatzy.player;

import java.util.ArrayList;

/**
 * Created by rallesport on 2017-09-18.
 */

public class GameObjects {
    private int round;
    private int halfScore;
    private static final int NUMBEROFROUNDS = 14;
    private ArrayList<Player> players;
    public GameObjects(ArrayList<Player> players){
        this.players = players;
        round = 1;
    }
    public int getNextPlayer(int playerPos){
        if(players.size() >= 1) {
            switch (playerPos) {
                case 0:
                    return 1;
                case 1:
                    if(players.size() >= 2) {
                        return 2;
                    }
                case 2:
                    if(players.size() >= 3) {
                        return 3;
                    }
                case 3:
                    if(players.size() >= 4) {
                        return 0;
                    }
                default:
            }
        }
        return 0;
    }
    public void initializeNextRound(){
        if(!this.checkIfLastRound()){
            this.setRound();
        }else{
            System.out.println("endgame");
        }
        System.out.println("getrounds?=" + this.getRound());
    }
    private boolean checkIfLastRound(){
        if(this.getRound() < NUMBEROFROUNDS){
            return false;
        }else{
            return true;
        }
    }
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
    public int getRound() {
        return round;
    }

    public void setRound() {
       this.round++;
    }

    public int getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(int halfScore) {
        this.halfScore = halfScore;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
