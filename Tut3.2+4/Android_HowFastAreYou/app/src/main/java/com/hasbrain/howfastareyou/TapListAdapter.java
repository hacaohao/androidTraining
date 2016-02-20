package com.hasbrain.howfastareyou;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hao on 2/12/2016.
 */
public class TapListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Tap> mTaps;

    public TapListAdapter(List<Tap> taps){
        mTaps = taps;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_detail, parent, false);
        TapViewHolder viewHolder = new TapViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tap tap = mTaps.get(position);
        TapViewHolder viewHolder = (TapViewHolder) holder;
        viewHolder.bind(tap);
    }

    @Override
    public int getItemCount() {
        return mTaps.size();
    }

    private class TapViewHolder extends RecyclerView.ViewHolder{
        public TextView mTime;
        public TextView mCount;

        public TapViewHolder(View itemView){
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.tap_date);
            mCount = (TextView) itemView.findViewById(R.id.tap_count);
        }

        public void bind(Tap tap){
            mTime.setText(tap.getDate());
            mCount.setText(String.valueOf(tap.getCount()));
        }
    }
}
