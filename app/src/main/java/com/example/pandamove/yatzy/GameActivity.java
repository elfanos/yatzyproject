package com.example.pandamove.yatzy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.pandamove.yatzy.controllers.GameActivityInterface;
import com.example.pandamove.yatzy.controllers.OnButtonClickedListener;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.fragments.FragmentSliderPagerAdapter;
import com.example.pandamove.yatzy.fragments.InGameFragment;
import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.player.GameObjects;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;
import com.example.pandamove.yatzy.score.ScoreHandler;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameActivity extends AppCompatActivity implements SensorEventListener, OnButtonClickedListener,
        GameActivityInterface {

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private HashMap<String,Integer> listOfPossibleScores = new HashMap<>();

    private ArrayList<Dice> dices = new ArrayList<>();


    private OnButtonClickedListener onButtonClickedListener;

    private GameActivityInterface gameActivityInterface;

    private ArrayList<Player> players = new ArrayList<>();

    private GameObjects gameObjects;

    private ArrayList<Integer> playersIcon;

    private int ralle = 0;
    private static final ArrayList<Animation> animations = new ArrayList<>();

    private ArrayList<TextView> highScore = new ArrayList<>();

    private static Animation scoreAnimation;

    private LeaderBoard leaderBoard = new LeaderBoard();
    Animation animation;

    private String[] scores = {
            "Header",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Sum",
            "Bonus",
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
    private int[] diceScore = {
            12,
            15,
            14,
            11,
            6,
            20
    };

    /*Keep track on fragments*/
    private SparseArray<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tab);
        Intent numbOfPlayers = getIntent();
        int numberOfPlayers = numbOfPlayers.getIntExtra("players",0);
        System.out.println("other activityaaaaaaaaaaaa " + numberOfPlayers);
        this.initializePlayers(numberOfPlayers);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_alpha);


        this.initializePlayerIcon();

        gameObjects = new GameObjects(players);

        fragments = new SparseArray<>();

        Activity activity = this;
        try{
            onButtonClickedListener = (OnButtonClickedListener) activity;
            gameActivityInterface = (GameActivityInterface) activity;
        } catch (ClassCastException e){
            System.out.println("Exception lal" + e);
        }

        mPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new FragmentSliderPagerAdapter(
                getSupportFragmentManager(),
                onButtonClickedListener,
                fragments,
                dices,
                listOfPossibleScores,
                gameActivityInterface,
                players
        );
        mPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);
    }

    public void initializePlayers(int numberOfPlayers){
        switch (numberOfPlayers){
            case 1:
                Player player = new Player("Yellow", scores);
                player.setColumnPosition(0);
                player.setNumberOfThrows(0);
                player.setCurrentPlayer(true);
                players.add(player);
                break;
            case 2:
                this.addTwoPlayers();
                break;
            case 3:
                this.addThreePlayers();
                break;
            case 4:
                this.addFourPlayers();
                break;
        }
    }
    public void initializePlayerIcon(){
        playersIcon = new ArrayList<>();
        playersIcon.add(R.drawable.playericon_one);
        playersIcon.add(R.drawable.playericon_two);
        playersIcon.add(R.drawable.playericon_three);
        playersIcon.add(R.drawable.playericon_four);

        highScore.add(((TextView) findViewById(R.id.playerLeader)));
        highScore.add(((TextView) findViewById(R.id.playerSecond)));
        highScore.add(((TextView) findViewById(R.id.playerThird)));
        highScore.add(((TextView) findViewById(R.id.playerForth)));
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
        ((TextView)findViewById(R.id.rounds)).setText(String.format("%s",gameObjects.getRound()));
        players.get(
                gameObjects.getNextPlayer(player.getColumnPosition())
        ).setCurrentPlayer(true);
        this.updateView(this.inGameFragmentView());
        this.updateHighScore(this.inGameFragmentView());
    }
    @Override
    public void roundsEnd(Player player){
        ralle++;
        player.setCurrentPlayer(false);
        player.setScoreIsSet(true);
        if(gameObjects.checkIfLastPlayer()){
            this.setNewRound(player);
        }else if(gameObjects.getNextPlayer(player.getColumnPosition()) == 0){
            this.setNewRound(player);
        }else{
            players.get(
                    gameObjects.getNextPlayer(player.getColumnPosition())
            ).setCurrentPlayer(true);
            this.updateView(this.inGameFragmentView());
            this.updateHighScore(this.inGameFragmentView());
        }
    }
    @Override
    public void updateView(View v){
        this.initializePlayerIcon();
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
                if(scoreFragment instanceof ScoreFragment){;
                    listOfPossibleScores.put(scores[1],diceScore[1]);
                    listOfPossibleScores.put(scores[3],diceScore[2]);
                    listOfPossibleScores.put(scores[8],diceScore[3]);
                    listOfPossibleScores.put(scores[8],diceScore[3]);
                   // this.printMap(listOfPossibleScores);
                    ((ScoreFragment) scoreFragment).getScoreListAdapater().viewCombination(player, animation);

                }
                break;
            default:
                break;
        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        }

    }
    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public void addTwoPlayers(){
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        player.setCurrentPlayer(true);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
    }
    public void addThreePlayers(){
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        player.setCurrentPlayer(true);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
        Player player3 = new Player("Blue", scores);
        player3.setColumnPosition(2);
        player3.setNumberOfThrows(0);
        players.add(player3);
    }
    public void addFourPlayers(){
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        player.setCurrentPlayer(true);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
        Player player3 = new Player("Blue", scores);
        player3.setColumnPosition(2);
        player3.setNumberOfThrows(0);
        players.add(player3);
        Player player4 = new Player("Black",scores);
        player4.setColumnPosition(3);
        player4.setNumberOfThrows(0);
        players.add(player4);
    }



}
