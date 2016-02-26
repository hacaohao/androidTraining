package com.hasbrain.chooseyourcar;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.hasbrain.chooseyourcar.model.Car;
import com.hasbrain.chooseyourcar.model.JSONReader;
import com.hasbrain.chooseyourcar.model.ListAdapter;
import com.hasbrain.chooseyourcar.model.ThumbnailImageLoader;

import java.util.List;

public class CarListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Car>> {
    public static ThumbnailImageLoader<ImageView> sImageLoader;
    private RecyclerView mRecyclerView;
    private List<Car> mCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        sImageLoader = new ThumbnailImageLoader<>(this, new Handler());
        sImageLoader.setListener(new ThumbnailImageLoader.Listener<ImageView>() {
            @Override
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                imageView.setImageBitmap(thumbnail);
            }
        });
        sImageLoader.start();
        sImageLoader.getLooper();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list_car);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sImageLoader.clearCache();
        sImageLoader.quit();
    }

    @Override
    public Loader<List<Car>> onCreateLoader(int id, Bundle args) {
        return new JSONReader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Car>> loader, List<Car> data) {
        mCars = data;

        ListAdapter adapter = new ListAdapter(CarListActivity.this, mCars);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Car>> loader) {
        mCars = null;
    }
}