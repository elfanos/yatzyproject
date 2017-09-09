package com.example.pandamove.yatzy;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.pandamove.yatzy.controllers.ListPossibleScores;
import com.example.pandamove.yatzy.controllers.OnButtonClickedListener;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.fragments.FragmentSliderPagerAdapter;
import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.fragments.ScoreViewAdapter;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreHandler;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity implements SensorEventListener, OnButtonClickedListener,
        ListPossibleScores {

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private HashMap<String,Integer> listOfPossibleScores = new HashMap<>();

    private ArrayList<Dice> dices = new ArrayList<>();


    private OnButtonClickedListener onButtonClickedListener;

    private ListPossibleScores listPossibleScores;

    private ArrayList<Player> players;

    private String[] scores = {
            "Header",
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
        players = new ArrayList<>();
        Player player = new Player("ralle", scores);
        player.setColumnPosition(0);
        players.add(player);
        Player player2 = new Player("ralle2",scores);
        player2.setColumnPosition(1);
        players.add(player2);
        Player player3 = new Player("ralle3", scores);
        player3.setColumnPosition(2);
        players.add(player3);
        Player player4 = new Player("ralle4",scores);
        player4.setColumnPosition(3);
        players.add(player4);

        fragments = new SparseArray<>();

        Activity activity = this;
        try{
            onButtonClickedListener = (OnButtonClickedListener) activity;
            listPossibleScores = (ListPossibleScores) activity;
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
                listPossibleScores,
                players
        );
        mPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);
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
        printMap(listOfPossibleScores);
        resetHashMap();
        ScoreHandler scoreHandler2 = new ScoreHandler(dices);
        listOfPossibleScores = scoreHandler2.possibleScores();
        Fragment scoreFragment = fragments.get(1);
        players.get(3).setCurrentPlayer(true);


        Player player = checkCurrentPlayer();
        if(player != null) {
            player.getScoreKeeper().setScores(listOfPossibleScores);
            if (scoreFragment instanceof ScoreFragment) {
                ((ScoreFragment) scoreFragment).getScoreListAdapater().viewCombination(player);
            }
        }


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
                    /*for(int i = 1; i < 5; i++) {

                        ((ScoreFragment) scoreFragment).
                                getScoreListAdapater().
                                addScore(scores[i], 5, 1);
                    }*/
                    listOfPossibleScores.put(scores[1],diceScore[1]);
                    listOfPossibleScores.put(scores[3],diceScore[2]);
                    listOfPossibleScores.put(scores[8],diceScore[3]);
                    listOfPossibleScores.put(scores[8],diceScore[3]);
                   // this.printMap(listOfPossibleScores);
                    ((ScoreFragment) scoreFragment).getScoreListAdapater().viewCombination(player);

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


}
