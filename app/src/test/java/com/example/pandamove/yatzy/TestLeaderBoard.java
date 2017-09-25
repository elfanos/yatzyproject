package com.example.pandamove.yatzy;

import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rallesport on 2017-09-25.
 */

public class TestLeaderBoard {
    private LeaderBoard test = new LeaderBoard();
    private ArrayList<Player> players = new ArrayList<>();
    private String[] scores = {
            "Header",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Sum",
            "Bonus",
            "1 Pair",
            "2 Pair",
            "3 of a Kind",
            "4 of a Kind",
            "Full House",
            "Small Straight",
            "Long Straight",
            "Chance",
            "Yatzy",
            "Total",
            "Total of All"
    };
    @Test
    public void testTotalOfAll(){
        HashMap<String,Integer> listOfScoresOne = new HashMap<>();
        listOfScoresOne.put("Two", 10);
        HashMap<String,Integer> listOfScoresTwo = new HashMap<>();
        listOfScoresTwo.put("Two", 4);
        HashMap<String,Integer> listOfScoresThree = new HashMap<>();
        listOfScoresThree.put("Two", 3);
        HashMap<String,Integer> listOfScoresFour = new HashMap<>();
        listOfScoresFour.put("Two", 8);
        ArrayList<Player> topScore = new ArrayList<>();
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        player.setCurrentPlayer(true);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
        Player player3 = new Player("Blue", scores);
        player3.setColumnPosition(2);
        player3.setNumberOfThrows(0);
        players.add(player3);
        Player player4 = new Player("Black",scores);
        player4.setColumnPosition(3);
        player4.setNumberOfThrows(0);
        players.add(player4);

        //Yellow 10
        player.getScoreKeeper().setScores(listOfScoresOne);
        player.getScoreKeeper().setUsedScore("Two");

        //Black 4
        player4.getScoreKeeper().setScores(listOfScoresTwo);
        player4.getScoreKeeper().setUsedScore("Two");

        //Orange 3
        player2.getScoreKeeper().setScores(listOfScoresThree);
        player2.getScoreKeeper().setUsedScore("Two");

        //Blue 8
        player3.getScoreKeeper().setScores(listOfScoresFour);
        player3.getScoreKeeper().setUsedScore("Two");
        System.out.println("get tutal?? " + player.getScoreKeeper().getTotalOfAll());

        topScore = test.checkTopList(players);

        for(int i = 0; i < topScore.size(); i++){
            System.out.println("le name?=? " + topScore.get(i).getName());
        }
        Assert.assertEquals("All scores should be",
                "Yellow", topScore.get(0).getName()
        );
        Assert.assertEquals("All scores should be",
                "Blue", topScore.get(1).getName()
        );
        Assert.assertEquals("All scores should be",
                "Black", topScore.get(2).getName()
        );
        Assert.assertEquals("All scores should be",
                "Orange", topScore.get(3).getName()
        );



    }
    @Test
    public void testThreeTotalOfAll(){
        HashMap<String,Integer> listOfScoresOne = new HashMap<>();
        listOfScoresOne.put("Two", 10);
        HashMap<String,Integer> listOfScoresTwo = new HashMap<>();
        listOfScoresTwo.put("Two", 4);
        HashMap<String,Integer> listOfScoresThree = new HashMap<>();
        listOfScoresThree.put("Two", 3);
        ArrayList<Player> topScore = new ArrayList<>();
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
        Player player3 = new Player("Blue", scores);
        player3.setColumnPosition(2);
        player3.setNumberOfThrows(0);
        players.add(player3);

        //Yellow 10
        player.getScoreKeeper().setScores(listOfScoresOne);
        player.getScoreKeeper().setUsedScore("Two");

        //Orange 3
        player2.getScoreKeeper().setScores(listOfScoresThree);
        player2.getScoreKeeper().setUsedScore("Two");

        //Blue 4
        player3.getScoreKeeper().setScores(listOfScoresTwo);
        player3.getScoreKeeper().setUsedScore("Two");
        System.out.println("get tutal?? " + player.getScoreKeeper().getTotalOfAll());

        topScore = test.checkTopList(players);
        Assert.assertEquals("All scores should be",
                "Yellow", topScore.get(0).getName()
        );
        Assert.assertEquals("All scores should be",
                "Blue", topScore.get(1).getName()
        );
        Assert.assertEquals("All scores should be",
                "Orange", topScore.get(2).getName()
        );

    }
    @Test
    public void testTwoTotalOfAll(){
        HashMap<String,Integer> listOfScoresOne = new HashMap<>();
        listOfScoresOne.put("Two", 3);
        HashMap<String,Integer> listOfScoresThree = new HashMap<>();
        listOfScoresThree.put("Two", 4);
        ArrayList<Player> topScore = new ArrayList<>();
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);

        //Yellow 3
        player.getScoreKeeper().setScores(listOfScoresOne);
        player.getScoreKeeper().setUsedScore("Two");

        //Orange 4
        player2.getScoreKeeper().setScores(listOfScoresThree);
        player2.getScoreKeeper().setUsedScore("Two");

        topScore = test.checkTopList(players);
        Assert.assertEquals("All scores should be",
                "Orange", topScore.get(0).getName()
        );
        Assert.assertEquals("All scores should be",
                "Yellow", topScore.get(1).getName()
        );

    }
}
