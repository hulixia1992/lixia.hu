package com.example.drum.hulixia.data.main_data;

import com.example.drum.hulixia.data.main_data.entity.FoodsData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hulixia on 2016/7/6.
 * 获取食物的实体类
 */
public interface FoodService {
    @GET("index5.php")
    Observable<FoodsData> getFoods(@Query("format") String format);
}
