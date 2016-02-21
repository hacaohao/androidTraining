package com.hasbrain.howfastareyou;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/14/15.
 */
public class TapCountResultFragment extends Fragment {
    private static final String SAVED_COUNT = "saved count";
    private TextView mTapCount;
    private int mCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mCount = savedInstanceState.getInt(SAVED_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_count, parent, false);

        mTapCount = (TextView) v.findViewById(R.id.text_view_tap_count);
        mTapCount.setText(String.valueOf(mCount));

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt(SAVED_COUNT, mCount);
    }

    public void addNewTap(int tap){
        mCount = tap;
        mTapCount.setText(String.valueOf(mCount));
    }
}
