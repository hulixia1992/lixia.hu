package com.example.drum.hulixia.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.drum.hulixia.data.main_data.entity.TopItem;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hulixia on 2016/7/7.
 */
public class AdvertisingPagerHolder implements Holder<TopItem> {
    private SimpleDraweeView draweeView;

    @Override
    public View createView(Context context) {
        draweeView = new SimpleDraweeView(context);
        draweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        draweeView.setAspectRatio((float) 720 / 285);
        draweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return draweeView;
    }

    @Override
    public void UpdateUI(Context context, int position, TopItem data) {
        // String[] click_objs = data.click_obj.split(";");
        // String imageUrl = click_objs[1];
        // draweeView.setHierarchy(FrescoManager.getGenericDraweeHierarchy(context, R.drawable.preset_banner));
        try {
            draweeView.setImageURI(Uri.parse(data.photo));
            //  FrescoManager.setImageUri(draweeView, imageUrl, SyncStateContract.Constants.HOME_BANNER_SIZE);
        } catch (OutOfMemoryError e) {
            Fresco.getImagePipeline().clearMemoryCaches();
            System.gc();
            //FrescoManager.setImageUri(draweeView, imageUrl, SyncStateContract.Constants.HOME_BANNER_SIZE);
        }
    }
}
