package com.haozi.baselibrary.net.retrofit;

import android.text.TextUtils;

import com.haozi.baselibrary.base.BaseApplication;
import com.haozi.baselibrary.db.controller.FileControl;
import com.haozi.baselibrary.log.LogUtil;
import com.haozi.baselibrary.net.config.Hosts;
import com.haozi.baselibrary.net.okhttp.convertor.FastJsonConvertFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by admin on 2016/10/18.
 */
public class RetrofitHelper {

    public static final long CONNECT_TIMEOUT = 30_000;
    public static final long WRITE_TIMEOUT = 30_000;
    public static final long READ_TIMEOUT = 30_000;
    public static final int REQUEST_CACHE_SIZE = 10 * 1024 * 1024;
    public static final int POOLING_MAX_CONNECTIONS = 5;
    public static final int REQUEST_KEEP_ALIVE_DEFAULT = 30000;

    private Retrofit mRetrofit;

    private static class SingletonHolder {
        /***单例对象实例*/
        static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return RetrofitHelper.SingletonHolder.INSTANCE;
    }

    public Retrofit provideRetrofit() {
        String baseUrl = Hosts.HTTPSERVER_URL;
        if(mRetrofit == null){
            mRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .client(provideOkHttp())
                    .addConverterFactory(FastJsonConvertFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public <T> T callAPI(Class<T> type){
        return provideRetrofit().create(type);
    }

    /**
     * OkHttpClient 日志记录实例
     * */
    private OkHttpClient provideOkHttp() {
        //初始化缓存文件控制器
        final FileControl fileCache = new FileControl(BaseApplication.getInstance());
        //初始化httplcient
        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cache(new Cache(new File(fileCache.requestCacheFloderPath()), REQUEST_CACHE_SIZE))
                .connectionPool(new ConnectionPool(POOLING_MAX_CONNECTIONS, REQUEST_KEEP_ALIVE_DEFAULT,TimeUnit.MILLISECONDS));
        //设置拦截日志
        final Logger sLogger = Logger.getLogger(LogUtil.sLogTag);
        sLogger.setLevel(Level.FINE);
        //增加日志拦截
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long t1 = System.nanoTime();
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();

                if (request.body() != null && TextUtils.isEmpty(request.headers().get("Content-Type"))) {
                    builder.header("Content-Type", "gzip").method(request.method(), request.body()).build();
                }
                request = builder
                        //.addHeader(Header.HEADER_CLIENT_KEY, Header.HEADER_CLIENT_VALUE)
                        //.addHeader(Header.HEADER_DEVICE_ID, DeviceConfigUtil.sDeviceId)
                        //.addHeader("User-Agent", System.getProperty("http.agent"))
                        .build();
                if (LogUtil.DEBUG)
                    sLogger.info(String.format("Sending request %s on Connecttion: %s %n Headers: %s ",
                            request.url(),
                            chain.connection(),
                            request.headers()));

                Response response = chain.proceed(request);
                long t2 = System.nanoTime();
                if (LogUtil.DEBUG){
                    sLogger.info(
                            String.format(
                                    "Received response for " +
                                    "%s in %.1fms %n" +
                                    "Response Status Code: %s %n" +
                                    "Response Headers: %s %n" +
                                    "Response CacheControl:%s %n" +
                                    "Response Body :%s %n" +
                                    "Is From Cached Response: %s %n",
                                    request.url(),
                                    (t2 - t1) / 1e6d,
                                    response.code(),
                                    response.headers(),
                                    response.cacheControl(),
                                    response,
                                    response.cacheResponse() == response.networkResponse()
                            )
                    );
                }
                return response;
            }
        });
        //返回客户端
        return httpClientBuilder.build();
    }
}
