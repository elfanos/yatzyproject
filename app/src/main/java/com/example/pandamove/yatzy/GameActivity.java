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
import java.util.List;

public class GameActivity extends AppCompatActivity implements SensorEventListener, OnButtonClickedListener {

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private OnButtonClickedListener onButtonClickedListener;

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

    @Override
    public void onButtonClicked(View v){
        System.out.println("le id: " + v.getId());
        switch (v.getId()){
            case R.id.testButton:
                System.out.println("pressed test button");
                Fragment scoreFragment =  fragments.get(1);
                if(scoreFragment instanceof ScoreFragment){
                    System.out.println("alright: " + ((ScoreFragment) scoreFragment).getScoreListAdapater().getCount());
                    ((ScoreFragment) scoreFragment).getScoreListAdapater().addScore("Two",23,2);
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
