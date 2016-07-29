package com.example.drum.hulixia.module;

import android.content.Context;

import com.example.drum.hulixia.data.main_data.entity.Mp3Info;
import com.example.drum.hulixia.util.MediaUtil;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hulixia on 2016/7/21.
 */
@Module
public class MusicModule {
    private Context context;

    public MusicModule(Context context) {
        this.context = context;
    }

    @Provides
    public List<Mp3Info> getMp3Infos() {
        return MediaUtil.getMp3Infos(context);
    }
}
