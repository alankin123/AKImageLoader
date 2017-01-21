package com.alankin.myimageloader;

import android.support.annotation.DrawableRes;

/**
 * Created by alankin on 2017/1/21.
 * AKImageLoader参数配置类
 */
public class AKImageLoaderConfig {
    /**
     * 加载调度顺序
     */
    public AKImageLoaderType akImageLoaderType = AKImageLoaderType.LIFO;

    /**
     * 线程池线程数量
     */
    public int threadCount = 5;
}
