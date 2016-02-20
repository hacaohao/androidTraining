package com.example.hao.tut24andaddanddel;

import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PostInSectionActivity extends AppCompatActivity implements NewPostDialog.NewPostDialogListener {
    private static final String STICKY_POST = "Sticky post";
    private static final String NORMAL_POST = "Normal post";
    private static final String NEW_POST = "New post";
    private static final String TAG = "crimeListFragment";
    
    private List<Item> mItems;
    private PostLab mPostLab;
    private ArrayList<Post> mPosts;
    private FragmentManager mManager;

    public RecyclerView mRecyclerView;
    private PostMultiSelector mMultiSelector = new PostMultiSelector();
    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu){
            super.onCreateActionMode(actionMode, menu);
            getMenuInflater().inflate(R.menu.context_menu_post_in_action, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.menu_item_del){
                // Need to finish the action mode before doing the following,
                // not after. No idea why, but it crashes.
                mode.finish();
                for(int i = mPosts.size(); i >= 0; i--){
                    if(mMultiSelector.isSelected(i, 0)){
                        Item deleteItem = mItems.get(i);
                        Post deletePost = deleteItem.getPost();
                        mPostLab.deletePost(i);
                        mItems.remove(deleteItem);
                        mRecyclerView.getAdapter().notifyItemRemoved(i);
                    }
                }
                mMultiSelector.clearSelections();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_in_section);

        mManager = getFragmentManager();
        mItems = new ArrayList<>();
        mPostLab = PostLab.get(this);
        mPosts = PostLab.get(PostInSectionActivity.this).getPosts();
        mMultiSelector.setOffset(PostLab.get(this).getStickyCount());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        View footerTextView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_view_footer, null, false);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ExpandableListAdapter adapter = null;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            preparePortraitData();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(PostInSectionActivity.this, LinearLayoutManager.VERTICAL, false));
            adapter = new ExpandableListAdapter(PostInSectionActivity.this, mItems, footerTextView);
            
        } else {
            prepareLandscapeData();
            mRecyclerView.setLayoutManager(new GridLayoutManager(PostInSectionActivity.this, 3));
            adapter = new ExpandableListAdapter(PostInSectionActivity.this, mItems, null);
        }
        adapter.setDeleteMode(mDeleteMode);
        adapter.setMultiSelector(mMultiSelector);
        mRecyclerView.setAdapter(adapter);

        if (mMultiSelector != null) {
            if (savedInstanceState != null) {
                mMultiSelector.restoreSelectionStates(savedInstanceState.getBundle(TAG));
            }

            if (mMultiSelector.isSelectable()) {
                if (mDeleteMode != null) {
                    mDeleteMode.setClearOnPrepare(false);
                    startSupportActionMode(mDeleteMode);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBundle(TAG, mMultiSelector.saveSelectionStates());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu_post_in_section, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_add) {
            NewPostDialog dialog = new NewPostDialog();
            dialog.show(mManager, NEW_POST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResultReturn(String postTitle){
        Post newPost = new Post(1, "AutoModerator", "androiddev", postTitle, 0, "self.androiddev", 1, true);
        mPostLab.addPost(0, newPost);
        Item newItem = new Item(ExpandableListAdapter.CHILD, STICKY_POST, newPost);
        mItems.add(1,newItem);
        mRecyclerView.getAdapter().notifyItemInserted(mItems.indexOf(newItem));
    }

    private void preparePortraitData() {
        mMultiSelector.setPortrait(true);
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
        mMultiSelector.setPortrait(false);
        for (Post post : mPosts){
            mItems.add(new Item(ExpandableListAdapter.LAND, null, post));
        }
    }
}
