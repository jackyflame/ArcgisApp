package com.jf.arcgisapp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.haozi.baselibrary.interfaces.views.IActivityViewTools;
import com.haozi.baselibrary.log.LogW;
import com.haozi.baselibrary.utils.StringUtil;
import com.haozi.baselibrary.view.ToastUtil;
import com.jf.arcgisapp.BR;
import com.jf.arcgisapp.R;
import com.jf.arcgisapp.base.vm.BaseVM;

/**
 * Created by Haozi on 2017/5/21.
 */

public class BaseDBActivity<X extends ViewDataBinding,T extends BaseVM> extends AppCompatActivity implements IActivityViewTools {

    protected T viewModel;
    protected X mBinding;
    //UI控件
    private ProgressDialog progressDialog;
    private TextView progressContent;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    public X bindLayout(int layoutRes) {
        return bindLayout(layoutRes, null,true);
    }

    public X bindLayout(int layoutRes,T vm) {
        return bindLayout(layoutRes, vm, true);
    }

    public X bindLayout(int layoutRes,T viewModel, boolean homeAsUp) {
        //绑定页面
        mBinding = DataBindingUtil.setContentView(this, layoutRes);
        //操作titlebar
        initTitleBar(homeAsUp);
        //绑定viewModel
        this.viewModel = viewModel;
        if(this.viewModel != null){
            bindData(this.viewModel);
        }
        //返回mbinding
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            mBinding.executePendingBindings();
        }
        return mBinding;
    }

    private void initTitleBar(boolean homeAsUp){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titlebar = (TextView) findViewById(R.id.toolbar_title);
        if (toolbar != null) {
            toolbar.setSubtitle("");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUp);
                //if (homeAsUp == false) {
                //    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_selector);
                //}
            }
            if(titlebar != null){
                titlebar.setText(toolbar.getTitle());
                toolbar.setTitle("");
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    public void setTitle(int textRes){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titlebar = (TextView) findViewById(R.id.toolbar_title);
        if(titlebar != null){
            titlebar.setText(textRes);
        }else if(toolbar != null){
            toolbar.setTitle(textRes);
        }
    }

    public void setTitle(String text){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titlebar = (TextView) findViewById(R.id.toolbar_title);
        if(titlebar != null){
            titlebar.setText(text);
        }else if(toolbar != null){
            toolbar.setTitle(text);
        }
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(true);
    }

    @Override
    public void showProgressDialog(boolean cancelable) {
        showProgressDialog(cancelable,null);
    }

    @Override
    public void showProgressDialog(boolean cancelable,String content) {
        if (isFinishing()) return;
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(cancelable);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            //刷新页面
            View view = getLayoutInflater().inflate(R.layout.layout_progress,null);
            progressContent = (TextView) view.findViewById(R.id.txv_tips);
            if(progressContent != null && !StringUtil.isEmpty(content)){
                progressContent.setText(content);
                progressContent.setVisibility(View.VISIBLE);
            }else{
                progressContent.setVisibility(View.GONE);
            }
            progressDialog.setContentView(view);
        }else if(!StringUtil.isEmpty(content)){
            if(progressContent != null){
                progressContent.setText(content);
                progressContent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
            progressContent = null;
        }
    }

    @Override
    public void showToast(int stringRes) {
        ToastUtil.show(this, getString(stringRes));
    }

    @Override
    public void showToast(int stringRes, Object... objects) {
        ToastUtil.show(this, getString(stringRes, objects));
    }

    @Override
    public void showToast(String string) {
        ToastUtil.show(this, string);
    }

    protected void hideKeyboard() {
        try {
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            LogW.e("WTF!!!");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
