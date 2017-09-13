package com.haozi.baselibrary.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.haozi.baselibrary.base.BaseApplication;

/**
 * AUTHOR：lanchuanke on 16/4/29 14:27
 */
public class NetWorkUtil {

    public static boolean isWifiState(Context context){
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if(info!=null){
            return ConnectivityManager.TYPE_WIFI == info.getType();
        }else{
            return false;
        }
    }

    /**
     * 网络是否联通（wifi或者移动网络）
     * */
    public static boolean isWifiOrMobile() {
        return isWifi() || isMobile();
    }

    /**
     * wifi是否链接成功
     * */
    public static boolean isWifi() {
        ConnectivityManager connManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        boolean isWifi = NetworkInfo.State.CONNECTED == state;
        return isWifi;
    }

    /**
     * 移动网络是否链接成功
     * */
    public static boolean isMobile() {
        ConnectivityManager connManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        boolean isMobile = NetworkInfo.State.CONNECTED == state;
        return isMobile;
    }

}
