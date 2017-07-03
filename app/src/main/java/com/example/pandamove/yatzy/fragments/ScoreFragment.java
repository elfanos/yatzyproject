package com.example.pandamove.yatzy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class ScoreFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private ListView listView;
    private List<ScoreListHandler> listOfScores;
    private String[] scores = {
            "YATZY",
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
            "Total",
            "Total of All"
    };

    private List<Player> players;

    private ScoreViewAdapter scoreViewAdapter;

    private ArrayList<Dice> dices;

    private ListView scoreListView;
    public ScoreFragment(){
        players = new ArrayList<>();
        listOfScores = new ArrayList<>();
    }
    public static ScoreFragment newInstance(int page, ArrayList<Dice> dices){
        Bundle args = new Bundle();
        ScoreFragment object = new ScoreFragment();
        args.putInt(ARG_PAGE, page);
        args.putParcelableArrayList("dices", dices);
        object.setArguments(args);

        return object;
    }


    @Override
    public void onCreate(Bundle onSavedInstace){
        super.onCreate(onSavedInstace);
        dices = getArguments().getParcelableArrayList("dices");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.scoregame_page, container, false);
        scoreListView = (ListView) view.findViewById(R.id.list_view_score);

        /**
         * TODO fix so that set column arent static?
         * */
        Player player = new Player("ralle", scores);
        player.setColumnPosition(0);
        players.add(player);
        Player player2 = new Player("ralle2",scores);
        player2.setColumnPosition(1);
        players.add(player2);
        Player player3 = new Player("ralle3", scores);
        player3.setColumnPosition(2);
        players.add(player3);
        Player player4 = new Player("ralle4",scores);
        player4.setColumnPosition(3);
        players.add(player4);
        if(scoreViewAdapter == null){
            this.updateAdapter();
        }
        /*scoreViewAdapter = new ScoreViewAdapter(inflater.getContext());
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
        scoreListView.setAdapter(scoreViewAdapter);*/
        return view;
    }
    public void updateAdapter(){

        scoreViewAdapter = new ScoreViewAdapter(this.getActivity(), listOfScores, dices);
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
        scoreListView.setAdapter(scoreViewAdapter);
    }
    public int listOfScoresCount(){
        return listOfScores.size();
    }
    public ScoreViewAdapter getScoreListAdapater (){
        return (ScoreViewAdapter) scoreListView.getAdapter();
    }
    public ListView getListView(){
        return scoreListView;
    }
    public int getPlayerListSize(){
        return players.size();
    }
}
