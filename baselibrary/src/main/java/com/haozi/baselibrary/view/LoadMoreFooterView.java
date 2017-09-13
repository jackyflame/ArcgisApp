package com.haozi.baselibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.haozi.baselibrary.R;

/**
 * Created by Haozi on 2017/5/25.
 */

public class LoadMoreFooterView extends LinearLayout implements SwipeTrigger, SwipeLoadMoreTrigger {

    private LinearLayout mRootView;
    private TextView title;
    private ProgressBar progress;

    public LoadMoreFooterView(Context context) {
        super(context);
        //在构造函数中将Xml中定义的布局解析出来。
        mRootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_refresh_footer, this, true);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //在构造函数中将Xml中定义的布局解析出来。
        mRootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_refresh_footer, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.txv_title_fresh_footer);
        progress = (ProgressBar) findViewById(R.id.progress_fresh_footer);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void onLoadMore() {
        title.setText("加载中...");
        //progress.setVisibility(VISIBLE);
    }

    @Override
    public void onPrepare() {}

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                title.setText("松开加载");
            } else {
                title.setText("上拉加载");
            }
        } else {
            //title.setText("加载返回");
        }
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        //progress.setVisibility(GONE);
    }

    @Override
    public void onReset() {}
}
