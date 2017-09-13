package com.haozi.baselibrary.net;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Android Studio.
 * ProjectName: ChongQingHaoLi
 * Author: haozi
 * Date: 2017/6/20
 * Time: 11:03
 */

public class ExpandWebView extends WebView {

    public ExpandWebView(Context context) {
        super(context.getApplicationContext());
    }

    public ExpandWebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
    }

    public ExpandWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context.getApplicationContext(), attrs, defStyleAttr);
    }
}
