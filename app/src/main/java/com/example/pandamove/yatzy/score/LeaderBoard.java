package com.example.pandamove.yatzy.score;

import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by rallesport on 2017-09-25.
 */

public class LeaderBoard {
    private ArrayList<Player> players;



    public ArrayList<Player> checkTopList(ArrayList<Player> players){

        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player lhs, Player rhs) {
                if(lhs == rhs) {
                    return 0;
                }
                return lhs.getScoreKeeper().getTotalOfAll() > rhs.getScoreKeeper().getTotalOfAll() ? -1 : 1;
            }
        });
        return players;
    }
   /* public Player getLastPlayer(ArrayList<Player> firstThreePlayers){
        Player player = null;
        for(int j = 0; j < firstThreePlayers.size(); j++){
            System.out.println("playa name?? " + firstThreePlayers.get(j).getName());
        }
        for (int i = 0; i < players.size(); i++) {
            System.out.println("playa name?? " + players.get(i).getName());
            if(!players.get(i).getName().equals(firstThreePlayers.get(0).getName())
                    &&
                    !players.get(i).getName().equals(firstThreePlayers.get(1).getName())
                    &&
                    !players.get(i).getName().equals(firstThreePlayers.get(2).getName())
                    ){
                player = players.get(i);
            }
        }
        System.out.println("wadda isawdawd=? " + player.getScoreKeeper().getTotalOfAll());
        return player;
    }
    public Player getLeadingPlayer(){
        int score = 0;
        Player player = null;
        for(int i = 0; i < players.size(); i++){
            if(score == 0){
                score = players.get(i).getScoreKeeper().getTotalOfAll();
                player = players.get(i);
            }else if(score < players.get(i).getScoreKeeper().getTotalOfAll()){
                score = players.get(i).getScoreKeeper().getTotalOfAll();
                player = players.get(i);
            }
        }
        System.out.println("wadda is=? " + player.getScoreKeeper().getTotalOfAll());
        return player;
    }
    public Player getSecondPlayer(Player leadingPlayer){
        int score = 0;
        Player player = null;
        for(int i = 0; i < players.size(); i++){
            if(!players.get(i).equals(leadingPlayer)) {
                if (score == 0) {
                    score = players.get(i).getScoreKeeper().getTotalOfAll();
                    player = players.get(i);
                } else if (score < players.get(i).getScoreKeeper().getTotalOfAll()) {
                    score = players.get(i).getScoreKeeper().getTotalOfAll();
                    player = players.get(i);
                }
            }
        }
        return player;
    }
    public Player getThirdPlayer(ArrayList<Player> lastTwoPlayers){
        int score = lastTwoPlayers.get(0).getScoreKeeper().getTotalOfAll();
        int scoreTwo = lastTwoPlayers.get(1).getScoreKeeper().getTotalOfAll();
        if(score > scoreTwo || score == scoreTwo){
            return lastTwoPlayers.get(0);
        }else {
            return lastTwoPlayers.get(1);
        }
    }*/
}
