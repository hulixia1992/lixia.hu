package com.example.drum.hulixia.daggertest;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hulixia on 2016/7/11.
 */
@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}
