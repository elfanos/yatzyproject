package com.example.pandamove.yatzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;

/**
 * Activity for the score view show the score leader board
 * @author Rasmus Dahlkvist
 */

public class ScoreViewActivity extends AppCompatActivity {
    ArrayList<Player> players;
    ArrayList<Integer> playersIcon;
    @Override
    protected void onCreate(Bundle onSavedInstance){
        super.onCreate(onSavedInstance);
        setContentView(R.layout.score_view_activity);
        players = new ArrayList<>();
        playersIcon = new ArrayList<>();
        Intent getIntent = getIntent();
        Bundle getGameInfo = getIntent.getExtras();

        players = ((ArrayList) getGameInfo.getSerializable("players"));
        playersIcon = ((ArrayList) getGameInfo.getSerializable("playersIcon"));
        System.out.println("wat is size?=? " + players.size());
        this.initializeScoreViews();

    }

    /**
     *Initialize the scoreview based on how
     * many players that were in the game
     * */
    public void initializeScoreViews(){
        for(int i = 0; i < players.size(); i++) {
            switch (i) {
                case 0:
                    this.addFirstPlayer();
                    break;
                case 1:
                    this.addSecondPlayer();
                    break;
                case 2:
                    this.addThirdPlayer();
                    break;
                case 3:
                    this.addFourthPlayer();
                    break;
            }
        }
    }

    /**
     * Add first player
     * */
    public void addFirstPlayer(){
        ((TextView)findViewById(R.id.n_numberOne)).setText(
                players.get(0).getName()
        );
        (findViewById(R.id.p_numberOne)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberOne)).setVisibility(View.VISIBLE);
        (findViewById(R.id.s_numberOne)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberOne)).setBackgroundResource(
             this.getBackground(players.get(0).getName())
        );
        ((TextView) findViewById(R.id.s_numberOne)).setText(
                String.format("%s", players.get(0).getScoreKeeper().getTotalOfAll())
        );
    }

    /**
     * Return background based on player name
     *
     * @param leader playerName
     * @return playerIcons as integer
     * */
    public int getBackground(String leader){
        switch (leader){
            case "Yellow":
                return this.playersIcon.get(0);
            case "Orange":
                return this.playersIcon.get(1);
            case "Blue":
                return this.playersIcon.get(2);
            case "Black":
                return this.playersIcon.get(3);
        }
        return 0;
    }
    /**
     * Add second player
     * */
    public void addSecondPlayer(){
        ((TextView)findViewById(R.id.n_numberTwo)).setText(
                players.get(1).getName()
        );
        (findViewById(R.id.p_numberTwo)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberTwo)).setVisibility(View.VISIBLE);
        (findViewById(R.id.s_numberTwo)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberTwo)).setBackgroundResource(
               this.getBackground(players.get(1).getName())
        );
        ((TextView) findViewById(R.id.s_numberTwo)).setText(
                String.format("%s", players.get(1).getScoreKeeper().getTotalOfAll())
        );

    }

    /**
     * Add third player
     * */
    public void addThirdPlayer(){
        ((TextView)findViewById(R.id.n_numberThree)).setText(
                players.get(2).getName()
        );
        (findViewById(R.id.p_numberThree)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberThree)).setVisibility(View.VISIBLE);
        (findViewById(R.id.s_numberThree)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberThree)).setBackgroundResource(
                this.getBackground(players.get(2).getName())
        );
        ((TextView) findViewById(R.id.s_numberThree)).setText(
                String.format("%s", players.get(2).getScoreKeeper().getTotalOfAll())
        );
    }

    /**
     * Add fourth player
     * */
    public void addFourthPlayer(){
        ((TextView)findViewById(R.id.n_numberFour)).setText(
                players.get(0).getName()
        );
        (findViewById(R.id.p_numberFour)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberFour)).setVisibility(View.VISIBLE);
        (findViewById(R.id.s_numberFour)).setVisibility(View.VISIBLE);
        (findViewById(R.id.n_numberFour)).setBackgroundResource(
               this.getBackground(players.get(3).getName())
        );
        ((TextView) findViewById(R.id.s_numberFour)).setText(
                String.format("%s", players.get(3).getScoreKeeper().getTotalOfAll())
        );

    }
}
