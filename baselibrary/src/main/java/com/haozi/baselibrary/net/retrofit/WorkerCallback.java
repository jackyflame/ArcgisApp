package com.haozi.baselibrary.net.retrofit;

import com.haozi.baselibrary.event.HttpEvent;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: yh
 * Date: 2017/5/23
 * Time: 11:32
 */
public class WorkerCallback<T> extends RequestCallbackSubscriber<T> {

    private BaseWorker worker;
    private ReqCallback<T> callback;

    public WorkerCallback(BaseWorker worker, ReqCallback<T> callback) {
        this.worker = worker;
        this.callback = callback;
    }

    @Override
    public void onSuccess(T respEntity) {
        if (callback != null && worker.isAlive())
            callback.onNetResp(respEntity);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callback != null && worker.isAlive())
            callback.onReqStart();
    }

    @Override
    public void onError(HttpEvent baseEvent) {
        if (callback != null && worker.isAlive())
            callback.onReqError(baseEvent);
    }
}