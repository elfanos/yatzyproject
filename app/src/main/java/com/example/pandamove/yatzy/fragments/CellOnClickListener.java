package com.example.pandamove.yatzy.fragments;

import android.view.View;

import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Rasmus on 14/06/17.
 */

public class CellOnClickListener implements View.OnClickListener {
    private ScoreViewAdapter scoreViewAdapter;
    private int currentPlayer;
    private String yatzyScore;
    private int score;
    private int position;
    public CellOnClickListener(
            ScoreViewAdapter scoreViewAdapter,int currentPlayer,
            String yatzyScore, int score, int position
    ){
        this.scoreViewAdapter = scoreViewAdapter;
        this.currentPlayer = currentPlayer;
        this.yatzyScore = yatzyScore;
        this.score = score;
        this.position = position;

    }
    @Override
    public void onClick(View view) {

        if(view.isPressed()) {
            HashMap<Integer,CellOnClickListener> listeners = scoreViewAdapter.getObserveListeners();
            System.out.println("wats le position??: " + position);
            System.out.println("how many??: " + scoreViewAdapter.getObserveListeners().size());
            scoreViewAdapter.addScore(yatzyScore, score, currentPlayer);
            Iterator iterator = listeners.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry map = (Map.Entry) iterator.next();
                Object index = scoreViewAdapter.getItem((Integer) map.getKey());
                if(index instanceof ScoreListHandler){
                    ((ScoreListHandler) index).destroyListener(currentPlayer);
                    if(position != (Integer) map.getKey()){
                        ((ScoreListHandler) index).setScore(0,currentPlayer,"");
                        ((ScoreListHandler) index).setScoreBackground(currentPlayer,0);
                    }
                }
            }


        }

    }
}
