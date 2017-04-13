package com.example.enclaveit.schoolmateapp.bean;

/**
 * Created by vuongluis on 4/9/2017.
 */

public class Feature {

    private String name;
    private int thumbnail;

    public Feature() {
    }

    public Feature(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
