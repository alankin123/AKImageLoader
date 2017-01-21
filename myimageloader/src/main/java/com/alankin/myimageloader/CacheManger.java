package com.alankin.myimageloader;

import android.graphics.Bitmap;

/**
 * Created by alankin on 2017/1/21.
 */
public class CacheManger {

    public Bitmap load(String url) {
        CacheLoader cacheLoader = chooseLoader(url);

        return cacheLoader.load(url);
    }

    private CacheLoader chooseLoader(String url) {
        LruCacheLoader lruCacheLoader = new LruCacheLoader();
        if (lruCacheLoader.check(url)) {
            return lruCacheLoader;
        }

        FileCacheLoader fileCacheLoader = new FileCacheLoader();
        if (fileCacheLoader.check(url)) {
            return fileCacheLoader;
        }

        HttpCacheLoader httpCacheLoader = new HttpCacheLoader();
        if (httpCacheLoader.check(url)) {
            return httpCacheLoader;
        }
        return null;
    }
}
