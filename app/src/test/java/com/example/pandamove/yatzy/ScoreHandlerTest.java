package com.example.pandamove.yatzy;

import android.util.SparseArray;

import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.score.ScoreHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Rasmus on 02/09/17.
 */

public class ScoreHandlerTest {
    ScoreHandler scoreHandler;
    private SparseArray<Dice> dices;
    @Test
    public void scoreInThree(){
        dices = new SparseArray<Dice>();
        for(int i = 0; i < 5; i++){
            Dice newDice = new Dice(true, 3, i);
            dices.put(newDice.getSurfaceIndex(),newDice);
        }
        scoreHandler = new ScoreHandler(dices);
        int value = scoreHandler.possibleScores().get("Three");
        Assert.assertEquals("All scores should be",
                15, value
        );
    }
   @Test
    public void someRandomNumb(){
        dices = new SparseArray<Dice>();
        Dice newDice = new Dice(true, 1, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 2, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 4, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 4, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int two = scoreHandler.possibleScores().get("Two");
        int four = scoreHandler.possibleScores().get("Four");
        int five = scoreHandler.possibleScores().get("Five");
        int one = scoreHandler.possibleScores().get("One");
        Assert.assertEquals("All scores should be",
                2, two
        );
        Assert.assertEquals("All scores should be",
                5, five
        );
        Assert.assertEquals("All scores should be",
                8, four
        );
        Assert.assertEquals("All scores should be",
                2, one
        );
    }
    @Test
    public void someRandomNumbTwo(){
        dices = new SparseArray<Dice>();
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 2, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int two = scoreHandler.possibleScores().get("Two");
        int six = scoreHandler.possibleScores().get("Six");
        int one = scoreHandler.possibleScores().get("One");
        Assert.assertEquals("All scores should be",
                4, two
        );
        Assert.assertEquals("All scores should be",
                12, six
        );
        Assert.assertEquals("All scores should be",
                2, one
        );
    }
    @Test
    public void pairChecker(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 2, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int highestPair = scoreHandler.possibleScores().get("1 Pair");
        Assert.assertEquals("All scores should be",
                12, highestPair
        );
    }
    @Test
    public void pairTwoChecker(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 2, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int twoPair = scoreHandler.possibleScores().get("2 Pair");
        Assert.assertEquals("All scores should be",
                14, twoPair
        );
    }
    @Test
    public void threeOfAKindChecker(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int threeofKind = scoreHandler.possibleScores().get("3 of a Kind");
        Assert.assertEquals("All scores should be",
                18, threeofKind
        );
    }
    @Test
    public void threeOfAKindCheckerTwo(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int threeofKind = scoreHandler.possibleScores().get("3 of a Kind");
        int onePair = scoreHandler.possibleScores().get("1 Pair");
        Assert.assertEquals("All scores should be",
                3, threeofKind
        );
        Assert.assertEquals("All scores should be",
                6, onePair
        );
    }
    @Test
    public void threeOfAKindCheckerThree(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int threeofKind = scoreHandler.possibleScores().get("3 of a Kind");
        int onePair = scoreHandler.possibleScores().get("1 Pair");
        Assert.assertEquals("All scores should be",
                9, threeofKind
        );
        Assert.assertEquals("All scores should be",
                6, onePair
        );
    }
    @Test
    public void fourOfAKindChecker(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int fourOfKind = scoreHandler.possibleScores().get("4 of a Kind");
        Assert.assertEquals("All scores should be",
                4, fourOfKind
        );
    }
    @Test
    public void checkSmallStraight(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 2, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 1, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 4, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int sStraight = scoreHandler.possibleScores().get("Small Straight");
        Assert.assertEquals("All scores should be",
                15, sStraight
        );
    }
    @Test
    public void checkLargeStraight(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 2, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 2, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 4, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int lStraight = scoreHandler.possibleScores().get("Long Straight");
        Assert.assertEquals("All scores should be",
                20, lStraight
        );
    }
    @Test
    public void checkFullHouse(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 3, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int fullHouse = scoreHandler.possibleScores().get("Full House");
        Assert.assertEquals("All scores should be",
                19, fullHouse
        );
    }
    @Test
    public void checkFullHouseTwo(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 3, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int fullHouse = scoreHandler.possibleScores().get("Full House");
        Assert.assertEquals("All scores should be",
                21, fullHouse
        );
    }
    @Test
    public void checkForChance(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 3, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int chance = scoreHandler.possibleScores().get("Chance");
        Assert.assertEquals("All scores should be",
                21, chance
        );
    }
    @Test
    public void checkYatzy(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 3, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 3, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int yatzy = scoreHandler.possibleScores().get("Yatzy");
        Assert.assertEquals("All scores should be",
                15, yatzy
        );
    }
    @Test
    public void checkYatzyTwo(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 3, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        int yatzy = scoreHandler.possibleScores().get("Yatzy");
        Assert.assertEquals("All scores should be",
                30, yatzy
        );
    }

    @Test
    public void checkTwoPair(){
        dices = new SparseArray<Dice>();
        //Should be dummy :D
        Dice newDice = new Dice(true, 3, 0);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 1);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 2);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 4, 3);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 5, 4);
        dices.put(newDice.getSurfaceIndex(),newDice);
        newDice = new Dice(true, 6, 5);
        dices.put(newDice.getSurfaceIndex(),newDice);
        scoreHandler = new ScoreHandler(dices);

        Object yatzy = scoreHandler.possibleScores().get("2 Pair");
        Assert.assertNull(yatzy);
    }


}
