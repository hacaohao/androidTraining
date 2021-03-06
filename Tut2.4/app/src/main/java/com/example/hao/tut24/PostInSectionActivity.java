package com.example.hao.tut24;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hao on 2/11/2016.
 */
public class PostInSectionActivity extends AppCompatActivity {
    private static final String STICKY_POST = "Sticky post";
    private static final String NORMAL_POST = "Normal post";
    private List<Item> mItems;
    private ArrayList<Post> mPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_in_section);

        View footerTextView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_view_footer, null, false);

        mItems = new ArrayList<>();
        mPosts = PostLab.get(PostInSectionActivity.this).getPosts();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            preparePortraitData();
            recyclerView.setLayoutManager(new LinearLayoutManager(PostInSectionActivity.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new ExpandableListAdapter(PostInSectionActivity.this, mItems, footerTextView));
        } else {
            prepareLandscapeData();
            recyclerView.setLayoutManager(new GridLayoutManager(PostInSectionActivity.this, 3));
            recyclerView.setAdapter(new ExpandableListAdapter(PostInSectionActivity.this, mItems, null));
        }
    }

    private void preparePortraitData() {
        mItems.add(new Item(ExpandableListAdapter.HEADER, STICKY_POST, null));
        for (Post post : mPosts) {
            if (post.isSticky()) mItems.add(new Item(ExpandableListAdapter.CHILD, null, post));
        }
        mItems.add(new Item(ExpandableListAdapter.HEADER, NORMAL_POST, null));
        for (Post post : mPosts) {
            if (!post.isSticky()) mItems.add(new Item(ExpandableListAdapter.CHILD, null, post));
        }
    }

    private void prepareLandscapeData() {
        for (Post post : mPosts) {
            mItems.add(new Item(ExpandableListAdapter.LAND, null, post));
        }
    }
}
