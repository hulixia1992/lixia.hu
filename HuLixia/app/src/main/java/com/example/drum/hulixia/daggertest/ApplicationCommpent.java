package com.example.drum.hulixia.daggertest;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hulixia on 2016/7/11.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationCommpent {
    Gson getGson();//暴露的接口
    ACommponent plus(AModule aModule);//添加声明
}
