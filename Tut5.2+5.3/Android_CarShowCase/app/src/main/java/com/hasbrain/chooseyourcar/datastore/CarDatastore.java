package com.hasbrain.chooseyourcar.datastore;

import com.hasbrain.chooseyourcar.model.Car;

import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/16/15.
 */
public interface CarDatastore {
    List<Car> getCarList();
}
