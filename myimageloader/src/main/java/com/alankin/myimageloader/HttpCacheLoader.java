package com.alankin.myimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by alankin on 2017/1/21.
 */
public class HttpCacheLoader implements CacheLoader {
    private Bitmap bitmap;

    @Override
    public Bitmap load(String path) {
        return bitmap;
    }

    @Override
    public boolean check(String path) {
        bitmap = null;
        Request request = new Request.Builder().get().url(path).build();
        Call call = new OkHttpClient.Builder().build().newCall(request);
        try {
            InputStream inputStream = call.execute().body().byteStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null) {
            return false;
        }
        return true;
    }

    @Override
    public void save(String name, Bitmap bitmap) {

    }
}
