package com.example.pandamove.yatzy.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class ScoreViewAdapter extends BaseAdapter {
    private Context context;
    private List<ScoreListHandler> playerList;
    private LayoutInflater inflater;
    private static final int SCORE_ITEM = 1;
    private static final int HEADER_ITEM = 0;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private final int SUM_OF_FIRST_SECTION = 7;
    private final int SUM_OF_TOTAL_SCORE = 17;
    private int tempSum = 0;

    public ScoreViewAdapter(Context context, List<ScoreListHandler> playerList){
        this.playerList = playerList;
        this.context = context;
    }
    public void addItem(String yatzyScore, List<Player> players){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, yatzyScore, false);
        playerList.add(scoreHandler);
        this.notifyDataSetChanged();
    }
    public void addSectionHeader(String header, List<Player> players){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, header, false);
        playerList.add(scoreHandler);
        sectionHeader.add(playerList.size() - 1);
        this.notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position){
        return playerList.get(position);
    }

    @Override
    public int getCount(){
        return playerList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? HEADER_ITEM : SCORE_ITEM;
    }
    @Override
    public boolean hasStableIds(){
        return true;
    }
    @Override
    public int getViewTypeCount(){
        return 2;
    }

    public void addScore(String yatzyScore, int score, int player){
        for(int i = 0; i < this.getCount(); i++){
            if((this.getItem(i) instanceof  ScoreListHandler)){
                if(((ScoreListHandler) this.getItem(i)).
                        getYatzyScore().equals(yatzyScore)){
                    ((ScoreListHandler) this.getItem(i)).getPlayers().get(0).setCurrentPlayer(true);
                    switch (this.checkIfTotalOrSum(player)){
                        case 0:
                            System.out.println("yala inside one?");
                            ((ScoreListHandler) this.getItem(i)).setScore(score, player, yatzyScore);
                            this.notifyDataSetChanged();
                            break;
                        case 1:
                            System.out.println("yala inside 1");
                            //((ScoreListHandler) this.getItem(i)).setScore(score, player, yatzyScore);
                           // ((ScoreListHandler) this.getItem(i)).setScore(tempSum, player, yatzyScore);
                            this.notifyDataSetChanged();
                            break;
                        case 2:
                            System.out.println("yala inside 2");
                           // ((ScoreListHandler) this.getItem(i)).setScore(score, player, yatzyScore);
                            //((ScoreListHandler) this.getItem(i)).setScore(tempSum, player, yatzyScore);
                            this.notifyDataSetChanged();
                            break;
                    }
                }
            }
        }
    }

    /**
     * TODO Remake this shit haha xD
     * */
    public int checkIfTotalOrSum(int currentPlayer){

        tempSum = 0;
        for(int i = 0; i < SUM_OF_FIRST_SECTION; i++) {
            if(((ScoreListHandler)this.
                    getItem(SUM_OF_FIRST_SECTION)).
                    getScore(currentPlayer) == 0) {
                if ((this.getItem(i) instanceof ScoreListHandler)) {
                    tempSum += ((ScoreListHandler) this.getItem(i))
                            .getScore(currentPlayer);
                    /*System.out.println("le temp sum: " + tempSum);
                    System.out.println("le i value: " + i);
                    System.out.println("le currentPlayer??: " + currentPlayer);*/
                    if (((ScoreListHandler) this.getItem(i))
                            .getScore(currentPlayer) != 0) {
                        System.out.println("woot 0??: " + ((ScoreListHandler) this.getItem(i))
                                .getScore(currentPlayer));
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }else if(((ScoreListHandler)this.
                    getItem(SUM_OF_TOTAL_SCORE)).
                    getScore(currentPlayer) == 0){
                for(int j = 8; j < SUM_OF_TOTAL_SCORE; j++) {
                    if ((this.getItem(i) instanceof ScoreListHandler)) {
                        tempSum += ((ScoreListHandler) this.getItem(i))
                                .getScore(currentPlayer);
                        if (((ScoreListHandler) this.getItem(j))
                                .getScore(currentPlayer) != 0) {
                            return 2;
                        }else{
                            return 0;
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public View getView(int position, View scoreView, ViewGroup parent){
        YatzyScoreBoard scoreBoard;
        int rowType = this.getItemViewType(position);
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(scoreView == null) {
            scoreBoard = new YatzyScoreBoard();
            switch (rowType){
                case HEADER_ITEM:
                    scoreView = inflater.inflate(R.layout.score_header_row, parent, false);
                    scoreBoard.yatzyScores = (TextView) scoreView.findViewById(R.id.header1);
                    scoreBoard.playerOneScore= (TextView) scoreView.findViewById(R.id.hplayerone);
                    scoreBoard.playerTwoScore = (TextView) scoreView.findViewById(R.id.hplayertwo);
                    scoreBoard.playerThreeScore = (TextView) scoreView.findViewById(R.id.hplayerthree);
                    scoreBoard.playerFourScore = (TextView) scoreView.findViewById(R.id.hplayerfour);
                    break;
                case SCORE_ITEM:
                    scoreView = inflater.inflate(R.layout.score_row, parent, false);
                    scoreBoard.yatzyScores = (TextView) scoreView.findViewById(R.id.yatzyscore);
                    scoreBoard.playerOneScore= (TextView) scoreView.findViewById(R.id.playerone);
                    scoreBoard.playerTwoScore = (TextView) scoreView.findViewById(R.id.playertwo);
                    scoreBoard.playerThreeScore = (TextView) scoreView.findViewById(R.id.playerthree);
                    scoreBoard.playerFourScore = (TextView) scoreView.findViewById(R.id.playerfour);
                    break;
            }
            scoreView.setTag(scoreBoard);
        } else {
            scoreBoard = (YatzyScoreBoard) scoreView.getTag();
        }
        final ScoreListHandler scoreListHandler = playerList.get(position);
        scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));

        scoreBoard.playerOneScore.setText(String.format("%d", scoreListHandler.getScore(0)));
        scoreBoard.playerTwoScore.setText(String.format("%d", scoreListHandler.getScore(1)));
        scoreBoard.playerThreeScore.setText(String.format("%d", scoreListHandler.getScore(2)));
        scoreBoard.playerFourScore.setText(String.format("%d", scoreListHandler.getScore(3)));
        return scoreView;
    }
    static class YatzyScoreBoard {
        TextView yatzyScores;
        TextView playerOneScore;
        TextView playerTwoScore;
        TextView playerThreeScore;
        TextView playerFourScore;
    }
}
