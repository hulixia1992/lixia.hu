package com.example.drum.hulixia.daggertest;

import dagger.Subcomponent;

/**
 * Created by hulixia on 2016/7/12.
 */
@AScope
@Subcomponent(modules = AModule.class)
public interface ACommponent {
    void inject(AActivity aActivity);
}
