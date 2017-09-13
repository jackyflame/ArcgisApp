package com.haozi.baselibrary.net;

import android.net.Uri;

import com.haozi.baselibrary.net.config.Hosts;
import com.haozi.baselibrary.utils.StringUtil;

import java.util.Locale;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: haozi
 * Date: 2017/5/24
 * Time: 18:01
 */

public class UrlManager {
    /**
     * 获取http根地址
     * @return String
     * */
    public static String getHttpRootUrl() {
        String url = "http://" + Hosts.HTTPSERVER_URL + ":"
                + Hosts.HTTPSERVER_PORT + "/";
        return url;
    }

    /**
     * 根据传入子路径，获取http请求全址
     * @param subUrl
     * */
    public static String getHttpUrl(String subUrl) {
        String newUrl = null;
        if(StringUtil.isEmpty(subUrl) || subUrl.toLowerCase(Locale.getDefault()).startsWith("http")) {
            newUrl = subUrl;
        } else {
            newUrl = getHttpRootUrl() + subUrl;
        }
        return newUrl;
    }

    /**
     * 根据传入完整路径，获取相对路径
     * @param url
     * */
    public static String getRelativeAddress(String url) {
        if (StringUtil.isEmpty(url)) {
            return url;
        }
        Uri uri = Uri.parse(url);
        String path = uri.getPath();
        return (path.startsWith("/") ? path.replaceFirst("/", "") : path);
    }

    /**
     * 根据传入完整路径，获取URL名称
     * @param url
     * */
    public static String getUrlName(String url) {
        int lidx = url.lastIndexOf('/');
        String name = lidx >= 0 ? url.substring(lidx + 1) : url;
        return name;
    }

    /**
     * 获取http根地址
     * @return String
     * */
    public static String getFileRootUrl() {
        String url = "http://" + Hosts.FILEERVER_URL + ":"
                + Hosts.FILESERVER_PORT + "/";
        return url;
    }

    /**
     * 根据传入子路径，获取http请求全址
     * @param subUrl
     * */
    public static String getFileUrl(String subUrl) {
        String newUrl = null;
        if(StringUtil.isEmpty(subUrl) || subUrl.toLowerCase(Locale.getDefault()).startsWith("http")) {
            newUrl = subUrl;
        } else {
            newUrl = getFileRootUrl() + subUrl;
        }
        return newUrl;
    }
}
