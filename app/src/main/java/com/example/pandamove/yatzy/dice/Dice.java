package com.example.pandamove.yatzy.dice;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pandamove.yatzy.player.Player;

import java.io.Serializable;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class Dice implements Parcelable{
    private boolean isActive;
    private int score;
    private int surfaceIndex;

    public Dice(boolean startingState, int defaultScore, int s_index) {
        isActive = startingState;
        this.score = defaultScore;
        this.surfaceIndex = s_index;
    }
    public int getSurfaceIndex() {
        return surfaceIndex;
    }

    public void setSurfaceIndex(int surfaceIndex) {
        this.surfaceIndex = surfaceIndex;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public Dice(Parcel in) {
        super();
        readFromParcel(in);
    }

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
