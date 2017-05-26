package com.zmq.lottery.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmq.lottery.R;
import com.zmq.lottery.utils.StatusBarCompat;
import com.zmq.lottery.utils.ToastUtils;


/**
 * Created by Administrator on 2017/5/15 0015.
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity activity; //
    protected Toolbar mToolbar;
    protected TextView tv_left, tv_title, tv_right;
    public View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (null != getArguments()) {
            getBundleExtras(getArguments());
        }

        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = initContentView(inflater, container, savedInstanceState);
        }
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tv_left = (TextView) view.findViewById(R.id.tv_toolbar_back);
        tv_title = (TextView) view.findViewById(R.id.tv_toolbar_title);
        tv_right = (TextView) view.findViewById(R.id.tv_toolbar_right);
        if (mToolbar != null) {
            activity.setSupportActionBar(mToolbar);
        }
        setStatusBar(Color.BLACK);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;//获取当前上下文联系

    }

    // 初始化UI setContentView
    protected abstract View initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                                            @Nullable Bundle savedInstanceState);

    // 初始化控件
    protected abstract void initView(View view);

    /**
     * 获取bundle信息
     *
     * @param bundle
     */
    protected abstract void getBundleExtras(Bundle bundle);
    /**
     * 改变状态栏颜色
     * @param colorId
     */
    public void setStatusBar(int colorId){
        if (colorId != Color.BLACK){
            StatusBarCompat.compat(activity, colorId);
        }else {
            StatusBarCompat.compat(activity, Color.BLACK);
        }
    }

    /**
     * 设置标题栏返回键
     * @param resId
     * @param msg
     * @param listener
     */
    public void setLeftIcon(int resId, String msg, View.OnClickListener listener) {
        tv_left.setVisibility(View.VISIBLE);
        Drawable drawable = ContextCompat.getDrawable(activity, resId);
        if (msg != null) {
            tv_left.setText(msg);
        }
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_left.setCompoundDrawables(drawable, null, null, null);
        if (listener != null) {
            tv_left.setOnClickListener(listener);
        }
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title) {
        if (title != null) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
    }

    /**
     * 设置标题栏右控件
     * @param resId
     * @param msg
     * @param listener
     */
    public void setRightIcon(int resId, String msg, View.OnClickListener listener) {
        tv_right.setVisibility(View.VISIBLE);
        Drawable drawable = ContextCompat.getDrawable(activity, resId);
        if (msg != null) {
            tv_right.setText(msg);
        }
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_right.setCompoundDrawables(drawable, null, null, null);
        if (listener != null) {
            tv_right.setOnClickListener(listener);
        }
    }


    /**
     * Toast显示
     * @param string
     */
    protected void showShortToast(String string) {
        ToastUtils.showShortToast(getActivity(), string);
    }

    protected void showShortToast(int stringId) {
        ToastUtils.showShortToast(getActivity(), stringId);
    }

    protected void showLongToast(String string) {
        ToastUtils.showShortToast(getActivity(), string);
    }

    protected void showLongToast(int stringId) {
        ToastUtils.showShortToast(getActivity(), stringId);
    }

    /**
     * 界面跳转
     *
     * @param cls 目标Activity
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 跳转界面，传参
     *
     * @param cls    目标Activity
     * @param bundle 数据
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param cls 目标Activity
     */
    protected void startActivityAndFinish(Class<?> cls) {
        startActivityAndFinish(cls, null);
    }

    /**
     * @param cls    目标Activity
     * @param bundle 数据
     */
    protected void startActivityAndFinish(Class<?> cls, Bundle bundle) {
        startActivity(cls, bundle);
        getActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void startActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
