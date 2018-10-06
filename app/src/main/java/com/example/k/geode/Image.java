package com.example.k.geode;

import android.widget.ImageView;

public class  Image {
    private int mResId;
    private String mImageName;

    public Image(int resId, String imageName){
        this.mResId = resId;
        this.mImageName = imageName;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int resId) {
        mResId = resId;
    }

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }
}
