package com.haozi.baselibrary.log;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogW {

    public static final String sLogTag = "CQHL";

    public static String LEVE_I="I/";
    public static String LEVE_E="E/";

    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean DEBUG = true;
    public static boolean WRITE_TO_FILE = false;

    private static long lastLogFileUpdateTime = 0;
    private static String lastLogFilePath = "";
    private static OutputStreamWriter logWriter;
    private static final String LOG_SUFFIX_WRITE_DEFAULT = "w";

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void i(String msg) {
        if (DEBUG) {
            if(msg == null){
                msg = "$log-msg-null$";
            }
            Log.i(sLogTag, msg);
            if(isWriteToFile()){
                writeLogToFile(LEVE_I,sLogTag,msg);
            }
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
            if(msg == null){
                msg = "$log-msg-null$";
            }
            Log.i(tag, msg);
            if(isWriteToFile()){
                writeLogToFile(LEVE_I,tag,msg);
            }
        }
    }

    public static void isuf(String tag, String msg, String suffix) {
        if (DEBUG) {
            if(msg == null){
                msg = "$log-msg-null$";
            }
            Log.i(tag, msg);
            if(isWriteToFile()){
                writeLogToFile(LEVE_I,tag,msg,suffix);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            if(msg == null){
                msg = "$log-msg-null$";
            }
            Log.e(tag, msg);
            if(isWriteToFile()){
                writeLogToFile(LEVE_E,tag,msg);
            }
        }
    }

    public static void esuf(String tag, String msg, String suffix) {
        if (DEBUG) {
            if(msg == null){
                msg = "$log-msg-null$";
            }
            Log.e(tag, msg);
            if(isWriteToFile()){
                writeLogToFile(LEVE_E,tag,msg,suffix);
            }
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            if(msg == null){
                msg = "$log-msg-null$";
            }
            Log.e(tag, msg, throwable);
            if(isWriteToFile()){
                writeLogToFile(LEVE_E,tag,msg,throwable);
            }
        }
    }

    public static boolean isWriteToFile(){
        //return (BuildConfig.LOG_WITEFILE && WRITE_TO_FILE);
        return false;
    }

    public static void writeLogToFile(String level, String tag, String msg, Throwable throwable){
        writeLogToFile(level, tag, msg, throwable,LOG_SUFFIX_WRITE_DEFAULT);
    }

    public static void writeLogToFile(String level, String tag, String msg){
        writeLogToFile(level, tag, msg, null, LOG_SUFFIX_WRITE_DEFAULT);
    }

    public static void writeLogToFile(String level, String tag, String msg, String suffix){
        writeLogToFile(level, tag, msg, null, suffix);
    }

    public static void writeLogToFile(String level, String tag, String msg, Throwable throwable, String suffix){
        StringBuilder logContent = new StringBuilder();
        logContent.append(myLogSdf.format(new Date()))
                .append(" ").append(level).append(tag).append(": ");
        logContent.append(msg);
        if(throwable != null){
            logContent.append("\n").append(throwable.getMessage());
        }
        recordLogInLastLogFile(logContent.toString(),suffix);
    }

    /**
     * 记录日志到最新的日志文件
     * @param logmsg
     * @return
     * @time 2016/11/8 11:09
     * */
    private static String recordLogInLastLogFile(String logmsg, String suffix) {
        try {
            if(lastLogFilePath == null || lastLogFilePath.isEmpty()
                    //超过10分钟没有更新日志，则重新检查最新的日志文件
                    || (System.currentTimeMillis() - lastLogFileUpdateTime > 10 * 1000 * 60)){
                //lastLogFilePath = LogWatherFileUtil.getInstance().getNowPeriodLogFileName();
                if(lastLogFilePath == null){
                    return null;
                }
                if(suffix == null || suffix.isEmpty()){
                    suffix = LOG_SUFFIX_WRITE_DEFAULT;
                }
                if(!lastLogFilePath.endsWith("log"+suffix)){
                    if(lastLogFilePath.lastIndexOf("0") > 0){
                        lastLogFilePath = lastLogFilePath.substring(0,lastLogFilePath.lastIndexOf("."))+".log"+suffix;
                    }else{
                        lastLogFilePath = lastLogFilePath +".log"+suffix;
                    }
                }
                Log.e(sLogTag, "create new logw file:"+lastLogFilePath);
            }
            File logFile = new File(lastLogFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            lastLogFileUpdateTime = System.currentTimeMillis();
            logWriter = new OutputStreamWriter(new FileOutputStream(lastLogFilePath, true));
            logWriter.write(logmsg);
            logWriter.write("\n");
            logWriter.flush();
            return lastLogFilePath;
        } catch (Exception e) {
            Log.e(sLogTag, "an error occured while writing file...", e);
        }
        return null;
    }
}
