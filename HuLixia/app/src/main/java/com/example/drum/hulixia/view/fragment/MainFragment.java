package com.example.drum.hulixia.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.presenter.MusicPresenter;


/**
 * Created by hulixia on 2016/7/5.
 * 音乐的fragment
 */
public class MainFragment extends Fragment {
    private static MainFragment mainFragment;
    private RecyclerView recyclerView;
    private TextView tvMusicDesc;
    private MusicPresenter presenter;


    public static MainFragment getInstance() {
        if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_music_list);
        tvMusicDesc = (TextView) view.findViewById(R.id.tv_music_decs);
        presenter = new MusicPresenter(getActivity());
        presenter.setMusicInfo(recyclerView, tvMusicDesc);
        //获取图片信息
        return view;
    }
}
