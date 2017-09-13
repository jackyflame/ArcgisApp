package com.haozi.baselibrary.net.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.haozi.baselibrary.event.BaseEvent;
import com.haozi.baselibrary.event.RxBus;
import com.haozi.baselibrary.net.entity.RespEntity;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: yh
 * Date: 2017/5/23
 * Time: 11:32
 */
public class BaseWorker{

    private CompositeDisposable subscription = new CompositeDisposable();
    private boolean isAlive = true;

    private <T> WorkerCallback<T> defaultCallback(ReqCallback<T> callback) {
        return new WorkerCallback<>(this, callback);
    }

    public <T> void

    defaultCall(@NonNull Observable<Response<RespEntity<T>>> observable, @Nullable ReqCallback<T> callback) {
        observable.toFlowable(BackpressureStrategy.BUFFER)
                //.retryWhen(new FlowableRetryWithDelay(1,3000)) //Observer的时候使用：retryWhen(new RetryWithDelay(1,3000))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(defaultCallback(callback));
    }

    public <T> void call(@NonNull Observable<Response<RespEntity<T>>> observable, @NonNull WorkerCallback<T> callback) {
        subscription.add(observable.toFlowable(BackpressureStrategy.BUFFER)
                //.retryWhen(new FlowableRetryWithDelay(1,3000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeWith(callback));
    }

    public void subscribe(Disposable subscription) {
        this.subscription.add(subscription);
    }

    public void disposeAll(){
        this.subscription.dispose();
        this.subscription.clear();
    }

    public <T extends BaseEvent> void subscribeEvent(Class<T> aClass, Consumer<T> eventAction) {
        subscribe(
                RxBus.getInstance()
                        .asObservable(aClass)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(eventAction)
        );
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
