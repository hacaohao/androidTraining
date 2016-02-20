package com.hasbrain.howfastareyou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by hao on 2/19/2016.
 */
public class HighscoreDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "highscore.sqlite";
    private static final int VERSION = 1;
    private static final String TABLE_HIGHSCORE = "highscore";
    private static final String COLUMN_HIGHSCORE_DATE = "date";
    private static final String COLUMN_HIGHSCORE_SCORE = "score";

    public HighscoreDatabaseHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table highscore (" +
                "_id integer primary key autoincrement, date long, score integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertHighscore(Highscore highscore){
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HIGHSCORE_DATE, highscore.getDate().getTime());
        cv.put(COLUMN_HIGHSCORE_SCORE, highscore.getScore());

        return getWritableDatabase().insert(TABLE_HIGHSCORE, null, cv);
    }

    public HighscoreCursor queryHighscore(){
        Cursor wrapped = getReadableDatabase().query(TABLE_HIGHSCORE, null, null, null, null, null, COLUMN_HIGHSCORE_SCORE + " DESC");
        return new HighscoreCursor(wrapped);
    }

    public void clearHighscore(){
        getWritableDatabase().delete(TABLE_HIGHSCORE, null, null);
    }

    public HighscoreCursor queryLastHighscore(){
        Cursor wrapped = getReadableDatabase().query(TABLE_HIGHSCORE,
                new String[]{COLUMN_HIGHSCORE_DATE}, null, null, null, null, COLUMN_HIGHSCORE_DATE + " DESC", "1");
        return new HighscoreCursor(wrapped);
    }

    public static class HighscoreCursor extends CursorWrapper{
        public HighscoreCursor(Cursor cursor){
            super(cursor);
        }

        public Highscore getHighscore(){
            if(isBeforeFirst() || isAfterLast()) return null;

            long date = getLong(getColumnIndex(COLUMN_HIGHSCORE_DATE));
            int score = getInt(getColumnIndex(COLUMN_HIGHSCORE_SCORE));

            return new Highscore(new Date(date), score);
        }

        public Date getLastHighscore(){
            if(isBeforeFirst() || isAfterLast()) return null;
            return new Date(getLong(getColumnIndex(COLUMN_HIGHSCORE_DATE)));
        }
    }
}
