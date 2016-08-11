package com.example.drum.hulixia.view.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.constants.Constants;
import com.example.drum.hulixia.util.image_cache.ImageDownloadUtils;
import com.example.drum.hulixia.view.BaseActivity;
import com.squareup.picasso.Picasso;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;


public class PictureActivity extends BaseActivity {
    private ImageView sdvPicture;
    private String imageUrl;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //根据图片颜色更改ToolBar颜色
            findViewById(R.id.toolbar).setBackgroundColor((Integer) msg.obj);
        }
    };

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        initView();
    }

    private void initView() {
        initTitleView(getIntent().getStringExtra(Constants.IMAGE_DESC));
        sdvPicture = (ImageView) findViewById(R.id.sdv_picture_foods);
        ViewCompat.setTransitionName(sdvPicture, Constants.PICTURE);
        imageUrl = getIntent().getStringExtra(Constants.IMAGE_URL);
        ImageDownloadUtils.getInstance().getBitmapToIV(imageUrl, sdvPicture);
        initToolbarColor();

    }

    private void initToolbarColor() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Bitmap bitmap = Picasso.with(PictureActivity.this).load(imageUrl).get();
                    Filter myFilter = new Filter();
                    myFilter.addSubFilter(new BrightnessSubfilter(30));
                    myFilter.addSubFilter(new ContrastSubfilter(1.1f));
                    //  bitmap = myFilter.processFilter(outbitmap);
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Message msg = new Message();
                            msg.obj = palette.getVibrantColor(getResources().getColor(R.color.main_color));
                            handler.sendMessage(msg);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void finishActivity() {
        onBackPressed();
    }
}
