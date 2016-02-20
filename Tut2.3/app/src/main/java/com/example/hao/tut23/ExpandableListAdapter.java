package com.example.hao.tut23;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hao on 2/9/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<String> mGroups;
    private HashMap<String, ArrayList<Post>> mGroupChild;

    public ExpandableListAdapter(Context context, ArrayList<String> groups, HashMap<String, ArrayList<Post>> groupChild) {
        mContext = context;
        mGroups = groups;
        mGroupChild = groupChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.mGroupChild.get(this.mGroups.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Post post = (Post) getChild(groupPosition, childPosition);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, null);

        TextView textViewPostScore = (TextView) convertView.findViewById(R.id.text_view_post_score);
        textViewPostScore.setText(String.valueOf(post.getPostScore()));

        String author = mContext.getString(R.string.author, post.getAuthor(), post.getSubreddit());
        TextView textViewAuthor = (TextView) convertView.findViewById(R.id.text_view_author);
        textViewAuthor.setText(Html.fromHtml(author));

        TextView textViewContent = (TextView) convertView.findViewById(R.id.text_view_content);

        if (groupPosition == 0) {//do dòng này nên ko dc tái sử dụng convert view
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

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mGroupChild.get(this.mGroups.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mGroups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.text_view_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
