package com.example.pandamove.yatzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;

import java.util.ArrayList;

/**
 * Created by rallesport on 2017-09-26.
 */

public class EndActivity extends AppCompatActivity {

    private ArrayList<Integer> playersIcon;
    private ArrayList<Player> players;
    private LeaderBoard leaderBoard;
    private ArrayList<Player> leaders;

    @Override
    protected void onCreate(Bundle onSavedInstance){
        super.onCreate(onSavedInstance);
        setContentView(R.layout.end_game_screen);
        playersIcon = new ArrayList<>();
        players = new ArrayList<>();
        leaders = new ArrayList<>();
        leaderBoard = new LeaderBoard();
        Intent getInfo = getIntent();
        Bundle getPlayers = getIntent().getExtras();
        playersIcon = getInfo.getIntegerArrayListExtra("playersIcon");
        players = ((ArrayList<Player>) getPlayers.getSerializable("players"));

        System.out.println("Size? " + players.size());
        leaders = (players);
        leaders = leaderBoard.checkTopList(leaders);
        int background = this.getBackground(leaders.get(0).getName());
        this.endWinner(background, leaders.get(0));

        (findViewById(R.id.newGameButton)).setOnClickListener(new NewGameListener());
        (findViewById(R.id.checkScoreButton)).setOnClickListener(
                new ScoreButtoneListener(playersIcon,leaders)
        );
        //(findViewById(R.id.))
    }
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
    public void endWinner(int background, Player player){
        (findViewById(R.id.winnerText)).
                setBackgroundResource(
                        background
                );
        ((TextView)(findViewById(R.id.winnerText))).
                setText(String.format("%s",player.getName()));
        ((TextView)(findViewById(R.id.winnerScore))).
                setText(String.format("%s",player.getScoreKeeper().getTotalOfAll()));
    }
    public class NewGameListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent newGame = new Intent(getApplication(), StartActivity.class);
            startActivity(newGame);
        }
    }
    public class ScoreButtoneListener implements View.OnClickListener{
        private ArrayList<Integer> playersIcon;
        private ArrayList<Player> leaders;
        public ScoreButtoneListener(ArrayList<Integer> playersIcon,
                ArrayList<Player> leaders){
            this.playersIcon = playersIcon;
            this.leaders = leaders;
        }
        @Override
        public void onClick(View v){
            Intent newGame = new Intent(getApplication(), ScoreViewActivity.class);
            Bundle scoreBundle = new Bundle();
            scoreBundle.putSerializable("playersIcon", playersIcon);
            scoreBundle.putSerializable("players", players);
            newGame.putExtras(scoreBundle);
            startActivity(newGame);
        }
    }
}
