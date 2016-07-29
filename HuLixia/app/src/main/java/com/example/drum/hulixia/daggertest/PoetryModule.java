package com.example.drum.hulixia.daggertest;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hulixia on 2016/7/11.
 */

@Module
public class PoetryModule {

    @PoetryScope
    @Provides
    public Poetry getPoetry(String poem) {
        return new Poetry(poem);
    }

    @Provides
    public String getPoetrySrting() {
        return "生活是一片汪洋";
    }
}
