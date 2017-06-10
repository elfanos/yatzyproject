package com.example.pandamove.yatzy.score;

import com.example.pandamove.yatzy.player.Player;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rallmo on 2017-04-06.
 */
public class ScoreListHandler {

    private List<Player> players;
    private int score;
    private int scorePlayerOne;
    private int scorePlayerTwo;
    private int scorePlayerThree;
    private int scorePlayerFour;
    private boolean scoreSetted;
    private String yatzyScore;
    private HashMap<String,Integer> scoresInColumn;

    public ScoreListHandler (List<Player> players, String yatzyScore, boolean scoreSetted){
        scoresInColumn = new HashMap<>();
        this.players = players;
        this.yatzyScore = yatzyScore;
        this.scoreSetted = scoreSetted;
    }
    public int checkWhichColumn(){
        System.out.println("check columns :D");
        if(!this.isScoreSetted()){
            System.out.println("hola inside here");
            for(int i = 0; i < players.size(); i++){
                System.out.println(players.get(i).isCurrentPlayer());
                if(players.get(i).isCurrentPlayer()){
                    System.out.println("never here?");
                    System.out.println("Column pos?: " + players.get(i).getColumnPosition());
                    return players.get(i).getColumnPosition();
                }
            }
        }
        return 0;
    }
    public int checkScore(String player){
        switch (player){
            case "one":

                return players.get(0).getScoreKeeper().getScore();
            case "two":
                return players.get(1).getScoreKeeper().getScore();
            case "three":
                return players.get(2).getScoreKeeper().getScore();
            case "four":
                //System.out.println(players.get(3).getScoreKeeper().getScore());
                return players.get(3).getScoreKeeper().getScore();
            default:
        }
        return 0;
    }

    public String getYatzyScore() {
        return yatzyScore;
    }


    /**
     * When all score is setted this will change to true
     *
     * @param bol
     * */
    public void setIsScoreSetted(boolean bol){
        this.scoreSetted = bol;
    }
    public boolean isScoreSetted() {
        return scoreSetted;
    }
    public int getScore(String player) {
        switch (player){
            case "one":
                return scorePlayerOne;
            case "two":
                return scorePlayerTwo;
            case "three":
                return scorePlayerThree;
            case "four":
                return scorePlayerFour;
            default:
                return 0;
        }
    }

    public void setScore(int score, int player, String row) {
        System.out.println("wtfs??");
        if(players.get(player).isCurrentPlayer()){
            System.out.println("wtfs??");
            players.get(player).
                    getScoreKeeper().setScore(row , score);
        }
        this.score = score;
        switch (player){
            case 0:
                this.scorePlayerOne = score;
                break;
            case 1:
                this.scorePlayerTwo = score;
                break;
            case 2:
                this.scorePlayerThree = score;
                break;
            case 3:
                this.scorePlayerFour = score;
                break;
            default:
                this.score = score;
                break;
        }

    }

    public List<Player> getPlayers() {
        return players;
    }


}
