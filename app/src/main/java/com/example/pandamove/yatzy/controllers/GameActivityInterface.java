package com.example.pandamove.yatzy.controllers;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;

import java.io.Serializable;

/**
 * Game interface for both fragment
 * to send values between them
 * @author Rasmus Dahlkvist
 */

public interface GameActivityInterface extends Serializable {

    /**
     * Called in ingamefragment when throwthread is done
     * @param dices send all the dices generate on a roll
     * */
    void onThrowPostPossibleScores(SparseArray<Dice> dices);
    /**
     * Set the player view
     *
     * @param v view of the application
     * */
    void setPlayerView(View v);
    /**
     * Set the score view
     *
     * @param v view of the application
     * */
    void setScoreView(View v);

    /**
     * Set the throws
     *
     * @param v view of the application
     * */
    void getThrows(View v);

    /**
     * Set rounds end
     *
     * @param player the current player
     * @param activity the game activity
     * */
    void roundsEnd(Player player,Activity activity);

    /**
     * Update view
     *
     * @param v view of the application
     * */
    void updateView(View v);

    /**
     * Update highscore
     *
     * @param v view of the application
     * */
    void updateHighScore(View v);

}
