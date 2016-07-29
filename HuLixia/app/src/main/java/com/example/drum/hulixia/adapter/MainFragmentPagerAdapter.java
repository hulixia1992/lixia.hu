package com.example.drum.hulixia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.drum.hulixia.view.fragment.FoodsFragment;
import com.example.drum.hulixia.view.fragment.MainFragment;
import com.example.drum.hulixia.view.fragment.NewFragment;

/**
 * Created by hulixia on 2016/7/5.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.getInstance();
            case 1:
                return NewFragment.getInstance();
            case 2:
                return FoodsFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
