/**
 * 
 */
package com.haozi.baselibrary.utils;

import android.graphics.Bitmap;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;

import com.haozi.baselibrary.R;
import com.haozi.baselibrary.base.BaseApplication;
import com.haozi.baselibrary.db.controller.FileControl;
import com.haozi.baselibrary.log.LogW;
import com.haozi.baselibrary.net.UrlManager;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.io.File;

/**
 * 类名：ImageLoaderUtils
 * @author yinhao
 * @功能
 * @创建日期 2015年11月5日 下午4:56:42
 * @备注 [修改者，修改日期，修改内容]
 */
public class ImageLoaderUtils {

	public ImageLoaderUtils() {
		//获取图片缓存路径
		File cacheDir = new File(FileControl.getInstance().getImageCacheRootPath());
		//设置配置文件
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(BaseApplication.getInstance())
				.diskCache(new UnlimitedDiskCache(cacheDir))
				//.imageDownloader(new ImageCustomDownloader(BaseApplication.getInstance(),5000,5000))
				.build();
		//初始化
		ImageLoader.getInstance().init(config);
	}

	/**静态单例初始化*/
    private static class SingletonHolder{
        /** 静态初始化器，由JVM来保证线程安全*/
        private static ImageLoaderUtils instance = new ImageLoaderUtils();
    }
    /**单例静态引用*/
    public static ImageLoaderUtils getInstance(){
        return SingletonHolder.instance;
    }
	
	
	/**
	 * 显示网络图片
	 * */
	public void displayImage(String imageUrl,ImageView mImageView){
        displayImage(imageUrl, R.drawable.img_default, mImageView);
	}

	/**
	 * 显示网络图片
	 * */
	public void displayImage(String imageUrl,int defaultImgres,ImageView mImageView){
		displayImage(imageUrl, defaultImgres, mImageView, null);
	}
	
	/**
	 * 显示网络图片
	 * */
	public void displayImage(String imageUrl,int defaultImgres,ImageView mImageView,ImageLoadingListener listener){
		//检查WIFI设施
		//if(NetWorkUtil.isWifi() == false){
		//	mImageView.setImageResource(defaultImgres);
		//	return;
		//}
		//检查图片url
		if(StringUtil.isEmpty(imageUrl)){
			mImageView.setImageResource(defaultImgres);
			return;
		}
		//判断是否为网络URL
		String serverImgulr = UrlManager.getFileUrl(imageUrl);
		//显示图片的配置  
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(defaultImgres)
		//.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		LogW.i("displayImage with url:"+serverImgulr);
		//加载图片
		try{
			if(listener == null){
				ImageLoader.getInstance().displayImage(serverImgulr, mImageView, options);
			}else{
				ImageLoader.getInstance().displayImage(serverImgulr, mImageView, options,listener);
			}
		}catch(Exception e){
			LogW.e("displayImage url is viliable!!!");
		}
	}
	
	/**
	 * 设置滚动刷新图片监听（用于禁止滚动的时候下载图片）
	 * */
	public PauseOnScrollListener getImgOnScrollListener(OnScrollListener listener){
		return new PauseOnScrollListener(ImageLoader.getInstance(),false,false,listener);
	}

	/**
	 * 设置滚动刷新图片监听（用于禁止滚动的时候下载图片）
	 * */
	public PauseOnScrollListener getImgOnScrollListener(){
		return new PauseOnScrollListener(ImageLoader.getInstance(),false,false,new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {}
		});
	}

	/**
	 * 清理内存缓存
	 */
	public void clearMemoryCache() {
		ImageLoader.getInstance().clearMemoryCache();
	}
	
	/**
	 * 清理本地缓存
	 */
	public void clearDiskCache(){
		ImageLoader.getInstance().clearDiskCache();
	}
}
