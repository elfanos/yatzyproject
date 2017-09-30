package com.example.pandamove.yatzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;

import java.util.ArrayList;

/**
 *
 * End game activity called when last round is reached
 *
 * @author Rasmus Dahlkvist
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
        leaders = (players);
        leaders = leaderBoard.checkTopList(leaders);
        int background = this.getBackground(leaders.get(0).getName());
        this.endWinner(background, leaders.get(0));
        (findViewById(R.id.newGameButton)).setOnClickListener(new NewGameListener());
        (findViewById(R.id.checkScoreButton)).setOnClickListener(
                new ScoreButtoneListener(playersIcon,leaders)
        );
    }

    /**
     * Get background based on player name
     * @return the player background as int
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
     * Set winner of the game of yatzy
     *
     * @param background player background
     * @param player the winning player
     * */
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

    /**
     * Listener for new game
     *
     * */
    public class NewGameListener implements View.OnClickListener{

        /**
         * Send user to start activity
         *
         * @param v which view is clicked
         * */
        @Override
        public void onClick(View v){
            Intent newGame = new Intent(getApplication(), StartActivity.class);
            newGame.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newGame);
            finish();
        }
    }

    /**
     * Class for scourebutton listener send the user to the score view
     * */
    public class ScoreButtoneListener implements View.OnClickListener{
        private ArrayList<Integer> playersIcon;
        private ArrayList<Player> leaders;
        public ScoreButtoneListener(ArrayList<Integer> playersIcon,
                ArrayList<Player> leaders){
            this.playersIcon = playersIcon;
            this.leaders = leaders;
        }

        /**
         * Send user to score view activity
         * @param v check click view
         * */
        @Override
        public void onClick(View v){
            Intent scoreView = new Intent(getApplication(), ScoreViewActivity.class);
            Bundle scoreBundle = new Bundle();
            scoreBundle.putSerializable("playersIcon", playersIcon);
            scoreBundle.putSerializable("players", players);
            scoreView.putExtras(scoreBundle);
            startActivity(scoreView);
        }
    }

    /**
     * On back pressed it goes to new game
     * */
    @Override
    public void onBackPressed() {
        Intent newGame = new Intent(getApplication(), StartActivity.class);
        newGame.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newGame);
        finish();
    }
}
