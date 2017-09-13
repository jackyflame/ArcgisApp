package com.haozi.baselibrary.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haozi.baselibrary.R;
import com.haozi.baselibrary.interfaces.listeners.DialogCallBack;

/**
 * Created by admin on 2017/8/9.
 */

public class ViewUtils {

    public static String getTextFromTextView(View view){
        if(view == null){
            return null;
        }
        if(view instanceof TextView){
            CharSequence charSequence = ((TextView)view).getText();
            if(charSequence != null){
                return charSequence.toString();
            }
        }
        return null;
    }

    /**
     * 消息展示
     * */
    public static AlertDialog showMsgDialog(Context context,String msg) {
        return showMsgDialog(context,msg,false,null);
    }

    /**
     * 消息展示
     * */
    public static AlertDialog showMsgDialog(Context context, int msg, final DialogCallBack callBack) {
        return showMsgDialog(context,msg,false,callBack);
    }

    /**
     * 消息展示
     * */
    public static AlertDialog showMsgDialog(Context context, int msg, boolean showCancel, final DialogCallBack callBack) {
        String msgStr = context.getString(msg);
        return showMsgDialog(context,msgStr,showCancel,callBack);
    }

    /**
     * 消息展示
     * */
    public static AlertDialog showMsgDialog(Context context, String msg, boolean showCancel, final DialogCallBack callBack) {
        AlertDialog.Builder dialogBuilder = new AlertDialog
                .Builder(context)
                .setTitle(context.getString(R.string.dialog_title_remined))
                .setMessage(msg)
                .setNegativeButton(context.getString(R.string.dialog_action_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(callBack != null){
                            callBack.negativeButtonCallBack();
                        }
                    }
                });
        if(showCancel){
            dialogBuilder.setNeutralButton(context.getString(R.string.dialog_action_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if(callBack != null){
                        callBack.neutralButtonCallBack();
                    }
                }
            });
        }
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static void Toast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
