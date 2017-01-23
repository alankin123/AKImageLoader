package com.alankin.myimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by alankin on 2017/1/21.
 */
public class FileCacheLoader implements CacheLoader {
    private final static String picDirName = "AKpics";
    private final static File dirFile = new File(Environment.getDataDirectory(), picDirName);
    private static FileCacheLoader instance;

    private FileCacheLoader() {

    }

    public static FileCacheLoader getInstance() {
        if (instance == null) {
            synchronized (LruCacheLoader.class) {
                if (instance == null) {
                    instance = new FileCacheLoader();
                }
            }
        }
        return instance;
    }

    @Override
    public Bitmap load(String path) {
        Bitmap bitmap = null;
        File file = new File(dirFile, Utils.getWholeName(path));
        if (!file.exists()) {
            return bitmap;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public boolean check(final String path) {
        File[] files = dirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if (Utils.getName(file.getName()).equals(Utils.getName(Utils.getWholeName(path)))) {
                    return true;
                }
                return false;
            }
        });
        if (files != null && files.length > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void save(String name, Bitmap bitmap) {
        File file = new File(dirFile, name);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
