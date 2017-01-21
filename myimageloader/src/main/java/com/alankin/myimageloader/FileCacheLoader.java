package com.alankin.myimageloader;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;

/**
 * Created by alankin on 2017/1/21.
 */
public class FileCacheLoader implements CacheLoader {
    private final static String picDirName = "AKpics";
    private final static File dirFile = new File(Environment.getDataDirectory(), picDirName);

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
