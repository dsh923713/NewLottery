package com.zmq.lottery.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zmq.lottery.R;
import com.zmq.lottery.activity.BusinessRecordActivity;
import com.zmq.lottery.activity.LoginActivity;
import com.zmq.lottery.base.BaseFragment;
import com.zmq.lottery.utils.GifSizeFilter;
import com.zmq.lottery.utils.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/5/15.
 */

public class PersonFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_CHOOSE = 1001;
    private static final int WRITE_EXTERNAL_STORAGE = 1002;
    @BindView(R.id.civ_head)
    ImageView civ_head; //头像
    @BindView(R.id.tv_name)
    TextView tv_name;  //昵称
    @BindView(R.id.tv_login_style)
    TextView tv_login_style;  //登陆方式
    @BindView(R.id.tv_business_record)
    TextView tv_business_record; //交易记录
    @BindView(R.id.tv_my_news)
    TextView tv_my_news; //我的消息
    @BindView(R.id.tv_contact_custom_service)
    TextView tv_contact_custom_service; //联系客服
    @BindView(R.id.tv_my_extend)
    TextView tv_my_extend;  //我的推广
    @BindView(R.id.tv_back_login)
    TextView tv_back_login; //退出登陆

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initView(View view) {
        if (!TextUtils.isEmpty(SPUtil.getString("image" + SPUtil.getString("alias")))) {
            Glide.with(this).load(SPUtil.getString("image" + SPUtil.getString("alias"))).into(civ_head);
        }
        setStatusBar(ContextCompat.getColor(activity, R.color.colorAccent));
        //注册点击事件
        tv_business_record.setOnClickListener(this);
        tv_my_news.setOnClickListener(this);
        tv_contact_custom_service.setOnClickListener(this);
        tv_my_extend.setOnClickListener(this);
        tv_back_login.setOnClickListener(this);
        civ_head.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_business_record: //交易记录
                startActivity(BusinessRecordActivity.class);
                break;
            case R.id.tv_my_news:  //我的消息
                break;
            case R.id.tv_contact_custom_service:  //联系客服
                break;
            case R.id.tv_my_extend: //我的推广
                break;
            case R.id.tv_back_login:  //退出登陆
                startActivityAndFinish(LoginActivity.class);
                break;
            case R.id.civ_head:  //头像
                AndPermission.with(this).requestCode(WRITE_EXTERNAL_STORAGE).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(this).start();
                break;
        }
    }

    // 成功回调的方法，用注解即可，这里的300就是请求时的requestCode。
    @PermissionYes(WRITE_EXTERNAL_STORAGE)
    private void getPermissionYes(List<String> grantedPermissions) {
        // TODO 申请权限成功。
        Matisse.from(PersonFragment.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @PermissionNo(WRITE_EXTERNAL_STORAGE)
    private void getPermissionNo(List<String> deniedPermissions) {
        // TODO 申请权限失败。
        //用AndPermission默认的提示语。
        AndPermission.defaultSettingDialog(this, WRITE_EXTERNAL_STORAGE).show();
    }

    List<Uri> mSelected;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            SPUtil.saveString("image" + SPUtil.getString("alias"), mSelected.get(0).toString());
            Glide.with(this).load(mSelected.get(0)).into(civ_head);
        }
    }
}
