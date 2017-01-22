package com.alankin.myimageloader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by alankin on 2017/1/22.
 * 请求建造者
 */
public class RequestCreater {
    protected String imgUrl;
    @DrawableRes
    protected int erroPic;
    @DrawableRes
    protected int defautPic;
    protected ImageView imageView;
    protected AKImageLoaderConfig akImageLoaderConfig;
    protected Context context;

    RequestCreater(Context context, String imgUrl) {
        this.context = context;
        this.imgUrl = imgUrl;
    }

    public RequestCreater url(String url) {
        imgUrl = url;
        return this;
    }

    public RequestCreater erroPic(@DrawableRes int res) {
        erroPic = res;
        return this;
    }

    public RequestCreater defautPic(@DrawableRes int res) {
        defautPic = res;
        return this;
    }

    public RequestCreater config(AKImageLoaderConfig Config) {
        akImageLoaderConfig = Config;
        return this;
    }

    public void Bind(ImageView imageView) {
        this.imageView = imageView;
        ReadyPool.getInstance().getTask(this);
    }
}
