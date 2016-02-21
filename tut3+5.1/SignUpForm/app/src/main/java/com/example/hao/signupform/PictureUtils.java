package com.example.hao.signupform;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hao on 2/20/2016.
 */
public class PictureUtils {
    private Activity mActivity;

    public PictureUtils(Activity activity) {
        mActivity = activity;
    }

    public BitmapDrawable getScaledDrawable(String path) {
        //get the window dimensions
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        float destWidth = display.getWidth();
        float destHeight = display.getHeight();

        //read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //tính toán size mới, lấy size nhỏ hơn và giữ nguyên tỷ lệ
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }

        //set attribute vừa tính dc
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //tạo đối tượng với các attribute mới vừa tính dc
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return new BitmapDrawable(mActivity.getResources(), bitmap);
    }

    public String saveImage(Bitmap bitmap) {
        String oldPhoto = PreferenceManager.getDefaultSharedPreferences(mActivity)
                .getString(MainActivity.PREF_IMAGE, null);
        if(oldPhoto != null) {
            File oldFile = new File(oldPhoto);
            oldFile.delete();
        }
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        int realWidth = srcWidth;
        int realHeight = srcHeight;

        if (srcHeight > 400 || srcWidth > 400) {
            if (srcHeight > srcWidth) {
                realHeight = 400;
                realWidth = srcWidth / srcHeight * realHeight;
            } else {
                realWidth = 400;
                realHeight = srcHeight / srcWidth * realWidth;
            }
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, realWidth, realHeight, true);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + ".png";

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        File file = new File(myDir, imageFileName);
        if (file.exists()) file.delete();

        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = new FileOutputStream(file);
            // Writing the bitmap to the output stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());

        }
        return myDir.getAbsolutePath() + "/" + imageFileName;
    }

    public BitmapDrawable getImage(String imageName) {
        BitmapDrawable scaledImage = null;
        if (imageName != null) {
            scaledImage = getScaledDrawable(imageName);
        }
        return scaledImage;
    }
}
