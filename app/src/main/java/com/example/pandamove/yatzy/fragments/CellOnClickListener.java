package com.example.pandamove.yatzy.fragments;

import android.view.View;

import com.example.pandamove.yatzy.controllers.GameActivityInterface;
import com.example.pandamove.yatzy.player.Player;
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
    private String yatzyScore;
    private int position;
    private Player player;
    private GameActivityInterface gameActivityInterface;
    public CellOnClickListener(
            Player player, ScoreViewAdapter scoreViewAdapter, String yatzyScore, int position){
        this.scoreViewAdapter = scoreViewAdapter;
        this.yatzyScore = yatzyScore;
        this.position = position;
        this.player = player;

    }
    @Override
    public void onClick(View view) {

        if(view.isPressed()) {
            HashMap<Integer,CellOnClickListener> listeners = scoreViewAdapter.getObserveListeners();
            System.out.println("wats le position??: " + position);
            System.out.println("how many??: " + scoreViewAdapter.getObserveListeners().size());
            player.setNumberOfThrows(0);
            player.incrementRound();
            scoreViewAdapter.addScore(yatzyScore, player);
            Iterator iterator = listeners.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry map = (Map.Entry) iterator.next();
                Object index = scoreViewAdapter.getItem((Integer) map.getKey());
                if(index instanceof ScoreListHandler){
                    ((ScoreListHandler) index).destroyListener(player.getColumnPosition());
                }
            }
            this.checkIfCellIsActive();



        }

    }
    public boolean checkIfSumOrBonus(ScoreListHandler handler){
        if(handler.getYatzyScore().equals("Bonus") || handler.getYatzyScore().equals("Sum")||
                handler.getYatzyScore().equals("Total") || handler.getYatzyScore().equals("Total of All")){
            return true;
        }
        return false;

    }
    public boolean checkIfCellIsActive(){
        for(int i = 0; i < scoreViewAdapter.getCount(); i++){
            //if (i < 8 && i > 9) {
                if (scoreViewAdapter.getItem(i) instanceof ScoreListHandler) {

                    /*if (!player.getScoreKeeper().
                            getActive(((ScoreListHandler) scoreViewAdapter.
                                    getItem(i)).getYatzyScore())) {

                    }else if(((ScoreListHandler) scoreViewAdapter.getItem(i)).
                            getYatzyScore().equals("Bonus")){

                    } else {
                        ((ScoreListHandler) scoreViewAdapter.getItem(i)).
                                setScoreBackground(player.
                                        getColumnPosition(), 0, player, "nothing", 2);
                    }*/
                    if(player.getScoreKeeper().
                            getActive(((ScoreListHandler) scoreViewAdapter.
                                    getItem(i)).getYatzyScore()) &&
                            !this.checkIfSumOrBonus((ScoreListHandler) scoreViewAdapter.
                                    getItem(i))){
                        ((ScoreListHandler) scoreViewAdapter.getItem(i)).
                                setScoreBackground(player.
                                        getColumnPosition(), 0, player, "nothing", 2);
                    }

                }
          //  }
        }
        return false;
    }
}
