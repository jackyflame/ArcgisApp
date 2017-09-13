package com.jf.arcgisapp.base.vm;

import android.databinding.BaseObservable;

import com.haozi.baselibrary.db.BasePresent;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author:  haozi
 * Date: 5/21/17
 * Time: 10:52
 */
public class BaseVM<T extends BasePresent> extends BaseObservable {

    protected T mPrensent;

    public BaseVM() {}

    public BaseVM(T present) {
        this.mPrensent = present;
    }

}
