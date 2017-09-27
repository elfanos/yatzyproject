package com.example.pandamove.yatzy.controllers;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;

import java.io.Serializable;

/**
 * Created by Rasmus on 02/09/17.
 */

public interface GameActivityInterface extends Serializable {
    void onThrowPostPossibleScores(SparseArray<Dice> dices);
    boolean getIfRoundIsOver();
    void incrementRoundsForPlayer(View v);
    void setPlayerView(View v);
    void setScoreView(View v);
    void setThrows(View v);
    void setScoreForPlayer(View v);
    void roundsEnd(Player player,Activity activity);
    void updateView(View v);
    void updateHighScore(View v);

}
