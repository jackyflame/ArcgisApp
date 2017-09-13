package com.haozi.baselibrary.db;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: yh
 * Date: 2017/5/9
 * Time: 16:56
 */

public abstract class WeakAsyncTask<Params, Progress, Result, WeakTarget> extends AsyncTask<Params, Progress, Result> {

    protected WeakReference<WeakTarget> mTarget;

    public WeakAsyncTask(WeakTarget target) {
        mTarget = new WeakReference<WeakTarget>(target);
    }

    /**
     * 调用执行接口（所有执行均调用此接口，方便使用线程池）
     * */
    public void executeParallel(Params... params) {
        Executor pool = Executors.newFixedThreadPool(10);
        executeOnExecutor(pool, params);
    }

    @Override
    protected final void onPreExecute() {
        final WeakTarget target = mTarget.get();
        if (target != null) {
            this.onPreExecute(target);
        }
    }

    @Override
    protected final Result doInBackground(Params... params) {
        final WeakTarget target = mTarget.get();
        return this.doInBackground(target, params);
    }

    @Override
    protected final void onPostExecute(Result result) {
        final WeakTarget target = mTarget.get();
        this.onPostExecute(target, result);
    }

    protected void onPreExecute(WeakTarget target) {
        // No default action
    }

    protected abstract Result doInBackground(WeakTarget target,Params... params);

    protected abstract void onPostExecute(WeakTarget target, Result result);
}
