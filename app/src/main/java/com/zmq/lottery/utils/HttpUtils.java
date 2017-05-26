package com.zmq.lottery.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.zmq.lottery.base.RequestResult;

import java.io.IOException;
import java.util.Map;


/**
 * Created by Administrator on 2017/5/24.
 */

public class HttpUtils {
    private Context context;
    private RequestResult requestResult;
    private static String mRequestCode;
    private ProgressDialog dialog;
    private static final String url = "http://lottery.blmshop.com";

    public HttpUtils(Context context, RequestResult requestResult, String msg, boolean isShow) {
        this.dialog = DialogUtil.getProgressDialog(context, msg);
        if (isShow){
            if (dialog != null){
                dialog.show();
            }
        }
        this.context = context;
        this.requestResult = requestResult;
    }

    /**
     * 异步请求：回调方法可以直接操作UI
     */
    public void async(String requestCode, Map<String,String> data) {
        this.mRequestCode = requestCode;
        LogUtils.d(""+mRequestCode);
        OkHttpUtil.getDefault(context).doGetAsync(
                HttpInfo.Builder().setUrl(url).addParams(data).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (dialog != null){
                            dialog.dismiss();
                        }
                        if (requestResult != null) {
                            requestResult.onFailure(result, mRequestCode);
                        }
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (dialog != null){
                            dialog.dismiss();
                        }
                        if (requestResult != null) {
                            requestResult.onSuccess(result, mRequestCode);
                        }
                    }
                });
    }

//    /**
//     * 异步上传图片：显示上传进度
//     */
//    public void doUploadImg() {
//        HttpInfo info = HttpInfo.Builder()
//                .setUrl("")
//                .addUploadFile("file", "", new ProgressCallback() {
//                    //onProgressMain为UI线程回调，可以直接操作UI
//                    @Override
//                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
//                    }
//                })
//                .build();
//        OkHttpUtil.getDefault(this).doUploadFileAsync(info);
//    }
//
//    /**
//     * 单次批量上传：一次请求上传多个文件
//     */
//    public void doUploadBatch(List<String> imgList) {
//        imgList.clear();
//        imgList.add("/storage/emulated/0/okHttp_download/test.apk");
//        imgList.add("/storage/emulated/0/okHttp_download/test.rar");
//        HttpInfo.Builder builder = HttpInfo.Builder()
//                .setUrl("");
//        //循环添加上传文件
//        for (String path : imgList) {
//            //若服务器为php，接口文件参数名称后面追加"[]"表示数组，示例：builder.addUploadFile("uploadFile[]",path);
//            builder.addUploadFile("uploadFile", path);
//        }
//        HttpInfo info = builder.build();
//        OkHttpUtil.getDefault(context).doUploadFileAsync(info, new ProgressCallback() {
//            @Override
//            public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
//            }
//
//            @Override
//            public void onResponseMain(String filePath, HttpInfo info) {
//                String result = info.getRetDetail();
//                if (requestResult != null) {
//                    requestResult.onSuccess(result);
//                }
//            }
//        });
//    }


}
