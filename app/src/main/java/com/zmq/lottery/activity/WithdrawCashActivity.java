package com.zmq.lottery.activity;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmq.lottery.R;
import com.zmq.lottery.base.BaseActivity;
import com.zmq.lottery.utils.AssetsBankInfo;
import com.zmq.lottery.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawCashActivity extends BaseActivity {

    @BindView(R.id.tv_withdraw_cash_bank)
    TextView tvWithdrawCashBank;//银行名称
    @BindView(R.id.et_bank_account)
    EditText etBankAccount; //提现银行卡号
    @BindView(R.id.et_bank_binding_person)
    EditText etBankBindingPerson; //银行开户行姓名
    @BindView(R.id.et_withdraw_cash_num)
    EditText etWithdrawCashNum;//提现金额
    @BindView(R.id.iv_clear)
    ImageView ivClear; //取消提现金额
    @BindView(R.id.tv_usable_cash)
    TextView tvUsableCash;//可用余额
    @BindView(R.id.tv_all_withdraw_cash)
    TextView tvAllWithdrawCash; //全部提现
    @BindView(R.id.tv_sure_withdraw_cash)
    TextView tvSureWithdrawCash; //确认提现

    private String bankBin; //银行BIN号
    private String allBankName;//银行名称（含类型）
    private String[] bankName;//银行名称

    public WithdrawCashActivity() {
        super(R.layout.activity_withdraw_cash);
    }

    @Override
    protected void initView() {
        setTitle("提现");
        setLeftIcon(R.mipmap.ic_back, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSureWithdrawCash.setClickable(false);//先禁用点击事件
        setBankName();
        setWithdrawCashNum();
    }

    /**
     * 根据输入的卡号来显示银行名称
     */
    private void setBankName() {
        etBankAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5) {
                    bankBin = etBankAccount.getText().toString().substring(0, 6);
                    allBankName = AssetsBankInfo.getNameOfBank(WithdrawCashActivity.this, Long.parseLong(bankBin));
                    if (!allBankName.contains("-")) {
                        tvWithdrawCashBank.setText(allBankName);
                        return;
                    }
                    bankName = allBankName.split("-");
                    tvWithdrawCashBank.setText(bankName[0] + "\n" + bankName[1] + "-" + bankName[2]);
                } else {
                    tvWithdrawCashBank.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 根据是否输入提现金额来修改控件状态
     */
    private void setWithdrawCashNum() {
        etWithdrawCashNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.d(s + "--" + start + "--" + before + "--" + count);
                if (s.length() > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                    tvSureWithdrawCash.setBackgroundResource(R.drawable.blue_validcode_shape);
                    tvSureWithdrawCash.setTextColor(ContextCompat.getColor(WithdrawCashActivity.this, R.color.white));
                    tvSureWithdrawCash.setClickable(true);
                } else {
                    ivClear.setVisibility(View.GONE);
                    tvSureWithdrawCash.setBackgroundResource(R.drawable.gray_light_shape);
                    tvSureWithdrawCash.setTextColor(ContextCompat.getColor(WithdrawCashActivity.this, R.color.gary));
                    tvSureWithdrawCash.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_clear, R.id.tv_all_withdraw_cash, R.id.tv_sure_withdraw_cash})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                etWithdrawCashNum.setText("");
                break;
            case R.id.tv_all_withdraw_cash:
                showShortToast("全部提现");
                break;
            case R.id.tv_sure_withdraw_cash:
                showShortToast("确认提现");
                break;
            default:
                break;
        }
    }
}
