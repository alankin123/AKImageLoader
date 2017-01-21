package com.alankin.myimageloader;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by alankin on 2017/1/21.
 */
public class AKApi {
    private String imgUrl;
    @DrawableRes
    private int erroPic;
    @DrawableRes
    private int defautPic;

    private AKImageLoaderConfig akImageLoaderConfig;

    public AKApi url(String url) {
        imgUrl = url;
        return this;
    }

    public AKApi erroPic(@DrawableRes int res) {
        erroPic = res;
        return this;
    }

    public AKApi defautPic(@DrawableRes int res) {
        defautPic = res;
        return this;
    }

    public AKApi config(AKImageLoaderConfig Config) {
        akImageLoaderConfig = Config;
        return this;
    }

    public void Bind(ImageView View) {
        /*imageView = View;
        getInstance().loadImage(imgUrl, View, erroPic, defautPic);*/
    }
}
