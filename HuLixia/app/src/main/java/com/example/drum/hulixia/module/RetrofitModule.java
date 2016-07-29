package com.example.drum.hulixia.module;

import com.example.drum.hulixia.scope.FoodsScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hulixia on 2016/7/12.
 * retrofit的module
 */
@Module
public class RetrofitModule {

    @FoodsScope
    @Provides
    public Retrofit getFoodRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.meishi.cc/v5/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
