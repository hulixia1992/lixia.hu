package com.example.drum.hulixia.Commponent;

import com.example.drum.hulixia.module.ObservableModule;
import com.example.drum.hulixia.presenter.FoodsPresenter;
import com.example.drum.hulixia.scope.ObservaleScope;

import dagger.Component;

/**
 * Created by hulixia on 2016/7/12.
 * observable连接器
 */
@ObservaleScope
@Component(modules = ObservableModule.class)
public abstract class ObservableCommponent {
    public abstract void inject(FoodsPresenter presenter);

    private static ObservableCommponent observableCommponent;

    public static ObservableCommponent getObservableCommponent() {
        if (observableCommponent == null) {
            observableCommponent = DaggerObservableCommponent.builder().build();
        }
        return observableCommponent;
    }
}
