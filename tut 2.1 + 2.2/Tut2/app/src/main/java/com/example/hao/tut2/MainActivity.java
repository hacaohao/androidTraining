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
    private ArrayList<Post> mPosts;
    private String mUrl = "https://www.reddit.com/r/androiddev/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPosts = PostLab.get(MainActivity.this).getPosts();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            View footerTextView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_view_footer, null, false);
            footerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPostActivity(null, mUrl);
                }
            });

            ListView listView = (ListView) findViewById(R.id.list_view_post);
            PostListAdapter postAdapter = new PostListAdapter(mPosts);
            listView.addFooterView(footerTextView);
            listView.setAdapter(postAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    UUID postId = mPosts.get(position).getId();
                    startPostActivity(postId, null);
                }
            });

        } else {
            GridView gridView = (GridView) findViewById(R.id.grid_view_post);
            if (MainActivity.this == null || gridView == null) return;

            if (mPosts != null) {
                gridView.setAdapter(new PostGridAdapter(mPosts));
            } else {
                gridView.setAdapter(null);
            }

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    UUID postId = mPosts.get(position).getId();
                    startPostActivity(postId, null);
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
    private class PostListAdapter extends ArrayAdapter<Post> {
        public PostListAdapter(ArrayList<Post> posts) {
            super(MainActivity.this, 0, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Post post = getItem(position);

            convertView = getLayoutInflater().inflate(R.layout.list_item_post, null);

            TextView textViewPostScore = (TextView) convertView.findViewById(R.id.text_view_post_score);
            textViewPostScore.setText(String.valueOf(post.getPostScore()));

            String author = getString(R.string.author, post.getAuthor(), post.getSubreddit());
            TextView textViewAuthor = (TextView) convertView.findViewById(R.id.text_view_author);
            String bonus = "";
            if (position == 0) {//do dòng này nên ko dc tái sử dụng convert view
                bonus = getString(R.string.sticky_post);
            }
            textViewAuthor.setText(Html.fromHtml(author + " " + bonus));

            TextView textViewContent = (TextView) convertView.findViewById(R.id.text_view_content);

            if (position == 0) {//do dòng này nên ko dc tái sử dụng convert view
                textViewContent.setTextColor(Color.parseColor("#387801"));
            }
            textViewContent.setText(post.getContent());


            TextView textViewComment = (TextView) convertView.findViewById(R.id.text_view_comment);
            String comments = (post.getComments() <= 1) ? " comment" : " comments";
            textViewComment.setText(post.getComments() + comments);

            TextView textViewDomain = (TextView) convertView.findViewById(R.id.text_view_domain);
            textViewDomain.setText(post.getDomain());

            TextView textViewDate = (TextView) convertView.findViewById(R.id.text_view_date_time);
            textViewDate.setText(post.getDate() + " hours ago");

            return convertView;
        }
    }

    //dùng cho grid view
    private class PostGridAdapter extends ArrayAdapter<Post> {
        public PostGridAdapter(ArrayList<Post> posts) {
            super(MainActivity.this, 0, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Post post = getItem(position);

            convertView = getLayoutInflater().inflate(R.layout.grid_item_post, parent, false);

            TextView textViewPostScore = (TextView) convertView.findViewById(R.id.text_view_post_score);
            textViewPostScore.setText(String.valueOf(post.getPostScore()));

            TextView textViewAuthor = (TextView) convertView.findViewById(R.id.text_view_author);
            textViewAuthor.setText(post.getAuthor());

            TextView textViewContent = (TextView) convertView.findViewById(R.id.text_view_content);

            if (position == 0) {//do dòng này nên ko dc tái sử dụng convert view
                textViewContent.setTextColor(Color.parseColor("#387801"));
            }
            textViewContent.setText(post.getContent());


            TextView textViewComment = (TextView) convertView.findViewById(R.id.text_view_comment);
            String comments = (post.getComments() <= 1) ? " comment" : " comments";
            textViewComment.setText(post.getComments() + comments);

            TextView textViewDomain = (TextView) convertView.findViewById(R.id.text_view_domain);
            textViewDomain.setText(post.getDomain());

            TextView textViewDate = (TextView) convertView.findViewById(R.id.text_view_date_time);
            textViewDate.setText(post.getDate() + " hours ago");

            return convertView;
        }
    }
}
