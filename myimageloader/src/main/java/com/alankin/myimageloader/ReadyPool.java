package com.alankin.myimageloader;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by alankin on 2017/1/22.
 */
public class ReadyPool {
    private static ReadyPool instance;
    /**
     * 准备池任务队列
     */
    private LinkedList<Runnable> mTasks;

    /**
     * 准备池线程
     */
    private Thread readyThread;

    /**
     * 通知线程池从准备池取图片的handler（绑定子线程）
     */
    private Handler readyHandler;

    /**
     * 加载完成通知Handler
     */
    private Handler finishHandler;
    /**
     * 线程池
     */
    private LoaderPool loaderPool;

    /**
     * 请求建造者
     */
    private RequestCreater requestCreater;

    /**
     * 信号量，线程之间的串行协作
     */
    private Semaphore semaphore;

    private ReadyPool() {
        init();
    }

    private void init() {
        finishHandler = new Handler();
        if (mTasks == null) {
            mTasks = new LinkedList<>();
        }
        semaphore = new Semaphore(1);
        if (readyThread == null) {
            readyThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    if (readyHandler == null) {
                        try {
                            semaphore.acquire();
                            //子线程中构建handler
                            readyHandler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    //收到准备池的通知，让线程池取出其中的任务
                                    if (loaderPool == null) {
                                        loaderPool = LoaderPool.getInstance(finishHandler);
                                    }
                                    loaderPool.takeRunable(takeTask());
                                }
                            };
                            semaphore.release();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Looper.loop();
                }
            });
            readyThread.start();
        }
    }

    public static ReadyPool getInstance() {
        if (instance == null) {
            synchronized (ReadyPool.class) {
                if (instance == null) {
                    instance = new ReadyPool();
                }
            }
        }
        return instance;
    }

    /**
     * 添加进队列，并通知线程池取
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        mTasks.add(runnable);
        try {
            semaphore.acquire();//等待子线线程中readyHandler被实例化之后才能往下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readyHandler.sendEmptyMessage(0x110);
    }

    /**
     * 添加进队列中
     *
     * @param requestCreater
     */
    public void getTask(RequestCreater requestCreater) {
        this.requestCreater = requestCreater;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //这里是加载的主体逻辑
                 AKImageLoader.load(finishHandler, ReadyPool.this.requestCreater);
            }
        };
        addTask(runnable);
    }

    public synchronized Runnable takeTask() {
        AKImageLoaderType akImageLoaderType = requestCreater.akImageLoaderConfig.akImageLoaderType;
        if (akImageLoaderType == AKImageLoaderType.LIFO) {
            return mTasks.removeLast();
        } else {
            return mTasks.removeFirst();
        }
    }
}
