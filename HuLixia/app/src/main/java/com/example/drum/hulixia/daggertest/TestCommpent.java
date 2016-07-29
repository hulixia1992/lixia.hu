package com.example.drum.hulixia.daggertest;

import dagger.Component;

/**
 * Created by hulixia on 2016/7/11.
 */
@PoetryScope
@Component(dependencies = ApplicationCommpent.class, modules = {TestModule.class, PoetryModule.class})
public abstract class TestCommpent {
    abstract void inject(DraggerTestActivity activity);

    abstract void inject(OtherActivit activit);

    private static TestCommpent testModule;

    public static TestCommpent getInstance() {
        if (testModule == null) {
            testModule = DaggerTestCommpent.builder()
                    .applicationCommpent(MainApplication.getInstance()
                            .getApplicationComponent())
                    .poetryModule(new PoetryModule())
                    .build();
        }
        return testModule;
    }

}
