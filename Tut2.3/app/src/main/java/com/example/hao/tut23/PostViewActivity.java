package com.example.hao.tut23;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class PostViewActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "url";
    public static final String EXTRA_ID = "id";
    private String mUrl;
    private UUID mId;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mId = (UUID) getIntent().getSerializableExtra(EXTRA_ID);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        final TextView titleTextView = (TextView) findViewById(R.id.titleTextView);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        if (mUrl != null) {
            mWebView.loadUrl(mUrl);
        } else {
            AssetManager manager = getAssets();
            String content = "";
            Post post = PostLab.get(PostViewActivity.this).getPost(mId);
            String comments = (post.getComments() <= 1) ? " comment" : " comments";
            try {
                InputStream in = manager.open("index.html");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder htmlString = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    htmlString.append(line);
                }
                content = htmlString.toString()
                        .replace("%POST_SCORE%", String.valueOf(post.getPostScore()))
                        .replace("%AUTHOR%", post.getAuthor())
                        .replace("%SUBREDDIT%", post.getSubreddit())
                        .replace("%CONTENT%", post.getContent())
                        .replace("%COMMENTS%", String.valueOf(post.getComments()) + comments)
                        .replace("%DOMAIN%", post.getDomain())
                        .replace("%DATE%", String.valueOf(post.getDate()) + " hours ago");

            } catch (IOException e) {
                e.printStackTrace();
            }
            mWebView.loadDataWithBaseURL("file:///android_asset/index.html", content, "text/html", "UTF-8", null);
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }

            public void onReceivedTitle(WebView webView, String title) {
                titleTextView.setText(title);
            }
        });
    }
}
