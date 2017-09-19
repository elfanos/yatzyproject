package com.example.pandamove.yatzy.score;

import android.graphics.drawable.Drawable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.fragments.CellOnClickListener;
import com.example.pandamove.yatzy.fragments.ScoreViewAdapter;
import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rallmo on 2017-04-06.
 */
public class ScoreListHandler {

    private List<Player> players;
    private int scorePlayerOne;
    private int scorePlayerTwo;
    private int scorePlayerThree;
    private int scorePlayerFour;
    private boolean scoreSetted;
    private String yatzyScore;
    private int playerOneView;
    private int playerTwoView;
    private int playerThreeView;
    private int playerFourView;
    private CellOnClickListener playerOneListener;
    private CellOnClickListener playerTwoListener;
    private CellOnClickListener playerThreeListener;
    private CellOnClickListener playerFourListener;
    private ArrayList<Integer> differentLayouts;
    private int imageScore;

    public ScoreListHandler (List<Player> players, String yatzyScore, boolean scoreSetted, int imageId){
        this.players = players;
        this.yatzyScore = yatzyScore;
        this.scoreSetted = scoreSetted;
        playerOneListener = null;
        playerTwoListener = null;
        playerThreeListener = null;
        playerFourListener = null;
        this.differentLayouts = new ArrayList<>();
        this.imageScore = imageId;
        differentLayouts.add(R.drawable.layout_border);
        differentLayouts.add(R.drawable.layout_border_higlight);
        differentLayouts.add(R.drawable.layout_border_scored);
    }
    public int getImageScore(){
        return imageScore;
    }
    public String getYatzyScore() {
        return yatzyScore;
    }

    public CellOnClickListener getListener(int player){
        switch (player){
            case 0:
                return playerOneListener;
            case 1:
                return  playerTwoListener;
            case 2:
                return playerThreeListener;
            case 3:
                return playerFourListener;
            default:
                return null;
        }
    }
    public void setListener(Player player, ScoreViewAdapter scoreViewAdapter,
                            String yatzyScore, int position){
        switch (player.getColumnPosition()){
            case 0:
                System.out.println("well wat is happening?");
                playerOneListener = new CellOnClickListener(
                        player, scoreViewAdapter, yatzyScore, position
                        );
                scoreViewAdapter.addToObserveListeners(position,playerOneListener);
                break;
            case 1:
                playerTwoListener = new CellOnClickListener(
                        player, scoreViewAdapter, yatzyScore, position
                );
                scoreViewAdapter.addToObserveListeners(position,playerTwoListener);
                break;
            case 2:
                playerThreeListener = new CellOnClickListener(
                        player, scoreViewAdapter, yatzyScore, position
                );
                scoreViewAdapter.addToObserveListeners(position,playerThreeListener);
                break;
            case 3:
                playerFourListener = new CellOnClickListener(
                        player, scoreViewAdapter, yatzyScore, position
                );
                scoreViewAdapter.addToObserveListeners(position,playerFourListener);
                break;
        }

    }
    public void destroyListener(int player){
        switch (player){
            case 0:
                playerOneListener = null;
                break;
            case 1:
                playerTwoListener = null;
                break;
            case 2:
                playerThreeListener = null;
                break;
            case 3:
                playerFourListener = null;
                break;
        }
    }
    public int getScoreBackground(int player){
        switch (player){
            case 0:
                return playerOneView;
            case 1:
                return playerTwoView;
            case 2:
                return playerThreeView;
            case 3:
                return playerFourView;
            default:
                return 0;
        }
    }
    public void setScoreBackground(int playerPos, int viewCase, Player player, String yatzyScore, int whichBackground){
        if(!player.getScoreKeeper().getActive(yatzyScore)){
          //  System.out.println("le background" + whichBackground);
           // System.out.println("how many unactive!!!!!!!!!!!!!");
            viewCase = 2;
            switch (playerPos) {
                case 0:
                    playerOneView = differentLayouts.get(2);
                    break;
                case 1:
                    playerTwoView = differentLayouts.get(2);
                    break;
                case 2:
                    playerThreeView = differentLayouts.get(2);
                    break;
                case 3:
                    playerFourView = differentLayouts.get(2);
                    break;
            }
        }else {
            switch (playerPos) {
                case 0:
                    switch (viewCase) {
                        case 0:
                            playerOneView = differentLayouts.get(0);
                            break;
                        case 1:
                            playerOneView = differentLayouts.get(1);
                            break;
                        case 2:
                            playerOneView = differentLayouts.get(2);
                            break;
                    }
                    break;
                case 1:
                    switch (viewCase) {
                        case 0:
                            playerTwoView = differentLayouts.get(0);
                            break;
                        case 1:
                            playerTwoView = differentLayouts.get(1);
                            break;
                        case 2:
                            playerTwoView = differentLayouts.get(2);
                            break;
                    }
                    break;
                case 2:
                    switch (viewCase) {
                        case 0:
                            playerThreeView = differentLayouts.get(0);
                            break;
                        case 1:
                            playerThreeView = differentLayouts.get(1);
                            break;
                        case 2:
                            playerThreeView = differentLayouts.get(2);
                            break;
                    }
                    break;
                case 3:
                    switch (viewCase) {
                        case 0:
                            playerFourView = differentLayouts.get(0);
                            break;
                        case 1:
                            playerFourView = differentLayouts.get(1);
                            break;
                        case 2:
                            playerFourView = differentLayouts.get(2);
                            break;
                    }
                    break;
            }
        }
    }

    public int getScore(int player) {
        switch (player){
            case 0:
                return scorePlayerOne;
            case 1:
                return scorePlayerTwo;
            case 2:
                return scorePlayerThree;
            case 3:
                return scorePlayerFour;
            default:
                return 0;
        }
    }


    public void setScore(Player player, String row, int addOrTemp) {
        switch (player.getColumnPosition()){
            case 0:
                if(player.getScoreKeeper().getActive(row)) {
                    if(addOrTemp == 1) {
                        player.getScoreKeeper().setUsedScore(row);
                        System.out.println("wat is used?? ");
                    }else if(addOrTemp == 2){

                    }
                    this.scorePlayerOne = player.getScoreKeeper().getScoresPossible(row);
                   // players.get(player).getScoreKeeper().setColumnScore(row);
                    break;
                }
                break;
            case 1:
                if(player.getScoreKeeper().getActive(row)) {
                    if(addOrTemp == 1) {
                        player.getScoreKeeper().setUsedScore(row);
                    }else if(addOrTemp == 2){

                    }
                    this.scorePlayerOne = player.getScoreKeeper().getScoresPossible(row);
                  //  players.get(player).getScoreKeeper().setColumnScore(row);
                    break;
                }
                break;
            case 2:
                if(player.getScoreKeeper().getActive(row)) {
                    if(addOrTemp == 1) {
                      // player.getScoreKeeper().setUsedScore(row);
                        this.scorePlayerThree = player.getScoreKeeper().getScoreOnRow(row);
                    }else if(addOrTemp == 0) {
                        this.scorePlayerThree = player.getScoreKeeper().getScoresPossible(row);
                    }else if(addOrTemp == 2){

                    }
                   // players.get(player).getScoreKeeper().setColumnScore(row);
                    break;
                }
                break;
            case 3:
                if(player.getScoreKeeper().getActive(row)) {

                    if(addOrTemp == 0) {
                        this.scorePlayerFour = player.getScoreKeeper().getScoresPossible(row);
                    }else if(addOrTemp == 1) {
                      //  player.getScoreKeeper().setUsedScore(row);
                        this.scorePlayerFour = player.getScoreKeeper().getScoreOnRow(row);
                    }else if(addOrTemp == 2){
                        this.scorePlayerFour = player.getScoreKeeper().checkBonus();
                    }else if(addOrTemp == 3){
                        this.scorePlayerFour = player.getScoreKeeper().getSumOfNumbers();
                    }else if(addOrTemp == 4){
                        //total
                        this.scorePlayerFour = player.getScoreKeeper().getTotal();
                    }else if(addOrTemp == 5){
                        //total of all
                        this.scorePlayerFour = player.getScoreKeeper().getTotalOfAll();
                    }
                   //players.get(player).getScoreKeeper().setColumnScore(row);
                    break;
                }
                break;
            default:
                break;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }


}
