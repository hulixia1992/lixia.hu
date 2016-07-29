package com.example.drum.hulixia.daggertest;

import android.app.Application;

/**
 * Created by hulixia on 2016/7/11.
 */
public class MainApplication extends Application {
    private ApplicationCommpent applicationCommpent;
    private static MainApplication application;
    private ACommponent aCommponent;

    public static MainApplication getInstance() {
        if (application == null) {
            application = new MainApplication();
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        applicationCommpent = DaggerApplicationCommpent.builder()
                .build();
    }

    public ACommponent getaCommponent() {
        if (aCommponent == null) {
            aCommponent = applicationCommpent.plus(new AModule());
        }
        return aCommponent;
    }

    public ApplicationCommpent getApplicationComponent() {
        if (applicationCommpent == null) {
            applicationCommpent = DaggerApplicationCommpent.builder()
                    .build();
        }
        return applicationCommpent;
    }
}
