package com.hasbrain.chooseyourcar.datastore;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hasbrain.chooseyourcar.model.Car;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/16/15.
 */
public class AssetBasedCarDatastoreImpl implements CarDatastore {
    private Context context;
    private String dataFilename;
    private Gson gson;

    public AssetBasedCarDatastoreImpl(Context context, String dataFilename, Gson gson) {
        this.context = context;
        this.dataFilename = dataFilename;
        this.gson = gson;
    }

    @Override
    public List<Car> getCarList() {
        Type type = new TypeToken<List<Car>>() {
        }.getType();
        InputStream is = null;
        List<Car> cars = null;
        try {
            is = context.getAssets().open(dataFilename);
            cars = gson.fromJson(new InputStreamReader(is), type);
            for (Car car : cars) {
                car.setId(UUID.randomUUID().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cars;
    }
}
