package com.example.pandamove.yatzy.score;

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
    public ScoreKeeper(String[] scores){
        checkIfScoresIsSetted = new HashMap<>();
        for(int i = 0; i < scores.length; i++){
            checkIfScoresIsSetted.put(scores[i],false);
        }
        this.scores = scores;
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
    public boolean checkIfColumnGotScore(String row){
        Iterator iterator = checkIfScoresIsSetted.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry map = (Map.Entry) iterator.next();
            if(map.getKey() == row){
                return (Boolean) map.getValue();
            }
        }
        return false;
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



}
