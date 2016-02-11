package com.example.hao.tut24;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by hao on 2/7/2016.
 */
public class PostLab {
    private static PostLab sPost;
    private static Context mAppContext;
    private ArrayList<Post> mPosts;

    private PostLab(Context context) {
        mAppContext = context;
        mPosts = new ArrayList<Post>();
        mPosts.add(new Post(11, "AutoModerator", "androiddev", "Weekly \"who's hiring\" thread", 19, "self.androiddev", 2, true));
        mPosts.add(new Post(23, "Zhuinden", "androiddev", "Realm.io DB has finally added Nullable types to 0.83.0-SNAPSHOT", 3, "github.com", 5, false));
        mPosts.add(new Post(7, "VylarLtd", "androiddev", "looking to partner with a developer of a media player to make it the 'recommended' player for AudioPocket", 0, "self.androiddev", 2, false));
        mPosts.add(new Post(8, "MarcusTheGreat7", "androiddev", "How to recreate the shared elements transition of the Play Store", 2, "self.androiddev", 5, true));
        mPosts.add(new Post(35, "CunningLogic", "androiddev", "Android Piracy Research:", 19, "self.androiddev", 14, false));
        mPosts.add(new Post(8, "danielgomez22", "androiddev", "JUnit 4 Assert wait for Expresso Idling Resource", 419, "reddit", 5, false));
        mPosts.add(new Post(8, "ViksaaSkool", "androiddev", "AwesomeSplash - customizable splash screen", 419, "self.androiddev", 5, false));
        mPosts.add(new Post(3, "Distelzombie42", "androiddev", "Monochrome color space (red - black)", 419, "self.androiddev", 5, false));
        mPosts.add(new Post(3, "the_middle_grey_cat", "androiddev", "Intro to Functional Reactive Programming, with Juan Gomez of Neffix", 419, "self.androiddev", 5, false));
    }

    public static PostLab get(Context context) {
        if (sPost == null) {
            sPost = new PostLab(context.getApplicationContext());
        }

        return sPost;
    }

    public ArrayList<Post> getPosts() {
        return mPosts;
    }

    public Post getPost(UUID id) {
        for (Post post : mPosts) {
            if (post.getId().equals(id)) return post;
        }

        return null;
    }
}
