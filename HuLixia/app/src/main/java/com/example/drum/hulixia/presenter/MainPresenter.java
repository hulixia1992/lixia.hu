package com.example.drum.hulixia.presenter;

import android.view.View;

import com.example.drum.hulixia.view.inter.IMainView;

/**
 * Created by hulixia on 2016/7/5.
 * mainActivity的presenter
 */
public class MainPresenter {
    private IMainView iMainView;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public void showSnackToast(View view) {
        iMainView.showSnackToast(view);
    }
}
