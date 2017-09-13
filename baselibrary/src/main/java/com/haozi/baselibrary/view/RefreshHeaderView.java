package com.haozi.baselibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.haozi.baselibrary.R;

/**
 * Created by Haozi on 2017/5/25.
 */

public class RefreshHeaderView extends LinearLayout implements SwipeRefreshTrigger, SwipeTrigger {

    private TextView title;
    private ProgressBar progress;

    public RefreshHeaderView(Context context) {
        super(context);
        //在构造函数中将Xml中定义的布局解析出来。
        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header, this, true);
        title = (TextView) view.findViewById(R.id.txv_title_fresh_header);
        progress = (ProgressBar) view.findViewById(R.id.progress_fresh_header);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //在构造函数中将Xml中定义的布局解析出来。
        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header, this, true);
        title = (TextView) view.findViewById(R.id.txv_title_fresh_header);
        progress = (ProgressBar) view.findViewById(R.id.progress_fresh_header);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {

    }

    @Override
    public void onRefresh() {
        title.setText("刷新中...");
        //progress.setVisibility(VISIBLE);
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                title.setText("松开刷新");
            } else {
                title.setText("下拉刷新");
            }
            title.postInvalidate();
        } else {
            //title.setText("读取数据...");
        }

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        //progress.setVisibility(INVISIBLE);
    }

    @Override
    public void onReset() {

    }
}
