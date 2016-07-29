package com.example.drum.hulixia.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.drum.hulixia.Commponent.ObservableCommponent;
import com.example.drum.hulixia.R;
import com.example.drum.hulixia.adapter.AdvertisingPagerHolder;
import com.example.drum.hulixia.adapter.FoodsDataAdapter;
import com.example.drum.hulixia.constants.Constants;
import com.example.drum.hulixia.data.main_data.entity.FoodsData;
import com.example.drum.hulixia.data.main_data.entity.SanCanItem;
import com.example.drum.hulixia.data.main_data.entity.TopItem;
import com.example.drum.hulixia.scope.ObservaleScope;
import com.example.drum.hulixia.transfomer.AccordionTransformer;
import com.example.drum.hulixia.view.activity.PictureActivity;
import com.example.drum.hulixia.view.activity.WebActivity;
import com.example.drum.hulixia.view.inter.IFoodsView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hulixia on 2016/7/6.
 * 美食presenter
 */
public class FoodsPresenter implements FoodsDataAdapter.PictureClickEvent {
    private IFoodsView iFoodsView;
    private ArrayList<TopItem> topBanner;
    private ArrayList<SanCanItem> foodsItems;
    private Context context;
    private ConvenientBanner displayBanner;
    // private ObservableUtil observableUtil;
    @ObservaleScope
    @Inject
    Observable<FoodsData> observable;

    public FoodsPresenter(IFoodsView iFoodsView, Context context) {
        this.iFoodsView = iFoodsView;
        this.context = context;
        //   observableUtil = new ObservableUtil();
        ObservableCommponent.getObservableCommponent().inject(this);
    }

    public void initFoodsData() {
       // Observable<FoodsData> observale = observableUtil.getFoodsObservale();
        Subscriber<ArrayList<SanCanItem>> subscriber = getFoodSubscriber();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<FoodsData, ArrayList<SanCanItem>>() {
                    @Override
                    public ArrayList<SanCanItem> call(FoodsData foodsData) {
                        return foodsData.obj.san_can;
                    }
                }).subscribe(subscriber);
    }

    public void initBannerAction(ConvenientBanner displayBanner) {
        this.displayBanner = displayBanner;
        displayBanner.setPageIndicator(new int[]{R.drawable.home_banner_unselect, R.drawable.home_banner_select}).setPageTransformer(new AccordionTransformer());
        displayBanner.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position >= topBanner.size()) {
                            return;
                        }
                        TopItem data = topBanner.get(position);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("advert", "首页焦点图" + (position + 1));
                        // MobclickAgent.onEvent(mContext, "active", map);

                        if (data.click_obj != null && !"".equals(data.click_obj)) {
                            String[] strs = data.click_obj.split(";");
                            Intent intent = new Intent(context, WebActivity.class);
                            intent.putExtra(Constants.WEB_URL, strs[1]);
                            intent.putExtra(Constants.WEB_TITLE, strs[0]);
                            context.startActivity(intent);
                        }
                    }
                });
    }

    public void initTopBanner() {
      //  Observable<FoodsData> observable = observableUtil.getFoodsObservale();
        Subscriber<ArrayList<TopItem>> subscriber = getTopBannerSubsucriber();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<FoodsData, ArrayList<TopItem>>() {
                    @Override
                    public ArrayList<TopItem> call(FoodsData foodsData) {
                        ArrayList<TopItem> allTop = foodsData.obj.top3;
                        allTop.addAll(foodsData.obj.top4);
                        return allTop;
                    }
                }).subscribe(subscriber);
    }

    public Subscriber<ArrayList<TopItem>> getTopBannerSubsucriber() {
        return new Subscriber<ArrayList<TopItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<TopItem> topItems) {
                //  iFoodsView.setRefreshing(false);
                topBanner = topItems;
                // displayBanner.setPages(new AdvertisingPagerHolder(), topItems);
                displayBanner.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new AdvertisingPagerHolder();
                    }
                }, topItems);
                displayBanner.notifyDataSetChanged();
            }
        };
    }

    public ArrayList<TopItem> getTopItems() {
        return topBanner;
    }

    public Subscriber<ArrayList<SanCanItem>> getFoodSubscriber() {

        return new Subscriber<ArrayList<SanCanItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<SanCanItem> sanCanItems) {
                iFoodsView.setRefreshing(false);
                FoodsDataAdapter adapter = new FoodsDataAdapter(sanCanItems, context, FoodsPresenter.this);
                foodsItems = sanCanItems;
                iFoodsView.initRecyclerView(adapter);
            }
        };
    }

    @Override
    public void onClick(int position, View view) {
        SanCanItem sanCanItem = foodsItems.get(position);
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(Constants.IMAGE_URL, sanCanItem.tj_img);
        intent.putExtra(Constants.IMAGE_DESC, sanCanItem.descr);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation((Activity) context, view, Constants.PICTURE);
        ActivityCompat.startActivity((Activity) context, intent, activityOptionsCompat.toBundle());
    }
}
