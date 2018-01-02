package com.example.pandamove.yatzy.score;

import android.widget.TextView;

import com.example.pandamove.yatzy.R;
import com.example.pandamove.yatzy.controllers.CellOnClickListener;
import com.example.pandamove.yatzy.fragments.ScoreViewAdapter;
import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class scorelistHandler works like
 * a decorator and controller for the list adapter
 * in the scorefragment
 *
 * @author Rasmus Dahlkvist
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

    private boolean isHeaderItem;
    private HeaderItem headerItem = null;
    private ArrayList<Integer> differentLayouts;
    private int imageScore;

    /**
     * Constructor for scoreList handler
     * @param players list of all players
     * @param yatzyScore the yatzy score of the handler
     * @param scoreSetted if the score is setted or not
     * @param imageId for the handler to help return right view
     * */
    public ScoreListHandler (List<Player> players, String yatzyScore, boolean scoreSetted, int imageId){
        this.players = players;
        this.yatzyScore = yatzyScore;
        this.scoreSetted = scoreSetted;
        playerOneListener = null;
        playerTwoListener = null;
        playerThreeListener = null;
        playerFourListener = null;
        this.isHeaderItem = false;
        this.differentLayouts = new ArrayList<>();
        this.imageScore = imageId;
        differentLayouts.add(R.drawable.layout_border);
        differentLayouts.add(R.drawable.layout_border_higlight);
        differentLayouts.add(R.drawable.layout_border_scored);
        differentLayouts.add(R.drawable.layout_border_zero_score);
    }
    /**
     * @return the imageScore as an integer
     * */
    public int getImageScore(){
        return imageScore;
    }

    /**
     * @return the yatzy score as a string
     * */
    public String getYatzyScore() {
        return yatzyScore;
    }

    /**
     * @param scorePlayerOne set score on playerOne
     * */
    public void setScorePlayerOne(int scorePlayerOne) {
        this.scorePlayerOne = scorePlayerOne;
    }

    /**
     * @param scorePlayerTwo set score on player two
     * */
    public void setScorePlayerTwo(int scorePlayerTwo) {
        this.scorePlayerTwo = scorePlayerTwo;
    }


    /**
     * @param scorePlayerThree set score on player three
     * */
    public void setScorePlayerThree(int scorePlayerThree) {
        this.scorePlayerThree = scorePlayerThree;
    }

    /**
     * @param scorePlayerFour set score on player four
     * */
    public void setScorePlayerFour(int scorePlayerFour) {
        this.scorePlayerFour = scorePlayerFour;
    }

    public CellOnClickListener getListener(int player, TextView scoreTextView){
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

    /**
     * Set a headerItem with a headerId
     *
     * @param headerId headerId as integer
     * */
    public void setHeaderItem(int headerId){
        isHeaderItem = true;
        headerItem = new HeaderItem(headerId);
        //header pos
        //header
    }

    /**
     * @return isHeaderItem true or false check if
     *          ScoreListHandler contains  a headerItem
     * */
    public boolean isHeaderItem() {
        return isHeaderItem;
    }

    /**
     * @return headerItem item has a new background
     *          as int to define it
     * */
    public int getHeaderItem(int headerColumn){
        return headerItem.getHeaderBackground(headerColumn);
    }

    /**
     * Set header value score
     *
     * @param row first value in the header row
     * @param player current player
     * */
    public void setHeaderValueScore(String row, Player player){
        this.headerItem.setHeaderValue(row, player);
    }


    /**
     * Set a new listener for a
     * cell in the list view
     *
     * @param player current player
     * @param scoreViewAdapter the listview adapter
     * @param yatzyScore the yatzyScore of the row
     * @param position in the listview
     * */
    public void setListener(Player player, ScoreViewAdapter scoreViewAdapter,
                            String yatzyScore, int position){
        switch (player.getColumnPosition()){
            case 0:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerOneListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position
                    );
                    scoreViewAdapter.addToObserveListeners(position, playerOneListener);}
                break;
            case 1:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerTwoListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position);
                    scoreViewAdapter.addToObserveListeners(position, playerTwoListener);
                }
                break;
            case 2:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerThreeListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position);
                    scoreViewAdapter.addToObserveListeners(position,playerThreeListener);
                }
                break;
            case 3:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerFourListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position);
                    scoreViewAdapter.addToObserveListeners(position, playerFourListener);
                }
                break;
        }
    }

    /**
     * Destroy listener
     * @param player the player which
     *               the listener is going to
     *               be destroy on
     * */
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

    /**
     * Return a score background
     *
     * @param player which player background
     *
     * @return playerView as integer depending on
     *         which player sent in the parameter
     * */
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

    /**
     * Set score background to inactive if the cell
     * is inactive
     *
     * @param player current player
     * @param playerPos the player position in the row
     * @param yatzyScore the row yatzyScore
     * @param whichBackground which process the method is called on
     *                        if its temporary scoring or setUsedScore
     *
     * */
    public void setScoreBackgroundInActive(int playerPos, int viewCase,
                                           Player player, String yatzyScore, int whichBackground){
        if(!player.getScoreKeeper().getActive(yatzyScore)){
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
            this.setScoreBackgroundWhenActive(playerPos,viewCase);
        }
    }
    /**
     * Set scorebackground when on active cell
     *
     * @param playerPos which player based on column
     * @param viewCase which background view
     * */
    public void setScoreBackgroundWhenActive(int playerPos, int viewCase){
        switch (playerPos){
            case 0:
                this.setPlayerOneView(viewCase);
                break;
            case 1:
                this.setPlayerTwoView(viewCase);
                break;
            case 2:
                this.setPlayerThreeView(viewCase);
                break;
            case 3:
                this.setPlayerFourView(viewCase);
                break;
        }
    }

    /**
     * Set a new layout in the current cell for a player
     *
     * @param viewCase which background layout on the current cell
     * */
    public void setPlayerOneView(int viewCase) {
        switch (viewCase){
            case 0:
                playerOneView = differentLayouts.get(0);
                break;
            case 1:
                playerOneView = differentLayouts.get(1);
                break;
            case 2:
                playerOneView = differentLayouts.get(2);
                break;
            case 3:
                playerOneView = differentLayouts.get(3);
                break;
        }
    }

    /**
     * Set a new layout in the current cell for a player
     *
     * @param viewCase which background layout on the current cell
     * */
    public void setPlayerTwoView(int viewCase) {
        switch (viewCase){
            case 0:
                this.playerTwoView  = differentLayouts.get(0);
                break;
            case 1:
                this.playerTwoView  = differentLayouts.get(1);
                break;
            case 2:
                this.playerTwoView  = differentLayouts.get(2);
                break;
            case 3:
                this.playerTwoView  = differentLayouts.get(3);
                break;
        }
    }

    /**
     * Set a new layout in the current cell for a player
     *
     * @param viewCase which background layout on the current cell
     * */
    public void setPlayerThreeView(int viewCase) {

        switch (viewCase){
            case 0:
                this.playerThreeView = differentLayouts.get(0);
                break;
            case 1:
                this.playerThreeView = differentLayouts.get(1);
                break;
            case 2:
                this.playerThreeView = differentLayouts.get(2);
                break;
            case 3:
                this.playerThreeView = differentLayouts.get(3);
                break;
        }
    }

    /**
     * Set a new layout in the current cell for a player
     *
     * @param viewCase which background layout on the current cell
     * */
    public void setPlayerFourView(int viewCase) {
        switch (viewCase){
            case 0:
                this.playerFourView = differentLayouts.get(0);
                break;
            case 1:
                this.playerFourView = differentLayouts.get(1);
                break;
            case 2:
                this.playerFourView = differentLayouts.get(2);
                break;
            case 3:
                this.playerFourView = differentLayouts.get(3);
                break;
        }
    }

    /**
     * Get score for a player in a cell
     *
     * @param player the current player
     * */
    public String getScore(int player) {
        switch (player){
            case 0:
                if(scorePlayerOne != 0) {
                    return Integer.toString(scorePlayerOne);
                }else{
                    return "0";
                }
            case 1:
                if(scorePlayerTwo != 0) {
                    return Integer.toString(scorePlayerTwo);
                }else{
                    return "0";
                }
            case 2:
                if(scorePlayerThree != 0) {
                    return Integer.toString(scorePlayerThree);
                }else{
                    return "0";
                }
            case 3:
                if(scorePlayerFour != 0) {
                    return Integer.toString(scorePlayerFour);
                }else{
                    return "0";
                }
            default:
                return "0";
        }
    }

    /**
     * Set the score to a new value
     *
     * @param value the new score
     * @param playerPos the position of the player in the row
     * */
    public void setStateScoreOnPlayer(int value, int playerPos){
        switch (playerPos){
            case 1:
                this.setScorePlayerOne(value);
                break;
            case 2:
                this.setScorePlayerTwo(value);
                break;
            case 3:
                this.setScorePlayerThree(value);
                break;
            case 4:
                this.setScorePlayerFour(value);
                break;
        }
    }

    /**
     * Give a score value based on which state the
     * set score is on
     *
     * @param state the score state
     * @param value the current player values
     * @param player player position in the row
     * @param row the yatzy score
     * */
    public void stateOfSetScore(int state, Player value, int player, String row){
        switch (state){
            case 0:
                this.setStateScoreOnPlayer(
                        value.getScoreKeeper().getScoresPossible(row), player
                );
                break;
            case 1:
                this.setStateScoreOnPlayer(
                        value.getScoreKeeper().getScoreOnRow(row), player
                );
                break;
            case 2:
                this.setForHeader(value,row,value.getColumnPosition());
                break;
        }

    }

    /**
     * check if score cell is active or not
     * and find the right player and go to
     * state score
     *
     * @param player current player
     * @param row the yatzy score
     * @param addOrTemp the state of th score
     * */
    public void setScore(Player player, String row, int addOrTemp) {
        switch (player.getColumnPosition()){
            case 0:
                if(player.getScoreKeeper().getActive(row)) {
                    this.stateOfSetScore(addOrTemp,player,1,row);
                    break;
                }
                break;
            case 1:
                if(player.getScoreKeeper().getActive(row)) {
                    this.stateOfSetScore(addOrTemp,player,2,row);
                    break;
                }
                break;
            case 2:
                if(player.getScoreKeeper().getActive(row)) {
                    this.stateOfSetScore(addOrTemp,player,3,row);
                    break;
                }
                break;
            case 3:
                if(player.getScoreKeeper().getActive(row)) {
                    this.stateOfSetScore(addOrTemp,player,4,row);
                    break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Set value for the row in the header
     *
     * @param player current player the header is set for
     * @param row the yatzyscore on the row
     * */
    private void setForHeader(Player player, String row, int scoreColumn){
        switch (row){
            case "Sum":
                if(player.getScoreKeeper().checkBonus() >= 50) {
                    int total =
                            (player.getScoreKeeper().getSumOfNumbers() +
                                    player.getScoreKeeper().checkBonus());
                    this.setForHeadRightColumn(total,scoreColumn);
                }else {
                    this.setForHeadRightColumn(player.getScoreKeeper().getSumOfNumbers(), scoreColumn);
                }
                break;
            case "Bonus":
                this.setForHeadRightColumn(player.getScoreKeeper().checkBonus(),scoreColumn);
                break;
            case "Total":
                this.setForHeadRightColumn(player.getScoreKeeper().getTotal(),scoreColumn);
                break;
            case "Total of All":
                this.setForHeadRightColumn(player.getScoreKeeper().getTotalOfAll(),scoreColumn);
                break;
        }

    }

    /**
     * Method which add scores to the
     * header section in the list view
     *
     * @param scoreColumn which player cell the score gonna be updated on
     * @param score tecturen score fo the palyer
     * */
    private void setForHeadRightColumn( int score, int scoreColumn){
        switch (scoreColumn){
            case 0:
                this.setScorePlayerOne(score);
                break;
            case 1:
                this.setScorePlayerTwo(score);
                break;
            case 2:
                this.setScorePlayerThree(score);
                break;
            case 3:
                this.setScorePlayerFour(score);
                break;
            default:
        }

    }


    /**
     * @return a list of players
     * */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Class for handling header item in list view
     *
     * @author Rasmus Dahlkvist
     * */
    class HeaderItem {
        private int headerId;
        private int headerOneView;
        private int headerTwoView;
        private int headerThreeView;
        private int headerFourView;
        private int headerFiveView;
        private int headerPlayerOne;
        private int headerPlayerTwo;
        private int headerPlayerThree;
        private int headerPlayerFour;
        private ArrayList<Integer> headerLayouts;
        private boolean textVisible;

        /**
         * Constructor for headerItem class
         *
         * @param id give a header item an id
         * */
        public HeaderItem(int id){
            System.out.println("leid?" + id);
            this.headerId = id;
            headerLayouts = new ArrayList<>();
            headerLayouts.add(R.drawable.header_one_first);
            headerLayouts.add(R.drawable.header_one_second);
            headerLayouts.add(R.drawable.header_one_third);
            headerLayouts.add(R.drawable.header_one_fourth);
            headerLayouts.add(R.drawable.header_one_five);
            headerOneView = headerLayouts.get(0);
            headerTwoView = headerLayouts.get(1);
            headerThreeView = headerLayouts.get(2);
            headerFourView = headerLayouts.get(3);
            headerFiveView = headerLayouts.get(4);

            if(id != 0){
                textVisible = true;
            }else{
                textVisible = false;
            }
        }
        public int getHeaderId() {
            return headerId;
        }

        /**
         * Get view based on which column.
         *
         * @param headerColumn column position on a row in the header
         * */
        public int getHeaderBackground(int headerColumn){
            switch (headerColumn) {
                case 0:
                    return headerOneView;
                case 1:
                    return headerTwoView;
                case 2:
                    return headerThreeView;
                case 3:
                    return headerFourView;
                case 4:
                    return headerFiveView;
                default:
                    return 0;
            }

        }

        /**
         * Set a new header value in the row
         *
         * @param row first name of column in the row
         * @param player current player
         * */
        public void setHeaderValue(String row, Player player){
            switch (player.getColumnPosition()){
                case 0:
                    this.setHeaderValueOnRow(row, player, headerPlayerOne);
                    break;
                case 1:
                    this.setHeaderValueOnRow(row, player, headerPlayerTwo);
                    break;
                case 2:
                    this.setHeaderValueOnRow(row, player, headerPlayerThree);
                    break;
                case 3:
                    this.setHeaderValueOnRow(row, player, headerPlayerFour);
                    break;
            }
        }
        /**
         * Set new header value on the row
         *
         * @param player current playyer
         * @param row first column in the row
         * @param headerValue the new headerValue on the row
         *
         * */
        public void setHeaderValueOnRow(String row, Player player, int headerValue){

            switch (row){
                case "Sum":
                    headerValue= player.getScoreKeeper().getSumOfNumbers();
                    break;
                case "Bonus":
                    headerValue = player.getScoreKeeper().checkBonus();
                    break;
                case "Total":
                    headerValue = player.getScoreKeeper().getTotal();
                    break;
                case "Total of All":
                    headerValue = player.getScoreKeeper().getTotalOfAll();
                    break;
            }
        }
    }


}
