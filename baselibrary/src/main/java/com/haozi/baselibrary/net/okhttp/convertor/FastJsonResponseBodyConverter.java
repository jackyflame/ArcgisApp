/*
 * Copyright (c) 2016 ShenBianVip, Ltd.
 * Unauthorized copying of this file, via any medium is strictly prohibited proprietary and
 *  confidential.
 * Created on 1/5/16 4:00 PM
 * ProjectName: shenbian_android_cloud_speaker ; ModuleName: CSpeakerPhone ; ClassName: FastJsonResponseBodyConverter.
 * Author: Lena; Last Modified: 1/5/16 4:00 PM.
 *  This file is originally created by Lena.
 */

package com.haozi.baselibrary.net.okhttp.convertor;

import com.alibaba.fastjson.JSON;
import com.haozi.baselibrary.constants.ContentType;
import com.haozi.baselibrary.log.LogUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String jsonString = new String(value.bytes(), ContentType.CHARSET_UTF_8);
            LogUtil.i("Parse Response Json :\n" + jsonString);
            return JSON.parseObject(jsonString, type);
        } catch (Exception e) {
            LogUtil.e("Parse Json Error :" + e.getMessage());
        }
        return null;
    }
}
