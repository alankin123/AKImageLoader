package com.alankin.myimageloader;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alankin on 2017/1/22.
 */
public class LoaderPool {
    private static LoaderPool instance;
    /**
     * 图片加载线程池
     */
    private ExecutorService mLoadPoll;

    private LoaderPool() {
        init();
    }

    private void init() {
        mLoadPoll = Executors.newFixedThreadPool(5);
    }

    public static LoaderPool getInstance(Handler finishHandler) {
        if (instance == null) {
            synchronized (LoaderPool.class) {
                if (instance == null) {
                    instance = new LoaderPool();
                }
            }
        }
        return instance;
    }

    /**
     * 添加进线程池中运行
     *
     * @param runnable
     */
    public void takeRunable(Runnable runnable) {
        mLoadPoll.execute(runnable);
    }
}
