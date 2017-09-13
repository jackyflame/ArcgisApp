package com.haozi.baselibrary.log;

public class LogUtil {

    public static String sLogTag = "GASSCANER";

    public static boolean DEBUG = true;

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void setsLogTag(String sLogTag) {
        LogUtil.sLogTag = sLogTag;
    }

    public static void i(String msg) {
        if (DEBUG) {
            LogW.i(sLogTag, msg);
        }
    }

    public static void isuf(String msg, String suffix) {
        if (DEBUG) {
            LogW.isuf(sLogTag, msg, suffix);
        }
    }

    public static void e(String msg) {
        e(sLogTag, msg);
    }

    public static void e(String msg, Throwable throwable) {
        e(sLogTag, msg, throwable);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            LogW.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            LogW.e(tag, msg);
        }
    }

    public static void esuf(String msg, String suffix) {
        if (DEBUG) {
            LogW.esuf(sLogTag, msg, suffix);
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            LogW.e(tag, msg, throwable);
        }
    }

}
