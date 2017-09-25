package com.example.pandamove.yatzy.score;

import android.view.View;
import android.widget.TextView;

import com.example.pandamove.yatzy.StartActivity;

import java.util.ArrayList;

/**
 * Created by rallesport on 2017-09-25.
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
    @Override
    public void onClick(View v){
        if(v.isPressed()){
            currentButton.setAlpha(1);
            this.setLowAlphaOnButtons();
        }
    }
    public void setLowAlphaOnButtons(){
        for(int i = 0; i < allButtons.size(); i++){
            if(!allButtons.get(i).equals(this.currentButton)){
                allButtons.get(i).setAlpha(0.5f);
            }else{
                this.startActivity.setHowManyPlayers((i+1));
            }
        }
    }
}
