package com.example.enclaveit.schoolmateapp.bean;

/**
 * Created by vuongluis on 3/26/2017.
 */

public class Category {
    private int resId;
    private String title;

    public Category() {
    }

    public Category(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public String getTitle() {
        return title;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
