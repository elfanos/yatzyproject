package com.example.pandamove.yatzy.controllers;
import android.view.View;

import java.io.Serializable;

/**
 * Created by Rallmo on 2017-03-13.
 */
public interface OnButtonClickedListener extends Serializable {
    public void onButtonClicked(View v);
}
