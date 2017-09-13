package com.haozi.baselibrary.interfaces;

import com.haozi.baselibrary.interfaces.listeners.OnDataLoaded;

public interface IFileControl {

    <T> T get(String key);

    <T> T get(String key, T defaultValue);

    <T> void put(String key, T value);

    <T> void asyncPut(final String key, final T value);

     <T> void asyncGet(final String key, final OnDataLoaded<T> onDataLoaded);

    int remove(String key);

    boolean exists(String key);

    void cacheCheck();

    void cleanCacheUpdateVersion();

    String requestCacheFloderPath();
}
