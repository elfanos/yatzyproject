package com.example.pandamove.yatzy.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.pandamove.yatzy.EndGameActivity;
import com.example.pandamove.yatzy.GameActivity;
import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.fragments.InGameFragment;
import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.player.GameObjects;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;
import com.example.pandamove.yatzy.score.ScoreHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rasmus on 26/09/17.
 */

public class CommunicationHandler implements GameActivityInterface, OnButtonClickedListener{

    private static CommunicationHandler instance = null;

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(GameObjects gameObjects) {
        this.gameObjects = gameObjects;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<TextView> getHighScore() {
        return highScore;
    }

    public void setHighScore(ArrayList<TextView> highScore) {
        this.highScore = highScore;
    }

    public ArrayList<Integer> getPlayersIcon() {
        return playersIcon;
    }

    public void setPlayersIcon(ArrayList<Integer> playersIcon) {
        this.playersIcon = playersIcon;
    }

    public SparseArray<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(SparseArray<Fragment> fragments) {
        this.fragments = fragments;
    }

    public HashMap<String, Integer> getListOfPossibleScores() {
        return listOfPossibleScores;
    }

    public void setListOfPossibleScores(HashMap<String, Integer> listOfPossibleScores) {
        this.listOfPossibleScores = listOfPossibleScores;
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(LeaderBoard leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public int getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(int currentFragment) {
        this.currentFragment = currentFragment;
    }

    private GameActivity gameActivity;
    private OnButtonClickedListener buttonClickedListener;
    private GameActivityInterface gameActivityController;
    private GameObjects gameObjects;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<TextView> highScore;
    private ArrayList<Integer> playersIcon;
    private SparseArray<Fragment> fragments;
    private HashMap<String,Integer> listOfPossibleScores;
    private LeaderBoard leaderBoard;
    private Animation animation;
    private int currentFragment = 0;

    public static CommunicationHandler getInstance(){
        if(instance==null){
            instance = new CommunicationHandler();
        }
        return instance;
    }

    public OnButtonClickedListener getButtonClickedListener() {
        return buttonClickedListener;
    }

    public GameActivityInterface getGameActivityController() {
        return gameActivityController;
    }

    public void onEndGame(Activity activity){
        Intent jaman = new Intent(gameActivity, EndGameActivity.class);
        gameActivity.startActivity(jaman);
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
    @Override
    public void onThrowPostPossibleScores(SparseArray<Dice> dices){
        resetHashMap();
        ScoreHandler scoreHandler = new ScoreHandler(dices);
        listOfPossibleScores = scoreHandler.possibleScores();
        //printMap(listOfPossibleScores);
        resetHashMap();
        ScoreHandler scoreHandler2 = new ScoreHandler(dices);
        listOfPossibleScores = scoreHandler2.possibleScores();
        Fragment scoreFragment = fragments.get(1);
        Player player = checkCurrentPlayer();
        if(player != null) {
            player.getScoreKeeper().setScores(listOfPossibleScores);
            if (scoreFragment instanceof ScoreFragment) {
                ((ScoreFragment) scoreFragment).getScoreListAdapater().viewCombination(player, animation);
            }
        }
    }
    public View inGameFragmentView(){
        Fragment inGameFragment = fragments.get(0);
        if(inGameFragment instanceof InGameFragment){
            return inGameFragment.getView();
        }
        return null;
    }
    public Player getCurrentPlayer(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isCurrentPlayer()){
                return players.get(i);
            }
        }
        return null;
    }
    public void setNewRound(Player player){
        //Need next player refresh score setted
        // start next round end?
        gameObjects.refreshScoreIsSetted();
        gameObjects.initializeNextRound();
//        rounds.setText(String.format("%s",gameObjects.getRound()));
        players.get(
                gameObjects.getNextPlayer(player.getColumnPosition())
        ).setCurrentPlayer(true);
        this.updateView(this.inGameFragmentView());
        this.updateHighScore(this.inGameFragmentView());
    }
    @Override
    public void roundsEnd(Player player, Activity activity){
        if(gameObjects.getRound() < 14) {
            player.setCurrentPlayer(false);
            player.setScoreIsSet(true);
            System.out.println("wats let round? " + gameObjects.getRound());

            if (gameObjects.checkIfLastPlayer()) {
                this.setNewRound(player);
            } else if (gameObjects.getNextPlayer(player.getColumnPosition()) == 0) {
                this.setNewRound(player);
            } else {
                players.get(
                        gameObjects.getNextPlayer(player.getColumnPosition())
                ).setCurrentPlayer(true);
                this.updateView(this.inGameFragmentView());
                this.updateHighScore(this.inGameFragmentView());
            }
        }else{
            System.out.println("jaman lets find it??");
            //this.onEndGame(activity);
            gameActivity.endGame();
        }
    }
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
    public void setViewBasedOnPlayer(Player player, View v){
        (v.findViewById(R.id.currentPlayer)).
                setBackgroundResource(
                        playersIcon.get(player.getColumnPosition())
                );
        ((TextView)(v.findViewById(R.id.currentPlayer))).
                setText(String.format("%s",player.getName()));
        ((TextView)(v.findViewById(R.id.currentplayerscore))).
                setText(String.format("%s",player.getScoreKeeper().getTotalOfAll()));
        ((TextView)v.findViewById(R.id.roundText)).
                setText(String.format("%s", gameObjects.getRound()));

    }
    public void setViewOnHighScore(Player player, int position, View v){
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
    @Override
    public void updateHighScore(View v){
        ArrayList<Player> highScorePlayers = new ArrayList<>();
        highScorePlayers = players;
        highScorePlayers = leaderBoard.checkTopList(highScorePlayers);
        for(int i = 0; i < highScorePlayers.size(); i++){
            this.setViewOnHighScore(highScorePlayers.get(i), i, v);
        }
    }
    @Override
    public void setScoreForPlayer(View v){
        Player player = this.getCurrentPlayer();
        ((TextView)v.findViewById(R.id.currentplayerscore)).setText(String.format("%s",
                player.getScoreKeeper().getCurrentScore()));
    }
    @Override
    public void incrementRoundsForPlayer(View v){
        Player player = this.getCurrentPlayer();
        player.incrementRound();
        // ((TextView)v.findViewById(R.id.))
    }
    @Override
    public void setPlayerView(View v){
        Player player = this.getCurrentPlayer();
        ((TextView)v.findViewById(R.id.currentPlayer)).
                setText(String.format("%s", player.getName()));
    }
    @Override
    public void setScoreView(View v){
        Player player = this.getCurrentPlayer();
        ((TextView)v.findViewById(R.id.currentplayerscore)).
                setText(String.format("%s", player.getRound()));
    }
    @Override
    public boolean getIfRoundIsOver(){
        Player player = this.getCurrentPlayer();
        if(player.getNumberOfThrows() > 2){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void setThrows(View v){
        Player player = this.getCurrentPlayer();
        // if(player == null){
        //    player = players.get(0);
        // }
        player.increseNumberOfThrows();
        ((TextView)v.findViewById(R.id.thrownumber)).setText(String.format("%s",
                player.getNumberOfThrows()));
    }
    public void resetHashMap(){
        listOfPossibleScores = null;
        listOfPossibleScores = new HashMap<>();
    }
    /**
     * params
     * which yatzyscore
     * score
     * player
     * */
    @Override
    public void onButtonClicked(View v){
        System.out.println("le id: " + v.getId());
        switch (v.getId()){
            case R.id.buttonScore:
                //System.out.println("pressed test button");
                Fragment scoreFragment =  fragments.get(1);
                Player player = players.get(1);
                if(scoreFragment instanceof ScoreFragment){


                }
                break;
            default:
                break;
        }

    }

}