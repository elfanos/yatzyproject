package com.example.pandamove.yatzy.controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import com.example.pandamove.yatzy.activities.GameActivity;
import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.fragments.InGameFragment;
import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;
import com.example.pandamove.yatzy.score.ScoreHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rasmus on 26/09/17.
 */

public class CommunicationHandler implements GameActivityInterface{

    private static CommunicationHandler instance = null;

    /**
     * @param gameObjects set a gameObject
     * */
    public void setGameObjects(GameObjects gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * @return players an list of players
     * */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Set a list of players
     * @param players a list of players
     * */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    /**
     * @param highScore send in a arraylist which contain highscore
     * */
    public void setHighScore(ArrayList<TextView> highScore) {
        this.highScore = highScore;
    }

    /**
     * @return the playersIcon as a list
     * */
    public ArrayList<Integer> getPlayersIcon() {
        return playersIcon;
    }


    /**
     * Set playersicon
     *
     * @param playersIcon the players icon as list
     * */
    public void setPlayersIcon(ArrayList<Integer> playersIcon) {
        this.playersIcon = playersIcon;
    }


    /**
     * @return fragments as sparseArray
     * */
    public SparseArray<Fragment> getFragments() {
        return fragments;
    }

    /**
     * Set the current fragments
     * @param fragments as sparaseArray
     * */
    public void setFragments(SparseArray<Fragment> fragments) {
        this.fragments = fragments;
    }

    /**
     * Set list of possible scores
     * @param listOfPossibleScores as hashmap
     * */
    public void setListOfPossibleScores(HashMap<String, Integer> listOfPossibleScores) {
        this.listOfPossibleScores = listOfPossibleScores;
    }

    /**
     * Set leaderBoard
     *
     * @param leaderBoard as leaderBoard
     * */
    public void setLeaderBoard(LeaderBoard leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    /**
     * Set aniumation animation
     *
     * @param animation
     * */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }


    /**
     * Set the gameacitivty
     * @param gameActivity
     * */
    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    /**
     * @return the currenfragment that is viewed
     * */
    public int getCurrentFragment() {
        return (((ViewPager) gameActivity.findViewById(R.id.viewpager)).getCurrentItem());
    }


    /**
     * @param inGameView the ingameView
     * */
    public void setInGameView(View inGameView) {
        this.inGameView = inGameView;
    }

    /**
     * @param initializeDices boolean true or false
     * */
    public void setInitializeDices(boolean initializeDices) {
        this.initializeDices = initializeDices;
    }

    private boolean initializeDices;
    private View inGameView;
    private GameActivity gameActivity;
    private GameActivityInterface gameActivityController;
    private GameObjects gameObjects;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<TextView> highScore;
    private ArrayList<Integer> playersIcon;
    private SparseArray<Fragment> fragments;
    private HashMap<String,Integer> listOfPossibleScores;
    private LeaderBoard leaderBoard;
    private Animation animation;
    private int testish = 0;

    /**
     * Create a getInstance for the communication handler
     * so it never is instanced twice in the applicaiton
     * */
    public static CommunicationHandler getInstance(){
        if(instance==null){
            instance = new CommunicationHandler();
        }
        return instance;
    }

    /**
     * @return the current player in the arrayList
     * */
    public Player checkCurrentPlayer(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isCurrentPlayer()){
                return players.get(i);
            }
        }
        return null;
    }

    /**
     * Collect the dices from the opengl roll and add to the scorehandler
     * which return the row and values for visulasition in the list adapter
     *
     * @param dices
     * */
    @Override
    public void onThrowPostPossibleScores(SparseArray<Dice> dices){
            testish++;
            resetHashMap();
            ScoreHandler scoreHandler = new ScoreHandler(dices);
            listOfPossibleScores = scoreHandler.possibleScores();
            resetHashMap();
            ScoreHandler scoreHandler2 = new ScoreHandler(dices);
            listOfPossibleScores = scoreHandler2.possibleScores();
            Fragment scoreFragment = fragments.get(1);
            Player player = checkCurrentPlayer();
            System.out.println("Testish: " + testish);
            if (player != null) {
                player.getScoreKeeper().setScores(listOfPossibleScores);
                if (scoreFragment instanceof ScoreFragment) {
                    ((ScoreFragment) scoreFragment).getScoreListAdapater().viewCombination(player, animation);
                }
            }
    }

    /**
     * @return view of ingamefragment
     * */
    public View inGameFragmentView(){
        Fragment inGameFragment = fragments.get(0);
        if(inGameFragment instanceof InGameFragment){
            return inGameFragment.getView();
        }
        return null;
    }
    /**
     * @return  the current player
     * */
    public Player getCurrentPlayer(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isCurrentPlayer()){
                return players.get(i);
            }
        }
        return null;
    }
    /**
     * set new round
     * */
    public void setNewRound(Player player){
        //Need next player refresh score setted
        // start next round end?
        gameObjects.refreshScoreIsSetted();
        gameObjects.initializeNextRound();
        players.get(
                gameObjects.getNextPlayer(player.getColumnPosition())
        ).setCurrentPlayer(true);
        this.updateView(this.inGameFragmentView());
        this.updateHighScore(this.inGameFragmentView());
    }
    @Override
    public void roundsEnd(Player player, Activity activity){
        testish = 0;
        player.setCurrentPlayer(false);
        player.setScoreIsSet(true);
        System.out.println("wats let round? " + gameObjects.getRound());
        //If more players
        if (gameObjects.checkIfLastPlayer()) {
            if(gameObjects.getRound() < 14) {
                this.setNewRound(player);
                this.generateNewDices();
            } else
                gameActivity.endGame();

        }
        //If one player
        else if (gameObjects.getNextPlayer(player.getColumnPosition()) == 0) {
            if(gameObjects.getRound() < 14) {
                this.setNewRound(player);
                this.generateNewDices();
            } else
                gameActivity.endGame();
        } else {
            players.get(
                    gameObjects.getNextPlayer(player.getColumnPosition())
            ).setCurrentPlayer(true);
            this.generateNewDices();
            this.updateView(this.inGameFragmentView());
            this.updateHighScore(this.inGameFragmentView());
        }

    }

    /**
     * Update the view bsed on current player
     *
     * @param v the view
     * */
    @Override
    public void updateView(View v){
        //this.initializePlayerIcon();
        Player player = this.getCurrentPlayer();
        if(player == null){
            player = players.get(0);
        }
        switch (player.getColumnPosition()){
            case 0:
                this.setViewBasedOnPlayer(player, v);
                break;
            case 1:
                this.setViewBasedOnPlayer(player, v);
                break;
            case 2:
                this.setViewBasedOnPlayer(player, v);
                break;
            case 3:
                this.setViewBasedOnPlayer(player, v);
                break;
        }
    }
    /**
     * Set the view based on a player
     * @param player
     * @param v
     * */
    private void setViewBasedOnPlayer(Player player, View v){
        (v.findViewById(R.id.currentPlayer)).
                setBackgroundResource(
                        playersIcon.get(player.getColumnPosition())
                );

        ((TextView)(v.findViewById(R.id.currentPlayer))).
                setText(String.format("%s",player.getName()));
        ((TextView)(v.findViewById(R.id.currentplayerscore))).
                setText(String.format("%s",player.getScoreKeeper().getTotalOfAll()));

        (v.findViewById(R.id.currentplayerscore)).setVisibility(View.VISIBLE);
        ((TextView)v.findViewById(R.id.roundText)).
                setText(String.format("%s", gameObjects.getRound()));

    }
    /**
     * Set view on high score
     *
     * @param player
     * @param position'
     * @param v
     * */
    private void setViewOnHighScore(Player player, int position, View v){
        switch (position){
            case 0:
                (v.findViewById(R.id.playerLeader)).setBackgroundResource(
                        playersIcon.get(player.getColumnPosition())
                );
                ((TextView) v.findViewById(R.id.leadingPlayerScore)).setText(String.format("%s",
                        player.getScoreKeeper().getTotalOfAll()));
                (v.findViewById(R.id.leadingPlayerScore)).setVisibility(View.VISIBLE);
                break;
            case 1:
                (v.findViewById(R.id.playerSecond)).setBackgroundResource(
                        playersIcon.get(player.getColumnPosition())
                );
                ((TextView) v.findViewById(R.id.seondPlayerScore)).setText(String.format("%s",
                        player.getScoreKeeper().getTotalOfAll()));
                (v.findViewById(R.id.seondPlayerScore)).setVisibility(View.VISIBLE);
                break;
            case 2:
                (v.findViewById(R.id.playerThird)).setBackgroundResource(
                        playersIcon.get(player.getColumnPosition())
                );
                ((TextView) v.findViewById(R.id.thirdPlayerScore)).setText(String.format("%s",
                        player.getScoreKeeper().getTotalOfAll()));
                (v.findViewById(R.id.thirdPlayerScore)).setVisibility(View.VISIBLE);
                break;
            case 3:
                (v.findViewById(R.id.playerForth)).setBackgroundResource(
                        playersIcon.get(player.getColumnPosition())
                );
                ((TextView) v.findViewById(R.id.fourthPlayerScore)).setText(String.format("%s",
                        player.getScoreKeeper().getTotalOfAll()));
                (v.findViewById(R.id.fourthPlayerScore)).setVisibility(View.VISIBLE);
                break;
        }
    }
    /**
     * Update highscore
     *
     * @param v
     *
     * */
    @Override
    public void updateHighScore(View v){
        ArrayList<Player> highScorePlayers = null;
        highScorePlayers = new ArrayList<>();
        highScorePlayers.addAll(players);
        highScorePlayers = leaderBoard.checkTopList(highScorePlayers);
        for(int i = 0; i < highScorePlayers.size(); i++){
            this.setViewOnHighScore(highScorePlayers.get(i), i, v);
        }
    }

    /**
     * set the player view
     *
     * @param v
     * */
    @Override
    public void setPlayerView(View v){
        Player player = this.getCurrentPlayer();
        ((TextView)v.findViewById(R.id.currentPlayer)).
                setText(String.format("%s", player.getName()));
    }

    /**
     * set the score view
     *
     * @param v
     * */
    @Override
    public void setScoreView(View v){
        Player player = this.getCurrentPlayer();
        ((TextView)v.findViewById(R.id.currentplayerscore)).
                setText(String.format("%s", player.getRound()));
    }

    /**
     *
     * set throws
     *
     * @param v
     * */
    @Override
    public void setThrows(View v){
        Player player = this.getCurrentPlayer();
        player.increseNumberOfThrows();
        ((TextView)v.findViewById(R.id.thrownumber)).setText(String.format("%s",
                player.getNumberOfThrows()));
    }
    /**
     * refresh hashmap
     * */
    public void resetHashMap(){
        listOfPossibleScores = null;
        listOfPossibleScores = new HashMap<>();
    }

    /**
     * generate new dices to a new throw on round end
     *
     * */
    private void generateNewDices(){
        ((ViewPager) gameActivity.findViewById(R.id.viewpager)).setCurrentItem(0,true);
        ((InGameFragment)this.getFragments().get(0)).resetSelectedDices();
        ((InGameFragment)this.getFragments().get(0)).beginARollRound(inGameView);
        this.setInitializeDices(false);
    }
    /**
     * shake activity
     * */
    public void shakeActivity(){
        if(this.getCurrentFragment() == 0){
            ((InGameFragment)this.getFragments().get(0)).beginARollRound(inGameView);
        }
    }

    /**
     * Change viewpager to ingameview
     * */
    public void goToInGameView(){
        ((ViewPager) gameActivity.findViewById(R.id.viewpager)).setCurrentItem(0,true);
    }

}