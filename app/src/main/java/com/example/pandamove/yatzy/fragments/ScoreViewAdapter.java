package com.example.pandamove.yatzy.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.controllers.CellOnClickListener;
import com.example.pandamove.yatzy.controllers.CommunicationHandler;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;
import com.example.pandamove.yatzy.score.ScoreKeeper;
import com.example.pandamove.yatzy.score.ScoreListHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * Class scoreviewadapter is the baseadapter
 * which contains all the yatzyscore and give the user
 * option to select one score when the rolls are finnished
 *
 * @author Rasmus Dahlkvist
 */
public class ScoreViewAdapter extends BaseAdapter {
    private Activity context;
    private List<ScoreListHandler> playerList;
    private LayoutInflater inflater;
    private static final int SCORE_ITEM = 1;
    private static final int HEADER_ITEM = 0;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private HashMap<Integer,CellOnClickListener> observeListeners;
    private ArrayList<Dice> dices;
    private Animation setScoreAnimation;
    private int imageIndex = 0;
    private String[] headerRows = {
            "Sum",
            "Bonus",
            "Total",
            "Total of All"
    };
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

    /**
     * Constructor for ScoreViewAdapter
     *
     * @param context the main activity which
     *                the list view is present on
     * @param playerList a list containing ScoreListHandler
     * @param dices list which contains Dice
     * */
    public ScoreViewAdapter(Activity context,
                            List<ScoreListHandler> playerList,
                            ArrayList<Dice> dices){
        this.playerList = playerList;
        this.context = context;
        observeListeners = new HashMap<>();
        this.dices = dices;
    }

    /**
     * Set the score animation
     *
     * @param setScoreAnimation a animation value
     * */
    public void setSetScoreAnimation(Animation setScoreAnimation) {
        this.setScoreAnimation = setScoreAnimation;
    }

    /**
     * Add item in the list and notify the listview
     * also create a scorelist handler of the item
     * and add it to the playerList
     *
     * @param yatzyScore the yatzyScore of the add item
     * @param players list of all players
     **/
    public void addItem(String yatzyScore, List<Player> players){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, yatzyScore, false, imageId[imageIndex]);
        playerList.add(scoreHandler);
        this.notifyDataSetChanged();
        imageIndex++;
    }

    /**
     * Add a new sectionheader for the list view, and also
     * create a new scorehandler for the section and
     * add it to the playerlist
     *
     * @param header the header name of the first column in the header
     * @param players in the header section
     * @param position position in the listview of the header
     * */
    public void addSectionHeader(String header, List<Player> players, int position){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, header, false, imageId[1]);
        scoreHandler.setHeaderItem(position);
        playerList.add(scoreHandler);
        sectionHeader.add(playerList.size() - 1);
        this.notifyDataSetChanged();
    }

    /**
     * Add a cellonclicklistener to the
     * hashmap observelistener with an id
     *
     * @param index
     * @param listener
     * */
    public void addToObserveListeners(int index,CellOnClickListener listener){
        observeListeners.put(index,listener);
    }

    /**
     * @return observeListener a hashmap of CellOnClickListener with an id
     * */
    public HashMap<Integer,CellOnClickListener> getObserveListeners(){
        return observeListeners;
    }

    /**
     * @param position which position of the row for the item
     *
     * @return playerList a item in the list view
     * */
    @Override
    public Object getItem(int position){
        return playerList.get(position);
    }

    /**
     * Get the number of items in the list view by returning the size of playerList
     * @return playerList.size
     * */
    @Override
    public int getCount(){
        return playerList.size();
    }

    /**
     * getItemId based on position
     *
     * @param position of the item
     *
     * @return the position of the item id
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position of the view type
     *
     * @return a value between 0-1 if there is a header or item
     *          using treeSet to change between 0-1
     * */
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
     * Set score on player based on row and the current player
     * position in the row which the score is about to be
     * viewed in.
     *
     * @param i position in list
     * @param player the current player
     * @param row the row in the list
     * */
    public void setScoreOnPlayer(int i, Player player, String row){
        this.checkIfScoreExist(player);
        ((ScoreListHandler) this.getItem(i)).setScore(player, row, 0);
        ((ScoreListHandler) this.getItem(i)).setScoreBackgroundInActive(
                player.getColumnPosition(),1, player, row,0);
        ((ScoreListHandler) this.getItem(i)).setListener(player, this , row, i);
        this.notifyDataSetChanged();
    }

    /**
     * Check if score exist on a row by checking
     * for zero value in the scorekeeper for
     * the current player
     *
     * @param player the current player
     * */
    public void checkIfScoreExist(Player player){
        for(int i = 0; i < this.getCount(); i++){
            if(this.getItem(i) instanceof  ScoreListHandler){
                    String row = ((ScoreListHandler) this.getItem(i)).getYatzyScore();
                    int value = player.getScoreKeeper().
                            getScoresPossible(((ScoreListHandler) this.getItem(i)).
                                    getYatzyScore());
                    if(value == 0 && !this.checkIfSumOrBonus(row)){
                        ((ScoreListHandler) this.getItem(i)).setScore(player, row, 0);
                        ((ScoreListHandler) this.getItem(i)).
                                setScoreBackgroundInActive(player.getColumnPosition(),3, player, row,2);
                        ((ScoreListHandler) this.getItem(i)).setListener(player, this , row, i);
                        this.notifyDataSetChanged();
                    }
                    if(checkIfSumOrBonus(row)){
                        ((ScoreListHandler) this.getItem(i)).setHeaderValueScore(row,player);

                        ((ScoreListHandler) this.getItem(i)).setScore(player, row, 2);

                    }
            }
        }
    }
    private boolean checkIfSumOrBonus(String row){
        if(row.equals("Bonus") || row.equals("Sum") || row.equals("Total") || row.equals("Total of All")){
            return true;
        }
        return false;
    }


    /**
     * View combination start the communication for the
     * gathered from the inGameViews sparsearray of dice scores.
     *
     * @param animation the animation generated in the drawable file
     * @param player current player in the game
     * */
    public void viewCombination(Player player, Animation animation){
        this.setSetScoreAnimation(animation);
        ScoreKeeper cScoreKeeper = player.getScoreKeeper();
        for(int i = 0; i < this.getCount(); i++) {
            if ((this.getItem(i) instanceof ScoreListHandler)) {
                //Get the row i then baseadapter
                String row =((ScoreListHandler)this.getItem(i)).getYatzyScore();
                if(cScoreKeeper.getScoresPossible(row) != 0){
                    switch (player.getColumnPosition()) {
                        case 0:
                            this.setScoreOnPlayer(i, player, row);
                            break;
                        case 1:
                            this.setScoreOnPlayer(i, player, row);
                            break;
                        case 2:
                            this.setScoreOnPlayer(i, player, row);
                            break;
                        case 3:
                            this.setScoreOnPlayer(i, player, row);
                            break;
                    }
                }
            }
        }
    }


    /**
     * Add a score for the current player and
     * add it to the scorelisthandler decorater
     *
     * @param yatzyScore the yatzyScore row
     * @param player the current player
     * */
    public void addScore(String yatzyScore, Player player){
        for(int i = 0; i < this.getCount(); i++){
            if((this.getItem(i) instanceof  ScoreListHandler)){
                if(((ScoreListHandler) this.getItem(i)).getYatzyScore().equals(yatzyScore)){

                    this.notifyDataSetChanged();

                    ((ScoreListHandler) this.getItem(i)).setScoreBackgroundInActive(
                            player.getColumnPosition(),2, player, yatzyScore, 1
                    );
                    switch (this.checkIfTotalOrSum(
                            ((ScoreListHandler) this.getItem(i)), player.getColumnPosition())
                            ){
                        case 0:
                            if(player.getScoreKeeper().getActive(yatzyScore)){

                                player.getScoreKeeper().setUsedScore(yatzyScore);
                               for(int j = 0; j < player.getScoreKeeper().sizeOfScores(); j++){
                                   ((ScoreListHandler) this.getItem(i)).setScore(player, yatzyScore, 1);
                                   this.notifyDataSetChanged();
                               }
                               this.updateHeaderItems(player);
                                this.notifyDataSetChanged();
                                CommunicationHandler.getInstance().roundsEnd(player, context);
                            }
                            break;
                        case 1:
                            ((ScoreListHandler) this.getItem(i)).setScore(player, yatzyScore, 1);
                            this.notifyDataSetChanged();
                            break;
                        case 2:
                            ((ScoreListHandler) this.getItem(i)).setScore(player, yatzyScore, 1);
                            this.notifyDataSetChanged();
                            break;
                    }
                }
            }
        }
    }

    /**
     * Updates the headeritem with value in the
     * list view
     *
     * @param player the current player which the column
     *               in the header should be update on.
     * */
    public void updateHeaderItems(Player player){
        for(int i = 0; i < this.getCount(); i++){
            if(this.getItem(i) instanceof ScoreListHandler){

                String row = ((ScoreListHandler) this.getItem(i)).getYatzyScore();
                if(checkIfSumOrBonus(row)){

                    ((ScoreListHandler) this.getItem(i)).setHeaderValueScore(row,player);
                    ((ScoreListHandler) this.getItem(i)).setScore(player, row, 2);
                }
            }
        }
    }
    /**
     *
     * Method which check if its total or sum where the row
     * of the header should be updated on
     *
     * @param scoreListHandler the object containing the players
     * @param currentPlayer the currentPlayer given as an integer to
     *                      find values for the player
     * */
    public int checkIfTotalOrSum(ScoreListHandler scoreListHandler, int currentPlayer){
        return scoreListHandler.getPlayers().
                get(currentPlayer).
                getScoreKeeper().
                checkIfHalfScore();
    }

    /**
     * Method which returns the view of a row
     * in the list view. Containing all the
     * values from the ingameview that is relevant.
     * Like the score that the user have on the dices.
     *
     * @param position of the row item
     * @param scoreView the view of the current item row
     * @param parent the viewgroup containg all the views for
     *               the specific row
     * */
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
        if(!scoreListHandler.isHeaderItem()) {
            if (scoreBoard.yatzyScores == null) {
                this.initializeImageForHeader(scoreBoard,scoreListHandler);
            } else {
                scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));
            }
            scoreBoard.playerOneScore.setText(String.format("%s", scoreListHandler.getScore(0)));
            scoreBoard.playerOneScore.setBackgroundResource(scoreListHandler.getScoreBackground(0));
            scoreBoard.playerOneScore.setOnClickListener(scoreListHandler.getListener(0, scoreBoard.playerOneScore));
            scoreBoard.playerOneScore.setVisibility(View.VISIBLE);

            scoreBoard.playerTwoScore.setText(String.format("%s", scoreListHandler.getScore(1)));
            scoreBoard.playerTwoScore.setBackgroundResource(scoreListHandler.getScoreBackground(1));
            scoreBoard.playerTwoScore.setOnClickListener(scoreListHandler.getListener(1, scoreBoard.playerTwoScore));
            scoreBoard.playerTwoScore.setVisibility(View.VISIBLE);

            scoreBoard.playerThreeScore.setText(String.format("%s", scoreListHandler.getScore(2)));
            scoreBoard.playerThreeScore.setBackgroundResource(scoreListHandler.getScoreBackground(2));
            scoreBoard.playerThreeScore.setOnClickListener(scoreListHandler.getListener(2, scoreBoard.playerThreeScore));
            scoreBoard.playerThreeScore.setVisibility(View.VISIBLE);

            scoreBoard.playerFourScore.setText(String.format("%s", scoreListHandler.getScore(3)));
            scoreBoard.playerFourScore.setBackgroundResource(scoreListHandler.getScoreBackground(3));
            scoreBoard.playerFourScore.setOnClickListener(scoreListHandler.getListener(3, scoreBoard.playerFourScore));
            scoreBoard.playerFourScore.setVisibility(View.VISIBLE);
        }else{
            this.viewForHeader(scoreBoard,scoreListHandler);
        }

        return scoreView;
    }

    /**
     * Set the designed image to the yatzy score row instead
     * of using text as indication for the yatzyscore
     * Setting the size of each image before applying the
     * new image background.
     *
     * @param scoreBoard the scoreboard store all view values
     * @param scoreListHandler store a values set for a row
     * */
    private void initializeImageForHeader(YatzyScoreBoard scoreBoard,
                                         ScoreListHandler scoreListHandler){
        scoreBoard.diceImageOne.setImageResource(scoreListHandler.getImageScore());
        if (scoreListHandler.getYatzyScore().contains("1 Pair")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.pairnew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;
        } else if (scoreListHandler.getYatzyScore().contains("2 Pair")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.twopairsnew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;

        } else if (scoreListHandler.getYatzyScore().contains("3 of a Kind")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.threeofkindnew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;

        } else if (scoreListHandler.getYatzyScore().contains("4 of a Kind")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.fourofkindnew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;

        } else if (scoreListHandler.getYatzyScore().contains("Full House")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.fullhousenew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;

        } else if (scoreListHandler.getYatzyScore().contains("Small Straight")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.smallstraightnew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;

        } else if (scoreListHandler.getYatzyScore().contains("Long Straight")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.largestraightnew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;

        } else if (scoreListHandler.getYatzyScore().contains("Chance")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.chancenew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;

        } else if (scoreListHandler.getYatzyScore().contains("Yatzy")) {
            scoreBoard.diceImageOne.setImageResource(R.drawable.yatzynew);
            float logicalDensity = context.getResources().getDisplayMetrics().density;
            int px = (int) Math.ceil(100 * logicalDensity);
            scoreBoard.diceImageOne.getLayoutParams().width = px;
        }
    }

    /***
     * View for the header is all values on the current header
     * check the header based on the name in the scoreListHandler
     *
     * @param scoreListHandler the scoreListHandler of the specific row
     * @param scoreBoard the scoreBoard container
     *                   for the views in a row
     * */
    private void viewForHeader(YatzyScoreBoard scoreBoard, ScoreListHandler scoreListHandler){
        scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));

        if(!scoreListHandler.getYatzyScore().equals("YATZY")){
            scoreBoard.playerOneScore.setVisibility(View.VISIBLE);
            scoreBoard.playerOneScore.setText(String.format("%s", scoreListHandler.getScore(0)));
            scoreBoard.playerOneScore.setBackgroundResource(scoreListHandler.getHeaderItem(4));
        }else{
            scoreBoard.playerOneScore.setText(String.format("%s", ""));
            scoreBoard.playerOneScore.setBackgroundResource(scoreListHandler.getHeaderItem(0));
        }

        if (!scoreListHandler.getYatzyScore().equals("YATZY")) {
            scoreBoard.playerTwoScore.setVisibility(View.VISIBLE);
            scoreBoard.playerTwoScore.setText(String.format("%s", scoreListHandler.getScore(1)));
            scoreBoard.playerTwoScore.setBackgroundResource(scoreListHandler.getHeaderItem(4));
        } else {
            scoreBoard.playerTwoScore.setText(String.format("%s", ""));
            scoreBoard.playerTwoScore.setBackgroundResource(scoreListHandler.getHeaderItem(1));
        }

        if (!scoreListHandler.getYatzyScore().equals("YATZY")) {
            scoreBoard.playerThreeScore.setVisibility(View.VISIBLE);
            scoreBoard.playerThreeScore.setText(String.format("%s", scoreListHandler.getScore(2)));
            scoreBoard.playerThreeScore.setBackgroundResource(scoreListHandler.getHeaderItem(4));
        } else {
            scoreBoard.playerThreeScore.setText(String.format("%s", ""));
            scoreBoard.playerThreeScore.setBackgroundResource(scoreListHandler.getHeaderItem(2));
        }

        if (!scoreListHandler.getYatzyScore().equals("YATZY")) {
            scoreBoard.playerFourScore.setText(String.format("%s", scoreListHandler.getScore(3)));
            scoreBoard.playerFourScore.setBackgroundResource(scoreListHandler.getHeaderItem(4));
            scoreBoard.playerFourScore.setVisibility(View.VISIBLE);
        } else {
            scoreBoard.playerFourScore.setText(String.format("%s", ""));
            scoreBoard.playerFourScore.setBackgroundResource(scoreListHandler.getHeaderItem(3));
        }
    }

    /**
     * A class for holding the views in a
     * specific row in the list view.
     * */
    static class YatzyScoreBoard {
        TextView yatzyScores;
        TextView playerOneScore;
        TextView playerTwoScore;
        TextView playerThreeScore;
        TextView playerFourScore;
        ImageView diceImageOne;
    }
}
