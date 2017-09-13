package com.haozi.baselibrary.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.haozi.baselibrary.base.BaseApplication;
import com.haozi.baselibrary.log.LogW;
import com.haozi.baselibrary.utils.FileUtil;
import com.haozi.baselibrary.utils.StringUtil;

import java.io.File;
import java.io.IOException;

/**
 * 类名：SystemUtil
 * @author YH 创建日期：2014年11月24日 [修改者，修改日期，修改内容]
 */
public class SystemUtil {

	/**相机图片缓存*/
	private static File takePicFile;

	/**
	 * 获取缓存中最近一次图片引用
	 * */
	public static File getTakePicFile() {
		return takePicFile;
	}

	/**
	 * 得到IMEI号
	 * @return
	 */
	public static String getDeviceIMEI() {
		String strIMEI = null;
		try {
			TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance()
					.getSystemService(Context.TELEPHONY_SERVICE);
			strIMEI = tm.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtil.isEmpty(strIMEI)) {
			strIMEI = Long.toString(System.nanoTime());
		}
		return strIMEI;
	}

	/**
	 * 检查操作系统版本是否大于10
	 * */
	public static boolean checkVersionGreaterThan10() {
		return Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	/**
	 * 检查操作系统版本是否大于17
	 * */
	public static boolean checkVersionGreaterThan17() {
		return Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	/**
	 * 获取apk文件包信息
	 * @param filePath apk文件路径
	 * */
	public static PackageInfo getAPKInfo(String filePath) {
		PackageManager pkgManger = BaseApplication.getInstance().getPackageManager();
		PackageInfo pkgInfo = pkgManger.getPackageArchiveInfo(filePath,
				PackageManager.GET_ACTIVITIES);
		return pkgInfo;
	}

	/**
	 * 获取调用系统添加图片功能参数
	 */
	@SuppressLint("InlinedApi")
	private static Intent getAddImgIntent(){
		//使用Intent.ACTION_GET_CONTENT这个Action
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		//开启Pictures画面Type设定为image
		innerIntent.setType("image/*");
		//根据版本设置不同参数
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			innerIntent.addCategory(Intent.CATEGORY_OPENABLE);
		} else {
			innerIntent.setAction(Intent.ACTION_GET_CONTENT);
		}
		return innerIntent;
	}
	
	/**
	 * 剪切图片
	 * @param act
	 * @param uri
	 * @param requestCode
	 */
	public static void cropImage(Activity act, Uri uri, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 1024);//最大宽度
		intent.putExtra("outputY", 1024);//最大高度
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		act.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 调用系统添加图片功能
	 * @param act
	 * @param requestCode
	 */
	@TargetApi(19)
	public static void addImage(Activity act, int requestCode) {
		act.startActivityForResult(getAddImgIntent(), requestCode);
	}

	/**
	 * 调用系统添加图片功能
	 * @param frag
	 * @param requestCode
	 */
	@TargetApi(19)
	public static void addImage(Fragment frag, int requestCode) {
		frag.startActivityForResult(getAddImgIntent(), requestCode);
	}
	
	/**
	 * 调用系统添加图片功能
	 * @param data
	 */
	public static File getAddPicFile(Intent data) {
		//获取URI
		Uri uri = data != null ? data.getData() : null;
		//转换为本地路径
		String imgPath = FileUtil.parseImagePath(uri);
		if(imgPath != null && imgPath.length() > 0) {
			File file = new File(imgPath);
			if(file.exists()) {
				if(FileUtil.isImage(imgPath)) {
					return file;
				} else {
					LogW.e("SystemUtil", "getAddPicture:Failed for Not img-file!");
					return null;
				}
			} else {
				LogW.e("SystemUtil", "getAddPicture:Failed for No File exsists!");
				return null;
			}
		} else {
			LogW.e("SystemUtil", "getAddPicture:Failed for EMPTY PATH!");
			return null;
		}
	}
	
	/**
	 * 获取拍照参数
	 * */
	private static Intent getTakePictureIntent(){
		//创建图片目录
		File dir = new File(FileUtil.PROJECT_IMAGE_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		//拍照路径设置
		takePicFile = new File(FileUtil.PROJECT_IMAGE_DIR, "take_temp.jpg");
		try {
			takePicFile.createNewFile();
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.6);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(takePicFile));
			return intent;
		} catch (IOException e) {
			e.printStackTrace();
			FileUtil.initDir();
		}
		return null;
	}
	
	/**
	 * 调用系统照相功能
	 * @param act
	 * @param requestCode
	 */
	public static void takePicture(Activity act, int requestCode) {
		act.startActivityForResult(getTakePictureIntent(), requestCode);
	}
	
	/**
	 * 调用系统照相功能
	 * @param frag
	 * @param requestCode
	 */
	public static void takePicture(Fragment frag, int requestCode) {
		frag.startActivityForResult(getTakePictureIntent(), requestCode);
	}

	/**
	 * 获取当前进程名
	 * @param context
	 * @return 进程名
	 */
	public static final String getProcessName(Context context) {
		String processName = null;

		// ActivityManager
		ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

		while (true) {
			for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
				if (info.pid == android.os.Process.myPid()) {
					processName = info.processName;

					break;
				}
			}

			// go home
			if (!TextUtils.isEmpty(processName)) {
				return processName;
			}

			// take a rest and again
			try {
				Thread.sleep(100L);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
