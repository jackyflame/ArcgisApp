package com.haozi.baselibrary.event;

import android.os.Bundle;
public class BaseEvent {

    private int code;
    private String message;
    private Bundle bundle;

    public BaseEvent() {
    }

    public BaseEvent(int code) {
        this.code = code;
    }

    public BaseEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
