package com.jf.arcgisapp.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author: haozi
 * Date: 2017/5/24
 * Time: 16:26
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    //TODO Warning : All viewModels in list cell layout must be named as "viewModel"!!!!!!!!!
    public void bindData(Object t) {
        binding.setVariable(BR.viewModel, t);
        binding.executePendingBindings();
    }

    public void bindData(int viewModelId, Object t) {
        binding.setVariable(viewModelId, t);
        binding.executePendingBindings();
    }

    public ViewDataBinding getDataBinding(){
        return binding;
    }
}
