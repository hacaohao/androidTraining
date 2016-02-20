package com.hasbrain.howfastareyou;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/14/15.
 */
public class TapCountResultFragment extends Fragment {
    private List<Tap> mTaps;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mTaps = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_count, parent, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TapListAdapter(mTaps));

        return v;
    }

    public void addNewTap(Tap tap){
        mTaps.add(tap);
        mRecyclerView.getAdapter().notifyItemInserted(mTaps.size() - 1);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());
            }
        });
    }

    public void clearTapList(){
        mTaps.clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
