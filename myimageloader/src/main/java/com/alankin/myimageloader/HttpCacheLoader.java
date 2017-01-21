package com.alankin.myimageloader;

import android.graphics.Bitmap;

/**
 * Created by alankin on 2017/1/21.
 */
public class HttpCacheLoader implements CacheLoader{
    @Override
    public Bitmap load(String path) {
        return null;
    }

    @Override
    public boolean check(String path) {
        return false;
    }

    @Override
    public void save(Bitmap bitmap) {

    }
}
