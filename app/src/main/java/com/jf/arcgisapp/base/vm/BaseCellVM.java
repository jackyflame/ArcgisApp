package com.jf.arcgisapp.base.vm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.haozi.baselibrary.interfaces.listeners.OnItemActionListener;
import com.haozi.baselibrary.interfaces.listeners.OnItemClickListener;

/**
 * Created by Haozi on 2017/5/29.
 */

public class BaseCellVM<T> extends BaseObservable {

    protected T item;
    protected OnItemClickListener<T> itemClickListener;
    protected OnItemActionListener<T> actionListener;

    public BaseCellVM(T item){
        this.item = item;
    }

    public BaseCellVM(T item, OnItemClickListener<T> listener) {
        this.item = item;
        itemClickListener = listener;
    }

    public BaseCellVM(T item, OnItemActionListener<T> listener) {
        this.item = item;
        actionListener = listener;
    }

    public BaseCellVM(T item, OnItemClickListener<T> itemClickListener, OnItemActionListener<T> actionListener) {
        this.item = item;
        this.itemClickListener = itemClickListener;
        this.actionListener = actionListener;
    }

    @Bindable
    public T getItem() {
        return item;
    }

    public void onItemClick(View view){
        if(itemClickListener != null){
            itemClickListener.onItemClick(item);
        }
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemActionListener(OnItemActionListener<T> actionListener) {
        this.actionListener = actionListener;
    }

    public void onViewClick(View view){
        if(actionListener != null){
            actionListener.onViewClick(item);
        }
    }
    public void onBuyClick(View view){
        if(actionListener != null){
            actionListener.onBuyClick(item);
        }
    }
}
