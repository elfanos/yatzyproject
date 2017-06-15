package com.example.pandamove.yatzy.fragments;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;

import com.google.common.collect.Table;

import java.util.List;

/**
 * Created by Rasmus on 14/06/17.
 */

public class CellOnClickListener implements View.OnClickListener {
    private ScoreViewAdapter scoreViewAdapter;
    private int currentPlayer;
    private String yatzyScore;
    private int score;
    public CellOnClickListener(
            ScoreViewAdapter scoreViewAdapter,int currentPlayer,
            String yatzyScore, int score
    ){
        this.scoreViewAdapter = scoreViewAdapter;
        this.currentPlayer = currentPlayer;
        this.yatzyScore = yatzyScore;
        this.score = score;
    }
    @Override
    public void onClick(View view) {

        if(view.isPressed()) {
           scoreViewAdapter.addScore(yatzyScore, score, currentPlayer);
        }

    }
}
