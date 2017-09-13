package com.haozi.baselibrary.net.okhttp.convertor;

import com.alibaba.fastjson.JSON;
import com.haozi.baselibrary.log.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;


public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        String postBody = JSON.toJSONString(value);
        LogUtil.i("Post Json Body " + postBody);
        return RequestBody.create(MEDIA_TYPE, postBody.getBytes(UTF_8));
    }
}
