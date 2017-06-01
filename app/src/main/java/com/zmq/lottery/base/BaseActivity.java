package com.zmq.lottery.base;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zmq.lottery.R;
import com.zmq.lottery.utils.StatusBarCompat;
import com.zmq.lottery.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected TextView tv_left, tv_title, tv_right;
    protected BaseActivity context;
    private int resId;
    private Unbinder unbinder;

    public BaseActivity(int resId) {
        this.resId = resId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resId);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_left = (TextView) findViewById(R.id.tv_toolbar_back);
        tv_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tv_right = (TextView) findViewById(R.id.tv_toolbar_right);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setStatusBar(Color.BLACK);
        initContentView(savedInstanceState);
        unbinder = ButterKnife.bind(this); //绑定注解
        initView();
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected void getBundleExtras(Bundle extras){};

    protected void initContentView(Bundle bundle){};//恢复数据

    protected abstract void initView();//初始化控件

    /**
     * 改变状态栏颜色
     *
     * @param colorId
     */
    public void setStatusBar(int colorId) {
        if (colorId != Color.BLACK) {
            StatusBarCompat.compat(this, colorId);
        } else {
            StatusBarCompat.compat(this, Color.BLACK);
        }
    }


    /**
     * 设置标题栏返回键
     *
     * @param resId
     * @param msg
     * @param listener
     */
    public void setLeftIcon(int resId, String msg, View.OnClickListener listener) {
        tv_left.setVisibility(View.VISIBLE);
        Drawable drawable = ContextCompat.getDrawable(this, resId);
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
     *
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
     *
     * @param resId
     * @param msg
     * @param listener
     */
    public void setRightIcon(int resId, String msg, View.OnClickListener listener) {
        tv_right.setVisibility(View.VISIBLE);
        Drawable drawable = ContextCompat.getDrawable(this, resId);
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
     */
    protected void showShortToast(String string) {
        ToastUtils.showShortToast(this, string);
    }

    protected void showShortToast(int stringId) {
        ToastUtils.showShortToast(this, stringId);
    }

    protected void showLongToast(String string) {
        ToastUtils.showShortToast(this, string);
    }

    protected void showLongToast(int stringId) {
        ToastUtils.showShortToast(this, stringId);
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
        Intent intent = new Intent(this, cls);
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
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
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
        Intent intent = new Intent(this, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 切换fragment页面
     *
     * @param fragment
     */
    public void replaceFragment(int resId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        beginTransaction.replace(resId, fragment);
        beginTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){ //解绑注解
            unbinder.unbind();
            unbinder = null;
        }
    }
}
