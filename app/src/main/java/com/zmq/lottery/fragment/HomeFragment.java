package com.zmq.lottery.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmq.lottery.R;
import com.zmq.lottery.activity.PlayRoomActivity;
import com.zmq.lottery.adapter.HomeAdapter;
import com.zmq.lottery.base.BaseFragment;
import com.zmq.lottery.base.RequestResult;
import com.zmq.lottery.bean.HomeBean;
import com.zmq.lottery.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class HomeFragment extends BaseFragment implements RequestResult {
    private static final String TAG = "DSH -> HomeFragment";
    @BindView(R.id.rlv_home)
    RecyclerView rlv_home; //加载首页数据--红包牛牛等
    @BindView(R.id.cb_home_top)
    ConvenientBanner cb_home_top;//头部banner

    private List<Integer> imageList;//图标集合
    private List<HomeBean> homeModels = new ArrayList<>(); //集合数据
    private Bundle bundle;//传递参数
    private HttpUtils httpUtils;
    private String url;

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);//绑定注解
        return view;
    }

    @Override
    protected void initView(View view) {
        bundle = new Bundle();
        getImageList();
        //设置头部轮播数据
        cb_home_top.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                                 @Override
                                 public LocalImageHolderView createHolder() {
                                     return new LocalImageHolderView();
                                 }
                             },//设置两个点图片作为翻页指示器，
                imageList).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        initHomeModel();
        HomeAdapter adapter = new HomeAdapter(homeModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);//设置网格样式及列数
        rlv_home.setLayoutManager(gridLayoutManager);
        rlv_home.addItemDecoration(new SpaceItemDecoration(2));
        rlv_home.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bundle.clear();
                switch (position) {
                    case 0: //北京28
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 1://加拿大28
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 2: // 红包牛牛
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 3: //重庆时时彩
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 4: //PK拾
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 5: //双色球
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 6: //超级大乐透
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 7: //11选5
                        bundle.putString("id_name", homeModels.get(position).getTitle());
                        startActivity(PlayRoomActivity.class, bundle);
                        break;
                    case 8: //更多玩法
                        break;

                }
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    /**
     * 添加网格数据
     */
    private void initHomeModel() {
        HomeBean beijing = new HomeBean(R.mipmap.ic_launcher, "北京28");
        homeModels.add(beijing);
        HomeBean canada = new HomeBean(R.mipmap.ic_launcher, "加拿大28");
        homeModels.add(canada);
        HomeBean taurus = new HomeBean(R.mipmap.ic_launcher, "红包牛牛");
        homeModels.add(taurus);
        HomeBean frequent_colors = new HomeBean(R.mipmap.ic_launcher, "重庆时时彩");
        homeModels.add(frequent_colors);
        HomeBean playkilling = new HomeBean(R.mipmap.ic_launcher, "PK拾");
        homeModels.add(playkilling);
        HomeBean chromosphere = new HomeBean(R.mipmap.ic_launcher, "双色球");
        homeModels.add(chromosphere);
        HomeBean lottery_ticket = new HomeBean(R.mipmap.ic_launcher, "超级大乐透");
        homeModels.add(lottery_ticket);
        HomeBean elevenAndfive = new HomeBean(R.mipmap.ic_launcher, "11选5");
        homeModels.add(elevenAndfive);
        HomeBean morePlay = new HomeBean(R.mipmap.ic_launcher, "更多玩法");
        homeModels.add(morePlay);
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

    @Override
    public void onSuccess(String result, String requestCode) {

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

    /**
     * 设置网格子项间隔
     */
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 3 == 0) {
                outRect.left = 0;
            }
        }

    }
}
