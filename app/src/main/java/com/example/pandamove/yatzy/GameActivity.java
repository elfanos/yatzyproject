package com.example.pandamove.yatzy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamove.yatzy.controllers.CommunicationHandler;
import com.example.pandamove.yatzy.controllers.OnBackPressedListener;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.fragments.FragmentSliderPagerAdapter;
import com.example.pandamove.yatzy.fragments.InGameFragment;
import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.player.GameObjects;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.LeaderBoard;
import java.util.ArrayList;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity{

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private HashMap<String,Integer> listOfPossibleScores = new HashMap<>();

    private ArrayList<Dice> dices = new ArrayList<>();


    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Integer> playersIcon = new ArrayList<>();

    private int ralle = 0;
    private static final ArrayList<Animation> animations = new ArrayList<>();

    private ArrayList<TextView> highScore = new ArrayList<>();

    private GameObjects gameObjects;

    protected OnBackPressedListener onBackPressedListener;

    //private

    private static Animation scoreAnimation;

   // private CommunicationHandler communicationHandler;
    private LeaderBoard leaderBoard = new LeaderBoard();
    Animation animation;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_tab);
        Intent numbOfPlayers = getIntent();
        int numberOfPlayers = numbOfPlayers.getIntExtra("players",0);
        System.out.println("other activityaaaaaaaaaaaa " + numberOfPlayers);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_alpha);
        this.initializePlayerIcon();
        this.initializePlayers(numberOfPlayers);
        gameObjects = new GameObjects(players);
        gameObjects.setRoundTest(1);

        fragments = new SparseArray<>();
        mPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new FragmentSliderPagerAdapter(
                getSupportFragmentManager(),
                fragments,
                dices,
                listOfPossibleScores,
                players
        );
        mPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);

        this.initializeCommunicationHandler();

    }
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if(CommunicationHandler.getInstance().getCurrentFragment() == 1){
            CommunicationHandler.getInstance().goToInGameView();
        }else{;
            if (exit) {
                finish(); // finish activity
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
    }
    public ScoreFragment getFragmentOne(){
        return ((ScoreFragment)fragments.get(1));
    }
    public Fragment inGameFragment(){
        return fragments.get(0);
    }
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
    }
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
