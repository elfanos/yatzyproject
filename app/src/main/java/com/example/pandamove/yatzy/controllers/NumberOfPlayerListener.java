package com.example.pandamove.yatzy.controllers;

import android.view.View;
import android.widget.TextView;

import com.example.pandamove.yatzy.activities.StartActivity;

import java.util.ArrayList;

/**
 *
 * Listener for number of players
 * @author Rasmus Dahlkvist
 */

public class NumberOfPlayerListener implements View.OnClickListener  {
    private TextView currentButton;
    private ArrayList<TextView> allButtons;
    private StartActivity startActivity;
    public NumberOfPlayerListener(ArrayList<TextView> allButtons,
                                  TextView currentButton, StartActivity startActivity){
        this.allButtons = allButtons;
        this.currentButton = currentButton;
        this.startActivity = startActivity;
    }

    /**
     * @param v which view is clicked
     * */
    @Override
    public void onClick(View v){
        if(v.isPressed()){
            currentButton.setAlpha(1);
            this.setLowAlphaOnButtons();
        }
    }

    /**
     * Change alpha based on which button is clicked
     * */
    public void setLowAlphaOnButtons(){
        for(int i = 0; i < allButtons.size(); i++){
            if(!allButtons.get(i).equals(this.currentButton)){
                allButtons.get(i).setAlpha(0.3f);
            }else{
                this.startActivity.setHowManyPlayers((i+1));
            }
        }
    }
}
