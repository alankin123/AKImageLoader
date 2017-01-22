package com.alankin.myimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by alankin on 2017/1/21.
 * 图片加载器
 */
public class AKImageLoader {
    public static void load(Handler finishHandle, RequestCreater requestCreater) {
        Bitmap bitmap;
        String imgUrl = requestCreater.imgUrl;
        String name = Utils.getName(imgUrl);
        ImageView imageView = requestCreater.imageView;
        int erroPic = requestCreater.erroPic;
        int defautPic = requestCreater.defautPic;
        Context context = requestCreater.context;

        BitmapDrawable defautDrawable = (BitmapDrawable) context.getResources().getDrawable(defautPic);
        bitmap = defautDrawable.getBitmap();
        setImg2view(finishHandle, imageView, bitmap);
        LruCacheLoader lruCacheLoader = new LruCacheLoader();
        //内存有缓存
        if (lruCacheLoader.check(imgUrl)) {
            bitmap = lruCacheLoader.load(imgUrl);
        } else {//内存没有缓存
            FileCacheLoader fileCacheLoader = new FileCacheLoader();
            //文件有缓存
            if (fileCacheLoader.check(imgUrl)) {
                bitmap = fileCacheLoader.load(imgUrl);
                lruCacheLoader.save(name, bitmap);
            } else {//文件没有缓存
                HttpCacheLoader httpCacheLoader = new HttpCacheLoader();
                //网络有缓存
                if (httpCacheLoader.check(imgUrl)) {
                    bitmap = httpCacheLoader.load(imgUrl);
                    lruCacheLoader.save(name, bitmap);
                    fileCacheLoader.save(name, bitmap);
                } else {//网络没有缓存
                    BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(erroPic);
                    bitmap = drawable.getBitmap();
                }
            }
        }
        setImg2view(finishHandle, imageView, bitmap);
    }

    public static void setImg2view(Handler finishHandle, final ImageView imageView, final Bitmap bitmap) {
        finishHandle.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
