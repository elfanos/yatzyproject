package com.example.pandamove.yatzy.score;

import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class to get a leaderboard list by using
 * checkTopList method
 *
 * @author Rasmus Dahlkvist
 */

public class LeaderBoard {


    /**
     * Compare a arraylist of player to sort the list based on
     * total of all inside the scorekeep of all players in the list
     *
     * @param players
     * */
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
}
