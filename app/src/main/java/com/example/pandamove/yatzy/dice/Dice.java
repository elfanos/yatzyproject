package com.example.pandamove.yatzy.dice;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pandamove.yatzy.player.Player;

import java.io.Serializable;

/**
 * Class for dice contains importan values
 * for the dice in the game
 * @author Rasmus Dahlkvist
 */
public class Dice implements Parcelable{
    private boolean isActive;
    private int score;
    private int surfaceIndex;

    /**
     * Constructor for dice class
     *
     * @param startingState state of the dice on initialize
     * @param defaultScore the default score for the dice
     * @param s_index the dice index to separet dices
     * */
    public Dice(boolean startingState, int defaultScore, int s_index) {
        isActive = startingState;
        this.score = defaultScore;
        this.surfaceIndex = s_index;
    }

    /**
     * @return the index of the dice surfaceIndex
     * */
    public int getSurfaceIndex() {
        return surfaceIndex;
    }

    /**
     * @return the score of the dice
     * */
    public int getScore() {
        return score;
    }

    /**
     * @param score given score to set on the dice
     * */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Paraceble stuff to make the dice paraceble
     * */
    public Dice(Parcel in) {
        super();
        readFromParcel(in);
    }

    /**
     * Auto generated
     * */
    public static final Parcelable.Creator<Dice> CREATOR = new Parcelable.Creator<Dice>() {
        public Dice createFromParcel(Parcel in) {
            return new Dice(in);
        }

        public Dice[] newArray(int size) {

            return new Dice[size];
        }

    };

    public void readFromParcel(Parcel in) {
        isActive = in.readByte() != 0;
        score = in.readInt();
        surfaceIndex = in.readInt();

    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeInt(score);
    }
}
