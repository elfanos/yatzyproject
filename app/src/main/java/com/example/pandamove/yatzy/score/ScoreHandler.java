package com.example.pandamove.yatzy.score;
import android.util.SparseArray;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.fragments.InGameFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Class which handles the score, using logic
 * to find the right score based on the rules of yatzy
 *
 * @author Rasmus Dahlkvist
 */
public class ScoreHandler {

    private SparseArray<Dice> dices;
    private HashMap<String,Integer> allScores;
    private ArrayList<Integer> pairs;
    private SparseArray<Dice> pairFourOfKind;
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


    /**
     * Set the values collected from the rolling phase
     * and add it to the dices array in ScoreHandler
     * Also initialize the scoring process
     *
     * @param dices is array of all scores retrived from
     *              the rolling phase
     * */
    public ScoreHandler(SparseArray<Dice> dices) {
        this.dices = dices;
        this.pairFourOfKind = dices;
        allScores = new HashMap<>();
        pairs = new ArrayList<>();
        this.checkScores();
    }
    /**
     * Starts all anonymous methods inside
     * the scorehandler to calculate right score
     * */
    private void checkScores(){
        this.checkForNumbers();
        this.checkForHighestScore();
        this.checkForScoreThreeOfKind();
        this.checkForLowStraight();
        this.checkForBigStraight();
        this.checkForFullHouse();
        this.checkForChance();
        this.checkForYatzy();
        this.addZeroOnOtherScores();
    }
    /**
     * @return possibleScores a hashmap that
     *          contains all possible scores
     * */
    public HashMap<String,Integer> possibleScores(){
        return allScores;
    }


    /**
     * Initialize zero value on every score to avoid
     * null pointer when adding the score to the ScoreAdapter
     * */
    private void addZeroOnOtherScores(){
        for(int i = 0; i < scores.length; i++){
            if(!allScores.containsKey(scores[i])){
                allScores.put(scores[i], 0);
            }
        }
    }
    /**
     * Check all score for each number, iterate through the
     * array and check dices for number value between, 1,2,3,4,5,6
     * and add if there exist duplicates or more of the same number
     *
     * */
    private void checkForNumbers(){
        int one = 0, two = 0, three = 0, four= 0, five = 0, six = 0;
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
    /**
     * Check for the highest pair inb the pairCollector
     * list
     *
     * @param pairCollector contains pairs in a list
     * */
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

    /**
     * Check for pair inside the pairFourOfKind, check first element
     * then combination for it until the array has iterated through. Remove
     * element which have been combined
     *
     * @param pairCollector container for the pairs
     * @param position starting position of iteration
     * @param pairFourOfKind arraylist of integers that is the scores
     * @param gotOnePair boolean to check if one pair is already combined
     * */
    private void checkForPair(int position, List<Integer> pairCollector, ArrayList<Integer> pairFourOfKind,
                              boolean gotOnePair){
        if(pairFourOfKind.size() > 1) {
            for (int i = 0; i < pairFourOfKind.size(); i++) {
                if(pairFourOfKind.get(i) != null && pairFourOfKind.get(position) != null) {
                    if(position != i) {
                        if (pairFourOfKind.get(position).equals(pairFourOfKind.get(i))) {
                            int value = (pairFourOfKind.get(position)+ (pairFourOfKind.get(i)));
                            pairCollector.add(value);
                            pairs.add(value);
                            if (!gotOnePair) {
                                this.setOnePair(position,i, false, pairFourOfKind, pairCollector);
                                break;
                            } else {;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(pairFourOfKind.size() > 1) {
            this.setOnePair(position,position, true, pairFourOfKind,pairCollector);
        }
    }

    /**
     * @return a arrayList of integer gathered
     *          by score from the dices in the sparseArrayList
     * */
    private ArrayList<Integer> initializeArrayList(){
        ArrayList<Integer> scoreList = new ArrayList<>();
        for (int i = 1;  i < dices.size(); i++){
            scoreList.add(dices.get(i).getScore());
        }
        return scoreList;
    }

    /**
     * Remove element inside the arrayList of scores, if there
     * is a combination two elements is removed the element
     * with the combination. When its done the checkForPairs is
     * called again
     *
     * @param first index position of the score in the arrayList
     * @param last index position of the score in the arrayList
     * @param pairFourOfKind the arrayList of integer containing
     *                       the scores
     * @param pairCollector the collector of pairs
     * */
    private void setOnePair(int first, int last, boolean noCombination,
                            ArrayList<Integer> pairFourOfKind, List<Integer> pairCollector){
        pairFourOfKind.remove(first);
        if(!noCombination)
            pairFourOfKind.remove(last);
        this.checkForPair(0,pairCollector, pairFourOfKind, true);
    }

    /**
     * Method to check if there is more then on pair inside the list
     * then check if it is 4 of a kind or 2 pair
     *
     * @param pairCollector container for the pairs
     * */
    private void checkForPairs(List<Integer> pairCollector){
        ArrayList<Integer> scoreList = this.initializeArrayList();
        //Sort the list before iteration through it
        //highest value first
        Collections.sort(scoreList, Collections.reverseOrder());
        this.checkForPair(0,pairCollector,scoreList, false);
        int highestPair = this.checkTheHighestPair(pairCollector);
        allScores.put(scores[7], highestPair);
        if(pairCollector.size() > 1) {
            this.checkForTwoPairOrFourOfKind(pairCollector);
        }
    }

    /**
     *
     * pair or pairs
     * */
    private void checkForHighestScore(){
        List<Integer> scoreCollector = new ArrayList<>();
        this.checkForPairs(scoreCollector);
    }

    /**
     * Method that will find if there is a duplicate of the
     * two pairs inside the pairCollector if so it is
     * 4 of a kind.
     *
     * @param pairCollector container for all the pairs
     * */
    private void checkForPairOrThreeOfAkind(List<Integer> pairCollector){
        int value = 0;
        for(int i = 0; i < pairCollector.size(); i++){
            if(pairCollector.size() > 1){
                if((i+1) < pairCollector.size()){
                    if(!pairCollector.get(i).equals(pairCollector.get(i+1))){

                        value += pairCollector.get(i);
                        value += pairCollector.get(i+1);
                    }else{
                        value = 0;
                    }
                }
            }
        }
        if(value != 0) {
            allScores.put(scores[8], value);
        }
    }
    /**
     * check for two pair or four of kind by iterating through the pairColletor
     * list and check if contains duplicate or not then add the 2 pairs
     * or four of a kind insidet the allscores hashmap.
     *
     * @param pairCollector
     * */
    private void checkForTwoPairOrFourOfKind(List<Integer> pairCollector){
        System.out.println("ehma ja=?? ");
        int value = 0;
        int fourOfKind = 0;
        System.out.println("pairColccetor? " + pairCollector.get(0));
        System.out.println("pairColccetor? " + pairCollector.get(1));
        for(int i = 0; i < pairCollector.size(); i++){
            if(pairCollector.size() > 1) {
                if((i+1) < pairCollector.size()) {
                    System.out.println("ehma ja=?? ");
                    if (!pairCollector.get(i).equals(pairCollector.get(i + 1))) {
                        System.out.println("ehma ja=?? 2pair????");
                        value += pairCollector.get(i);
                        value += pairCollector.get(i + 1);
                    } else {
                        System.out.println("ehma ja=?? four a kinddded ");
                        fourOfKind = pairCollector.get(i) + pairCollector.get(i + 1);
                    }
                }
            }else{
                this.checkForPairOrThreeOfAkind(pairCollector);
            }
        }
        if(value != 0) {
            allScores.put(scores[8], value);
        }else if(fourOfKind != 0){
            allScores.put(scores[10], fourOfKind);
        }
    }

    /**
     * Method which check for three of a kind by using
     * three pivot element to iterate through the arraylist
     * checking each nodes neighbour (pivot element).
     * */
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
    /**
     * Checking the list neighbour if they are the same value
     * */
    private void checkForThreeOfAKind(int position, List<Integer> scoreCollector){
        for(int i = position; i < dices.size(); i++){
            if((i+1) < dices.size()) {
                if (dices.get(position).getScore() == dices.get(i + 1).getScore()){
                    this.checkCombination(position, (i+1), scoreCollector);
                }
            }
        }
    }
    /**
     * Check other the arraylist neighbours add the three combination if the arraylist
     * contains three of the same.
     * */
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
    /**
     * Using boolean list to check for straight score
     * Checking all score one by one in the array of dices.
     * if the matrix allchecked contains only true values
     * it is a low straight
     * */
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
    /**
     * Same principle as lowstraight checking the score of
     * each dice in the list then add true if they contain the right
     * score check if all boolean in the matrix allchecked are true
     * if so it is a straight
     * */
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

    /**
     *
     * Checking for fullhouse by checking if it exist a pair and
     * a three of a kind in the scorelisthandle if so it first
     * check if there are duplicates if not it is a three of a kind
     * */
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
    /**
     * Sums all the score on the current dices
     * */
    private void checkForChance(){
        int value = 0;
        for(int i = 1; i < dices.size(); i++){
            value += dices.get(i).getScore();
        }

        allScores.put(scores[14], value);

    }
    /**
     * Check if all dices in the list are the same
     * if so it sums the value of them and then add it with 50.
     * */
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
            allScores.put(scores[15], (50+value));
        }
    }

}
