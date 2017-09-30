package com.example.pandamove.yatzy.controllers;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.example.pandamove.yatzy.fragments.ScoreViewAdapter;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * Listener for all cells in the list adapter
 *
 * @author Rasmus Dahlkvist
 */

public class CellOnClickListener implements View.OnClickListener {
    private ScoreViewAdapter scoreViewAdapter;
    private String yatzyScore;
    private int position;
    private Player player;

    /**
     * Constructor
     *
     * @param player the current player
     * @param scoreViewAdapter the baseAdapter
     * @param yatzyScore the yatzyScore
     * @param position the position of the cell
     * */
    public CellOnClickListener(
            Player player, ScoreViewAdapter scoreViewAdapter, String yatzyScore, int position){
        this.scoreViewAdapter = scoreViewAdapter;
        this.yatzyScore = yatzyScore;
        this.position = position;
        this.player = player;

    }

    /**
     * Iterate through hashmap to get the right yatzy score which was clicked
     * set it as inactive and add the score to the list adapter with new grey view
     *
     * @param view the view of the click
     * */
    @Override
    public void onClick(View view) {
        if(view.isPressed()) {
            HashMap<Integer,CellOnClickListener> listeners = scoreViewAdapter.getObserveListeners();
            player.setNumberOfThrows(0);
            player.incrementRound();
            scoreViewAdapter.addScore(yatzyScore, player);
            Iterator iterator = listeners.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry map = (Map.Entry) iterator.next();
                Object index = scoreViewAdapter.getItem((Integer) map.getKey());
                if(index instanceof ScoreListHandler){
                    scoreViewAdapter.getItem((Integer) map.getKey());
                    ((ScoreListHandler) index).destroyListener(player.getColumnPosition());
                }
            }
            this.checkIfCellIsActive(view);

        }

    }
    /**
     * Check if sum or bonus
     *
     * @param handler a scoreListHandler to get row
     *
     * @return true or false
     * */
    public boolean checkIfSumOrBonus(ScoreListHandler handler){
        if(handler.getYatzyScore().equals("Bonus") || handler.getYatzyScore().equals("Sum")||
                handler.getYatzyScore().equals("Total") || handler.getYatzyScore().equals("Total of All")){
            return true;
        }
        return false;

    }

    /**
     * Check if cell is active or not then change view based on it
     *
     * @param view the view
     * */
    public boolean checkIfCellIsActive(View view){
        for(int i = 0; i < scoreViewAdapter.getCount(); i++){
            if (scoreViewAdapter.getItem(i) instanceof ScoreListHandler) {
                if(player.getScoreKeeper().
                        getActive(((ScoreListHandler) scoreViewAdapter.
                                getItem(i)).getYatzyScore()) &&
                        !this.checkIfSumOrBonus((ScoreListHandler) scoreViewAdapter.
                                getItem(i))){
                    ((ScoreListHandler) scoreViewAdapter.getItem(i)).
                            setScoreBackgroundInActive(player.
                                    getColumnPosition(), 0, player, "nothing", 2);
                }else{
                    Animation animation = new AlphaAnimation(0.0f, 0.5f);
                    animation.setDuration(0);
                    Animation animation1 = new AlphaAnimation(0.5f, 1.0f);
                    animation1.setDuration(500);
                    AnimationSet set = new AnimationSet(false);
                    set.addAnimation(animation);
                    set.addAnimation(animation1);
                    view.startAnimation(set);
                }

            }
        }
        return false;
    }
}
