package com.example.drum.hulixia.util.image_cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by hulixia on 2016/8/10.
 * 图片存入磁盘工具
 */
public class LocalCacheUtils {
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HuLiXia";
    private static LocalCacheUtils localCacheUtils;

    private LocalCacheUtils() {

    }

    public static LocalCacheUtils getLocalCacheUtils() {
        if (localCacheUtils == null) {
            synchronized (LocalCacheUtils.class) {
                localCacheUtils = new LocalCacheUtils();
            }
        }
        return localCacheUtils;
    }

    public static Bitmap getBitmap(String url) {

        // fileName = MD5Encoder.encode(url);
        File file = new File(CACHE_PATH, url);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setBitmap(String url, Bitmap bitmap) {
        File file = new File(CACHE_PATH, url);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //把图片保存至本地
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("hulixia", "sd卡缓存");
    }
}
