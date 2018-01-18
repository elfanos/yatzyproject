package com.example.pandamove.yatzy.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.controllers.CommunicationHandler;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.fragments.FragmentSliderPagerAdapter;
import com.example.pandamove.yatzy.fragments.InGameFragment;
import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.controllers.GameObjects;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GameActivity
 * The activity during the yatzy game
 *
 * @author Rasmus Dahlkvist
 * */
public class GameActivity extends AppCompatActivity{
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private HashMap<String,Integer> listOfPossibleScores = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Integer> playersIcon = new ArrayList<>();
    private ArrayList<TextView> highScore = new ArrayList<>();
    private GameObjects gameObjects;
    private LeaderBoard leaderBoard = new LeaderBoard();
    Animation animation;
    private ArrayList<Integer> glScore = new ArrayList<>();

    private String[] scores = {
            "Header",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Sum",
            "Bonus",
            "1 Pair",
            "2 Pair",
            "3 of a Kind",
            "4 of a Kind",
            "Full House",
            "Small Straight",
            "Long Straight",
            "Chance",
            "Yatzy",
            "Total",
            "Total of All"
    };
    private int[] diceScore = {
            12,
            15,
            14,
            11,
            6,
            20
    };

    /*Keep track on fragments*/
    private SparseArray<Fragment> fragments;
    private Boolean exit = false;

    private SensorManager sm;

    private float acelVal; //Accelaration and gravity
    private float acelLast; //Lastt ac and graveitiy
    private float shake; // acce and differ gravity

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
        setContentView(R.layout.navigation_tab);
        Intent numbOfPlayers = getIntent();
        int numberOfPlayers = numbOfPlayers.getIntExtra("players",0);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_alpha);
        this.initializePlayerIcon();
        this.initializePlayers(numberOfPlayers);
        gameObjects = new GameObjects(players, glScore);
        gameObjects.setRoundTest(0);
        fragments = new SparseArray<>();
        mPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new FragmentSliderPagerAdapter(
                getSupportFragmentManager(),
                fragments,
                listOfPossibleScores
        );
        mPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);
        this.initializeCommunicationHandler();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

    }

    /**
     * Gather stored values inside bundle savedInstanceState
     *
     * @param savedInstanceState contains stored values
     * */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        gameObjects = (GameObjects) savedInstanceState.getSerializable("gameobject");
        CommunicationHandler.getInstance().setGameObjects(gameObjects);
        CommunicationHandler.getInstance().setPlayers(gameObjects.getPlayers());
    }

    /**
     * Save values for next initialization of the activity
     *
     * @param outState keeps values stored
     * */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"inGameFragment",
               (CommunicationHandler.getInstance()).getFragments().get(0));
        getSupportFragmentManager().putFragment(outState,"scoreFragment",
               (CommunicationHandler.getInstance()).getFragments().get(1));
        outState.putInt("numberofrounds", gameObjects.getRound());
        outState.putSerializable("gameobject", gameObjects);
        outState.putSerializable("themplayers",players);
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    /**
     * On back pressed check if the user really want to end the game
     * Show a toast text hen if so it exit the game and application
     * */
    @Override
    public void onBackPressed() {
        if(CommunicationHandler.getInstance().getCurrentFragment() == 1){
            CommunicationHandler.getInstance().goToInGameView();
        }else{
            if (exit) {
                Intent newGame = new Intent(getApplication(), StartActivity.class);
                newGame.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newGame);
                finish();
            } else {
                Toast.makeText(this, "Press Back again to Exit and Stop the Game.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
    }

    /**
     * Create a sensor event listerner the listen if the user
     * is shaking the phone or not
     * */
    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        /**
         * Using sensor to check gravity of the phone and position
         * Check the trashhold and if its over it will call shakeActivity
         * */
		@Override
		public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if(shake > 12){
                CommunicationHandler.getInstance().shakeActivity();
            }
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	/**
     * Initialize values to the communicationHandler
     *
     * */
    public void initializeCommunicationHandler(){
        CommunicationHandler.getInstance().setPlayers(players);
        CommunicationHandler.getInstance().setAnimation(animation);
        CommunicationHandler.getInstance().setFragments(fragments);
        CommunicationHandler.getInstance().setHighScore(highScore);
        CommunicationHandler.getInstance().setPlayersIcon(playersIcon);
        CommunicationHandler.getInstance().setListOfPossibleScores(listOfPossibleScores);
        CommunicationHandler.getInstance().setLeaderBoard(leaderBoard);
        CommunicationHandler.getInstance().setGameObjects(gameObjects);
        CommunicationHandler.getInstance().setGameActivity(this);
        CommunicationHandler.getInstance().setInitializeDices(false);
        CommunicationHandler.getInstance().setThrows();
    }

    /**
     * End the game and send user to end activity
     * */
    public void endGame(){
        Intent endGame = new Intent(
                GameActivity.this,
                EndActivity.class
        );
        Bundle playerBundle = new Bundle();
        playerBundle.putSerializable("players", CommunicationHandler.getInstance().getPlayers());
        playerBundle.putSerializable("playersIcon", CommunicationHandler.getInstance().getPlayersIcon());
        endGame.putExtras(playerBundle);
        startActivity(endGame);
        finish();
    }

    /**
     * Initialize player icon
     * */
    public void initializePlayerIcon(){
        playersIcon = new ArrayList<>();
        playersIcon.add(R.drawable.playericon_one);
        playersIcon.add(R.drawable.playericon_two);
        playersIcon.add(R.drawable.playericon_three);
        playersIcon.add(R.drawable.playericon_four);

        highScore.add(((TextView) findViewById(R.id.playerLeader)));
        highScore.add(((TextView) findViewById(R.id.playerSecond)));
        highScore.add(((TextView) findViewById(R.id.playerThird)));
        highScore.add(((TextView) findViewById(R.id.playerForth)));
    }

    /**
     * Add two players
     * */
    public void addTwoPlayers(){
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        player.setCurrentPlayer(true);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
    }
    /**
     * Add three players
     * */
    public void addThreePlayers(){
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        player.setCurrentPlayer(true);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
        Player player3 = new Player("Blue", scores);
        player3.setColumnPosition(2);
        player3.setNumberOfThrows(0);
        players.add(player3);
    }
    /**
     * initialize player based on how many players there is
     * */
    public void initializePlayers(int numberOfPlayers){
        switch (numberOfPlayers){
            case 1:
                Player player = new Player("Yellow", scores);
                player.setColumnPosition(0);
                player.setNumberOfThrows(0);
                player.setCurrentPlayer(true);
                players.add(player);
                break;
            case 2:
                this.addTwoPlayers();
                break;
            case 3:
                this.addThreePlayers();
                break;
            case 4:
                this.addFourPlayers();
                break;
        }
    }

    /**
     * Add four players
     * */
    public void addFourPlayers(){
        Player player = new Player("Yellow", scores);
        player.setColumnPosition(0);
        player.setNumberOfThrows(0);
        player.setCurrentPlayer(true);
        players.add(player);
        Player player2 = new Player("Orange",scores);
        player2.setColumnPosition(1);
        player2.setNumberOfThrows(0);
        players.add(player2);
        Player player3 = new Player("Blue", scores);
        player3.setColumnPosition(2);
        player3.setNumberOfThrows(0);
        players.add(player3);
        Player player4 = new Player("Black",scores);
        player4.setColumnPosition(3);
        player4.setNumberOfThrows(0);
        players.add(player4);
    }

}
