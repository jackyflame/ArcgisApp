package com.jf.arcgisapp.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: haozi
 * Date: 2017/5/24
 * Time: 16:26
 */
public abstract class BaseListAdapter<T, P> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> list;
    protected LongSparseArray<T> wrapMap = new LongSparseArray<>();

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
        wrapMap.clear();
        if (getItemCount() > 0) {
            for (T t : this.list) {
                wrapMap.put(getIndex(t), t);
            }
        }
    }

    public List<T> getList() {
        return list;
    }

    public final T getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(buildBinding(LayoutInflater.from(parent.getContext()), parent, viewType));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(getViewModel(position));
    }

    protected abstract ViewDataBinding buildBinding(LayoutInflater layoutInflater, ViewGroup parent, int viewType);

    protected abstract P getViewModel(int position);

    public void addItem(int position, T t) {
        if (t == null)
            return;
        if (list == null) {
            list = new ArrayList<>();
            wrapMap.clear();
        }
        list.add(position, t);
        notifyItemInserted(position);
        wrapMap.put(getIndex(t), t);
    }

    public void addItemsToEnd(List<T> newlist) {
        if (newlist == null)
            return;
        if (list == null) {
            list = new ArrayList<>();
            wrapMap.clear();
        }
        int addStart = list.size() - 1;
        for(T item:newlist){
            list.add(item);
            wrapMap.put(getIndex(item), item);
        }
       notifyDataSetChanged();
    }

    public void addItem(T t) {
        addItem(0, t);
    }

    public boolean updateItem(T t) {
        if (t == null) return false;
        long key = getIndex(t);
        T localItem = wrapMap.get(key);
        if (localItem == null) return false;
        final int position = list.indexOf(localItem);
        if (position == -1) return false;
        wrapMap.put(key, t);
        list.set(position, t);
        notifyItemChanged(position);
        return true;
    }

    public void removeItemByPosition(int position) {
        T t = list.get(position);
        if (t == null) return;
        wrapMap.remove(getIndex(t));
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItemByIndex(long key) {
        T localItem = wrapMap.get(key);
        if (localItem == null) return;
        final int position = list.indexOf(localItem);
        if (position == -1) return;
        wrapMap.remove(key);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(T t) {
        if (t == null) return;

        long key = getIndex(t);
        T localItem = wrapMap.get(key);
        if (localItem == null) return;

        final int position = list.indexOf(localItem);
        if (position == -1) return;

        wrapMap.remove(key);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clearList() {
        if (getItemCount() > 0) {
            list.clear();
            notifyDataSetChanged();
            wrapMap.clear();
        }
    }

    public void resetList() {
        if (getItemCount() > 0) {
            list = new ArrayList<>();
            notifyDataSetChanged();
            wrapMap.clear();
        }
    }

    public long getIndex(T t) {
        return -1;
    }

}
