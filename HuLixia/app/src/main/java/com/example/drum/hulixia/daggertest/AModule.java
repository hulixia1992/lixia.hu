package com.example.drum.hulixia.daggertest;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hulixia on 2016/7/12.
 */

@Module
public class AModule {
    @PoetryQualifier("A")
    @AScope
    @Provides
    public Poetry getPoetry() {
        return new Poetry("反正有大把时光");
    }

    @PoetryQualifier("B")
    @AScope
    @Provides
    public Poetry getOtherPoetry() {
        return new Poetry("the other poetry");
    }
}
