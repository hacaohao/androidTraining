package com.example.hao.tut24;

import java.util.UUID;

/**
 * Created by hao on 2/7/2016.
 */
public class Post {
    private UUID mId;
    private int mPostScore;
    private String mAuthor;
    private String mSubreddit;
    private String mContent;
    private int mComments;
    private String mDomain;
    private int mDate;
    private boolean mIsSticky;

    public Post(int postScore, String author, String subreddit, String content, int comments, String domain, int date, boolean isSticky) {
        mId = UUID.randomUUID();
        mPostScore = postScore;
        mAuthor = author;
        mSubreddit = subreddit;
        mComments = comments;
        mContent = content;
        mDate = date;
        mDomain = domain;
        mIsSticky = isSticky;
    }

    public int getPostScore() {
        return mPostScore;
    }


    public String getAuthor() {
        return mAuthor;
    }


    public String getSubreddit() {
        return mSubreddit;
    }


    public String getContent() {
        return mContent;
    }


    public int getComments() {
        return mComments;
    }


    public String getDomain() {
        return mDomain;
    }


    public int getDate() {
        return mDate;
    }

    public boolean isSticky() {
        return mIsSticky;
    }

    public UUID getId() {
        return mId;
    }
}
