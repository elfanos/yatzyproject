package com.example.pandamove.yatzy.controllers;

import android.util.SparseArray;
import android.view.View;

import com.example.pandamove.yatzy.dice.Dice;

import java.io.Serializable;

/**
 * Created by Rasmus on 02/09/17.
 */

public interface ListPossibleScores extends Serializable {
    void onThrowPostPossibleScores(SparseArray<Dice> dices);
}
