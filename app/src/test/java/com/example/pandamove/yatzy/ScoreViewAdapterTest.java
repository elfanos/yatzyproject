package com.example.pandamove.yatzy;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import com.example.pandamove.yatzy.fragments.ScoreViewAdapter;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreHandler;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by rallesport on 2017-05-10.
 */

public class ScoreViewAdapterTest{
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private ScoreViewAdapter scoreViewAdapter;
    private List<Player> players = new ArrayList<>();

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
    public ScoreViewAdapterTest() {
        super();
    }

    protected void setUp() throws Exception{
        playerOne = new Player("ralle");
        playerTwo = new Player("Gura");
        playerThree = new Player("boi");

        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);

        scoreViewAdapter = new ScoreViewAdapter(mMockContext);
        for(int i = 0; i < scores.length; i++ ){
            if(i == 0){
                scoreViewAdapter.addSectionHeader(scores[i],players);
            }
            if(i != 0 && i < 7){
                scoreViewAdapter.addItem(scores[i],players);
            }
            if(i > 6 && i <8){
                scoreViewAdapter.addSectionHeader(scores[i], players);
            }
            if(i > 7 && i < 17 ){
                scoreViewAdapter.addItem(scores[i], players);
            }
            if(i > 16){
                scoreViewAdapter.addSectionHeader(scores[i], players);
            }
        }
    }
    @Test
    public void testGetItemPlayer() {
       try {
           this.setUp();
       }catch (Exception e){
           System.out.println("Lol: " + e);
       }
        assertEquals("John was expected.", playerOne.getName(),
                ((ScoreListHandler)scoreViewAdapter.getItem(0)).getPlayers().get(0).getName());

    }
    @Test
    public void testGetItemId() {
        try {
            this.setUp();
        }catch (Exception e){
            System.out.println("Lol: " + e);
        }

        assertEquals("Wrong ID.", 0,    scoreViewAdapter.getItemId(0));
    }

    @Test
    public void testGetCount() {
        try {
            this.setUp();
        }catch (Exception e){
            System.out.println("Lol: " + e);
        }

        assertEquals("Player amount incorrect.", 1, scoreViewAdapter.getCount());
    }

    @Test
    public void getViewTest(){
        try {
            this.setUp();
        }catch (Exception e){
            System.out.println("Lol: " + e);
        }
        View view = scoreViewAdapter.getView(0,null,null);
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
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}
