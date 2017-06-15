package com.example.pandamove.yatzy;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pandamove.yatzy.fragments.ScoreFragment;
import com.example.pandamove.yatzy.fragments.ScoreViewAdapter;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;
import static org.junit.Assert.assertEquals;

/**
 * Created by rallesport on 2017-05-10.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)

public class ScoreViewAdapterTest {
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private ScoreViewAdapter scoreViewAdapter;
    private List<Player> players = new ArrayList<>();
    private List<ScoreListHandler> listOfScores;
    private  Activity mainActivity;
    private ScoreFragment scoreFragment;
    private ListView listView;

    @Mock
    Context mMockContext;
    private String[] scores = {
            "Header",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Sum",
            "1 Pair",
            "2 Pair",
            "3 of a Kind",
            "4 of a Kind",
            "Full House",
            "Small Straight",
            "Long Straight",
            "Chance",
            "Yatzy",
            "Total"
    };
    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.setupActivity(GameActivity.class);
        scoreFragment = ScoreFragment.newInstance(1);
        startFragment(scoreFragment);
        listView = scoreFragment.getListView();
        listOfScores = new ArrayList<>();
        playerOne = new Player("ralle", scores);
        playerTwo = new Player("Gura", scores);
        playerThree = new Player("boi", scores);

        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        scoreViewAdapter = new ScoreViewAdapter(scoreFragment.getActivity(), listOfScores);
        scoreViewAdapter = this.initializeScoreViewAdapter(scoreViewAdapter);

    }
    public ScoreViewAdapter initializeScoreViewAdapter(ScoreViewAdapter scoreViewAdapterInit){
        for (int i = 0; i < scores.length; i++) {
            if (i == 0) {
                scoreViewAdapterInit.addSectionHeader(scores[i], players);
            }
            if (i != 0 && i < 7) {
                scoreViewAdapterInit.addItem(scores[i], players);
            }
            if (i > 6 && i < 8) {
                scoreViewAdapterInit.addSectionHeader(scores[i], players);
            }
            if (i > 7 && i < 17) {
                scoreViewAdapterInit.addItem(scores[i], players);
            }
            if (i > 16) {
                scoreViewAdapterInit.addSectionHeader(scores[i], players);
            }
        }
        return scoreViewAdapterInit;
    }
    @Test
    public void testMainActivity(){
        assertNotNull("is main activity readable?", mainActivity);
    }
    @Test
    public void testFragmentNotNull(){
        assertNotNull("is main activity readable?", scoreFragment);
    }

    @Test
    public void testGetItemPlayer() {

        assertEquals("John was expected.", playerOne.getName(),
                ((ScoreListHandler) scoreViewAdapter.getItem(0)).getPlayers().get(0).getName());

    }

    @Test
    public void testGetItemId() {

        assertEquals("Wrong ID.", 0, scoreViewAdapter.getItemId(0));
    }

    @Test
    public void testGetCount() {

        assertEquals("Base adapter size expected.", 18, scoreViewAdapter.getCount());
    }

    @Test
    public void getLayoutRowViewTest() {
        LayoutInflater layout =
                (LayoutInflater) RuntimeEnvironment.
                        application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layout.inflate(R.layout.score_row,null,false);

        TextView yatzy = (TextView) view.findViewById(R.id.yatzyscore);
        TextView playerOne = (TextView) view.findViewById(R.id.playerone);
        TextView playerTwo = (TextView) view.findViewById(R.id.playertwo);
        TextView playerThree = (TextView) view.findViewById(R.id.playerthree);
        TextView playerFour = (TextView) view.findViewById(R.id.playerfour);

        //On this part you will have to test it with your own views/data
        assertNotNull("View is null. ", yatzy);
        assertNotNull("Name TextView is null. ", playerOne);
        assertNotNull("Number TextView is null. ", playerTwo);
        assertNotNull("Photo ImageView is null. ", playerThree);
        assertNotNull("Name TextView is null. ", playerFour);

    }
    @Test
    public void getLayoutHeaderViewTest() {
        LayoutInflater layout =
                (LayoutInflater) RuntimeEnvironment.
                        application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layout.inflate(R.layout.score_header_row,null,false);

        TextView yatzy = (TextView) view.findViewById(R.id.header1);
        TextView playerOne = (TextView) view.findViewById(R.id.hplayerone);
        TextView playerTwo = (TextView) view.findViewById(R.id.hplayertwo);
        TextView playerThree = (TextView) view.findViewById(R.id.hplayerthree);
        TextView playerFour = (TextView) view.findViewById(R.id.hplayerfour);

        //On this part you will have to test it with your own views/data
        assertNotNull("View is null. ", yatzy);
        assertNotNull("Name TextView is null. ", playerOne);
        assertNotNull("Number TextView is null. ", playerTwo);
        assertNotNull("Photo ImageView is null. ", playerThree);
        assertNotNull("Name TextView is null. ", playerFour);

    }
    @Test
    public void testAddSectionHeaderAndItem(){

        Assert.assertEquals(0,scoreViewAdapter.getItemViewType(0));
        scoreViewAdapter.addSectionHeader("yala", players);
        scoreViewAdapter.addItem("yala", players);
        Assert.assertEquals(1,scoreViewAdapter.getItemViewType(1));

    }
    @Test
    public void addScore(){
        int score = 23;
        int player = 2;
        scoreViewAdapter.addScore("Two",score,player,listView);
        if(scoreViewAdapter.getItem(2) instanceof ScoreListHandler){
            Assert.assertEquals("Player three should have 23 in score: ",
                    23,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(2)
                    );
            Assert.assertEquals("Player two should have 0 in score: ",
                    0,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(1)
            );
            Assert.assertEquals("Player one should have 0 in score: ",
                    0,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(0)
            );
        }
        int playerTwoScore = 20;
        int playerTwo = 1;
        scoreViewAdapter.addScore("Two",playerTwoScore,playerTwo, listView);
        if(scoreViewAdapter.getItem(2) instanceof ScoreListHandler){
            Assert.assertEquals("Player three should have 23 in score: ",
                    23,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(2)
            );
            Assert.assertEquals("Player two should have 20 in score: ",
                    20,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(1)
            );
            Assert.assertEquals("Player one should have 0 in score: ",
                    0,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(0)
            );
        }
        int playerOneScore = 12;
        int playerOne = 0;
        scoreViewAdapter.addScore("Two",playerOneScore, playerOne, listView);
        if(scoreViewAdapter.getItem(2) instanceof ScoreListHandler){
            Assert.assertEquals("Player three should have 23 in score: ",
                    23,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(2)
            );
            Assert.assertEquals("Player two should have 20 in score: ",
                    20,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(1)
            );
            Assert.assertEquals("Player one should have 12 in score: ",
                    12,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(0)
            );
        }
        int playerOneScoreOtherRow = 5;
        int playerOneOtherRow = 0;
        scoreViewAdapter.addScore("One",playerOneScoreOtherRow, playerOneOtherRow, listView);
        if(scoreViewAdapter.getItem(2) instanceof ScoreListHandler){
            Assert.assertEquals("Player one should have 12 in score: ",
                    12,((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(0)
            );
            Assert.assertEquals("Player one should have 12 in score: ",
                    5,((ScoreListHandler) scoreViewAdapter.getItem(1)).getScore(0)
            );
            Assert.assertEquals("Player two should have 20 in score: ",
                    0,((ScoreListHandler) scoreViewAdapter.getItem(1)).getScore(1)
            );
        }
    }

    @Test
    public void testScoreSettedForAColumn(){

        int score = 12;
        int player = 2;
        for(int i = 0; i < scoreViewAdapter.getCount(); i++) {
            if ((scoreViewAdapter.getItem(i) instanceof ScoreListHandler)) {
                ((ScoreListHandler) scoreViewAdapter.getItem(i)).setScore(score,player,scores[i]);
            }
        }
        for(int j = 0; j < scoreViewAdapter.getCount(); j++){
            Assert.assertEquals("All scores on Player three should be 12",
                    12, ((ScoreListHandler) scoreViewAdapter.getItem(j)).getScore(2)
                    );
            Assert.assertEquals("All scores on Player two should be 0",
                    0, ((ScoreListHandler) scoreViewAdapter.getItem(j)).getScore(1)
            );
            Assert.assertEquals("All scores on Player one should be 0",
                    0, ((ScoreListHandler) scoreViewAdapter.getItem(j)).getScore(0)
            );

        }

    }

    @Test
    public void checkNoDuplicateScores(){
        scoreViewAdapter.addScore("Two",5,2,listView);
        scoreViewAdapter.addScore("Two",7,2, listView);
        Assert.assertEquals("All scores on Player three should be 1",
                5, ((ScoreListHandler) scoreViewAdapter.getItem(2)).getScore(2)
        );
    }
    /**
     *
     * Check add score
     *
     * */
    @Test
    public void checkScoreSumNTotal() {
        for (int i = 0; i < scoreViewAdapter.getCount(); i++) {
            scoreViewAdapter.addScore(scores[i],1,2, listView);
        }
        Assert.assertEquals("All scores on Player three should be 12",
                1, ((ScoreListHandler) scoreViewAdapter.getItem(1)).getScore(2)
        );
        Assert.assertEquals("All scores on Player three should be 12",
                6, ((ScoreListHandler) scoreViewAdapter.getItem(7)).getScore(2)
        );
        /**
         * Test for duplicate
         * */
        for (int i = 0; i < scoreViewAdapter.getCount(); i++) {
            scoreViewAdapter.addScore(scores[i],4,2, listView);
        }
        Assert.assertEquals("All scores on Player three should be 12",
                1, ((ScoreListHandler) scoreViewAdapter.getItem(1)).getScore(2)
        );
        Assert.assertEquals("All scores on Player three should be 12",
                6, ((ScoreListHandler) scoreViewAdapter.getItem(7)).getScore(2)
        );

        /**
         * Test with other score
         *
         * */

    }
    @Test
    public void checkHalfScoreWithOtherValues(){
        for (int i = 0; i < scoreViewAdapter.getCount(); i++) {
            scoreViewAdapter.addScore(scores[i],5,2, listView);
        }
        Assert.assertEquals("All scores on Player three should be 12",
                5, ((ScoreListHandler) scoreViewAdapter.getItem(1)).getScore(2)
        );
        Assert.assertEquals("All scores on Player three should be 12",
                30, ((ScoreListHandler) scoreViewAdapter.getItem(7)).getScore(2)
        );
    }

    @Test
    public void checkThatScoreIsReallySettedAtRightTime(){
        for (int i = 0; i < 6; i++) {
            scoreViewAdapter.addScore(scores[i],5,2, listView);
        }
        Assert.assertEquals("All scores on Player three should be 12",
                0, ((ScoreListHandler) scoreViewAdapter.getItem(7)).getScore(2)
        );
        Assert.assertEquals("All scores on Player three should be 12",
                0, ((ScoreListHandler) scoreViewAdapter.getItem(6)).getScore(2)
        );
    }

    @Test
    public void checkLastSectionScore(){
        for (int i = 0; i < scoreViewAdapter.getCount(); i++) {
            scoreViewAdapter.addScore(scores[i],1,2, listView);
        }
        Assert.assertEquals("All scores on Player three should be 12",
                6, ((ScoreListHandler) scoreViewAdapter.getItem(7)).getScore(2)
        );
        Assert.assertEquals("All scores on Player three should be 12",
                1, ((ScoreListHandler) scoreViewAdapter.getItem(8)).getScore(2)
        );
        Assert.assertEquals("All scores on Player three should be 12",
                9, ((ScoreListHandler) scoreViewAdapter.getItem(17)).getScore(2)
        );
    }

}

