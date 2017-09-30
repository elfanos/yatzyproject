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

    private int[] visability = {
            0,4,8
    };
    private int[] playerID = {
            1,2,3,4
    };
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

    public void setHeaderItem(int headerId){
        isHeaderItem = true;
        headerItem = new HeaderItem(headerId);
        //header pos
        //header
    }
    public boolean isHeaderItem() {
        return isHeaderItem;
    }
    public int getHeaderItem(int headerColumn){
        return headerItem.getHeaderBackground(headerColumn);
    }
    public void setHeaderValueScore(String row, Player player){
        this.headerItem.setHeaderValue(row, player);
    }
    public String getHeaderScore(int playerPos){
        return this.headerItem.getHeaderScore(playerPos);
    }
    public boolean headerVisible(int headerColumn){
        return headerItem.getVisible(headerColumn);
    }
    public void setListener(Player player, ScoreViewAdapter scoreViewAdapter,
                            String yatzyScore, int position){
        switch (player.getColumnPosition()){
            case 0:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerOneListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position
                    );
                    scoreViewAdapter.addToObserveListeners(position, playerOneListener);
                }
                break;
            case 1:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerTwoListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position
                    );
                    scoreViewAdapter.addToObserveListeners(position, playerTwoListener);
                }
                break;
            case 2:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerThreeListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position
                    );
                    scoreViewAdapter.addToObserveListeners(position,playerThreeListener);
                }
                break;
            case 3:
                if(player.getScoreKeeper().getActive(yatzyScore)) {
                    playerFourListener = new CellOnClickListener(
                            player, scoreViewAdapter, yatzyScore, position
                    );
                    scoreViewAdapter.addToObserveListeners(position, playerFourListener);
                }
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
    public void setScoreBackground(int playerPos, int viewCase,
                                   Player player, String yatzyScore, int whichBackground){
        //System.out.println("how manyawd" + viewCase);
        if(!player.getScoreKeeper().getActive(yatzyScore)){
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
                        case 3:
                            playerOneView = differentLayouts.get(3);
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
                        case 3:
                            playerTwoView = differentLayouts.get(3);
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
                        case 3:
                            playerThreeView = differentLayouts.get(3);
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
                        case 3:
                            playerFourView = differentLayouts.get(3);
                            break;
                    }
                    break;
            }
        }
    }

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
    public void setScore(Player player, String row, int addOrTemp) {
        switch (player.getColumnPosition()){
            case 0:
                if(player.getScoreKeeper().getActive(row)) {
                    if(addOrTemp == 1) {
                        // player.getScoreKeeper().setUsedScore(row);
                        this.scorePlayerOne = player.getScoreKeeper().getScoreOnRow(row);
                    }else if(addOrTemp == 0) {
                        this.scorePlayerOne = player.getScoreKeeper().getScoresPossible(row);
                    }else if(addOrTemp == 2){
                        this.setForHeader(player,row,player.getColumnPosition());
                    }
                   // players.get(player).getScoreKeeper().setColumnScore(row);
                    break;
                }
                break;
            case 1:
                if(player.getScoreKeeper().getActive(row)) {
                    if(addOrTemp == 1) {
                        // player.getScoreKeeper().setUsedScore(row);
                        this.scorePlayerTwo = player.getScoreKeeper().getScoreOnRow(row);
                    }else if(addOrTemp == 0) {
                        this.scorePlayerTwo = player.getScoreKeeper().getScoresPossible(row);
                    }else if(addOrTemp == 2){
                        this.setForHeader(player,row,player.getColumnPosition());
                    }
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
                        this.setForHeader(player,row,player.getColumnPosition());
                    }
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
                        this.setForHeader(player,row,player.getColumnPosition());
                    }
                    break;
                }
                break;
            default:
                break;
        }
    }
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
    public boolean getVisible(int player){
        switch (player){
            case 0:
                return this.getPlayers().get(player).isCurrentPlayer();
            case 1:
                return this.getPlayers().get(player).isCurrentPlayer();
            case 2:
                return this.getPlayers().get(player).isCurrentPlayer();
            case 3:
                return this.getPlayers().get(player).isCurrentPlayer();
            default:
                return false;
        }

    }

    public List<Player> getPlayers() {
        return players;
    }

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
        public String getHeaderScore(int player) {
            switch (player){
                case 0:
                    if(headerPlayerOne != 0) {
                        return Integer.toString(headerPlayerOne);
                    }else{
                        return "0";
                    }
                case 1:
                    if(headerPlayerTwo != 0) {
                        return Integer.toString(headerPlayerTwo);
                    }else{
                        return "0";
                    }
                case 2:
                    if(headerPlayerThree != 0) {
                        return Integer.toString(headerPlayerThree);
                    }else{
                        return "0";
                    }
                case 3:
                    if(headerPlayerFour != 0) {
                        return Integer.toString(headerPlayerFour);
                    }else{
                        return "0";
                    }
                default:
                    return "0";
            }
        }
        public void setHeaderId(int headerId) {
            this.headerId = headerId;
        }
        public boolean getVisible(int headerColumn){
            if(headerColumn != 0){
                return true;
            }else{
                return false;
            }
        }
    }


}
