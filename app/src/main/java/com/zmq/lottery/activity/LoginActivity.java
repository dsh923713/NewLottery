package com.zmq.lottery.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zmq.lottery.R;
import com.zmq.lottery.base.BaseActivity;
import com.zmq.lottery.base.RequestResult;
import com.zmq.lottery.bean.LoginBean;
import com.zmq.lottery.finals.RequestCode;
import com.zmq.lottery.utils.ExampleUtil;
import com.zmq.lottery.utils.GsonUtil;
import com.zmq.lottery.utils.HttpUtils;
import com.zmq.lottery.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity implements View.OnClickListener, RequestResult {
    private static final String TAG = "DSH -> LoginActivity";
    @BindView(R.id.et_name)
    EditText et_name; //用户名
    @BindView(R.id.et_password)
    EditText et_password; //密码
    @BindView(R.id.tv_login)
    TextView tv_login; //登陆
    @BindView(R.id.tv_register)
    TextView tv_register; //注册
    private HttpUtils httpUtils; //联网请求类
    private Map<String, String> data = new HashMap<>();//参数集合
    private String alias; //用户名（JPush别名）
    private String pwd; //密码

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    protected void initContentView(Bundle bundle) {

    }

    @Override
    protected void initView() {
        setTitle("登陆");
        ButterKnife.bind(this);
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        if (!TextUtils.isEmpty(SPUtil.getString("alias")) && !TextUtils.isEmpty(SPUtil.getString("pwd"))) {
            et_name.setText(SPUtil.getString("alias"));
            et_password.setText(SPUtil.getString("pwd"));
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                setAlias();
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    /**
     * 判断用户名与密码是否正常并将用户名设置为JPush别名
     */
    private void setAlias() {
        //用户名（设置JPush别名）
        alias = et_name.getText().toString().trim();
        //密码
        pwd = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(alias)) {
            showShortToast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showShortToast("密码不能为空");
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            showShortToast("别名不可用");
            return;
        }
        data.clear();
        data.put("m","sys");
        data.put("act", "login");
        data.put("code", alias);
        data.put("password", pwd);
        httpUtils = new HttpUtils(LoginActivity.this, LoginActivity.this, "正在登陆", true);
        httpUtils.async(RequestCode.LOGIN_ACCOUNT, data);
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    SPUtil.saveboolean("isSuccess" + alias, true);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    if (!SPUtil.getBoolean("isSuccess" + alias)) {
                        Log.d(TAG, "Set alias in handler.");
                        JPushInterface.setAliasAndTags(getApplicationContext(),
                                (String) msg.obj, null, mAliasCallback);
                    }

                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    @Override
    public void onSuccess(String result, String requestCode) {
        LoginBean bean = GsonUtil.GsonToBean(result, LoginBean.class);
        if (bean.getCode() == 0) {
            SPUtil.saveString("alias", alias);
            SPUtil.saveString("pwd", pwd);
            startActivityAndFinish(HomeActivity.class);
        }
        showShortToast(bean.getMessage());
    }

    @Override
    public void onFailure(String result, String requestCode) {

    }
}
