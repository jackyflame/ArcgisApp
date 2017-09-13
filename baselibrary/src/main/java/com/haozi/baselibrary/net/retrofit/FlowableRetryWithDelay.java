package com.haozi.baselibrary.net.retrofit;

import com.haozi.baselibrary.log.LogW;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: haozi
 * Date: 2017/5/23
 * Time: 17:48
 */

public class FlowableRetryWithDelay implements Function<Flowable<? extends Throwable>, Flowable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public FlowableRetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Flowable<?> apply(@NonNull Flowable<? extends Throwable> flowable) throws Exception {
        return flowable.flatMap(new Function<Throwable, Flowable<?>>() {
            @Override
            public Flowable<?> apply(@NonNull Throwable throwable) throws Exception {
                if (++retryCount <= maxRetries) {
                    String errorMsg = throwable.getMessage();
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    LogW.e("get error :"+errorMsg+", it will try after " + retryDelayMillis  + " millisecond, retry count " + retryCount);
                    return Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Flowable.error(throwable);
            }
        });
    }
}
