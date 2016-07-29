package com.example.drum.hulixia.module;

import com.example.drum.hulixia.Commponent.FoodsConnponent;
import com.example.drum.hulixia.data.main_data.FoodService;
import com.example.drum.hulixia.data.main_data.entity.FoodsData;
import com.example.drum.hulixia.scope.ObservaleScope;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by hulixia on 2016/7/12.
 */
@Module
public class ObservableModule {
    @Inject
    Retrofit retrofit;

    @ObservaleScope
    @Provides
    public Observable<FoodsData> getFoodsObservale() {
        FoodsConnponent.getFoodsConnponent().inject(ObservableModule.this);
        FoodService foodService = retrofit.create(FoodService.class);
        return foodService.getFoods("json");
    }
}
