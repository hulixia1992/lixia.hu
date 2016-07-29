package com.example.drum.hulixia.daggertest;

import javax.inject.Inject;

/**
 * Created by hulixia on 2016/7/11.
 */
public class Poetry {
    private String mPemo;

    @Inject
    public Poetry() {
        mPemo = "turn to redio up!";
    }

    public Poetry(String mPemo) {
        this.mPemo = mPemo;
    }

    public String getmPemo() {
        return mPemo;
    }
}
