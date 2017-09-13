package com.haozi.baselibrary.db;

import android.os.AsyncTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class LAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    @Override
    protected abstract void onPostExecute(Result result);

    public void executeParallel(Params... params) {
        Executor pool = Executors.newFixedThreadPool(10);
        executeOnExecutor(pool, params);
    }

}
