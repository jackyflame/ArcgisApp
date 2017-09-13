package com.haozi.baselibrary.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.haozi.baselibrary.base.BaseApplication;
import com.haozi.baselibrary.log.FileLog;

import java.io.File;
import java.util.Locale;

public abstract class FileUtil {

    public static String PROJECT_DIR;
    public static String PROJECT_IMAGE_DIR;
    public static String PROJECT_IMAGE_HEADER_CACHE;

    private static final String TAG = "FileUtil";

    public static void initDir() {
        /**系统级app缓存目录*/
        String cachePath;
        //如果SD卡可用，或者外部存储卡可被拆卸，则使用系统缓存目录
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            //存储于android标准的缓存目录下（可以通过android程序管理中的清除缓存来清理掉）
            cachePath = BaseApplication.getInstance().getExternalCacheDir().getPath();
        } else {
            cachePath = BaseApplication.getInstance().getCacheDir().getPath();
        }
        PROJECT_DIR = cachePath;
        File dir = new File(PROJECT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        PROJECT_IMAGE_DIR = PROJECT_DIR + File.separator + "image";
        dir = new File(PROJECT_IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        PROJECT_IMAGE_HEADER_CACHE = PROJECT_DIR + File.separator + "image" + File.separator + "hearder_temp.jpg";
    }

    public static boolean delete(String fileName) {
        return delete(new File(fileName));
    }

    public static boolean delete(File file) {
        if (!file.exists()) {
            FileLog.i(TAG + "Delete " + file.getPath() + " Fail.");
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(file.getPath());
            } else {
                return deleteDirectory(file.getPath(), true);
            }
        }
    }

    public static boolean deleteChild(String fileName) {
        return deleteChild(new File(fileName));
    }

    public static boolean deleteChild(File file) {
        if (!file.exists()) {
            FileLog.i(TAG + "Delete Child " + file.getPath() + " Fail.");
            return false;
        } else {
            if (file.isFile()) {
                FileLog.i(TAG + "Delete Child in" + file.getPath() + " Fail," + file.getPath() + " is not a directory.");
                return false;
            } else {
                return deleteDirectory(file.getPath(), false);
            }
        }
    }

    public static boolean deleteFile(String fileName) {
        return deleteFile(new File(fileName));
    }

    public static boolean deleteFile(File file) {
        if (file.isFile() && file.exists()) {
            return file.delete();
        } else {
            FileLog.i(TAG + "Delete " + file.getPath() + " Fail");
            return false;
        }
    }

    public static boolean deleteDirectory(String dir, boolean deleteSelf) {
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        return deleteDirectory(new File(dir), deleteSelf);
    }

    public static boolean deleteDirectory(File dirFile, boolean deleteSelf) {
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            FileLog.i(TAG + "Derectory" + dirFile.getPath() + " not exist");
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath(), true);
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            FileLog.i(TAG + "Delete Derectory " + dirFile.getPath() + " Fail");
            return false;
        }
        if (deleteSelf) {
            if (dirFile.delete()) {
                return true;
            } else {
                FileLog.i(TAG + "Delete Derectory " + dirFile.getPath() + " Fail");
                return false;
            }
        }
        return true;
    }

    /**
     * 解析文件URI
     * @param uri 文件URI
     * @return 文件地址
     */
    public static String parseImagePath(Uri uri) {
        if (uri != null) {
            String scheme = uri.getScheme();
            if ("content".equalsIgnoreCase(scheme)) {
                return getImageFromUri(uri);
            } else if ("file".equalsIgnoreCase(scheme)) {
                return uri.getPath();
            }
        }
        return null;
    }

    /**
     * 根据文件URI得到文件本地路径
     * @param uri 文件URI
     * @return 文件本地路径
     */
    public static String getImageFromUri(Uri uri) {
        //文件路径
        String filePath = null;
        //检查当前系统版本是否大于等于19（即4.4.2）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            if (!TextUtils.isEmpty(wholeID) && wholeID.contains(":")) {
                String id = wholeID.split(":")[1];
                String[] column = { MediaStore.Images.Media.DATA };
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor csr = BaseApplication.getInstance().getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                column, sel, new String[] { id }, null);
                if (csr != null) {
                    int columnIndex = csr.getColumnIndex(column[0]);
                    if (csr.moveToFirst()) {
                        filePath = csr.getString(columnIndex);
                    }
                    csr.close();
                }
            }
            //若小于，则用兼容方案
        } else {
            Cursor c = BaseApplication.getInstance().getContentResolver()
                    .query(uri, new String[] { MediaStore.Images.Media.DATA }, null, null,null);
            if(c != null){
                if (c.moveToFirst()) {
                    filePath = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }
                c.close();
            }
        }
        return filePath;
    }

    /**
     * 根据后缀名判断是否是图片文件
     * @param filePath
     * @return 是否是图片结果true or false
     */
    public static boolean isImage(String filePath) {
        String str = getFileType(filePath);
        if (str != null
                && (str.equalsIgnoreCase("jpg") || str.equalsIgnoreCase("gif")
                || str.equalsIgnoreCase("png")
                || str.equalsIgnoreCase("jpeg")
                || str.equalsIgnoreCase("bmp")
                || str.equalsIgnoreCase("wbmp")
                || str.equalsIgnoreCase("ico") || str
                .equalsIgnoreCase("jpe"))) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件类型（例如 abc.ff 返回 ff）
     * @param fileName
     * @return 文件后缀名
     */
    public static String getFileType(String fileName) {
        if (fileName != null) {
            int typeIndex = fileName.lastIndexOf(".");
            if (typeIndex != -1) {
                String fileType = fileName.substring(typeIndex + 1).toLowerCase(Locale.getDefault());
                return fileType;
            }
        }
        return "";
    }
}
