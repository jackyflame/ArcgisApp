package com.jf.arcgisapp.base.adapter;

import com.haozi.baselibrary.interfaces.listeners.OnItemActionListener;
import com.haozi.baselibrary.interfaces.listeners.OnItemClickListener;

/**
 * Created by Haozi on 2017/5/29.
 */

public abstract class EventListAdapter<T, P> extends BaseListAdapter<T,P>{

    protected OnItemClickListener<T> listener;
    protected OnItemActionListener<T> actionListener;

    public void setOnItemClickListener(OnItemClickListener<T> listener){
        this.listener = listener;
    }

    public void setOnItemActionListener(OnItemActionListener<T> actionListener) {
        this.actionListener = actionListener;
    }
}
