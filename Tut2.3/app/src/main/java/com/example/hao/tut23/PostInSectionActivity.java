package com.example.hao.tut23;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PostInSectionActivity extends AppCompatActivity {
    private ArrayList<String> mGroups;
    private HashMap<String, ArrayList<Post>> mGroupChild;
    private ArrayList<Post> mPosts;
    private String mUrl = "https://www.reddit.com/r/androiddev/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_in_section);

        mPosts = PostLab.get(PostInSectionActivity.this).getPosts();

        prepareListData();

        View footerTextView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_view_footer, null, false);
        footerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostActivity(null, mUrl);
            }
        });

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expand_list_view);
        ExpandableListAdapter adapter = new ExpandableListAdapter(PostInSectionActivity.this, mGroups, mGroupChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                UUID postId = mGroupChild.get(mGroups.get(groupPosition)).get(childPosition).getId();
                startPostActivity(postId, null);
                return true;
            }
        });
        expandableListView.addFooterView(footerTextView);
    }

    private void prepareListData() {
        mGroups = new ArrayList<String>();
        mGroupChild = new HashMap<String, ArrayList<Post>>();

        mGroups.add("Stiky posts");
        mGroups.add("Normal posts");

        ArrayList<Post> stikyPost = new ArrayList<Post>();
        for (Post post : mPosts) {
            if (post.isSticky()) {
                stikyPost.add(post);
            }
        }

        ArrayList<Post> normalPost = new ArrayList<Post>();
        for (Post post : mPosts) {
            if (!post.isSticky()) {
                normalPost.add(post);
            }
        }

        mGroupChild.put(mGroups.get(0), stikyPost);
        mGroupChild.put(mGroups.get(1), normalPost);
    }

    private void startPostActivity(UUID id, String url) {
        Intent intent = new Intent(PostInSectionActivity.this, PostViewActivity.class);
        intent.putExtra(PostViewActivity.EXTRA_ID, id);
        intent.putExtra(PostViewActivity.EXTRA_URL, url);

        startActivity(intent);
    }
}
