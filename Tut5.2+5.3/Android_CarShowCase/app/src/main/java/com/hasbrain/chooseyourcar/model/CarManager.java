package com.hasbrain.chooseyourcar.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hasbrain.chooseyourcar.datastore.AssetBasedCarDatastoreImpl;
import com.hasbrain.chooseyourcar.datastore.CarDatastore;

import java.util.List;

/**
 * Created by hao on 2/23/2016.
 */
public class CarManager {
    private static CarManager sCarManager;
    private List<Car> mCars;
    private Context mContext;

    private CarManager(Context context) {
        mContext = context.getApplicationContext();
        Gson gson = new GsonBuilder().create();
        CarDatastore carDatastore = new AssetBasedCarDatastoreImpl(mContext, "car_data.json", gson);
        mCars = carDatastore.getCarList();
    }

    public static CarManager get(Context context) {
        if (sCarManager != null) return sCarManager;
        return new CarManager(context);
    }

    public List<Car> getCars() {
        return mCars;
    }

    public Car getCar(int position) {
        return mCars.get(position);
    }
}
