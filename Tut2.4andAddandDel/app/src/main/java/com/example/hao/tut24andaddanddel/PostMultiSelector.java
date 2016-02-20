package com.example.hao.tut24andaddanddel;

import java.util.List;

/**
 * Created by hao on 2/17/2016.
 */
public class PostMultiSelector extends MultiSelector {
    private int mOffset = 0;
    private boolean mPortrait = true;

    public PostMultiSelector(){
        super();
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    public void setPortrait(boolean portrait) {
        mPortrait = portrait;
    }

    @Override
    protected void restoreSelections(List<Integer> selected) {
        if (selected == null) return;
        int position;
        mSelections.clear();
        for (int i = 0; i < selected.size(); i++) {
            position = selected.get(i);
            int offset;
            if(mPortrait) {
                if (position >= mOffset) {
                    offset = 2;
                } else {
                    offset = 1;
                }
            }else{
                if (position > mOffset) {
                    offset = 2;
                } else {
                    offset = 1;
                }
                offset = - offset;
            }
            mSelections.put(position + offset, true);
        }
        refreshAllHolders();
    }
}
