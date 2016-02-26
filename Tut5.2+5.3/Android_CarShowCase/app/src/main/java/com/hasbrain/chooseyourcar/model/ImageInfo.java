package com.hasbrain.chooseyourcar.model;

/**
 * Created by hao on 2/25/2016.
 */
public class ImageInfo {
    private String mId;
    private boolean mIsFullSize;

    public ImageInfo(String id, boolean isFullSize){
        mId = id;
        mIsFullSize = isFullSize;
    }

    public String getId() {
        return mId;
    }

    public boolean isFullSize() {
        return mIsFullSize;
    }
}
