package com.example.drum.hulixia.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drum.hulixia.R;

/**
 * Created by hulixia on 2016/7/5.
 * 新闻fragment
 */
public class NewFragment extends Fragment {
    private static NewFragment newFragment;

    public static NewFragment getInstance() {
        if (newFragment == null) {
            newFragment = new NewFragment();
        }
        return newFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container,false);
        return view;
    }
}
