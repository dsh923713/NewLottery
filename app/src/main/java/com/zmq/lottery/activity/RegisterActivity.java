package com.zmq.lottery.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zmq.lottery.R;
import com.zmq.lottery.base.BaseActivity;
import com.zmq.lottery.base.RequestResult;
import com.zmq.lottery.bean.RegisterBean;
import com.zmq.lottery.finals.RequestCode;
import com.zmq.lottery.utils.DateUtil;
import com.zmq.lottery.utils.GsonUtil;
import com.zmq.lottery.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RequestResult {
    @BindView(R.id.et_register_phone)
    EditText et_register_phone; //手机号
    @BindView(R.id.et_register_pw)
    EditText et_register_pw; //密码
    @BindView(R.id.et_sure_register_pw)
    EditText et_sure_register_pw; //确认密码
    @BindView(R.id.et_register_captcha_code)
    EditText et_register_captcha_code; //验证码
    @BindView(R.id.et_register_request_code)
    EditText et_register_request_code; //邀请码
    @BindView(R.id.tv_register)
    TextView tv_register; //注册
    @BindView(R.id.tv_get_captcha_code)
    TextView tvGetCaptchaCode; //获取验证码
    private HttpUtils httpUtils; //网络请求类
    private String phoneNum; //手机号
    private String pwd; //密码
    private String pwdSure; //确认密码
    private String captchaCode; //验证码
    private String requestCode; //邀请码
    private Timer timer = null;
    private TimerTask task;//计时器
    private long time = -1; //倒计时
    Map<String, String> data = new HashMap<>();//链接参数集合

    public RegisterActivity() {
        super(R.layout.activity_register);
    }

    @Override
    protected void initContentView(Bundle bundle) {

    }

    @Override
    protected void initView() {
        setLeftIcon(R.mipmap.ic_back, "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvGetCaptchaCode.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    /***
     * 注册时输入的信息
     */
    private void confirmation() {
        phoneNum = et_register_phone.getText().toString().trim();
        pwd = et_register_pw.getText().toString().trim();
        pwdSure = et_sure_register_pw.getText().toString().trim();
        captchaCode = et_register_captcha_code.getText().toString().trim();
        requestCode = et_register_request_code.getText().toString().trim();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void onClick(View v) {

        confirmation();
        switch (v.getId()) {
            case R.id.tv_get_captcha_code: //获取验证码
                data.clear();
                if (TextUtils.isEmpty(phoneNum)) {
                    showShortToast("请输入手机号码");
                    return;
                }
                if (!phoneNum.matches("[1][358]\\d{9}")) {
                    showShortToast("请输入正确的手机格式");
                    return;
                }
                //获取验证码
                httpUtils = new HttpUtils(RegisterActivity.this, RegisterActivity.this, "正在获取...", true);
                data.put("m", "sys");
                data.put("act", "sendvalidcode");
                data.put("tell", phoneNum);
                httpUtils.async(RequestCode.SEND_VALID_CODE, data);
                break;
            case R.id.tv_register: //注册
                data.clear();
                if (TextUtils.isEmpty(phoneNum)) {
                    showShortToast("请输入手机号码");
                    return;
                }
                if (!phoneNum.matches("[1][358]\\d{9}")) {
                    showShortToast("请输入正确的手机格式");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    showShortToast("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(pwdSure)) {
                    showShortToast("请输入确认密码");
                    return;
                }
                if (!pwd.matches("^[a-zA-Z0-9]{6,16}$")) {
                    showShortToast("密码只能以字母、数字组成");
                }
                if (!pwdSure.equals(pwd)) {
                    showShortToast("两次输入的密码不一致");
                    return;
                }
                if (TextUtils.isEmpty(captchaCode)) {
                    showShortToast("请输入验证码");
                    return;
                }
                httpUtils = new HttpUtils(RegisterActivity.this, RegisterActivity.this, "正在注册...", true);
                data.put("m", "sys");
                data.put("act", "validpost");
                data.put("tell", phoneNum);//手机号码
                data.put("validcode", captchaCode); //验证码
                data.put("password", pwd); //密码
                data.put("recommended", requestCode);  //邀请码
                httpUtils.async(RequestCode.REGISTER_ACCOUNT, data);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String result, String requestCode) {
        if (requestCode.equals(RequestCode.SEND_VALID_CODE)) {
            if (result.equals("fail")) {
                showShortToast("手机已经注册过");
                return;
            }
            showShortToast("正在获取...");
            timer = new Timer();
            time = 60;
            tvGetCaptchaCode.setClickable(false);
            timeCountDown();
        } else if (requestCode.equals(RequestCode.REGISTER_ACCOUNT)) {
            RegisterBean resultData = GsonUtil.GsonToBean(result, RegisterBean.class);
            if (resultData.getStatus().equals("success")) {
                finish();
            }
            showShortToast(resultData.getErrormsg());
        }
    }

    @Override
    public void onFailure(String result, String requestCode) {
        showShortToast(result);
    }

    /**
     * 获取验证码倒计时
     */
    private void timeCountDown() {
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        tvGetCaptchaCode.setText(DateUtil.getTimeDown(time) + "s");
                        tvGetCaptchaCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
                        tvGetCaptchaCode.setBackgroundResource(R.drawable.blue_validcode_shape);
                        if (time == 0) {
                            timer.cancel();
                            tvGetCaptchaCode.setText("再次获取");
                            tvGetCaptchaCode.setBackgroundResource(R.drawable.register_code_background);
                            tvGetCaptchaCode.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.blue));
                            tvGetCaptchaCode.setClickable(true);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            task.cancel();
            timer = null;
        }
    }
}
