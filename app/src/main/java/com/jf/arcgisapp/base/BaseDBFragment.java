package com.jf.arcgisapp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.databinding.library.baseAdapters.BR;
import com.haozi.baselibrary.log.LogW;
import com.jf.arcgisapp.base.vm.BaseVM;

/**
 * Created by Haozi on 2017/5/21.
 */

public class BaseDBFragment<T extends ViewDataBinding,X extends BaseVM> extends Fragment {

    protected View mRootView;
    protected T mBinding;
    protected X viewModel;
    protected LayoutInflater mInflater;

    protected ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public T bindLayout(LayoutInflater inflater,int layoutRes, ViewGroup container) {
        return bindLayout(inflater,layoutRes,container,null);
    }

    public T bindLayout(LayoutInflater inflater,int layoutRes, ViewGroup container,X viewVm) {
        mInflater = inflater;
        mBinding = DataBindingUtil.inflate(inflater,layoutRes,container,false);
        mRootView = mBinding.getRoot();
        viewModel = viewVm;
        if(viewModel != null){
            bindData(viewModel);
        }
        return mBinding;
    }

    /**
     * @param t 绑定的数据模型 </>
     * 绑定VM:XML布局内的VM必须命名为“viewModel”
     * */
    protected void bindData(Object t) {
        bindData(BR.viewModel, t);
    }

    protected void bindData(int viewModelId, Object vm) {
        if(mBinding != null && vm != null){
            mBinding.setVariable(viewModelId, vm);
            mBinding.executePendingBindings();
        }else{
            LogW.i("bindData: failed cause bingd or vm is null");
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    public void hideKeyboard() {
        try {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            LogW.e("WTF!!!");
        }
    }

    public void showProgressDialog(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
        }
        if(mProgressDialog.isShowing() == false){
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }
}
