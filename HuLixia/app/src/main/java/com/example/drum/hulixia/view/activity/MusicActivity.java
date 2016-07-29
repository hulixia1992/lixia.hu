package com.example.drum.hulixia.view.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.data.main_data.entity.Mp3Info;
import com.example.drum.hulixia.presenter.MusicActivityPersenter;
import com.example.drum.hulixia.util.BitmapUtil;
import com.example.drum.hulixia.view.BaseActivity;
import com.example.drum.hulixia.view.inter.IMusicView;
import com.example.drum.hulixia.widget.PicImageView;

import java.util.List;

public class MusicActivity extends BaseActivity implements IMusicView {
    private PicImageView ivPic;
    private PicImageView ivPic1;
    private ImageView ivLast;
    private ImageView ivNext;
    private ImageView ivPlay;
    private TextView tvMusicTime;
    private MusicActivityPersenter presenter;
    private RelativeLayout rlMusic;
    private boolean isPlay = true;
    private List<Mp3Info> mp3Infos;

    private Animation musiceAnim;
    RotateAnimation animation;

    private AudioManager audioManager;
    private View view;
    private int w_screen;
    private int h_screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.activity_music, null);
        setContentView(view);
        initView();
        initData();
        initListener();
    }


    private void initListener() {
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.playOrStopMusic(isPlay);
                if (isPlay) {
                    ivPic1.setVisibility(View.GONE);
                    ivPic.startAnimation(musiceAnim);
                    ivPlay.setImageResource(R.drawable.music_stop);
                } else {
                    ivPic1.setVisibility(View.VISIBLE);
                    ivPic.clearAnimation();
//ivPic.stop
                    ivPlay.setImageResource(R.drawable.play);
                }
                isPlay = !isPlay;
            }
        });
        //点击上一首
        ivLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPic1.setVisibility(View.VISIBLE);
                presenter.playOrStopMusic(false);
                final DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                TranslateAnimation animation = new TranslateAnimation(0, displayMetrics.widthPixels, 0, 0);
                animation.setDuration(2000);
                ivPic1.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isPlay = true;
                        presenter.initData(ivPic, ivPic1, tvMusicTime, false);
                        initTitleString(presenter.getMusicName());
                        BitmapUtil.blur(MusicActivity.this, presenter.getBtPic(), rlMusic, w_screen, h_screen);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    private void initData() {
        presenter = new MusicActivityPersenter(this, this);
        presenter.initData(getIntent(), ivPic, ivPic1, tvMusicTime);
        initTitleView(presenter.getMusicName());
        DisplayMetrics dm = getResources().getDisplayMetrics();
        w_screen = dm.widthPixels;
        h_screen = dm.heightPixels;
        BitmapUtil.blur(this, presenter.getBtPic(), rlMusic, w_screen, h_screen);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.i("hulixia", "最大音量:" + max + " 当前音量：" + current);
        iniAnimation();
    }

    private void iniAnimation() {
        musiceAnim = AnimationUtils.loadAnimation(this, R.anim.music_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();
        musiceAnim.setInterpolator(interpolator);
        musiceAnim.setFillAfter(true);

        animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(6000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(-1);
    }

    private void initView() {
        ivPic = (PicImageView) findViewById(R.id.iv_music_pic);
        ivPic1 = (PicImageView) findViewById(R.id.iv_music_pic1);
        ivLast = (ImageView) findViewById(R.id.iv_music_last);
        ivNext = (ImageView) findViewById(R.id.iv_music_next);
        ivPlay = (ImageView) findViewById(R.id.iv_music_play);
        rlMusic = (RelativeLayout) findViewById(R.id.rl_music_main);
        tvMusicTime = (TextView) findViewById(R.id.tv_music_time);
    }

    @Override
    public void finishActivity() {
        presenter.stopMusic();
        finish();
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public void musicPlayed() {
        ivPic.clearAnimation();
        isPlay = !isPlay;
        ivPic.setImageResource(R.drawable.play);
    }
}
