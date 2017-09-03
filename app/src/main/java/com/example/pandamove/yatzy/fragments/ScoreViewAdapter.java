package com.example.pandamove.yatzy.fragments;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreListHandler;
import com.google.common.io.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private HashMap<Integer,CellOnClickListener> observeListeners;
    private ArrayList<Dice> dices;

    private int imageIndex = 0;

    private int [] imageId = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.mipmap.ic_dicesix,
            R.mipmap.ic_dicesix,
            R.mipmap.ic_dicethree,
            R.mipmap.ic_dicefour,
            R.mipmap.ic_dicefive,
            R.mipmap.ic_dicesix,
            R.mipmap.ic_diceone,
            R.mipmap.ic_dicetwo,
            R.mipmap.ic_dicethree,
            R.mipmap.ic_dicefour,
            R.mipmap.ic_dicefive,
            R.mipmap.ic_dicesix
    };
    public int getImageIndex(){
        return imageIndex;
    }
    public ScoreViewAdapter(Context context, List<ScoreListHandler> playerList, ArrayList<Dice> dices){
        this.playerList = playerList;
        this.context = context;
        observeListeners = new HashMap<>();
        this.dices = dices;
    }
    public void addItem(String yatzyScore, List<Player> players){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, yatzyScore, false, imageId[imageIndex]);
        playerList.add(scoreHandler);
        this.notifyDataSetChanged();
        imageIndex++;
    }
    public void addSectionHeader(String header, List<Player> players){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, header, false, imageId[1]);
        playerList.add(scoreHandler);
        sectionHeader.add(playerList.size() - 1);
        this.notifyDataSetChanged();
    }

    public void addToObserveListeners(int index,CellOnClickListener listener){
        observeListeners.put(index,listener);
    }

    public HashMap<Integer,CellOnClickListener> getObserveListeners(){
        return observeListeners;
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

    /**
     * TODO fix so it check for each yatzy score then
     * apply design on the cells based on player
     * */
    public void viewCombination(int currentPlayer,HashMap<String,Integer> listOfScores){
        for(int i = 0; i < this.getCount(); i++) {
            if ((this.getItem(i) instanceof ScoreListHandler)) {
                if(listOfScores.
                        get(((ScoreListHandler) this.getItem(i)).
                                getYatzyScore()) != null){
                    switch (currentPlayer) {
                        case 0:
                            ((ScoreListHandler) this.getItem(i)).
                                    setScore(listOfScores.get(((ScoreListHandler) this.getItem(i)).
                                                            getYatzyScore()), currentPlayer,
                                            ((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore()
                                    );
                            ((ScoreListHandler) this.getItem(i)).setScoreBackground(currentPlayer,1);

                            ((ScoreListHandler) this.getItem(i)).
                                    setListener(currentPlayer, this, listOfScores.
                                                    get(((ScoreListHandler) this.getItem(i)).
                                                            getYatzyScore()),
                                            ((ScoreListHandler) this.getItem(i)).getYatzyScore(), i
                                    );

                            this.notifyDataSetChanged();

                            break;
                        case 1:
                            ((ScoreListHandler) this.getItem(i)).
                                    setScore(
                                            listOfScores.
                                                    get(((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore()),
                                                    currentPlayer,
                                                    ((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore()
                            );
                            ((ScoreListHandler) this.getItem(i)).
                                    setScoreBackground(currentPlayer,1);

                            ((ScoreListHandler) this.getItem(i)).
                                    setListener(
                                            currentPlayer,
                                            this,
                                            listOfScores.
                                            get(((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore()),
                                            ((ScoreListHandler) this.getItem(i)).
                                            getYatzyScore(),
                                            i
                                    );

                            this.notifyDataSetChanged();

                            break;
                        case 2:
                            ((ScoreListHandler) this.getItem(i)).
                                    setScore(
                                            listOfScores.
                                                    get(((ScoreListHandler) this.getItem(i)).
                                                            getYatzyScore()),
                                            currentPlayer,
                                            ((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore()
                                    );
                            ((ScoreListHandler) this.getItem(i)).
                                    setScoreBackground(currentPlayer,1);

                            ((ScoreListHandler) this.getItem(i)).
                                    setListener(
                                            currentPlayer,
                                            this,
                                            listOfScores.
                                                    get(((ScoreListHandler) this.getItem(i)).
                                                            getYatzyScore()),
                                            ((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore(),
                                            i
                                    );

                            this.notifyDataSetChanged();
                            break;
                        case 3:
                            ((ScoreListHandler) this.getItem(i)).
                                    setScore(
                                            listOfScores.
                                                    get(((ScoreListHandler) this.getItem(i)).
                                                            getYatzyScore()),
                                            currentPlayer,
                                            ((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore()
                                    );
                            ((ScoreListHandler) this.getItem(i)).
                                    setScoreBackground(currentPlayer,1);

                            ((ScoreListHandler) this.getItem(i)).
                                    setListener(
                                            currentPlayer,
                                            this,
                                            listOfScores.
                                                    get(((ScoreListHandler) this.getItem(i)).
                                                            getYatzyScore()),
                                            ((ScoreListHandler) this.getItem(i)).
                                                    getYatzyScore(),
                                            i
                                    );

                            this.notifyDataSetChanged();
                            break;
                    }
                }


            }
        }
    }

    /**
     * TODO fix test for the new implemented list view
     * which fix the bakground on each cell.
     * */
    public void addScore(String yatzyScore, int score,
                         int player){
        for(int i = 0; i < this.getCount(); i++){
            if((this.getItem(i) instanceof  ScoreListHandler)){
                if(((ScoreListHandler) this.getItem(i)).
                        getYatzyScore().
                        equals(yatzyScore)){

                    ((ScoreListHandler) this.getItem(i)).
                            getPlayers().
                            get(player).
                            setCurrentPlayer(true);

                    ((ScoreListHandler) this.getItem(i)).setScoreBackground(player,2);
                    switch (this.checkIfTotalOrSum(
                            ((ScoreListHandler) this.getItem(i)),
                            player)
                            ){
                        case 0:
                            ((ScoreListHandler) this.getItem(i)).
                                    setScore(
                                            score,
                                            player,
                                            yatzyScore
                                    );
                            this.notifyDataSetChanged();
                            break;
                        case 1:
                            ((ScoreListHandler) this.getItem(i)).
                                    setScore(
                                            this.getHalfScore(player),
                                            player,
                                            yatzyScore
                                    );
                            this.notifyDataSetChanged();
                            break;
                        case 2:
                            ((ScoreListHandler) this.getItem(i)).
                                    setScore(
                                            this.getLastSectionScore(player),
                                            player,
                                            yatzyScore
                                    );
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
    public int checkIfTotalOrSum(
            ScoreListHandler scoreListHandler, int currentPlayer
    ){
        return scoreListHandler.getPlayers().
                get(currentPlayer).
                getScoreKeeper().
                checkIfHalfScore();
    }
    public int getHalfScore(int currentPlayer){
        int sum = 0;
        for(int i = 1; i < SUM_OF_FIRST_SECTION; i++){
            if(this.getItem(i) instanceof ScoreListHandler){
                sum += ((ScoreListHandler) this.getItem(i)).
                        getScore(currentPlayer);
            }
        }
        return sum;
    }
    public int getLastSectionScore(int currentPlayer){
        int sum = 0;
        for(int i = SUM_OF_FIRST_SECTION+1; i < SUM_OF_TOTAL_SCORE; i++){
            if(this.getItem(i) instanceof ScoreListHandler){
                sum += ((ScoreListHandler) this.getItem(i)).
                        getScore(currentPlayer);
            }
        }
        return sum;
    }
    public int getTotaleScore(int currentPlayer){
        int sum = 0;
        for(int i = 0; i < getCount(); i++){
            if(this.getItem(i) instanceof ScoreListHandler){
                sum += ((ScoreListHandler) this.getItem(i)).
                        getScore(currentPlayer);
            }
        }
        return sum;
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
                    //scoreBoard.yatzyScores = (TextView) scoreView.findViewById(R.id.yatzyscore);
                    scoreBoard.diceImageOne = (ImageView) scoreView.findViewById(R.id.imagescore);
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
        //scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));
        /*if (scoreBoard.yatzyScores == null) {
            System.out.println("yoyo inside score");
            scoreBoard.scoreImage.setImageResource(scoreListHandler.getImageScore());
            if(scoreListHandler.getYatzyScore().contains("1 Pair")){
                scoreBoard.scoreImage.setImageResource(scoreListHandler.getImageScore());
                scoreBoard.secondImage.setVisibility(View.VISIBLE);
                scoreBoard.secondImage.setImageResource(scoreListHandler.getImageScore());
            }
        } else {
            scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));
        }*/
        if (scoreBoard.yatzyScores == null) {
            scoreBoard.diceImageOne.setImageResource(scoreListHandler.getImageScore());
            if(scoreListHandler.getYatzyScore().contains("1 Pair")) {
                scoreBoard.diceImageOne.setImageResource(R.drawable.onepair);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;
            }else if(scoreListHandler.getYatzyScore().contains("2 Pair")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.twopair);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;

            }else if(scoreListHandler.getYatzyScore().contains("3 of a Kind")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.threeofkind);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;

            }else if(scoreListHandler.getYatzyScore().contains("4 of a Kind")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.fourofkind);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;

            }else if(scoreListHandler.getYatzyScore().contains("Full House")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.fullhouse);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;

            }else if(scoreListHandler.getYatzyScore().contains("Small Straight")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.smallstraight);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;

            }else if(scoreListHandler.getYatzyScore().contains("Long Straight")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.bigstraight);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;

            }else if(scoreListHandler.getYatzyScore().contains("Chance")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.chance);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;

            }else if(scoreListHandler.getYatzyScore().contains("Yatzy")){
                scoreBoard.diceImageOne.setImageResource(R.drawable.chance);
                float logicalDensity = context.getResources().getDisplayMetrics().density;
                int px = (int) Math.ceil(100 * logicalDensity);
                scoreBoard.diceImageOne.getLayoutParams().width = px;
            }
        } else{
            scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));
        }

        scoreBoard.playerOneScore.
                setText(String.format("%d", scoreListHandler.getScore(0)));
        scoreBoard.playerOneScore.
                setBackgroundResource(scoreListHandler.getScoreBackground(0));
        scoreBoard.playerOneScore.
                setOnClickListener(scoreListHandler.getListener(0));

        scoreBoard.playerTwoScore.
                setText(String.format("%d", scoreListHandler.getScore(1)));
        scoreBoard.playerTwoScore.
                setBackgroundResource(scoreListHandler.getScoreBackground(1));
        scoreBoard.playerTwoScore.
                setOnClickListener(scoreListHandler.getListener(1));


        scoreBoard.playerThreeScore.
                setText(String.format("%d", scoreListHandler.getScore(2)));
        scoreBoard.playerThreeScore.
                setBackgroundResource(scoreListHandler.getScoreBackground(2));
        scoreBoard.playerThreeScore.setOnClickListener(scoreListHandler.getListener(2));

        scoreBoard.playerFourScore.
                setText(String.format("%d", scoreListHandler.getScore(3)));
        scoreBoard.playerFourScore.
                setBackgroundResource(scoreListHandler.getScoreBackground(3));
        scoreBoard.playerFourScore.
                setOnClickListener(scoreListHandler.getListener(3));
        return scoreView;
    }
    static class YatzyScoreBoard {
        TextView yatzyScores;
        TextView playerOneScore;
        TextView playerTwoScore;
        TextView playerThreeScore;
        TextView playerFourScore;
        ImageView diceImageOne;
        ImageView diceImageTwo;
        ImageView diceImageThree;
        ImageView diceImageFour;
        ImageView diceImageFive;
        ImageView diceImageSix;
    }
}
