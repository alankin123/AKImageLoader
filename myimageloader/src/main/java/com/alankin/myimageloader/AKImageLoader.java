package com.alankin.myimageloader;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.util.LruCache;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by alankin on 2017/1/21.
 * 图片加载器
 */
public class AKImageLoader {
    /**
     * AKImageLoader单例对象
     */
    private static AKImageLoader akImageLoader;

    /**
     * AKImageLoader配置参数
     */
    private static AKImageLoaderConfig akImageLoaderConfig;

    /**
     * 图片内存缓存管理
     */
    private LruCache<String, Bitmap> bitmapLruCache;

    /**
     * 准备池任务队列
     */
    private LinkedList<Runnable> mTasks;

    /**
     * 准备池线程
     */
    private Thread readyThread;

    /**
     * 图片加载线程池
     */
    private ExecutorService mLoadPoll;

    /**
     * 控制readyHandler回调短暂阻塞的信号量，必须要让readyHandler创建完成之后才能回调
     */
    private Semaphore semaphore;
    /**
     * 通知线程池从准备池取图片的handler（绑定子线程）
     */
    private Handler readyHandler;

    /**
     * 通知加载完成的handler（绑定主线程）
     */
    private Handler finishedHandler;

    /**
     * 图片url
     */
    private static String imgUrl;

    /**
     * 加载失败的图片
     */
    @DrawableRes
    private static int erroPic;

    /**
     * 默认未加载的图片
     */
    @DrawableRes
    private static int defautPic;

    /**
     * 绑定的ImageView
     */
    private static ImageView imageView;

    private AKImageLoader() {
        if (akImageLoaderConfig == null) {
            akImageLoaderConfig = new AKImageLoaderConfig();
        }
        init();
    }

    /**
     * 双重检索获取单例
     *
     * @return
     */
    public static AKImageLoader getInstance() {
        if (akImageLoader == null) {
            synchronized (AKImageLoader.class) {
                if (akImageLoader == null) {
                    akImageLoader = new AKImageLoader();
                }
            }
        }
        return akImageLoader;
    }

    public static AKImageLoader url(String url) {
        imgUrl = url;
        return akImageLoader;
    }

    public static AKImageLoader erroPic(@DrawableRes int res) {
        erroPic = res;
        return akImageLoader;
    }

    public static AKImageLoader defautPic(@DrawableRes int res) {
        defautPic = res;
        return akImageLoader;
    }

    public static AKImageLoader config(AKImageLoaderConfig Config) {
        akImageLoaderConfig = Config;
        return akImageLoader;
    }

    public static void Bind(ImageView View) {
        imageView = View;
        getInstance().loadImage(imgUrl, View, erroPic, defautPic);
    }

    /**
     * AKImageLoader初始化
     */
    private void init() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        //只有在api12的时候才能够使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
                //重写这个方法是可以获知为每张图片分配多大的内存空间
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getWidth() * value.getHeight();
                }
            };
        }

        mTasks = new LinkedList<>();
        mLoadPoll = Executors.newFixedThreadPool(akImageLoaderConfig.threadCount);
        semaphore = new Semaphore(1);
        synchronized (this) {
            if (readyThread == null) {
                readyThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        //在子线程中绑定一个handler
                        readyHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                mLoadPoll.execute(getTask());
                            }
                        };
                        semaphore.release();//释放许可，readyThread可以正常使用
                        Looper.loop();
                    }
                });
            }
        }
    }

    /**
     * 从任务队列中获取任务
     *
     * @return
     */
    private Runnable getTask() {
       /* if(akImageLoaderConfig.akImageLoaderType=AKImageLoaderType.LIFO){

        }*/
        mTasks.removeFirst();
        return null;
    }

    /**
     * 加载图片
     *
     * @param path      图片路径
     * @param imageView 目标view
     * @param erroPic   加载错误显示图片资源
     * @param defautPic 默认图片资源
     */
    public void loadImage(String path,
                          ImageView imageView,
                          @DrawableRes int erroPic,
                          @DrawableRes int defautPic
    ) {

    }

    private void sendToPool() {

    }

    /**
     * Image相关持有类
     */
    private class ImageHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }
}
