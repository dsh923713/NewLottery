package com.zmq.lottery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmq.lottery.R;
import com.zmq.lottery.adapter.PlayRoomAdapter;
import com.zmq.lottery.base.BaseActivity;
import com.zmq.lottery.base.RequestResult;
import com.zmq.lottery.bean.RoomBean;
import com.zmq.lottery.finals.RequestCode;
import com.zmq.lottery.utils.GsonUtil;
import com.zmq.lottery.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/16.
 */

public class PlayRoomActivity extends BaseActivity implements RequestResult {
    private static final String TAG = "DSH -> PlayRoomActivity";
    @BindView(R.id.cb_redpacket_top)
    ConvenientBanner cb_redpacket_top; //头部轮播图
    @BindView(R.id.rlv_redpacket)
    RecyclerView rlv_redpacket; //房间数量的动态加载recyclerview

    private List<Integer> imageList;//头部轮播图片集合
    private List<String> data = new ArrayList<>(); //数据集合
    private String name; //标题名称
    private List<RoomBean> roomBeanList = new ArrayList<>();//房间数据
    private Bundle bundle;
    private HttpUtils httpUtils; //网络请求类
    private PlayRoomAdapter adapter; //适配器

    public PlayRoomActivity() {
        super(R.layout.activity_redpacketox);
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            name = extras.getString("id_name");
        }
    }

    @Override
    protected void initContentView(Bundle bundle) {

    }

    @Override
    protected void initView() {
        bundle = new Bundle();
        setTitle(name);//设置标题
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));//改变toolbar颜色
        setStatusBar(ContextCompat.getColor(this, R.color.colorAccent));//改变状态栏颜色
        setLeftIcon(R.mipmap.ic_back, "", new View.OnClickListener() { //添加返回按钮
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getImageList();
        cb_redpacket_top.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                                      @Override
                                      public LocalImageHolderView createHolder() {
                                          return new LocalImageHolderView();
                                      }
                                  },//设置两个点图片作为翻页指示器，
                imageList).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器显示的位置区域--居中
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        showRoom();
//        addData();
        adapter = new PlayRoomAdapter(roomBeanList);
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        rlv_redpacket.setLayoutManager(layout);
        rlv_redpacket.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bundle.clear();
                switch (position) {
                    case 0:
                        bundle.putInt("id", roomBeanList.get(position).getId());
                        bundle.putString("cname", roomBeanList.get(position).getCname());
                        break;
                }
                startActivity(MsgChatActivity.class, bundle);
            }
        });
    }

    /**
     * 联网获取房间列表数据
     */
    private void showRoom() {
        Map<String, String> data = new HashMap<>();
        switch (name) {
            case "北京28"://模拟
                httpUtils = new HttpUtils(PlayRoomActivity.this, PlayRoomActivity.this, "正在加载...", true);
                data.clear();
                data.put("m","sys");
                data.put("act", "getroom");
                data.put("id_kind", "1");
                httpUtils.async(RequestCode.GET_BEIJING_ROOM, data);
                break;
            case "红包牛牛":
                httpUtils = new HttpUtils(PlayRoomActivity.this, PlayRoomActivity.this, "正在加载...", true);
                data.clear();
                data.put("m","sys");
                data.put("act", "getroom");
                data.put("id_kind", "1");
                httpUtils.async(RequestCode.GET_RED_ROOM, data);
                break;
        }
    }

    /**
     * 往集合添加数据，用于recyclerview
     */
    private void addData() {
        data.add("普通房");
        data.add("高级房");
        data.add("豪华房");
        data.add("总统房");
    }

    /**
     * 添加头部轮播图
     */
    private void getImageList() {
        imageList = new ArrayList<>();
        imageList.add(R.mipmap.im1);
        imageList.add(R.mipmap.im2);
        imageList.add(R.mipmap.im3);
        imageList.add(R.mipmap.im4);
    }

    /**
     * 联网结果
     *
     * @param result
     * @param requestCode
     */
    @Override
    public void onSuccess(String result, String requestCode) {
        roomBeanList.clear();
        if (requestCode.equals(RequestCode.GET_RED_ROOM)) {
            roomBeanList.addAll(GsonUtil.jsonToList(result, RoomBean.class));
            adapter.notifyDataSetChanged();
        } else if (requestCode.equals(RequestCode.GET_BEIJING_ROOM)) {
            for (int i = 0; i < 3; i++)
                roomBeanList.addAll(GsonUtil.jsonToList(result, RoomBean.class));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(String result, String requestCode) {
        showShortToast(result);
    }

    /**
     * 加载轮播图片
     */
    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (roomBeanList != null) {
            roomBeanList.clear();
            roomBeanList = null;
        }
    }
}
