package com.haozi.baselibrary.net.retrofit;

import com.haozi.baselibrary.event.HttpEvent;

public interface ReqCallback<T> {

    void onReqStart();

    void onNetResp(T response);

    void onReqError(HttpEvent httpEvent);

}
