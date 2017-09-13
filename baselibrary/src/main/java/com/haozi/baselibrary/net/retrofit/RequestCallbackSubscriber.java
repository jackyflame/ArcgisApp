package com.haozi.baselibrary.net.retrofit;

import com.haozi.baselibrary.event.HttpEvent;
import com.haozi.baselibrary.event.RxBus;
import com.haozi.baselibrary.net.config.ErrorType;
import com.haozi.baselibrary.net.entity.RespEntity;

import java.io.IOException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Response;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: haozi
 * Date: 2017/5/15
 * Time: 18:13
 */
public abstract class RequestCallbackSubscriber<T> extends ResourceSubscriber<Response<RespEntity<T>>> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
        HttpEvent baseEvent;
        if (e instanceof IOException) {
            baseEvent = new HttpEvent(ErrorType.ERROR_NETWORK, "网络异常");
        } else {
            baseEvent = new HttpEvent(ErrorType.ERROR_OTHER, "未知异常");
        }
        handleError(baseEvent);
    }

    @Override
    public void onNext(Response<RespEntity<T>> response) {
        RespEntity<T> respEntity = response.body();
        if (response.isSuccessful()) {
            if (respEntity == null) {
                handleError(new HttpEvent(ErrorType.ERROR_RESPONSE_NULL, "系统繁忙,请重试."));
            } else {
                if (respEntity.isSuccess()) {
                    if(respEntity.getCode() > 0 && respEntity.getCode() != 200){
                        handleError(new HttpEvent(respEntity.getCode(), respEntity.getMsg()));
                    }else{
                        onSuccess(respEntity.getRstdata());
                    }
                } else {
                    handleError(new HttpEvent(ErrorType.ERROR_SERVER, respEntity.getMsg()));
                }
            }
        } else {
            HttpEvent baseEvent;
            if (respEntity != null) {
                baseEvent = new HttpEvent(ErrorType.ERROR_SERVER, respEntity.getMsg());
            } else {
                baseEvent = new HttpEvent(response.code(), response.message());
            }
            handleError(baseEvent);
        }
    }

    private void handleError(HttpEvent httpEvent) {
        RxBus.getInstance().post(httpEvent);
        onError(httpEvent);
    }

    abstract public void onSuccess(T respEntity);

    abstract public void onError(HttpEvent baseEvent);
}
