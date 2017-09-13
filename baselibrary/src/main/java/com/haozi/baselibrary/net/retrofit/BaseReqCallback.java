package com.haozi.baselibrary.net.retrofit;

import com.haozi.baselibrary.event.HttpEvent;
import com.haozi.baselibrary.log.LogUtil;

/**
 * Created by Android Studio.
 * User:  jf.yin
 * Date: 2016/7/29
 * Time: 10:22
 */
public abstract class BaseReqCallback<T> implements ReqCallbackEx<T>{

    @Override
    public void onReqStart() {
        LogUtil.i("BaseReqCallback","onReqStart!");
    }

    @Override
    public abstract void onNetResp(T response);

    @Override
    public void onReqUpdate(Object obj) {
        LogUtil.i("BaseReqCallback","onReqUpdate:"+obj);
    }

    @Override
    public void onReqError(HttpEvent httpEvent) {
        LogUtil.i("BaseReqCallback","onReqError:"+httpEvent.getMessage());
    }
}
