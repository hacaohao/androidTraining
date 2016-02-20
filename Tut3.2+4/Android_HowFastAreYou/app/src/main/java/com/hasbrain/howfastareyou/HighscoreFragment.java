package com.hasbrain.howfastareyou;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

/**
 * Created by hao on 2/19/2016.
 */
public class HighscoreFragment extends Fragment {
    private ListView mListView;
    private static final int LOADER_ID = 0;
    private HighscoreManager mManager;
    public static final String EXTRA_DATE = "last highscore";
    private Date mLastHighscore;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, new LoadHighscore());

        mManager = HighscoreManager.get(getActivity());
        mLastHighscore = (Date)getArguments().getSerializable(EXTRA_DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_highscore, parent, false);

        mListView = (ListView)v.findViewById(R.id.list_view_highscore);

        Button clearButton = (Button) v.findViewById(R.id.button_clear_highscore);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.clearHighscore();
                mListView.setAdapter(null);
            }
        });

        return v;
    }

    public static HighscoreFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        HighscoreFragment fragment = new HighscoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class LoadHighscore implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new HighscoreLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            HighscoreAdapter adapter = new HighscoreAdapter(getActivity(), (HighscoreDatabaseHelper.HighscoreCursor)data, mLastHighscore);
            mListView.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mListView.setAdapter(null);
        }
    }
}
