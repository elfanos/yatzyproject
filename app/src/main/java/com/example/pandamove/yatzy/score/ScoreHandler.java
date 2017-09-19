package com.example.pandamove.yatzy.score;

import android.util.SparseArray;
import android.widget.ArrayAdapter;

import com.example.pandamove.yatzy.dice.Dice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rallmo on 2017-04-05.
 *
 * @author Rasmus Dahlkvist
 */
public class ScoreHandler {

    private SparseArray<Dice> dices;
    private HashMap<String,Integer> allScores;
    private ArrayList<Integer> pairs;
    private int threeOfKind = 0;
    private int [] allScoresOnDice = {
            1, 2, 3, 4, 5, 6
    };
    private String[] scores = {
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
            "Yatzy"
    };


    public ScoreHandler(SparseArray<Dice> dices) {
        this.dices = dices;
        allScores = new HashMap<>();
        pairs = new ArrayList<>();
        this.checkScores();
    }
    private void checkScores(){
        this.checkForNumbers();
        this.checkForHighestScore();
        this.checkForScoreThreeOfKind();
        this.checkForLowStraight();
        this.checkForBigStraight();
        this.checkForFullHouse();
        this.checkForChance();
        this.checkForYatzy();
    }
    public HashMap<String,Integer> possibleScores(){
        return allScores;
    }

    private void checkForNumbers(){
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        for(int i = 1; i < dices.size(); i++){
            switch (dices.get(i).getScore()){
                case 1:
                    one += (dices.get(i).getScore());
                    allScores.put(scores[0], one);
                    break;
                case 2:
                    two += (dices.get(i).getScore());
                    allScores.put(scores[1], two);
                    break;
                case 3:
                    three += (dices.get(i).getScore());
                    allScores.put(scores[2], three);
                    break;
                case 4:
                    four += (dices.get(i).getScore());
                    allScores.put(scores[3], four);
                    break;
                case 5:
                    five += (dices.get(i).getScore());
                    allScores.put(scores[4], five);
                    break;
                case 6:
                    six += (dices.get(i).getScore());
                    allScores.put(scores[5], six);
                    break;
            }
        }

    }
    private int checkTheHighestPair(List<Integer> pairCollector){
        int value = 0;
        if(pairCollector.size() != 0) {
            for (int i = 0; i < pairCollector.size(); i++){
                if(value < pairCollector.get(i)){
                    value = pairCollector.get(i);
                }
            }
        }
        return value;
    }
    private void checkForPair(int position, List<Integer> pairCollector){
        for(int i = position; i < dices.size(); i++){
            if((i+1) < dices.size()) {
                if (dices.get(position).getScore() == dices.get(i + 1).getScore()) {
                    System.out.println("should be one time?");
                    int value = (dices.get(position).getScore()) + (dices.get(i + 1).getScore());
                    pairCollector.add(value);
                    System.out.println("wat is ur size" + pairCollector.size() + "and value?" + value);
                    pairs.add(value);
                }
            }
        }
    }
    private void checkForPairs(int position,List<Integer> pairCollector){
        this.checkForPair(position, pairCollector);
        int highestPair = this.checkTheHighestPair(pairCollector);
        allScores.put(scores[7], highestPair);
        if(pairCollector.size() > 1) {
            this.checkForTwoPairOrFourOfKind(pairCollector);
        }
    }
    private void checkForHighestScore(){
        List<Integer> scoreCollector = new ArrayList<>();
        //Use 4 pivot to check all pairs
        boolean [] pivot = { false, false, false, false};
        //Skip first element since it is a dummy
        for(int i = 0; i < pivot.length; i++) {
            if (!pivot[0]) {
                pivot[0] = true;
                this.checkForPairs(1, scoreCollector);
            } else if (!pivot[1]) {
                //second element
                this.checkForPairs(2, scoreCollector);
                pivot[1] = true;
            } else if (!pivot[2]) {
                //third element
                this.checkForPairs(3, scoreCollector);
                pivot[2] = true;
            } else if (!pivot[3]) {
                //fourth element
                this.checkForPairs(4, scoreCollector);
                pivot[3] = true;
            }
        }
    }
    private void checkForPairOrThreeOfAkind(List<Integer> pairCollector){
        System.out.println("thee of a " + pairCollector.size());
        int value = 0;
        for(int i = 0; i < pairCollector.size(); i++){
            if(pairCollector.size() > 1){
                if((i+1) < pairCollector.size()){
                    if(!pairCollector.get(i).equals(pairCollector.get(i+1))){

                        value += pairCollector.get(i);
                        value += pairCollector.get(i+1);
                    }else{
                        System.out.println("thee of a welwel ");
                        value = 0;
                    }
                }
            }
        }
        if(value != 0) {
            allScores.put(scores[8], value);
        }
    }
    private void checkForTwoPairOrFourOfKind(List<Integer> pairCollector){
        int value = 0;
        int fourOfKind = 0;
        System.out.println("thee of a aw");
        for(int i = 0; i < pairCollector.size(); i++){
            if(pairCollector.size() > 2) {
                if((i+1) < pairCollector.size()) {
                    if (!pairCollector.get(i).equals(pairCollector.get(i + 1))) {

                        value += pairCollector.get(i);
                        value += pairCollector.get(i + 1);

                    } else {
                        fourOfKind = pairCollector.get(i) + pairCollector.get(i + 1);
                    }
                }
            }else{
                this.checkForPairOrThreeOfAkind(pairCollector);
               // value += pairCollector.get(i);
            }
        }
        if(value != 0) {
            allScores.put(scores[8], value);
        }else if(fourOfKind != 0){
            allScores.put(scores[10], fourOfKind);
        }
    }
    private void checkForScoreThreeOfKind(){
        List<Integer> scoreCollector = new ArrayList<>();
        boolean [] pivot = { false, false, false};
        for(int i = 0; i < pivot.length; i++){
            if(!pivot[0]){
                this.checkForThreeOfAKind(1, scoreCollector);
                pivot[0] = true;
            }else if(!pivot[1]){
                this.checkForThreeOfAKind(2, scoreCollector);
                pivot[1] = true;
            }else if(!pivot[2]){
                this.checkForThreeOfAKind(3, scoreCollector);
                pivot[2] = true;
            }
        }
        if(scoreCollector.size() != 0){
            allScores.put("3 of a Kind", scoreCollector.get(0));
            threeOfKind = scoreCollector.get(0);
        }
    }
    private void checkForThreeOfAKind(int position, List<Integer> scoreCollector){
        for(int i = position; i < dices.size(); i++){
            if((i+1) < dices.size()) {
                if (dices.get(position).getScore() == dices.get(i + 1).getScore()){
                    this.checkCombination(position, (i+1), scoreCollector);
                }
            }
        }
    }
    private void checkCombination(int firstPosition, int lastCombPosition, List<Integer> scoreCollector){
        for(int i = lastCombPosition; i < dices.size(); i++){
            if((i+1) < dices.size()) {
                if (dices.get(lastCombPosition).getScore() == dices.get(i + 1).getScore()) {
                    int value = dices.get(firstPosition).getScore() +
                            dices.get(lastCombPosition).getScore() +
                            dices.get(i + 1).getScore();
                    scoreCollector.add(value);
                }
            }

        }
    }
    private void checkForLowStraight(){
        boolean [] allChecked = {false, false, false, false, false};
        int dicesInStraight = 0;
        for(int i = 1; i < dices.size(); i++){
            if(dices.get(i).getScore() == 1){
                if(!allChecked[0]) {
                    dicesInStraight++;
                    allChecked[0] = true;
                }
            }else if(dices.get(i).getScore() == 2){
                if(!allChecked[1]) {
                    dicesInStraight++;
                    allChecked[1] = true;
                }
            }else if(dices.get(i).getScore() == 3){
                if(!allChecked[2]) {
                    dicesInStraight++;
                    allChecked[2] = true;
                }
            }else if(dices.get(i).getScore() == 4){
                if(!allChecked[3]) {
                    dicesInStraight++;
                    allChecked[3] = true;
                }
            }else if(dices.get(i).getScore() == 5){
                if(!allChecked[4]) {
                    dicesInStraight++;
                    allChecked[4] = true;
                }
            }
        }
        if(dicesInStraight > 4){
            int value = 1+2+3+4+5;
            allScores.put(scores[12], value);
        }
    }
    private void checkForBigStraight(){
        boolean [] allChecked = {false, false, false, false, false};
        int dicesInStraight = 0;
        for(int i = 1; i < dices.size(); i++){
            if(dices.get(i).getScore() == 2){
                if(!allChecked[0]) {
                    dicesInStraight++;
                    allChecked[0] = true;
                }
            }else if(dices.get(i).getScore() == 3){
                if(!allChecked[1]) {
                    dicesInStraight++;
                    allChecked[1] = true;
                }
            }else if(dices.get(i).getScore() == 4){
                if(!allChecked[2]) {
                    dicesInStraight++;
                    allChecked[2] = true;
                }
            }else if(dices.get(i).getScore() == 5){
                if(!allChecked[3]) {
                    dicesInStraight++;
                    allChecked[3] = true;
                }
            }else if(dices.get(i).getScore() == 6){
                if(!allChecked[4]) {
                    dicesInStraight++;
                    allChecked[4] = true;
                }
            }
        }
        if(dicesInStraight > 4){
            int value = 2+3+4+5+6;
            allScores.put(scores[13], value);
        }
    }
    private void checkForFullHouse(){
        int fullHouse = 0;
        if(threeOfKind != 0 && pairs.size() != 0){
            for(int i = 0; i < pairs.size(); i++){
                if((pairs.get(i)/2) != (threeOfKind/3)){
                    fullHouse = pairs.get(i) + threeOfKind;
                    break;
                }
            }
        }
        if(fullHouse != 0){
            allScores.put(scores[11], fullHouse);
        }
    }
    private void checkForChance(){
        int value = 0;
        for(int i = 1; i < dices.size(); i++){
            value += dices.get(i).getScore();
        }

        allScores.put(scores[14], value);

    }
    private void checkForYatzy(){
        int incrementDices = 0;
        int value = 0;
        for(int i = 1; i < dices.size(); i++){
            if((i + 1) < dices.size()) {
                if (dices.get(i).getScore() == dices.get(i + 1).getScore()) {
                    incrementDices++;
                }
            }
        }
        if(incrementDices > 3){
            for(int i = 1; i < dices.size(); i++){
                value += dices.get(i).getScore();
            }
            allScores.put(scores[15], value);
        }
    }

}
