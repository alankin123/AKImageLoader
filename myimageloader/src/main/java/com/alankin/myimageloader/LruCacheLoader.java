package com.alankin.myimageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by alankin on 2017/1/21.
 */
public class LruCacheLoader implements CacheLoader {
    private volatile LruCache<String, Bitmap> bitmapLruCache;
    static LruCacheLoader instance;

    private LruCacheLoader() {
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

    public static LruCacheLoader getInstance() {
        if (instance == null) {
            synchronized (LruCacheLoader.class) {
                if (instance == null) {
                    instance = new LruCacheLoader();
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized Bitmap load(String path) {
        return bitmapLruCache.get(Utils.getWholeName(path));
    }

    @Override
    public synchronized boolean check(String path) {
        return bitmapLruCache.get(Utils.getWholeName(path)) != null;
    }

    @Override
    public synchronized void save(String name, Bitmap bitmap) {
        bitmapLruCache.put(name, bitmap);
    }
}
