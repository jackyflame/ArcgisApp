package com.haozi.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.haozi.baselibrary.utils.FileUtil;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: yh
 * Date: 2017/5/22
 * Time: 17:35
 */

public class BaseApplication extends Application {

    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        FileUtil.initDir();
        registerActivityLifecycleCallbacks();
    }

    public static BaseApplication getInstance(){
        return application;
    }

    private void registerActivityLifecycleCallbacks(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }
            @Override
            public void onActivityStarted(Activity activity) {

            }
            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManager.getInstance().setCurrentActivity(activity);
            }
            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityStopped(Activity activity) {

            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
