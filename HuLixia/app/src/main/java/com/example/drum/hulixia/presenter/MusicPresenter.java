package com.example.drum.hulixia.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.drum.hulixia.Commponent.DaggerMusicCommponent;
import com.example.drum.hulixia.Commponent.MusicCommponent;
import com.example.drum.hulixia.adapter.MusicAdapter;
import com.example.drum.hulixia.constants.Constants;
import com.example.drum.hulixia.data.main_data.entity.Mp3Info;
import com.example.drum.hulixia.module.MusicModule;
import com.example.drum.hulixia.util.MediaUtil;
import com.example.drum.hulixia.view.activity.MusicActivity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by hulixia on 2016/7/14.
 * MainFragment的presenter
 */
public class MusicPresenter {
    private Activity activity;
    @Inject
    List<Mp3Info> mp3Infos;

    public MusicPresenter(Activity activity) {
        this.activity = activity;
        DaggerMusicCommponent.builder().musicModule(new MusicModule(activity))
                .build().inject(this);
    }

    public void setMusicInfo(RecyclerView recyclerView, TextView tvMusicDesc) {
        tvMusicDesc.setText("共有歌曲" + mp3Infos.size() + "首");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final MusicAdapter adapter = new MusicAdapter(mp3Infos, activity);
        adapter.setPlayClickListener(new MusicAdapter.PlayClickListener() {
            @Override
            public void playClick(int position) {
                Intent intent = new Intent(activity, MusicActivity.class);
                intent.putExtra(Constants.MP3_INFO, (Serializable) mp3Infos);
                intent.putExtra(Constants.MP3_INDEX, position);
                activity.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
