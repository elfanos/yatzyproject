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
 * Created by Rallmo on 2017-04-05.
 */
public class ScoreViewAdapter extends BaseAdapter {
    private Activity context;
    private List<ScoreListHandler> playerList;
    private LayoutInflater inflater;
    private static final int SCORE_ITEM = 1;
    private static final int HEADER_ITEM = 0;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private final int SUM_OF_FIRST_SECTION = 7;
    private final int BONUS_OF_FIRST_SECTION = 7;
    private final int SUM_OF_TOTAL_SCORE = 17;
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
    public int getImageIndex(){
        return imageIndex;
    }
    public ScoreViewAdapter(Activity context,
                            List<ScoreListHandler> playerList,
                            ArrayList<Dice> dices){


        this.playerList = playerList;
        this.context = context;
        observeListeners = new HashMap<>();
        this.dices = dices;
    }
    public Animation getSetScoreAnimation() {
        return setScoreAnimation;
    }

    public void setSetScoreAnimation(Animation setScoreAnimation) {
        this.setScoreAnimation = setScoreAnimation;
    }

    public void addItem(String yatzyScore, List<Player> players){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, yatzyScore, false, imageId[imageIndex]);
        playerList.add(scoreHandler);
        this.notifyDataSetChanged();
        imageIndex++;
    }
    public void addSectionHeader(String header, List<Player> players, int position){
        ScoreListHandler scoreHandler = new ScoreListHandler(players, header, false, imageId[1]);
        scoreHandler.setHeaderItem(position);
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


    public void setScoreOnPlayer(int i, Player player, String row){
        int score = player.getScoreKeeper().getScoresPossible(row);
        this.checkIfScoreExist(player);

        ((ScoreListHandler) this.getItem(i)).setScore(player, row, 0);
        ((ScoreListHandler) this.getItem(i)).setScoreBackground(player.getColumnPosition(),1, player, row,0);
        ((ScoreListHandler) this.getItem(i)).setListener(player, this , row, i);
        this.notifyDataSetChanged();
    }
    public void checkIfScoreExist(Player player){
        for(int i = 0; i < this.getCount(); i++){
            if(this.getItem(i) instanceof  ScoreListHandler){
                    String row = ((ScoreListHandler) this.getItem(i)).getYatzyScore();
                    int value = player.getScoreKeeper().
                            getScoresPossible(((ScoreListHandler) this.getItem(i)).
                                    getYatzyScore());
                    if(value == 0 && !this.checkIfSumOrBonus(row)){
                        ((ScoreListHandler) this.getItem(i)).setScore(player, row, 0);
                        ((ScoreListHandler) this.getItem(i)).setScoreBackground(player.getColumnPosition(),3, player, row,2);
                        ((ScoreListHandler) this.getItem(i)).setListener(player, this , row, i);
                        this.notifyDataSetChanged();
                      //  System.out.println("Value? " + value);
                      //  System.out.println("Row? " + row);

                    }else if(!this.checkIfSumOrBonus(row)){

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
     * TODO fix so it check for each yatzy score then
     * apply design on the cells based on player
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
     * TODO fix test for the new implemented list view
     * which fix the bakground on each cell.
     * */
    public void addScore(String yatzyScore, Player player){
        for(int i = 0; i < this.getCount(); i++){
            if((this.getItem(i) instanceof  ScoreListHandler)){
                if(((ScoreListHandler) this.getItem(i)).getYatzyScore().equals(yatzyScore)){

                    this.notifyDataSetChanged();

                    ((ScoreListHandler) this.getItem(i)).setScoreBackground(
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
                               this.updateHeaderItems(player,((ScoreListHandler) this.getItem(i)));

                                this.notifyDataSetChanged();
                              // gameActivityInterface.roundsEnd(player, context);

                                CommunicationHandler.getInstance().roundsEnd(player, context);
                            }else{
                                System.out.println("not active yo");
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
    public void updateHeaderItems(Player player, ScoreListHandler scoreListHandler){
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
     * TODO Remake this shit haha xD
     * */
    public int checkIfTotalOrSum(ScoreListHandler scoreListHandler, int currentPlayer){
        return scoreListHandler.getPlayers().
                get(currentPlayer).
                getScoreKeeper().
                checkIfHalfScore();
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
                scoreBoard.diceImageOne.setImageResource(scoreListHandler.getImageScore());
                if (scoreListHandler.getYatzyScore().contains("1 Pair")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.onepair);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;
                } else if (scoreListHandler.getYatzyScore().contains("2 Pair")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.twopair);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;

                } else if (scoreListHandler.getYatzyScore().contains("3 of a Kind")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.threeofkind);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;

                } else if (scoreListHandler.getYatzyScore().contains("4 of a Kind")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.fourofkind);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;

                } else if (scoreListHandler.getYatzyScore().contains("Full House")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.fullhouse);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;

                } else if (scoreListHandler.getYatzyScore().contains("Small Straight")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.smallstraight);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;

                } else if (scoreListHandler.getYatzyScore().contains("Long Straight")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.bigstraight);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;

                } else if (scoreListHandler.getYatzyScore().contains("Chance")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.chance);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;

                } else if (scoreListHandler.getYatzyScore().contains("Yatzy")) {
                    scoreBoard.diceImageOne.setImageResource(R.drawable.chance);
                    float logicalDensity = context.getResources().getDisplayMetrics().density;
                    int px = (int) Math.ceil(100 * logicalDensity);
                    scoreBoard.diceImageOne.getLayoutParams().width = px;
                }
            } else {
                scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));
            }
            scoreBoard.playerOneScore.setText(String.format("%s", scoreListHandler.getScore(0)));
            scoreBoard.playerOneScore.setBackgroundResource(scoreListHandler.getScoreBackground(0));
            scoreBoard.playerOneScore.setOnClickListener(scoreListHandler.getListener(0, scoreBoard.playerOneScore));
            scoreBoard.playerOneScore.setVisibility(View.VISIBLE);
         /*   if (scoreListHandler.getVisible(0)) {
                scoreBoard.playerOneScore.setVisibility(View.VISIBLE);
            } else {
                scoreBoard.playerOneScore.setVisibility(View.INVISIBLE);
            }*/

            scoreBoard.playerTwoScore.setText(String.format("%s", scoreListHandler.getScore(1)));
            scoreBoard.playerTwoScore.setBackgroundResource(scoreListHandler.getScoreBackground(1));
            scoreBoard.playerTwoScore.setOnClickListener(scoreListHandler.getListener(1, scoreBoard.playerTwoScore));
            scoreBoard.playerTwoScore.setVisibility(View.VISIBLE);
          /*  if (scoreListHandler.getVisible(1)) {
                scoreBoard.playerTwoScore.setVisibility(View.VISIBLE);
            } else {
                scoreBoard.playerTwoScore.setVisibility(View.INVISIBLE);
            }*/


            scoreBoard.playerThreeScore.setText(String.format("%s", scoreListHandler.getScore(2)));
            scoreBoard.playerThreeScore.setBackgroundResource(scoreListHandler.getScoreBackground(2));
            scoreBoard.playerThreeScore.setOnClickListener(scoreListHandler.getListener(2, scoreBoard.playerThreeScore));
            scoreBoard.playerThreeScore.setVisibility(View.VISIBLE);
            /*if (scoreListHandler.getVisible(2)) {
                scoreBoard.playerThreeScore.setVisibility(View.VISIBLE);
            } else {
                scoreBoard.playerThreeScore.setVisibility(View.INVISIBLE);
            }*/

            scoreBoard.playerFourScore.setText(String.format("%s", scoreListHandler.getScore(3)));
            scoreBoard.playerFourScore.setBackgroundResource(scoreListHandler.getScoreBackground(3));
            scoreBoard.playerFourScore.setOnClickListener(scoreListHandler.getListener(3, scoreBoard.playerFourScore));
            scoreBoard.playerFourScore.setVisibility(View.VISIBLE);
        }else{
            this.viewForHeader(scoreBoard,scoreListHandler);
        }

        return scoreView;
    }
    private void viewForHeader(YatzyScoreBoard scoreBoard, ScoreListHandler scoreListHandler){
        scoreBoard.yatzyScores.setText(String.format("%s", scoreListHandler.getYatzyScore()));

       /* if(scoreListHandler.getYatzyScore().equals("Sum")){
            scoreBoard.playerOneScore.setVisibility(View.VISIBLE);
            scoreBoard.playerOneScore.setText(String.format("%s", scoreListHandler.getScore(0)));
        }
        else if(scoreListHandler.getYatzyScore().equals("Bonus")){
            scoreBoard.playerOneScore.setVisibility(View.VISIBLE);
            scoreBoard.playerOneScore.setText(String.format("%s", scoreListHandler.getScore(0)));
        }
        else if(scoreListHandler.getYatzyScore().equals("Total")){
            scoreBoard.playerOneScore.setVisibility(View.VISIBLE);
            scoreBoard.playerOneScore.setText(String.format("%s", scoreListHandler.getScore(0)));
        }
        else if(scoreListHandler.getYatzyScore().equals("Total of All")){
            scoreBoard.playerOneScore.setVisibility(View.VISIBLE);
            scoreBoard.playerOneScore.setText(String.format("%s", scoreListHandler.getScore(0)));
        }*/
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
    static class YatzyScoreBoard {
        TextView yatzyScores;
        TextView playerOneScore;
        TextView playerTwoScore;
        TextView playerThreeScore;
        TextView playerFourScore;
        ImageView diceImageOne;
    }
}
