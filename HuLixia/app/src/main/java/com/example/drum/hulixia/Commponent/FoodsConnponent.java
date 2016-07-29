package com.example.drum.hulixia.Commponent;

import com.example.drum.hulixia.module.ObservableModule;
import com.example.drum.hulixia.module.RetrofitModule;
import com.example.drum.hulixia.scope.FoodsScope;

import dagger.Component;

/**
 * Created by hulixia on 2016/7/12.
 * foods的commponent
 */
@FoodsScope
@Component(modules = RetrofitModule.class)
public abstract class FoodsConnponent {

    public abstract void inject(ObservableModule observableUtil);

    private static FoodsConnponent foodsConnponent;


    public static FoodsConnponent getFoodsConnponent() {
        if (foodsConnponent == null) {
            foodsConnponent = DaggerFoodsConnponent.builder().build();
        }
        return foodsConnponent;
    }
}
