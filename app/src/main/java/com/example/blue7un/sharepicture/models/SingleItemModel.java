package com.example.blue7un.sharepicture.models;

import android.support.annotation.NonNull;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class SingleItemModel {


    private String date;
    private String imagePath;


    public SingleItemModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public SingleItemModel(String date, String imagePath) {

        this.date = date;
        this.imagePath = imagePath;
    }


}
