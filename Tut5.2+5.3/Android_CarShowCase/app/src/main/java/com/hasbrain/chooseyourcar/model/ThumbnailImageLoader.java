package com.hasbrain.chooseyourcar.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by hao on 2/22/2016.
 */
public class ThumbnailImageLoader<Token> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final String RAW_FOLDER = "raw";
    private static final int MESSAGE_DOWNLOAD = 0;
    private static final int cacheSize = 4 * 1024 * 1024;

    Listener<Token> mListener;
    private Context mContext;
    private Queue<ImageInfo> mInfo;
    private Map<String, String> mRequestMap = Collections.synchronizedMap(new HashMap<String, String>());
    private LruCache<String, Bitmap> mCache;
    private Handler mResponseHandler;
    private Handler mHandler;

    public ThumbnailImageLoader(Context context, Handler reponseHandler) {
        super(TAG);
        mContext = context;
        mResponseHandler = reponseHandler;
        mCache = new LruCache<>(cacheSize);
        mInfo = new LinkedList<>();
    }

    public void setListener(Listener<Token> listener) {
        mListener = listener;
    }

    public void queueThumbnail(Token token, String url, ImageInfo info) {
        mRequestMap.put(info.getId(), url);
        mInfo.add(info);
        mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    @SuppressWarnings("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message.what == MESSAGE_DOWNLOAD) {
                    //ko cần kiểm tra trước khi cast (vì nếu kiểm tra có thể cast ko dc)
                    @SuppressWarnings("unchecked")
                    Token token = (Token) message.obj;
                    handleRequest(token);
                }
            }
        };
    }

    private void handleRequest(final Token token) {
        final Bitmap scaledBitmap;
        try {
            ImageInfo info = mInfo.remove();
            final String key = info.getId();
            boolean isFullSize = info.isFullSize();
            final String url = mRequestMap.get(key);

            if (url == null) return;

            if (mCache.get(url) != null && !isFullSize) {
                scaledBitmap = mCache.get(url);
            } else {
                Bitmap bitmap = getBitmap(url);
                if(!isFullSize) {
                    scaledBitmap = Bitmap.createScaledBitmap(bitmap, 160, 120, true);
                    mCache.put(url, scaledBitmap);
                }else{
                    scaledBitmap = bitmap;
                }
            }

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (!mRequestMap.get(key).equals(url)) return;
                    mRequestMap.remove(key);
                    mListener.onThumbnailDownloaded(token, scaledBitmap);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "Error downloading image", e);
        }
    }

    public void clearCache() {
        mCache.evictAll();
    }

    private InputStream getRawImage(String imageName) {
        return mContext.getResources()
                .openRawResource(mContext.getResources().getIdentifier(imageName, RAW_FOLDER, mContext.getPackageName()));
    }

    private byte[] getImageByte(String imageName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int bytesRead;
        byte[] buffer = new byte[1024];

        InputStream in = getRawImage(imageName);

        while ((bytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, bytesRead);
        }
        out.close();

        return out.toByteArray();
    }

    public Bitmap getBitmap(String imageName) throws IOException {
        byte[] imageBytes = getImageByte(imageName);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public interface Listener<Token> {
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }
}
