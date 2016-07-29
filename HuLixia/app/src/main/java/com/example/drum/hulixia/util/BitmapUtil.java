package com.example.drum.hulixia.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;

/**
 * Created by hulixia on 2016/7/15.
 * 图片处理工具
 */
public class BitmapUtil {
    /**
     * 高斯模糊算法
     *
     * @param bkg
     * @param view
     */
    public static void blur(Context context, Bitmap bkg, View view, int width, int height) {
        float radius = 20;
        Log.i("hulixia", bkg.getWidth() + "w,h" + bkg.getDensity());
        Bitmap overlay = Bitmap.createBitmap(bkg.getWidth(),
                bkg.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);

        RenderScript rs = RenderScript.create(context);

        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        view.setBackground(new BitmapDrawable(context.getResources(), overlay));
        rs.destroy();
    }
}
