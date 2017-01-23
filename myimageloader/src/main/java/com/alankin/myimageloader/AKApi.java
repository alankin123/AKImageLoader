package com.alankin.myimageloader;

import android.content.Context;

/**
 * Created by alankin on 2017/1/21.
 */
public class AKApi {
    private static AKApi instance;
    private Context context;

    private AKApi(Context context) {
        this.context = context;
    }

    public static AKApi with(Context context) {
        if (instance == null) {
            synchronized (AKApi.class) {
                if (instance == null) {
                    instance = new AKApi(context);
                }
            }
        }
        return instance;
    }

    public RequestCreater load(String url) {
        return new RequestCreater(context, url);
    }
}
