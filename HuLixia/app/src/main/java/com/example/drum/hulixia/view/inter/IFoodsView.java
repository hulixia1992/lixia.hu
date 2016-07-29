package com.example.drum.hulixia.view.inter;


import com.example.drum.hulixia.adapter.FoodsDataAdapter;

/**
 * Created by hulixia on 2016/7/6.
 */
public interface IFoodsView{
    //  Subscriber<ArrayList<SanCanItem>> getFoodSubscriber();
    //  Subscriber<ArrayList<TopItem>> getTopBannerSubsucriber();
    void initRecyclerView(FoodsDataAdapter adapter);

    void setRefreshing(boolean isRefreshing);


}
