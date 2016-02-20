package com.example.hao.tut2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hao on 2/17/2016.
 */
public class ListAdapter extends ArrayAdapter<Post> {

    public ListAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Post post = getItem(position);

        TextView textViewPostScore = (TextView) convertView.findViewById(R.id.text_view_post_score);
        textViewPostScore.setText(String.valueOf(post.getPostScore()));

        TextView textViewAuthor = (TextView) convertView.findViewById(R.id.text_view_author);
        textViewAuthor.setText(post.getAuthor());

        TextView textViewContent = (TextView) convertView.findViewById(R.id.text_view_content);

        if (post.isSticky()) {
            textViewContent.setTextColor(Color.parseColor("#387801"));
        }else {
            textViewContent.setTextColor(Color.parseColor("#909090"));
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
