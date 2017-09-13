package com.haozi.baselibrary.utils.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.haozi.baselibrary.R;

/**
 * Created by Android Studio.
 * ProjectName: ChongQingHaoLi
 * Author: Haozi
 * Date: 2017/7/15
 * Time: 11:34
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        ViewTarget.setTagId(R.id.glide_tag_id);
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
        //磁盘缓存100M
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 1024 * 1024 * 100));//内部磁盘缓存
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, 100 * 1024 * 1024));//磁盘缓存到外部存储
    }
}
