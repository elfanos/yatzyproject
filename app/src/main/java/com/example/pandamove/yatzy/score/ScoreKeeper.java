package com.example.pandamove.yatzy.score;

import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class ScoreKeeper {
    private String[] scores;
    private HashMap<String, Boolean> checkIfScoresIsSetted;
    private int numberScore;
    private ArrayList<ScoreTable> possibleScoreTable;
    private ArrayList<ScoreTable> scoreTables;
    private HashMap<String, Integer> listOfScores;
    private HashMap<String, Integer> differenceInNumbersScore;
    private int[] bonusScores = {
            3,
            6,
            9,
            12,
            15,
            18,
            63
    };
    private String[] rows = null;
    private String[] numbersRow = {
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
    };
    private String[] combinationRow = {
            "1 Pair",
            "2 Pair",
            "3 of a Kind",
            "4 of a Kind",
            "Full House",
            "Small Straight",
            "Long Straight",
            "Chance",
            "Yatzy",
    };

    public ScoreKeeper(String[] scores){
        checkIfScoresIsSetted = new HashMap<>();
        scoreTables = new ArrayList<>();
        differenceInNumbersScore = new HashMap<>();
        this.rows = scores;
        possibleScoreTable = new ArrayList<>();
        for(int i = 0; i < scores.length; i++){
            checkIfScoresIsSetted.put(scores[i],false);
        }
        this.scores = scores;
    }

    /**
     * Set possible scores that was executed in the throw
     *
     * @param listOfScores a hashmap which contains all
     *                     possible scores
     * */
    public void setScores(HashMap<String,Integer> listOfScores){
        this.listOfScores = listOfScores;
        this.setTempScore();
    }
    public void setColumnScore(String row){
        Iterator iterator = checkIfScoresIsSetted.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry map = (Map.Entry) iterator.next();
            if(map.getKey() == row){
                map.setValue(true);
            }
        }
    }

    public int getCurrentScore(){
        int score = 0;
        if(scoreTables.size() != 0){
            for(int i = 0; i < scoreTables.size(); i++){
                if(scoreTables.get(i).active){
                    score += scoreTables.get(i).score;
                    return score;
                }
            }
        }
        return score;
    }
    /**
     * Sets values to a the temporary possibleScores arraylist
     * that will be used to display all possible scores for the user
     *
     * */
    private void setTempScore(){
        this.clearScoreTablesScore();
        Iterator iterator = listOfScores.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry map = (Map.Entry) iterator.next();
            ScoreTable scoreTable = new ScoreTable();
            scoreTable.row = (String) map.getKey();
            scoreTable.active = true;
            scoreTable.score = (Integer) map.getValue();
            possibleScoreTable.add(scoreTable);
        }
    }
    public Integer sizeOfScores(){
        return scoreTables.size();
    }
    public Integer sizeOfPossibleScores(){
        return possibleScoreTable.size();
    }
    private void clearScoreTablesScore(){
        possibleScoreTable.clear();
    }

    /**
     * Check if the cell is active in a
     * specific row.
     *
     * @param row the row on which that is being check for activity
     * @return if the specific row in the scoreTable is active or not
     * */
    public boolean getActive(String row){
        for(int i = 0; i < scoreTables.size(); i++){
            if(scoreTables.get(i).row.equals(row)){
                return scoreTables.get(i).active;
            }
        }
        return true;
    }

    /**
     * Get possible score by choosing the specific
     * row where the score should appear.
     *
     * @param row the row on which the user choose to use the score
     *            for
     * */
    public Integer getScoresPossible(String row){
        int value = 0;
        for(int i = 0; i < possibleScoreTable.size(); i++){
            if(possibleScoreTable.get(i).row.equals(row)){
                value = possibleScoreTable.get(i).score;
            }
        }
        return value;
    }
    /**
     * Get possible score by choosing the specific
     * row where the score should appear.
     *
     * @param row the row on which the user choose to use the score
     *            for
     * */
    public Integer getScoreOnRow(String row){
        int value = 0;
        for(int i = 0; i < scoreTables.size(); i++){
            if(scoreTables.get(i).row.equals(row)){
                value = scoreTables.get(i).score;
            }
        }
        return value;
    }

    /**
     * When user select a specific score, it will be setted
     * as inactive for the row cell, and be added to the
     * main array scoreTables which contains all the scores
     * for the specific player
     *
     * @param row the row on which the user choose to use the score
     *            for
     * */
    public void setUsedScore(String row) {
        for(int i = 0; i < possibleScoreTable.size(); i++){
            if(possibleScoreTable.get(i).row.equals(row)){
                possibleScoreTable.get(i).active = false;
                System.out.println("hmm??");
                this.scoreTables.add(possibleScoreTable.get(i));
                this.clearScoreTablesScore();
            }
        }
        this.setBonus();
    }
    public boolean checkIfColumnGotScore(String row) {
        Iterator iterator = checkIfScoresIsSetted.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry map = (Map.Entry) iterator.next();
            if (map.getKey() == row) {
                return (Boolean) map.getValue();
            }
        }
        return false;
    }
    public int getSumOfNumbers(){
        int sum = 0;
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < numbersRow.length; j++){
                if(scoreTables.get(i).row.equals(numbersRow[j])){
                    System.out.println("Score row" + scoreTables.get(i).row + scoreTables.size());
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }
        }
        return sum;
    }
    public boolean checkIfItHalfScore() {
        int counter = 0;
        System.out.println("Sizare row" +  scoreTables.size());
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < numbersRow.length; j++){
                if(scoreTables.get(i).row.equals(numbersRow[j])){
                    System.out.println("Score row" + scoreTables.get(i).row + scoreTables.size());
                    if(!scoreTables.get(i).active) {
                        counter++;
                    }
                }
             }
        }
        if(counter >= 6){
            return true;
        }else{
            return false;
        }
    }
    public int checkBonus(){
        int bonus = 0;
        Iterator iterator = differenceInNumbersScore.entrySet().iterator();
       while (iterator.hasNext()) {
           Map.Entry map = (Map.Entry) iterator.next();
           System.out.println("le values" + map.getValue());
           bonus += (Integer) map.getValue();
       }
        if(this.checkIfItHalfScore()) {
            if(bonus > 0){
                return (50 + bonus);
            }else{
                return 0;
            }
        }
        return bonus;
    }
    public int getTotal(){
        int sum = 0;
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < combinationRow.length; j++){
                if(scoreTables.get(i).row.equals(combinationRow[j])){
                    System.out.println("Score row" + scoreTables.get(i).row + scoreTables.size());
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }

        }
        return sum;
    }
    public int getTotalOfAll(){
        int sum = 0;
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < combinationRow.length; j++){
                if(scoreTables.get(i).row.equals(combinationRow[j])){
                    System.out.println("Score row" + scoreTables.get(i).row + scoreTables.size());
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }
            for (int j = 0; j < numbersRow.length; j++){
                if(scoreTables.get(i).row.equals(numbersRow[j])){
                    System.out.println("Score row" + scoreTables.get(i).row + scoreTables.size());
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }
        }
        return sum;
    }
    private void setBonus(){
        int endScore = 0;
        for(int i = 0; i < scoreTables.size(); i++){
            switch (scoreTables.get(i).row){
                case "One":
                    endScore = scoreTables.get(i).score - bonusScores[0];
                    differenceInNumbersScore.put("One", endScore);
                    break;
                case "Two":
                    endScore = scoreTables.get(i).score - bonusScores[1];
                    differenceInNumbersScore.put("Two", endScore);
                    break;
                case "Three":
                    endScore = scoreTables.get(i).score - bonusScores[2];
                    differenceInNumbersScore.put("Three", endScore);
                    break;
                case "Four":
                    endScore = scoreTables.get(i).score - bonusScores[3];
                    differenceInNumbersScore.put("Four", endScore);
                    break;
                case "Five":
                    endScore = scoreTables.get(i).score - bonusScores[4];
                    differenceInNumbersScore.put("Five", endScore);
                    break;
                case "Six":
                    endScore = scoreTables.get(i).score - bonusScores[5];
                    differenceInNumbersScore.put("Six", endScore);
                    break;
            }
        }
    }
    public int checkIfHalfScore(){
     int pivot = 0;
        if(!checkIfScoresIsSetted.get(scores[7])) {
            if (checkIfScoresIsSetted.get(scores[1])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[2])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[3])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[4])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[5])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[6])) {
                pivot++;
            }
            if (pivot > 5) {
                return 1;
            }
        }
        if(!checkIfScoresIsSetted.get(scores[17])){
            if (checkIfScoresIsSetted.get(scores[8])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[9])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[10])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[11])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[12])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[13])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[14])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[15])) {
                pivot++;
            }
            if (checkIfScoresIsSetted.get(scores[16])) {
                pivot++;
            }
            if (pivot > 8) {
                return 2;
            }
        }
        return 0;
    }
    public void setNumberScore(int numberScore) {
        this.numberScore = numberScore;
    }

    public int getTotalScore(){
        return 0;
    }
    public int getNumberScore(){
        return numberScore;
    }

    static class ScoreTable{
        int score;
        boolean active;
        String row;
    }



}
