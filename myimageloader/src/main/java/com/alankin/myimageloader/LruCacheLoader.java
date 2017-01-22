package com.alankin.myimageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by alankin on 2017/1/21.
 */
public class LruCacheLoader implements CacheLoader {
    private static volatile LruCache<String, Bitmap> bitmapLruCache;

    public void CacheLoader() {
        if (bitmapLruCache == null) {
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int lruSize = maxMemory / 8;
            bitmapLruCache = new LruCache<String, Bitmap>(lruSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getHeight() * value.getWidth();
                }
            };
        }
    }

    @Override
    public Bitmap load(String path) {
        return null;
    }

    @Override
    public boolean check(String path) {
        return false;
    }

    @Override
    public synchronized void save(String name,Bitmap bitmap) {

    }
}
