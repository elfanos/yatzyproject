package com.example.pandamove.yatzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.pandamove.yatzy.score.NumberOfPlayerListener;

import java.util.ArrayList;

/**
 * Created by rallesport on 2017-09-25.
 */

public class StartActivity extends AppCompatActivity {
    ArrayList<TextView> playerButtons = new ArrayList<>();
    private int howManyPlayers = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.initializeButtons();
        this.initializeListeners();

        (findViewById(R.id.startGameButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameActivityIntent = new Intent(StartActivity.this,
                        GameActivity.class);
                gameActivityIntent.putExtra("players", howManyPlayers);
                startActivity(gameActivityIntent);

            }
        });
    }
    public void initializeButtons(){
        playerButtons.add(
                ((TextView) findViewById(R.id.playerButtonOne))
        );
        playerButtons.add(
                ((TextView) findViewById(R.id.playerButtonTwo))
        );
        playerButtons.add(
                ((TextView) findViewById(R.id.playerButtonThree))
        );
        playerButtons.add(
                ((TextView) findViewById(R.id.playerButtonFour))
        );
    }
    public void initializeListeners(){
        for(int i = 0; i < playerButtons.size(); i++){
            playerButtons.get(i).setOnClickListener(new NumberOfPlayerListener(
                   playerButtons, playerButtons.get(i), this
            ));
        }
    }
    public void setHowManyPlayers(int newValue){
        this.howManyPlayers = newValue;
    }
}
