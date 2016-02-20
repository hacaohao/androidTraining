package com.hasbrain.howfastareyou;

import android.annotation.TargetApi;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;

/**
 * Created by hao on 2/19/2016.
 */
public class HighscoreLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    private Context mContext;

    public HighscoreLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Cursor loadInBackground() {
        return HighscoreManager.get(mContext).queryHighscore();
    }

    @Override
    public void deliverResult(Cursor data){
        Cursor oldCursor = mCursor;
        mCursor = data;

        if(isStarted()){
            super.deliverResult(data);
        }

        if(oldCursor != null && oldCursor != data && !oldCursor.isClosed()){
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading(){
        if(mCursor != null){
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null){
            forceLoad();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStopLoading(){
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor){
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if(mCursor != null && !mCursor.isClosed()){
            mCursor.close();
        }

        mCursor = null;
    }
}
