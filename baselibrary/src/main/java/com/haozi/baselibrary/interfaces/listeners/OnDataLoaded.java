package com.haozi.baselibrary.interfaces.listeners;

import com.haozi.baselibrary.event.BaseEvent;

public interface OnDataLoaded<T> {
    void onStart();

    void onLoaded(T result);

    void onError(BaseEvent baseEvent);
}
