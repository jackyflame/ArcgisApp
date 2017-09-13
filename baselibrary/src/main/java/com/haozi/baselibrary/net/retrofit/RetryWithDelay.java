package com.haozi.baselibrary.net.retrofit;

import com.haozi.baselibrary.log.LogW;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: yh
 * Date: 2017/3/29
 * Time: 15:05
 */

public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(final Observable<? extends Throwable> attempts) {
        return attempts.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                if (++retryCount <= maxRetries) {
                    String errorMsg = throwable.getMessage();
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    LogW.e("get error :"+errorMsg+", it will try after " + retryDelayMillis  + " millisecond, retry count " + retryCount);
                    return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error(throwable);
            }
        });
    }

}
