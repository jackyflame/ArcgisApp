package com.haozi.baselibrary.interfaces.views;

/**
 * Created by Haozi on 2017/5/23.
 */

public interface IActivityViewTools {

    void showProgressDialog();

    void showProgressDialog(boolean cancelable);

    void showProgressDialog(boolean cancelable,String content);

    void hideProgressDialog();

    void showToast(int stringRes);

    void showToast(int stringRes, Object... objects);

    void showToast(String string);
}
