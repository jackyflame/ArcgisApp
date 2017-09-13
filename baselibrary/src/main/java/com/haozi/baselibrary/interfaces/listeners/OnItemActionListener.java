package com.haozi.baselibrary.interfaces.listeners;

/**
 * Created by Android Studio.
 * ProjectName: ChongQingHaoLi
 * Author: haozi
 * Date: 2017/6/1
 * Time: 17:11
 */

public interface OnItemActionListener<T> {

    void onViewClick(T info);

    void onBuyClick(T info);
}
