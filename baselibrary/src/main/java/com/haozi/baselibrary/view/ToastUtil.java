package com.haozi.baselibrary.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haozi.baselibrary.R;

/**
 * Created by Haozi on 2017/5/23.
 */

public class ToastUtil {

    public static final int TOAST_DURATION_LONG = Toast.LENGTH_LONG;

    /**
     * show toast message after action executed. the toast display time is short
     * default.
     *
     * @param context
     * @param message
     */

    private static Toast mToast = null;

    public static void show(Context context, String message) {
        if (context == null) {
            return;
        }
        View viewContainer = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) viewContainer;
        textView.setText(message);
        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setGravity(Gravity.BOTTOM, 0, 140);
        }
        mToast.setView(getToastView(message, context));
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * show toast message after action executed.
     *
     * @param context
     * @param message
     * @param duration : Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static void show(Context context, String message, int duration) {
        if (context == null) {
            return;
        }
        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setGravity(Gravity.BOTTOM, 0, 140);
        }
        mToast.setView(getToastView(message, context));
        mToast.setDuration(duration);
        mToast.show();
    }

    private static View getToastView(String message, Context context) {
        View viewContainer = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) viewContainer;
        textView.setText(message);
        return viewContainer;
    }
}
