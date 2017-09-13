package com.haozi.baselibrary.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haozi.baselibrary.log.LogUtil;
import com.haozi.baselibrary.utils.StringUtil;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: yh
 * Date: 2017/2/16
 * Time: 10:07
 */

public class SslWebViewClient extends WebViewClient {

    private Context mContext;
    private String requestUrl = "";

    public SslWebViewClient(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        //showHandler();
        if(StringUtil.isEmpty(url)){
            requestUrl = "";
        }else if(url.contains("?")){
            requestUrl = url.substring(0,url.indexOf("?"));
        }else{
            requestUrl = url;
        }
        LogUtil.i("SslWebViewClient 请求开始："+url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtil.i("SslWebViewClient 请求完成:"+url);
        //dismissHandler();
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        //dismissHandler();
        //vipInfoView.showToast(errorResponse.toString());
        LogUtil.e("SslWebViewClient 请求失败："+errorResponse.toString() + " => "+requestUrl);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        //dismissHandler();
        //vipInfoView.showToast("请求失败：（"+errorCode+"）"+description);
        LogUtil.e("SslWebViewClient 请求失败：（"+errorCode+"）"+description+ " => "+requestUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
        view.loadUrl(url);
        return true;
        //return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * 当load有ssl层的https页面时，如果这个网站的安全证书在Android无法得到认证，
     * WebView就会变成一个空白页，而并不会像PC浏览器中那样跳出一个风险提示框
     * */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //super.onReceivedSslError(view, handler, error);
        //接受所有证书(忽略证书的错误继续Load页面内容)
        handler.proceed();
        //handler.cancel();// Android默认的处理方式
        //handleMessage(Message msg); // 进行其他处理
    }
}
