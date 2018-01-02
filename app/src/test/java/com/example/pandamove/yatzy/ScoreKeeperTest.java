package com.example.pandamove.yatzy;

import com.example.pandamove.yatzy.score.ScoreKeeper;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class ScoreKeeperTest {
    private String[] scores = {
            "Header",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Sum",
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
    public void setPossibleScores(){
        HashMap<String,Integer> possibleScores = new HashMap<>();
        ScoreKeeper scoreKeeper = new ScoreKeeper(scores);
        possibleScores.put("One", 3);
        possibleScores.put("Two", 6);
        possibleScores.put("Three", 9);
        possibleScores.put("Four", 12);
        possibleScores.put("Five", 15);
        possibleScores.put("Six", 18);
        scoreKeeper.setScores(possibleScores);
        int size = scoreKeeper.sizeOfPossibleScores();
        Assert.assertEquals("All scores should be",
                6, size
        );
    }
    @Test
    public void getPossibleScores(){
        HashMap<String,Integer> possibleScores = new HashMap<>();
        ScoreKeeper scoreKeeper = new ScoreKeeper(scores);
        possibleScores.put("One", 3);
        possibleScores.put("Two", 6);
        possibleScores.put("Three", 9);
        possibleScores.put("Four", 12);
        possibleScores.put("Five", 15);
        possibleScores.put("Six", 18);
        scoreKeeper.setScores(possibleScores);
        int scoreOne = scoreKeeper.getScoresPossible("One");
        int scoreFour = scoreKeeper.getScoresPossible("Four");
        int scoreSix = scoreKeeper.getScoresPossible("Six");
        int noScore = scoreKeeper.getScoresPossible("1 Pair");
        Assert.assertEquals("All scores should be",
                3, scoreOne
        );
        Assert.assertEquals("All scores should be",
                12, scoreFour
        );
        Assert.assertEquals("All scores should be",
                18, scoreSix
        );
        Assert.assertEquals("All scores should be",
                0, noScore
        );
    }
    @Test
    public void setUsedScores(){
        HashMap<String,Integer> possibleScores = new HashMap<>();
        ScoreKeeper scoreKeeper = new ScoreKeeper(scores);
        possibleScores.put("One", 3);
        possibleScores.put("Two", 6);
        possibleScores.put("Three", 9);
        possibleScores.put("Four", 12);
        possibleScores.put("Five", 15);
        possibleScores.put("Six", 18);
        scoreKeeper.setScores(possibleScores);
        scoreKeeper.setUsedScore("Two");
        boolean usedScore = scoreKeeper.getActive("Two");
        boolean notUsedScore = scoreKeeper.getActive("One");
        boolean noneExistingScore = scoreKeeper.getActive("1 Pair");
        Assert.assertEquals("Active should be false",
                false, usedScore
        );
        Assert.assertEquals("Active should be true",
                true, notUsedScore
        );
        Assert.assertEquals("Active should be true",
                true, noneExistingScore
        );
    }
    @Test
    public void checkListAfterUsedScore(){
        HashMap<String,Integer> possibleScores = new HashMap<>();
        ScoreKeeper scoreKeeper = new ScoreKeeper(scores);
        possibleScores.put("One", 3);
        possibleScores.put("Two", 6);
        possibleScores.put("Three", 9);
        possibleScores.put("Four", 12);
        possibleScores.put("Five", 15);
        possibleScores.put("Six", 18);
        scoreKeeper.setScores(possibleScores);
        scoreKeeper.setUsedScore("Two");
        int size = scoreKeeper.sizeOfScores();
        int sizeOfPossible = scoreKeeper.sizeOfPossibleScores();
        Assert.assertEquals("Active should be false",
                1, size
        );
        Assert.assertEquals("Active should be false",
                0, sizeOfPossible
        );

    }
    @Test
    public void checkListAfterUsedScoreAllScores(){
        HashMap<String,Integer> possibleScores = new HashMap<>();
        ScoreKeeper scoreKeeper = new ScoreKeeper(scores);
        possibleScores.put("One", 3);
        possibleScores.put("Two", 6);
        possibleScores.put("Three", 9);
        possibleScores.put("Four", 12);
        possibleScores.put("Five", 20);
        possibleScores.put("Six", 18);
        scoreKeeper.setScores(possibleScores);
        scoreKeeper.setUsedScore("Two");
        int size = scoreKeeper.sizeOfScores();
        int sizeOfPossible = scoreKeeper.sizeOfPossibleScores();
        Assert.assertEquals("Active should be false",
                1, size
        );
        Assert.assertEquals("Active should be false",
                0, sizeOfPossible
        );

    }
}
