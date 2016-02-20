package com.example.hao.tut2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private PostLab mPostLab;
    private String mUrl = "https://www.reddit.com/r/androiddev/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPostLab = PostLab.get(MainActivity.this);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            View footerTextView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_view_footer, null, false);
            footerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPostActivity(null, mUrl);
                }
            });

            ListView listView = (ListView) findViewById(R.id.list_view_post);
            PostListAdapter postAdapter = new PostListAdapter(mPostLab.getPosts());
            listView.addFooterView(footerTextView);
            listView.setAdapter(postAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startNewPost(position);
                }
            });

        } else {
            GridView gridView = (GridView) findViewById(R.id.grid_view_post);
            if (MainActivity.this == null || gridView == null) return;

            if (mPostLab != null) {
                gridView.setAdapter(new PostGridAdapter(mPostLab.getPosts()));
            } else {
                gridView.setAdapter(null);
            }

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startNewPost(position);
                }
            });
        }
    }

    private void startPostActivity(UUID id, String url) {
        Intent intent = new Intent(MainActivity.this, PostViewActivity.class);
        intent.putExtra(PostViewActivity.EXTRA_ID, id);
        intent.putExtra(PostViewActivity.EXTRA_URL, url);

        startActivity(intent);
    }

    //dùng cho list view
    private class PostListAdapter extends ListAdapter {
        public PostListAdapter(ArrayList<Post> posts) {
            super(MainActivity.this, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Post post = getItem(position);
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_post, null);
            }

            String author = getString(R.string.author, post.getAuthor(), post.getSubreddit());
            TextView textViewAuthor = (TextView) convertView.findViewById(R.id.text_view_author);
            String bonus = "";
            if (post.isSticky()) {//do dòng này nên ko dc tái sử dụng convert view
                bonus = getString(R.string.sticky_post);
            }else{
                bonus = "";
            }
            textViewAuthor.setText(Html.fromHtml(author + " " + bonus));

            return super.getView(position, convertView, parent);
        }
    }

    //dùng cho grid view
    private class PostGridAdapter extends ListAdapter {
        public PostGridAdapter(ArrayList<Post> posts) {
            super(MainActivity.this, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.grid_item_post, parent, false);
            }
            return super.getView(position, convertView, parent);
        }
    }

    private void startNewPost(int position){
        ArrayList<Post> posts = mPostLab.getPosts();
        UUID postId = posts.get(position).getId();
        startPostActivity(postId, null);
    }
}
