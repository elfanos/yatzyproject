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

import com.example.pandamove.yatzy.controllers.OnButtonClickedListener;
import com.example.pandamove.yatzy.fragments.FragmentSliderPagerAdapter;
import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.fragments.ScoreViewAdapter;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends AppCompatActivity implements SensorEventListener, OnButtonClickedListener {

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private HashMap<String,Integer> listOfPossibleScores = new HashMap<>();

    private OnButtonClickedListener onButtonClickedListener;
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

        fragments = new SparseArray<>();

        Activity activity = this;
        try{
            onButtonClickedListener = (OnButtonClickedListener) activity;
        } catch (ClassCastException e){
            System.out.println("Exception lal" + e);
        }

        mPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new FragmentSliderPagerAdapter(getSupportFragmentManager(), onButtonClickedListener, fragments);
        mPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);
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
            case R.id.testButton:
                //System.out.println("pressed test button");
                Fragment scoreFragment =  fragments.get(1);
                if(scoreFragment instanceof ScoreFragment){;
                    /*for(int i = 1; i < 5; i++) {

                        ((ScoreFragment) scoreFragment).
                                getScoreListAdapater().
                                addScore(scores[i], 5, 1);
                    }*/
                    listOfPossibleScores.put(scores[1],diceScore[1]);
                    listOfPossibleScores.put(scores[3],diceScore[2]);
                    listOfPossibleScores.put(scores[8],diceScore[3]);
                    ((ScoreFragment) scoreFragment).
                            getScoreListAdapater().viewCombination(3,
                                    listOfPossibleScores
                    );

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

}
