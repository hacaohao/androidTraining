package com.hasbrain.chooseyourcar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/16/15.
 */
public class Car {
    private String name;
    private String brand;
    @SerializedName("imageFile")
    private String imageUrl;
    private String color;
    private String mId;

    public Car(String name, String brand, String imageUrl, String color) {
        this.name = name;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
