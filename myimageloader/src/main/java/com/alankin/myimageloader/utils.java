package com.alankin.myimageloader;

/**
 * Created by alankin on 2017/1/22.
 */
public class Utils {
    /**
     * url截取文件完整名称（包括格式名）
     *
     * @param url
     * @return
     */
    public static String getWholeName(String url) {
        if (url.lastIndexOf("/") == -1) {
            return "";
        }
        String substring = url.substring(url.lastIndexOf("/") + 1);
        return substring;
    }

    public static String getName(String wholeName) {
        if (wholeName.indexOf(".") == -1) {
            return "";
        }
        return wholeName.substring(0, wholeName.indexOf("."));
    }

}
