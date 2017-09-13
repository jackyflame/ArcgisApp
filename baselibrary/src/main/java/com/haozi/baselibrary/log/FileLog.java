package com.haozi.baselibrary.log;

/**
 * Created by Android Studio.
 * User:  Lena.t.Yan
 * Date: 10/29/15
 * Time: 18:15
 */
public class FileLog {

    public static boolean sShowLog = false;

    public static void i(String s) {
        if (sShowLog) LogUtil.i(s);
    }

    public static void e(String s) {
        if (sShowLog) LogUtil.e(s);
    }
}
