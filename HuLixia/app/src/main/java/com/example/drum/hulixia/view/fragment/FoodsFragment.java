package com.example.drum.hulixia.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.drum.hulixia.R;
import com.example.drum.hulixia.adapter.FoodsDataAdapter;
import com.example.drum.hulixia.presenter.FoodsPresenter;
import com.example.drum.hulixia.view.inter.IFoodsView;


/**
 * Created by hulixia on 2016/7/5.
 * 展示美食的fragment
 */
public class FoodsFragment extends Fragment implements IFoodsView, SwipeRefreshLayout.OnRefreshListener {
    private static FoodsFragment foodsFragment;
    private RecyclerView recyclerView;
    private FoodsPresenter foodsPresenter;
    private ConvenientBanner displayBanner;
    private SwipeRefreshLayout srlFoodsRefresh;

    private View view;

    public static FoodsFragment getInstance() {
        if (foodsFragment == null) {
            foodsFragment = new FoodsFragment();
        }
        return foodsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_foods, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_foods);
        foodsPresenter = new FoodsPresenter(this, getActivity());
        srlFoodsRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_foods_refresh);
        foodsPresenter.initTopBanner();
        srlFoodsRefresh.setRefreshing(true);
        foodsPresenter.initFoodsData();
        initFoodsRefresh();
        initDisplayBanner();
        return view;
    }

    private void initFoodsRefresh() {
        srlFoodsRefresh.setOnRefreshListener(this);
        srlFoodsRefresh.setRefreshing(true);
        srlFoodsRefresh.setColorSchemeResources(R.color.main_color,
                R.color.colorAccent, R.color.colorPrimary);
    }

    private void initDisplayBanner() {
        displayBanner = (ConvenientBanner) view.findViewById(R.id.food_banner);
        foodsPresenter.initBannerAction(displayBanner);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void initRecyclerView(FoodsDataAdapter adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        srlFoodsRefresh.setRefreshing(isRefreshing);
    }

    @Override
    public void onRefresh() {
          foodsPresenter.initFoodsData();
      //  srlFoodsRefresh.setRefreshing(false);
    }


}
