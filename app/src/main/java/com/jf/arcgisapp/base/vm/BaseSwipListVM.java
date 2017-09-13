package com.jf.arcgisapp.base.vm;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.haozi.baselibrary.db.BasePresent;
import com.jf.arcgisapp.base.adapter.BaseViewHolder;

import com.jf.arcgisapp.BR;

/**
 * Created by Haozi on 2017/5/26.
 */

public abstract class BaseSwipListVM<T extends BasePresent,X extends RecyclerView.Adapter<BaseViewHolder>> extends BaseVM<T> {

    /**上下文*/
    protected Context mContext;
    protected X adapter;
    protected LinearLayoutManager layoutManager;
    /**刷新状态标记*/
    protected boolean isRefreshing;
    protected boolean isLoadingMore;
    /**刷新监听*/
    protected OnLoadMoreListener loadMoreListener;
    protected OnRefreshListener refreshListener;
    /**当前分页*/
    protected int mCurPage = 1;

    public BaseSwipListVM(Context context,T present) {
        super(present);
        this.mContext = context;
    }

    protected abstract void refreshList();

    protected abstract void loadMoreList();

    //----------------------------------------VM数据绑定--------------------------------------------
    @Bindable
    public X getAdapter() {
        return adapter;
    }

    public void setAdapter(X adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public LinearLayoutManager getLayoutManager() {
        if(layoutManager == null){
            layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return layoutManager;
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager);
    }

    @Bindable
    public boolean getIsRefreshing() {
        return isRefreshing;
    }

    public void setIsRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
        notifyPropertyChanged(BR.isRefreshing);
    }

    @Bindable
    public boolean getIsLoadingMore() {
        return isLoadingMore;
    }

    public void setIsLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
        notifyPropertyChanged(BR.isLoadingMore);
    }

    @Bindable
    public OnLoadMoreListener getLoadMoreListener() {
        if(loadMoreListener == null){
            loadMoreListener = new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    loadMoreList();
                }
            };
        }
        return loadMoreListener;
    }

    @Bindable
    public OnRefreshListener getRefreshListener() {
        if(refreshListener == null){
            refreshListener = new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshList();
                }
            };
        }
        return refreshListener;
    }
}
