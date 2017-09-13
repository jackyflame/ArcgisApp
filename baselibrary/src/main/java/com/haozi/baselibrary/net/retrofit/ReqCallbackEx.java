package com.haozi.baselibrary.net.retrofit;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: haozi
 * Date: 2017/5/15
 * Time: 18:13
 */
public interface ReqCallbackEx<T> extends ReqCallback<T> {

    void onReqUpdate(Object obj);

}
