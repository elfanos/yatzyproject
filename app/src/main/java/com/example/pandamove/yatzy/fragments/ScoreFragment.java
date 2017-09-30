package com.example.pandamove.yatzy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment Class
 * A class which is used for the list view adapter
 * to view all possible score given when the user play
 * a round of yatzy
 *
 * @author Rasmus Dahlkvist
 */
public class ScoreFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
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
    private List<Player> players;
    private ScoreViewAdapter scoreViewAdapter;
    private ArrayList<Dice> dices;
    private ListView scoreListView;

    /**
     * Intance for the score fragment initialize
     * two array list one for player and one list of scores
     * */
    public ScoreFragment(){
        players = new ArrayList<>();
        listOfScores = new ArrayList<>();
    }

    /**
     * New instance is used for saftey when using
     * fragment, it make a instance of itself instead
     * of creating instance in another class. Save all important
     * information in bundles and send it to oncreate
     *
     * @param page fragment number
     * @param dices alla dices in a arraylist
     * @param players all the players in an arraylist
     * */
    public static ScoreFragment newInstance(int page, ArrayList<Dice> dices,
                                            ArrayList<Player> players){
        Bundle args = new Bundle();
        ScoreFragment object = new ScoreFragment();
        args.putInt(ARG_PAGE, page);
        args.putParcelableArrayList("dices", dices);
        args.putSerializable("players", players);
        object.setArguments(args);

        return object;
    }


    /**
     * Creates the fragment
     * retrive all important value by using getArgument
     * */
    @Override
    public void onCreate(Bundle onSavedInstace){
        super.onCreate(onSavedInstace);
        dices = getArguments().getParcelableArrayList("dices");
        players = (ArrayList<Player>) getArguments().getSerializable("players");
    }

    /**
     * Create the view for the fragment
     *
     * @param inflater get the xml file layout
     * @param container all the view inside the xml file
     * @param savedInstanceState get saved instance if there is any saved instance
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.scoregame_page, container, false);
        scoreListView = (ListView) view.findViewById(R.id.list_view_score);

        if(scoreViewAdapter == null){
            this.updateAdapter();
        }
        return view;
    }

    /**
     * Update or initialize a new list view adapter add all
     * the items
     * */
    public void updateAdapter(){
        int headerItem = 0;
        scoreViewAdapter = new ScoreViewAdapter(this.getActivity(),
                listOfScores, dices);
        for(int i = 0; i < scores.length; i++ ){
            if(i == 0){
                scoreViewAdapter.addSectionHeader(scores[i],players, headerItem);
                headerItem++;
            }
            if(i != 0 && i < 7){
                scoreViewAdapter.addItem(scores[i],players);
            }
            if(i > 6 && i <9){
                scoreViewAdapter.addSectionHeader(scores[i], players, headerItem);
                headerItem++;
            }
            if(i > 8 && i < 18 ){
                scoreViewAdapter.addItem(scores[i], players);
            }
            if(i > 17){
                scoreViewAdapter.addSectionHeader(scores[i], players, headerItem);
                headerItem++;
            }
        }
        scoreListView.setAdapter(scoreViewAdapter);
    }
    /**
     * @return scoreListViewAdapter return the list adapter
     * */
    public ScoreViewAdapter getScoreListAdapater (){
        return (ScoreViewAdapter) scoreListView.getAdapter();
    }

    /**
     * @return size of the players arraylist
     * */
    public int getPlayerListSize(){
        return players.size();
    }
}
