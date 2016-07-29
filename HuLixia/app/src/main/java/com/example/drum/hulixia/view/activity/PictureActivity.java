package com.example.drum.hulixia.view.activity;

import android.media.Image;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.constants.Constants;
import com.example.drum.hulixia.view.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

public class PictureActivity extends BaseActivity {
    private ImageView sdvPicture;

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
        String imageUrl = getIntent().getStringExtra(Constants.IMAGE_URL);
        Log.i("hulixia", imageUrl);
        Picasso.with(this).load(imageUrl).into(sdvPicture);
        // sdvPicture.setImageURI(Uri.parse(imageUrl));
    }
}
