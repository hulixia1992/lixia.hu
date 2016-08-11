package com.example.drum.hulixia.util.image_cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.drum.hulixia.data.main_data.entity.Object;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.http.GET;

/**
 * Created by hulixia on 2016/8/11.
 * 图片下载工具
 */
public  class ImageDownloadUtils {
    private static ImageDownloadUtils downloadUtils;

    public static ImageDownloadUtils getInstance() {
        if (downloadUtils == null) {
            synchronized (ImageDownloadUtils.class) {
                if (downloadUtils == null) {
                    downloadUtils = new ImageDownloadUtils();
                }
            }
        }
        return downloadUtils;
    }

    public static void getBitmapToIV(String url, ImageView imageView) {
        new BitmapTask().execute(url, imageView);
    }

    static class BitmapTask extends AsyncTask<java.lang.Object, Void, Bitmap> {
        private String url;
        private ImageView ivPic;

        @Override
        protected Bitmap doInBackground(java.lang.Object[] params) {
            url = (String) params[0];
            ivPic = (ImageView) params[1];
            return downloadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                ivPic.setImageBitmap(bitmap);
                MemoryCacheUtils.getMemoryCacheUtils().putBitmap(url, bitmap);
                LocalCacheUtils.getLocalCacheUtils().setBitmap(url, bitmap);
            }

        }
    }

    private static Bitmap downloadBitmap(String url) {
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            int requestCode = connection.getResponseCode();
            if (requestCode == 200) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return bitmap;
    }
}
