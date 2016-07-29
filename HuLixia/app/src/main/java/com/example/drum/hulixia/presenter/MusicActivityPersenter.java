package com.example.drum.hulixia.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drum.hulixia.constants.Constants;
import com.example.drum.hulixia.data.main_data.entity.Mp3Info;
import com.example.drum.hulixia.util.MediaUtil;
import com.example.drum.hulixia.view.inter.IMusicView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hulixia on 2016/7/15.
 * MusicActivity的presenter
 */
public class MusicActivityPersenter {
    private List<Mp3Info> mp3Infos;
    private Mp3Info currentMp3;
    private Activity activity;
    private MediaPlayer player;
    private boolean isFirstPlay = true;
    private Bitmap btPic;
    private IMusicView iMusicView;
    private int position;

    public MusicActivityPersenter(Activity activity, IMusicView musicView) {
        this.activity = activity;
        iMusicView = musicView;
    }

    public void initData(Intent intent, ImageView ivPic, ImageView ivPic1, TextView tvMusicTime) {
        mp3Infos = (List<Mp3Info>) intent.getExtras().get(Constants.MP3_INFO);
        position = intent.getIntExtra(Constants.MP3_INDEX, 0);
        currentMp3 = mp3Infos.get(position);
        initView(ivPic, ivPic1, tvMusicTime);
    }

    private void initView(ImageView ivPic, ImageView ivPic1, TextView tvMusicTime) {
        btPic = MediaUtil.setArtwork(activity, currentMp3.getUrl(), ivPic);
        ivPic1.setImageBitmap(btPic);

        player = new MediaPlayer();
        BigDecimal totalTime = new BigDecimal(currentMp3.getDuration());
        BigDecimal oneMin = new BigDecimal("60000");
        double result = totalTime.divide(oneMin, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
        tvMusicTime.setText("时长:" + result + "m");
        try {
            player.setDataSource(activity, Uri.parse(currentMp3.getUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initPlayerListener();
    }

    public void initData(ImageView ivPic, ImageView ivPic1, TextView tvMusicTime, boolean next) {
        if (position == mp3Infos.size() - 1 && next) {
            position = 0;
        } else if (position == 0 && !next) {
            position = mp3Infos.size() - 1;
        } else {
            if (next) {
                position++;
            } else {
                position--;
            }
        }
        currentMp3 = mp3Infos.get(position);
        initView(ivPic, ivPic1, tvMusicTime);
    }

    private void initPlayerListener() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                iMusicView.musicPlayed();
            }
        });
    }

    public void playOrStopMusic(boolean playMusic) {
        if (playMusic) {
            try {
                if (isFirstPlay) {
                    isFirstPlay = !isFirstPlay;
                    player.prepare();
                }
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.pause();
        }
    }

    public String getMusicName() {
        return currentMp3.getTitle();
    }

    public void stopMusic() {
        player.stop();
        player = null;
    }

    public Bitmap getBtPic() {
        return btPic;
    }
}
