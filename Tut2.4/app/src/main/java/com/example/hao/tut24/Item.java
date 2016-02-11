package com.example.hao.tut24;

import java.util.List;

/**
 * Created by hao on 2/10/2016.
 */
public class Item {
    public List<Item> invisibleChildren;
    private int mType;
    private String mHeader;
    private Post mPost;

    public Item(int type, String header, Post post) {
        mType = type;
        mPost = post;
        mHeader = header;
    }

    public Post getPost() {
        return mPost;
    }

    public int getType() {
        return mType;
    }

    public String getHeader() {
        return mHeader;
    }
}
