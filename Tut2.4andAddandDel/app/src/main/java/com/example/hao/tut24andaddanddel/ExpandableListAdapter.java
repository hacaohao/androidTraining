package com.example.hao.tut24andaddanddel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by hao on 2/10/2016.
 */
public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final int LAND = 2;
    public static final int LIST_FOOTER = 3;

    private static final String mUrl = "https://www.reddit.com/r/androiddev/";
    private Context mContext;
    private List<Item> mItems;
    private View mFooter;
    private MultiSelector mMultiSelector;
    private ModalMultiSelectorCallback mDeleteMode;

    public ExpandableListAdapter(Context context, List<Item> items, View footer) {
        mContext = context;
        mItems = items;
        mFooter = footer;
    }

    public void setMultiSelector(MultiSelector multiSelector) {
        mMultiSelector = multiSelector;
    }

    public void setDeleteMode(ModalMultiSelectorCallback deleteMode) {
        mDeleteMode = deleteMode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        Context context = parent.getContext();
        LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        switch (viewType) {
            case HEADER:
                v = inflater.inflate(R.layout.list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(v);
                return header;
            case CHILD:
                v = inflater.inflate(R.layout.list_item, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(v);
                return child;
            case LAND:
                v = inflater.inflate(R.layout.grid_item_post, parent, false);
                ListChildViewHolder landChild = new ListChildViewHolder(v);
                return landChild;
            case LIST_FOOTER:
                v = inflater.inflate(R.layout.text_view_footer, parent, false);
                ListFooterViewHolder footer = new ListFooterViewHolder(v);
                return footer;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == mItems.size()) return;
        final Item item = mItems.get(position);
        int type = item.getType();
        switch (type) {
            case HEADER:
                ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.bind(item);
                break;

            case LAND:
            case CHILD:
                Post post = item.getPost();
                ListChildViewHolder childController = (ListChildViewHolder) holder;
                childController.bind(post);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mFooter != null) return mItems.size() + 1;
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionFooter(position)) return LIST_FOOTER;
        return mItems.get(position).getType();
    }

    private boolean isPositionFooter(int position){
        return position == mItems.size();
    }
    
    private class ListHeaderViewHolder extends SwappingHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;

        public ListHeaderViewHolder(View itemView) {
            super(itemView, mMultiSelector);//nhớ pass mMultiSelector vào
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);

        }

        public void bind(final Item item){
            header_title.setText(item.getHeader());

            if (item.invisibleChildren == null) {
                btn_expand_toggle.setImageResource(R.drawable.circle_minus);
            } else {
                btn_expand_toggle.setImageResource(R.drawable.circle_plus);
            }
            btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.invisibleChildren == null) {
                        item.invisibleChildren = new ArrayList<Item>();
                        int count = 0;
                        int pos = mItems.indexOf(item);

                        while (mItems.size() > pos + 1 && mItems.get(pos + 1).getType() == CHILD) {
                            item.invisibleChildren.add(mItems.remove(pos + 1));
                            count++;
                        }

                        notifyItemRangeRemoved(pos + 1, count);
                        btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                    } else {
                        int pos = mItems.indexOf(item);
                        int index = pos + 1;

                        for (Item i : item.invisibleChildren) {
                            mItems.add(index, i);
                            index++;
                        }

                        notifyItemRangeInserted(pos + 1, index - pos - 1);
                        btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                        item.invisibleChildren = null;
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((AppCompatActivity) mContext).startSupportActionMode(mDeleteMode);
                    return true;

                }
            });
        }
    }

    private class ListChildViewHolder extends SwappingHolder {
        public TextView textViewPostScore;
        public TextView textViewAuthor;
        public TextView textViewContent;
        public TextView textViewComment;
        public TextView textViewDomain;
        public TextView textViewDate;

        public ListChildViewHolder(View itemView) {
            super(itemView, mMultiSelector);//nhớ pass mMultiSelector vào
            textViewPostScore = (TextView) itemView.findViewById(R.id.text_view_post_score);
            textViewAuthor = (TextView) itemView.findViewById(R.id.text_view_author);
            textViewContent = (TextView) itemView.findViewById(R.id.text_view_content);
            textViewComment = (TextView) itemView.findViewById(R.id.text_view_comment);
            textViewDomain = (TextView) itemView.findViewById(R.id.text_view_domain);
            textViewDate = (TextView) itemView.findViewById(R.id.text_view_date_time);
        }

        public void bind(final Post post){
            textViewPostScore.setText(String.valueOf(post.getPostScore()));

            String comments = (post.getComments() <= 1) ? " comment" : " comments";
            textViewComment.setText(post.getComments() + comments);

            textViewDomain.setText(post.getDomain());

            textViewDate.setText(post.getDate() + " hours ago");

            if (post.isSticky()) {
                textViewContent.setTextColor(Color.parseColor("#387801"));
            }else {
                textViewContent.setTextColor(Color.parseColor("#909090"));
            }
            textViewContent.setText(post.getContent());

            String author = mContext.getString(R.string.author, post.getAuthor(), post.getSubreddit());
            textViewAuthor.setText(Html.fromHtml(author));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mMultiSelector.tapSelection(ListChildViewHolder.this)) {
                        startPostActivity(post.getId(), null);
                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((AppCompatActivity) mContext).startSupportActionMode(mDeleteMode);
                    mMultiSelector.setSelected(ListChildViewHolder.this, true);
                    return true;

                }
            });
        }
    }

    private class ListFooterViewHolder extends SwappingHolder{

        public ListFooterViewHolder(View itemView) {
            super(itemView, mMultiSelector);//nhớ pass mMultiSelector vào
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPostActivity(null, mUrl);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((AppCompatActivity) mContext).startSupportActionMode(mDeleteMode);
                    return true;
                }
            });
        }
    }

    private void startPostActivity(UUID id, String url) {
        Intent intent = new Intent(mContext, PostViewActivity.class);
        intent.putExtra(PostViewActivity.EXTRA_ID, id);
        intent.putExtra(PostViewActivity.EXTRA_URL, url);

        mContext.startActivity(intent);
    }
}
