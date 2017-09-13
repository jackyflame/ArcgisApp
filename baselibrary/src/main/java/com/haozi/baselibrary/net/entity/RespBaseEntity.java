package com.haozi.baselibrary.net.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Android Studio.
 * User:  Lena.t.Yan
 * Date: 11/9/15
 * Time: 09:09
 */
public class RespBaseEntity {

    @JSONField(name = "attributes")
    private String attributes;
    @JSONField(name = "success")
    private boolean success;
    @JSONField(name = "msg")
    private String msg;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
