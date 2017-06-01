package com.zmq.lottery.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zmq.lottery.R;


public class DialogUtil {

    public static AlertDialog getAlertDialog(Context context, String msg, String btName) {
        final AlertDialog alert = new AlertDialog.Builder(context).create();
        alert.setMessage(msg);
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, btName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.dismiss();
            }
        });
        return alert;
    }

    public static AlertDialog getAlertDialog(Context context, String msg, String btName,
                                             DialogInterface.OnClickListener onclickListener) {
        final AlertDialog alert = new AlertDialog.Builder(context).create();
        alert.setMessage(msg);
        alert.setCancelable(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, btName, onclickListener);
        return alert;
    }

    public static AlertDialog getAlertDialog(Context context, String title, String msg, String btName,
                                             DialogInterface.OnClickListener onclickListener) {
        final AlertDialog alert = new AlertDialog.Builder(context).create();
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setCancelable(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, btName, onclickListener);
        return alert;
    }

    public static AlertDialog getAlertDialog(Context context, String title, String msg, String commitName,
                                             String cancelName, DialogInterface.OnClickListener onClick) {
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(commitName, onClick);
        builder.setNegativeButton(cancelName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert = builder.create();
        return alert;
    }

    public static AlertDialog getAlertDialog(Context context, String title, String msg, String commitName,
                                             String cancelName, DialogInterface.OnClickListener onClick, DialogInterface.OnClickListener oncanClick) {
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(commitName, onClick);
        builder.setNegativeButton(cancelName, oncanClick);
        alert = builder.create();
        return alert;
    }

    public static AlertDialog getAlertDialog(Context context, String msg, String items[],
                                             DialogInterface.OnClickListener onclickListener) {
        final AlertDialog alert = new AlertDialog.Builder(context).setItems(items, onclickListener).create();
        alert.setTitle(msg);
        alert.setCancelable(false);
        return alert;
    }

    public static AlertDialog getAlertDialog(Context context, String title, String items[], int selectItem,
                                             String cancelName, DialogInterface.OnClickListener onClick) {
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setSingleChoiceItems(items, selectItem, onClick);
        builder.setNegativeButton(cancelName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert = builder.create();
        return alert;
    }

    public static Dialog getDialog(Context context, View view) {
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(view);
        return dialog;
    }

    public static Dialog getDialog1(Context context, View view) {
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(false);
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setContentView(view);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (ScreenUtil.getScreenWidthPix(context)*0.95);
        p.height = (int) (ScreenUtil.getScreenHeightPix(context)*0.78);
        dialogWindow.setAttributes(p);
        return dialog;
    }

    public static Dialog getDialog(Context context, View view, boolean cancel) {
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(cancel);
        dialog.show();
        dialog.getWindow().setContentView(view);
        return dialog;
    }

    public static ProgressDialog getProgressDialog(Context context, String msg) {
        return getProgressDialog(context, msg, true);//true表示可以按返回键取消dialog
    }

    public static ProgressDialog getProgressDialog(Context context, String msg, boolean isCancelable) {
        ProgressDialog progressDialog = null;//, R.style.MyProgressDialog
        if (Build.VERSION.SDK_INT > 21) {
            progressDialog = new ProgressDialog(context, R.style.MyProgressDialog_5);
        } else {
            progressDialog = new ProgressDialog(context, R.style.MyProgressDialog);
        }
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setProgressNumberFormat("%1dMB/%2dMB");
        return progressDialog;
    }
}
