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

    public ScoreViewAdapter(Context context, List<ScoreListHandler> playerList){
        this.playerList = playerList;
        this.context = context;
       // this.playerList = new ArrayList<>();
    }
    public void addItem(String yatzyScore, List<Player> players){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, yatzyScore, false);
        playerList.add(scoreHandler);
        notifyDataSetChanged();
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
    public int getViewTypeCount(){
        return 2;
    }

    public void addScore(String yatzyScore, View scoreView){
        //System.out.println("well1");
        for(int i = 0; i < this.getCount(); i++){
            if((this.getItem(i) instanceof  ScoreListHandler)){
                if(((ScoreListHandler) this.getItem(i)).
                        getYatzyScore().equals(yatzyScore)){
                    ((ScoreListHandler) this.getItem(i)).getPlayers().get(0).setCurrentPlayer(true);
                    System.out.println("well1");
                    //((ScoreListHandler) this.getItem(i)).setIsScoreSetted(true);
                    System.out.println(((ScoreListHandler) this.getItem(i)).isScoreSetted());
                    System.out.println("le columns: " + ((ScoreListHandler) this.getItem(i)).checkWhichColumn());

                   // System.out.println("le columns: " + ((ScoreListHandler) this.getItem(i)).checkScore());
                    ((ScoreListHandler) this.getItem(i)).setScore(23, 3,yatzyScore);
                    this.notifyDataSetChanged();
                }
            }
        }
        //this.notifyDataSetChanged();
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
                    //System.out.println("Row type? " + rowType);
                    scoreView = inflater.inflate(R.layout.score_header_row, parent, false);
                    scoreBoard.yatzyScores = (TextView) scoreView.findViewById(R.id.header1);
                    scoreBoard.playerOneScore= (TextView) scoreView.findViewById(R.id.hplayerone);
                    scoreBoard.playerTwoScore = (TextView) scoreView.findViewById(R.id.hplayertwo);
                    scoreBoard.playerThreeScore = (TextView) scoreView.findViewById(R.id.hplayerthree);
                    scoreBoard.playerFourScore = (TextView) scoreView.findViewById(R.id.hplayerfour);
                    break;
                case SCORE_ITEM:
                    //System.out.println("Row type? " + rowType);
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
       // System.out.println("check if list is null: " + scoreListHandler.getYatzyScore());
        scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));
        /*
        scoreBoard.playerOneScore.setText(String.format("%d", scoreListHandler.checkScore("one")));
        scoreBoard.playerTwoScore.setText(String.format("%d", scoreListHandler.checkScore("two")));
        scoreBoard.playerThreeScore.setText(String.format("%d", scoreListHandler.checkScore("three")));
        scoreBoard.playerFourScore.setText(String.format("%d", scoreListHandler.checkScore("four")));*/
        scoreBoard.playerOneScore.setText(String.format("%d", scoreListHandler.getScore("one")));
        scoreBoard.playerTwoScore.setText(String.format("%d", scoreListHandler.getScore("two")));
        scoreBoard.playerThreeScore.setText(String.format("%d", scoreListHandler.getScore("three")));
        scoreBoard.playerFourScore.setText(String.format("%d", scoreListHandler.getScore("four")));
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
