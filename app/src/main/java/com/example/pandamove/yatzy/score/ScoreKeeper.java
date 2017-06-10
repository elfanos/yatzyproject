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
    private ArrayList<String> scoreOnColumn;
    private HashMap<String, Integer> scoresIncColumnNRow;
    private ArrayList<Boolean> updatedScores;
    private UpdatedRow updatedRow;
    public ScoreKeeper(String[] scores){
        scoresIncColumnNRow = new HashMap<>();
        scoreOnColumn = new ArrayList<>();
        updatedScores = new ArrayList<>();
        updatedRow = new UpdatedRow();
        for(int i = 0; i < scores.length; i++){
           // System.out.println("Scores: " + scores[i]);
            //this.setScore(scores[i],0);
            updatedScores.add(false);
        }
        this.scores = scores;
    }
    public int getScore() {
        Iterator iterator = scoresIncColumnNRow.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry map = (Map.Entry) iterator.next();

            //System.out.println(map);
            return this.checkRow(map);
        }
       // System.out.println("FINNISHED:       .::");
        return 0;
    }

    public int checkRow(Map.Entry map){
        if(!updatedRow.isOne()){
            System.out.println("isone");
            updatedRow.setOne(true);
            return 0;
        }else if(!updatedRow.isTwo()){
            System.out.println("two");
            updatedRow.setTwo(true);
            return 12;
        }
        else if(!updatedRow.isThree()){
            System.out.println("isthree");
            updatedRow.setThree(true);
            return 3;
        }
        return 13;
    }

    public void setScore(String row,int score) {
        System.out.println("haj" + row);
       // System.out.println(row+" : "+score);
        scoresIncColumnNRow.put(row, score);
    }
    public void changeValueOnKey(String row, int score){
        Iterator iterator = scoresIncColumnNRow.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry map = (Map.Entry) iterator.next();
           // System.out.println(map.getKey());
            if(map.getKey() == row){
              //  System.out.println("yalla");
               map.setValue(score);
            }
            //map.setValue(score)
            //System.out.println((Integer) map.getValue());
        }
        Iterator iterator1 = scoresIncColumnNRow.entrySet().iterator();
        while (iterator1.hasNext()){
            Map.Entry map = (Map.Entry) iterator1.next();
            //System.out.println("le scores: " + (Integer) map.getValue());
            //System.out.println(map);
        }
    }

    public int getTotalScore(){
        return 0;
    }
    public int getNumberScore(){
        return 0;
    }

    public class UpdatedRow{
        private boolean one;
        private boolean two;
        private boolean three;
        private boolean four;
        private boolean five;
        private boolean six;
        private boolean sumHalf;
        private boolean twoPair;
        private boolean threeKind;
        private boolean fourKind;
        private boolean sStragiht;
        private boolean lStraigt;
        private boolean chance;
        private boolean yatzy;
        private boolean fullHouse;

        public UpdatedRow(){
            one = false;
            two = false;
            three = false;
            four = false;
            five = false;
            six = false;
            sumHalf = false;
            twoPair = false;
            threeKind = false;
            fourKind = false;
            sStragiht = false;
            lStraigt = false;
            chance = false;
            yatzy = false;
            fullHouse = false;
        }

        public void setOne(boolean one) {
            this.one = one;
        }

        public void setTwo(boolean two) {
            this.two = two;
        }

        public void setThree(boolean three) {
            this.three = three;
        }

        public void setFour(boolean four) {
            this.four = four;
        }

        public void setFive(boolean five) {
            this.five = five;
        }

        public void setSix(boolean six) {
            this.six = six;
        }

        public void setSumHalf(boolean sumHalf) {
            this.sumHalf = sumHalf;
        }

        public void setTwoPair(boolean twoPair) {
            this.twoPair = twoPair;
        }

        public void setThreeKind(boolean threeKind) {
            this.threeKind = threeKind;
        }

        public void setFourKind(boolean fourKind) {
            this.fourKind = fourKind;
        }

        public void setsStragiht(boolean sStragiht) {
            this.sStragiht = sStragiht;
        }

        public void setlStraigt(boolean lStraigt) {
            this.lStraigt = lStraigt;
        }

        public void setChance(boolean chance) {
            this.chance = chance;
        }

        public void setYatzy(boolean yatzy) {
            this.yatzy = yatzy;
        }

        public void setFullHouse(boolean fullHouse) {
            this.fullHouse = fullHouse;
        }

        public boolean isOne() {
            return one;
        }

        public boolean isTwo() {
            return two;
        }

        public boolean isThree() {
            return three;
        }

        public boolean isFour() {
            return four;
        }

        public boolean isFive() {
            return five;
        }

        public boolean isSix() {
            return six;
        }

        public boolean isSumHalf() {
            return sumHalf;
        }

        public boolean isTwoPair() {
            return twoPair;
        }

        public boolean isThreeKind() {
            return threeKind;
        }

        public boolean isFourKind() {
            return fourKind;
        }

        public boolean issStragiht() {
            return sStragiht;
        }

        public boolean islStraigt() {
            return lStraigt;
        }

        public boolean isChance() {
            return chance;
        }

        public boolean isYatzy() {
            return yatzy;
        }

        public boolean isFullHouse() {
            return fullHouse;
        }

    }


}
