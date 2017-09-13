package com.haozi.baselibrary.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.haozi.baselibrary.base.BaseApplication;

/**
 * Created by admin on 2016/10/19.
 */

public class MyShareDbHelper {

    private SharedPreferences sharedPreferences;

    private static class SingletonHolder {
        /***
         * 单例对象实例
         */
        static final MyShareDbHelper INSTANCE = new MyShareDbHelper();
    }

    public static MyShareDbHelper getInstance() {
        return MyShareDbHelper.SingletonHolder.INSTANCE;
    }

    public SharedPreferences getSharedPreferences(){
        if(sharedPreferences == null){
            String applicationId = BaseApplication.getInstance().getPackageName();
            PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                sharedPreferences = BaseApplication.getInstance().getSharedPreferences(applicationId, Context.MODE_MULTI_PROCESS);
            } else {
                sharedPreferences = BaseApplication.getInstance().getSharedPreferences(applicationId, 0);
            }
        }
        return sharedPreferences;
    }

    public static void putBoolean(String key,boolean value){
        getInstance().getSharedPreferences().edit().putBoolean(key,value).apply();
    }

    public static boolean getBoolean(String key,boolean defaultValue){
        return getInstance().getSharedPreferences().getBoolean(key,defaultValue);
    }

    public static void putInt(String key,int value){
        getInstance().getSharedPreferences().edit().putInt(key,value).apply();
    }

    public static int getInt(String key,int defaultValue){
        return getInstance().getSharedPreferences().getInt(key,defaultValue);
    }

    public static void putString(String key,String value){
        getInstance().getSharedPreferences().edit().putString(key,value).apply();
    }

    public static String getString(String key,String defaultValue){
        return getInstance().getSharedPreferences().getString(key,defaultValue);
    }

    public static Long getLong(String key,long defaultValue){
        return getInstance().getSharedPreferences().getLong(key,defaultValue);
    }

    public static void putLong(String key,long value){
        getInstance().getSharedPreferences().edit().putLong(key,value).apply();
    }

    //-----------------------------------------------------------------------------------
}
