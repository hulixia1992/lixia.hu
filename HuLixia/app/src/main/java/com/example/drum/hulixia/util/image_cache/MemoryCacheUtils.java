package com.example.drum.hulixia.util.image_cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.example.drum.hulixia.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by hulixia on 2016/8/10.
 * 内存图片缓存工具
 */
public class MemoryCacheUtils {

    private static LruCache<String, Bitmap> imageCache;
    private static HashMap<String, Integer> countUse;
    private static ArrayList<String> urls;
    private static int COUNT_SIZE = 200;
    private static MemoryCacheUtils memoryCacheUtils;
    private static long maxMemorySize;

    private MemoryCacheUtils() {
        maxMemorySize = Runtime.getRuntime().maxMemory() / 8;
        imageCache = new LruCache<String, Bitmap>((int) maxMemorySize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        //countCache = new LruCache<String, Integer>(COUNT_SIZE);\
        countUse = new HashMap<>();
        urls = new ArrayList<>();
    }

    public static MemoryCacheUtils getMemoryCacheUtils() {
        if (memoryCacheUtils == null) {
            synchronized (MemoryCacheUtils.class) {
                if (memoryCacheUtils == null) {
                    memoryCacheUtils = new MemoryCacheUtils();
                }
            }
        }
        return memoryCacheUtils;
    }

    public static Bitmap getBitmap(String url) {
        Log.i("hulixia", "内存读取:"+url);
        return imageCache.get(url);
    }

    public static void putBitmap(String url, Bitmap bitmap) {
        if (imageCache.size() + bitmap.getByteCount() > (int) maxMemorySize) {
            for (int i = 0; i < countUse.size(); i++) {
                if (countUse.get(urls.get(i)) <= 2) {
                    imageCache.remove(urls.get(i));
                }
            }
        }

        if (countUse.get(url) != null) {
            int count = countUse.get(url);
            if (count <= 0) {
                count = 1;
            } else {
                count++;
            }
            countUse.put(url, count);
        }
        imageCache.put(url, bitmap);
        Log.i("hulixia", "内存缓存:"+url);
    }

}
