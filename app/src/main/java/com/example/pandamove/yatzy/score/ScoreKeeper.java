package com.example.pandamove.yatzy.score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class which keep track of a player scores
 * during the game.
 *
 * @author Rasmus Dahlkvist
 */
public class ScoreKeeper implements Serializable{
    private String[] scores;
    private HashMap<String, Boolean> checkIfScoresIsSetted;
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

    /**
     * Class constructor
     * initalized by getting all the scores
     * that are in the game of yatzy
     * Initialize two hashmap one for checking if score
     * is setted on the current row, One for the difference in number
     * score.
     * Initialize a possible score table which will use as temporary list
     * while giving the user a view of possible score, then the value will
     * be added to the main list scoreTables
     *
     * */
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

    /**
     * @return the size of scoreTables list
     * */
    public Integer sizeOfScores(){
        return scoreTables.size();
    }

    /**
     * @return size of possibleScoreTable
     * */
    public Integer sizeOfPossibleScores(){
        return possibleScoreTable.size();
    }

    /**
     * Clear the possibleScoreTable
     * */
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
     * Check if the scoreTables contains inactive
     * columns scores
     *
     * @return false if it does otherwise true
     * */
    public boolean containsInActive(){
        for(int i = 0; i < scoreTables.size(); i +=1){
            if(!scoreTables.get(i).active){
                return false;
            }else{
                return true;
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
                this.scoreTables.add(possibleScoreTable.get(i));
                this.clearScoreTablesScore();
            }
        }
        this.setBonus();
    }

    /**
     * Get the sum of numbers score in the game,
     * Sum all the rows which is a number score and return
     * the number score sum.
     *
     * @return the sum of all number score in the scoreTables
     * */
    public int getSumOfNumbers(){
        int sum = 0;
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < numbersRow.length; j++){
                if(scoreTables.get(i).row.equals(numbersRow[j])){
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }
        }
        return sum;
    }

    /**
     *
     * A method which iterate through scoreTables to find
     * if all score is setted on the numbers row by using a counter.
     * If so it returns true
     *
     * @return true or false based on the if the counter is 6 or higher
     * */
    public boolean checkIfItHalfScore() {
        int counter = 0;
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < numbersRow.length; j++){
                if(scoreTables.get(i).row.equals(numbersRow[j])){
                    if(!scoreTables.get(i).active) {
                        counter++;
                    }
                }
             }
        }
        return (counter >= 6);
    }

    /**
     * Return the bonus in the numbers row, and also check if it is
     * all the number score is setted if so it check if the bonus is avaible
     * for the current player or not if so it returns a value of 50 added with
     * extra bonus which is the numbers summed on the number score row is bigger the
     * three duplicates.
     *
     * @return bonus as integer which contain the bonus score
     * */
    public int checkBonus(){
        int bonus = 0;
        Iterator iterator = differenceInNumbersScore.entrySet().iterator();
       while (iterator.hasNext()) {
           Map.Entry map = (Map.Entry) iterator.next();
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

    /**
     * Return the total score of the rows for 1 pair and so on that arent
     * number scores
     *
     * @return sum of the rows for total
     * */
    public int getTotal(){
        int sum = 0;
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < combinationRow.length; j++){
                if(scoreTables.get(i).row.equals(combinationRow[j])){
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }

        }
        return sum;
    }

    /**
     * iterate through all both combination row and numbers row add all values
     * to sum .
     *
     * @return sum of all the score in all rows for the player
     * */
    public int getTotalOfAll(){
        int sum = 0;
        for (int i = 0; i < scoreTables.size(); i++) {
            for (int j = 0; j < combinationRow.length; j++){
                if(scoreTables.get(i).row.equals(combinationRow[j])){
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }
            for (int j = 0; j < numbersRow.length; j++){
                if(scoreTables.get(i).row.equals(numbersRow[j])){
                    if(!scoreTables.get(i).active) {
                        sum += scoreTables.get(i).score;
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Set bonus for each number row, check if the score contains more
     * then three duplicates or not, then calculate the difference.
     * The difference will be visualized for the user to give them info
     * about how far behind or in front the user are from getting the bonus
     *
     * */
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

    /**
     * Class for keeping important score values inside the
     * scoreTables list
     * */
    static class ScoreTable implements Serializable{
        int score;
        boolean active;
        String row;
    }



}
