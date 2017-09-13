package com.jf.arcgisapp.vm;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.haozi.baselibrary.base.ActivityManager;
import com.haozi.baselibrary.base.BaseApplication;
import com.haozi.baselibrary.db.WeakAsyncTask;
import com.haozi.baselibrary.net.UrlManager;
import com.haozi.baselibrary.utils.ImageLoaderUtils;
import com.haozi.baselibrary.utils.StringUtil;
import com.haozi.baselibrary.utils.VideoImageUtil;
import com.haozi.baselibrary.utils.glide.GlideApp;
import com.jf.arcgisapp.R;

import java.io.File;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifIOException;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: haozi
 * Date: 2017/5/26
 * Time: 18:03
 */

public class BindingAdapters {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        ImageLoaderUtils.getInstance().displayImage(imageUrl, R.mipmap.img_default,view);
    }

    @BindingAdapter({"imageUrl","defaultRes"})
    public static void loadImageWithDefault(ImageView view, String imageUrl,int defaultRes) {
        if(defaultRes > 0){
            ImageLoaderUtils.getInstance().displayImage(imageUrl, defaultRes,view);
        }else{
            ImageLoaderUtils.getInstance().displayImage(imageUrl, R.mipmap.img_default, view);
        }
    }

    @BindingAdapter({"gifUrl"})
    public static void loadGifImage(ImageView view, String imageUrl) {
        //检查图片url
        if(StringUtil.isEmpty(imageUrl)){
            view.setImageResource(R.mipmap.img_default);
            return;
        }
        //判断是否为网络URL
        String serverImgulr = UrlManager.getFileUrl(imageUrl);
        //展示图片
        GlideApp.with(BaseApplication.getInstance())
                .load(serverImgulr)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .override(400,600)
                .into(view);
    }

    @BindingAdapter({"gifUrl","defaultRes"})
    public static void loadGifImageWithDefault(ImageView view, String imageUrl,int defaultRes) {
        if(defaultRes <= 0){
            defaultRes = R.mipmap.img_default;
        }
        //检查图片url
        if(StringUtil.isEmpty(imageUrl)){
            view.setImageResource(defaultRes);
            return;
        }
        //判断是否为网络URL
        String serverImgulr = UrlManager.getFileUrl(imageUrl);
        //展示图片
        GlideApp.with(ActivityManager.getInstance().getCurrentActivity())
                .load(serverImgulr)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(defaultRes)
                .error(defaultRes)
                .override(400,600)
                .into(view);
    }

    @BindingAdapter({"gifImageUrl"})
    public static void loadGifToGifImage(final GifImageView view, String imageUrl) {
        //先显示默认图片
        view.setImageResource(R.mipmap.img_default);
        //检查图片url
        if(StringUtil.isEmpty(imageUrl)){
            return;
        }
        //判断是否为网络URL
        String serverImgulr = UrlManager.getFileUrl(imageUrl);
        //设置到GiftImageView
        ViewTarget<GifImageView,File> target = new ViewTarget<GifImageView,File>(view){
            @Override
            public void onResourceReady(File resource, Transition transition) {
                try {
                    GifDrawable drawable = new GifDrawable(resource);
                    if(drawable  == null){
                        this.view.setImageResource(R.mipmap.img_default);
                    }else{
                        this.view.setImageDrawable(drawable);
                    }
                }catch (Exception e){
                    if(e instanceof GifIOException){
                        try {
                            Drawable drawable = GifDrawable.createFromPath(resource.getPath());
                            if (drawable == null) {
                                this.view.setImageResource(R.mipmap.img_default);
                            } else {
                                this.view.setImageDrawable(drawable);
                            }
                        }catch (Exception ein){
                            ein.printStackTrace();
                        }
                    }
                }
            }
        };
        //展示图片
        RequestBuilder<File> requestBuilder= GlideApp.with(ActivityManager.getInstance().getCurrentActivity())
                .asFile()
                .load(serverImgulr)
                .override(400,600);
        requestBuilder.into(target);
    }

    @BindingAdapter({"videoUrl"})
    public static void loadVideoImage(ImageView view, final String videoUrl) {
        new WeakAsyncTask<String,Void,Bitmap,ImageView>(view){
            @Override
            protected Bitmap doInBackground(ImageView imageView, String... params) {
                Bitmap bitmap = VideoImageUtil.createVideoThumbnail(params[0], MediaStore.Images.Thumbnails.MINI_KIND);
                return bitmap;
            }
            @Override
            protected void onPostExecute(ImageView imageView, Bitmap bitmap) {
                if(bitmap != null && imageView != null){
                    imageView.setImageBitmap(bitmap);
                }else{
                    //imageView.setImageResource(R.mipmap.img_video_default);
                }
            }
        }.executeParallel(videoUrl);
    }
}
