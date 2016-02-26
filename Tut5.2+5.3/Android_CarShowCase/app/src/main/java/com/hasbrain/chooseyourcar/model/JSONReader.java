package com.hasbrain.chooseyourcar.model;

import android.annotation.TargetApi;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;

import java.util.List;

/**
 * Created by hao on 2/25/2016.
 */
public class JSONReader extends AsyncTaskLoader<List<Car>> {
    private Context mContext;
    private List<Car> mCars;

    public JSONReader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<Car> loadInBackground() {
        return CarManager.get(mContext).getCars();
    }

    @Override
    public void deliverResult(List<Car> cars){
        mCars = cars;
        if(isStarted()) super.deliverResult(cars);
    }

    @Override
    protected void onStartLoading(){
        if(mCars != null){
            deliverResult(mCars);
        }
        if(takeContentChanged() || mCars == null){
            forceLoad();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStopLoading(){
        cancelLoad();
    }

    @Override
    public void onCanceled(List<Car> cars){
        if(mCars != null){
            mCars = null;
        }
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();

        if(mCars != null){
            mCars = null;
        }

    }
}
