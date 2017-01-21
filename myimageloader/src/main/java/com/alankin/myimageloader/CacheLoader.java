package com.alankin.myimageloader;

import android.graphics.Bitmap;

/**
 * Created by alankin on 2017/1/21.
 * 缓存器接口
 */
public interface CacheLoader {
    Bitmap load(String path);

    boolean check(String path);

    void save(Bitmap bitmap);
}
