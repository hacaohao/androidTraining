package com.hasbrain.howfastareyou;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by hao on 2/12/2016.
 */
public class Tap {
    private int mCount;
    private Date mDate;

    public Tap(int count, Date date){
        mCount = count;
        mDate = date;
    }

    public int getCount() {
        return mCount;
    }

    public String getDate() {
        return DateFormat.format("dd/MM/yyyy hh:mm:ss", mDate).toString();
    }
}
