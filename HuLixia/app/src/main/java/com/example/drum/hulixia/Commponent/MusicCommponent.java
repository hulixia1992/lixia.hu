package com.example.drum.hulixia.Commponent;

import android.content.Context;

import com.example.drum.hulixia.module.MusicModule;
import com.example.drum.hulixia.presenter.MusicPresenter;

import dagger.Component;

/**
 * Created by hulixia on 2016/7/21.
 */
@Component(modules = MusicModule.class)
public abstract class MusicCommponent {
    public abstract void inject(MusicPresenter presenter);
}
