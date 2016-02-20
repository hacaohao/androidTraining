package com.hasbrain.howfastareyou;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by hao on 2/19/2016.
 */
public class HighscoreAdapter extends CursorAdapter {
    private HighscoreDatabaseHelper.HighscoreCursor mCursor;
    private Date mLastHighscore;

    public HighscoreAdapter(Context context, HighscoreDatabaseHelper.HighscoreCursor cursor, Date lastHighscore){
        super(context, cursor, 0);
        mCursor = cursor;
        mLastHighscore = lastHighscore;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.list_item_detail, parent ,false);
        return v;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Highscore highscore = mCursor.getHighscore();

        TextView dateTextView = (TextView) view.findViewById(R.id.tap_date);
        dateTextView.setText(DateFormat.format("dd/MM/yyyy hh:mm:ss", highscore.getDate()).toString());
        if(mLastHighscore != null && highscore.getDate().getTime() == mLastHighscore.getTime()) {
            dateTextView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            dateTextView.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGrey));
        }

        TextView scoreTextView = (TextView)view.findViewById(R.id.tap_count);
        scoreTextView.setText(String.valueOf(highscore.getScore()));
        if(mLastHighscore != null && highscore.getDate().getTime() == mLastHighscore.getTime()) {
            scoreTextView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            scoreTextView.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            scoreTextView.setBackgroundColor(context.getResources().getColor(android.R.color.background_light));
            scoreTextView.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));
            scoreTextView.setTextColor(context.getResources().getColor(R.color.colorText));
        }
    }
}
