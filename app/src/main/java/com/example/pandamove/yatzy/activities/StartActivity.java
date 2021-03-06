package com.example.pandamove.yatzy.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.controllers.NumberOfPlayerListener;

import java.util.ArrayList;

/**
 * The class start active
 * gives the start menu
 *
 * @author Rasmus Dahlkvist
 */

public class StartActivity extends AppCompatActivity{
    ArrayList<TextView> playerButtons = new ArrayList<>();
    private int howManyPlayers = 0;

    /**
     * Create new activity instance
     *  and initialize objects
     * @param savedInstanceState restore saved instance from previous
     *                           runs if there is any
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.initializeButtons();
        this.initializeListeners();

        //Start game activity
        (findViewById(R.id.startGameButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(howManyPlayers == 0) {
                    Toast.makeText(getApplicationContext(), "Select how many players", Toast.LENGTH_SHORT).show();
                }else {
                    Intent gameActivityIntent = new Intent(StartActivity.this,
                            GameActivity.class);
                    gameActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    gameActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    gameActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    gameActivityIntent.putExtra("players", howManyPlayers);
                    startActivity(gameActivityIntent);
                    finish();
                }

            }
        });
    }
    /**
     * Restore the instance and set the higlighted
     * button to the number of players that was setted
     * before
     * */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        TextView selectedButton = playerButtons
                .get(savedInstanceState.getInt("numbofplayers")-1);
        selectedButton.setAlpha(1);
        for(int i = 0; i < playerButtons.size(); i++){
            if(!playerButtons.get(i).equals(selectedButton)){

                playerButtons.get(i).setAlpha(0.3f);
            }else{
                this.setHowManyPlayers((i+1));
            }
        }

    }

    /**
     * Save values for next initialization of the activity
     *
     * @param outState keeps values stored
     * */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("numbofplayers", this.howManyPlayers);
    }

    /**
     * Initialize all the buttons in the start menu for selecting players
     * */
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

    /**
     * initialize listener for player buttons
     * */
    public void initializeListeners(){
        for(int i = 0; i < playerButtons.size(); i++){
            playerButtons.get(i).setOnClickListener(new NumberOfPlayerListener(
                    playerButtons, playerButtons.get(i), this
            ));
        }
    }

    /**
     * Set how many players
     * @param newValue how many players
     * */
    public void setHowManyPlayers(int newValue){
        this.howManyPlayers = newValue;
    }

    /**
     * Close activity on back pressed
     * */
    @Override
    public void onBackPressed() {
        finish();
    }
}
