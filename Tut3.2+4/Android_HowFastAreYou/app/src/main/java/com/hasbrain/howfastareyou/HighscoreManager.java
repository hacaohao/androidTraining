package com.hasbrain.howfastareyou;

import android.content.Context;

import java.util.Date;

/**
 * Created by hao on 2/19/2016.
 */
public class HighscoreManager {
    private Context mContext;
    public static HighscoreManager sHighscoreManager;
    private HighscoreDatabaseHelper mHelper;

    private HighscoreManager(Context context){
        mContext = context;
        mHelper = new HighscoreDatabaseHelper(context);
    }

    public static HighscoreManager get(Context context){
        if(sHighscoreManager == null){
            return new HighscoreManager(context.getApplicationContext());

        }
        return sHighscoreManager;
    }

    public long insertHighscore(int score){
        return mHelper.insertHighscore(new Highscore(score));
    }

    public HighscoreDatabaseHelper.HighscoreCursor queryHighscore(){
        return mHelper.queryHighscore();
    }

    public void clearHighscore(){
        mHelper.clearHighscore();
    }

    public Date getLastHighscore(){
        Date lastHighscore = null;
        HighscoreDatabaseHelper.HighscoreCursor cursor = mHelper.queryLastHighscore();
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) lastHighscore = cursor.getLastHighscore();
        cursor.close();
        return lastHighscore;
    }
}
