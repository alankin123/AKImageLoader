package com.alankin.myimageloader;

/**
 * Created by alankin on 2017/1/22.
 */
public class Utils {
    public static String getName(String url) {
        String substring = url.substring(url.lastIndexOf("/") + 1);
        return substring;
    }
}
