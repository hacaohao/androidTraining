package com.hasbrain.howfastareyou;

import java.util.Date;

/**
 * Created by hao on 2/19/2016.
 */
public class Highscore {
    private Date mDate;
    private int mScore;

    public Highscore(Date date, int score){
        mDate = date;
        mScore = score;
    }

    public Highscore(int score){
        mDate = new Date();
        mScore = score;
    }

    public Date getDate() {
        return mDate;
    }

    public int getScore() {
        return mScore;
    }
}
