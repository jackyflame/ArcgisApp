package com.haozi.baselibrary.base;

import android.app.Activity;

import com.haozi.baselibrary.log.LogW;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * ProjectName: CSpeakerAndroid
 * Author: haozi
 * Date: 2017/6/5
 * Time: 15:44
 */
public class ActivityManager {

    private static ActivityManager sInstance = new ActivityManager();
    private WeakReference<Activity> sCurrentActivityWeakRef;

    private ActivityManager() {}

    public static ActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
            LogW.i("--->>> currentActivity is " + currentActivity.getClass().getSimpleName());
        }else{
            LogW.i("--->>> currentActivity is null");
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }
}
