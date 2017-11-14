package com.youwonn_invest.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.andview.refreshview.XRefreshView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutSlideTransformer;
import com.youwonn_invest.R;
import com.youwonn_invest.adapter.recycler.RecyclerAdapter;
import com.youwonn_invest.adapter.recycler.RecyclerViewHolder;
import com.youwonn_invest.base.BaseApplication;
import com.youwonn_invest.constant.URLS;
import com.youwonn_invest.model.LiveRoomListModel;
import com.youwonn_invest.model.MessageResult;
import com.youwonn_invest.ui.activity.LiveRoomActivity;
import com.youwonn_invest.utils.NetUtil;
import com.youwonn_invest.utils.UIHelper;
import com.youwonn_invest.widget.extent.GlideImageLoader;
import com.youwonn_invest.widget.loding.LodingDialog;
import com.youwonn_invest.widget.refreshview.QuanTumRefreshHeader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class LiveFragment extends Fragment implements OnBannerListener {
    private View rootView;
    private RecyclerView recyclerView;
    private List<LiveRoomListModel> listRoomDatas = new ArrayList<>();
    private RecyclerAdapter<LiveRoomListModel> listRoomAdapter;
    private RecyclerView recViewChannel;
    private RecyclerAdapter channelReycAdapter;
    private List<String> channelReycList;

    private String token;
    private TextView live_state;
    private RelativeLayout rl_empty;
    private LodingDialog lodingDialog;
    private XRefreshView xrefreshview;
    private LinearLayout view_main;
    private Banner banner;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_live, container, false);
        this.recViewChannel = (RecyclerView) rootView.findViewById(R.id.recViewChannel);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        this.rl_empty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        this.xrefreshview = (XRefreshView) rootView.findViewById(R.id.xrefreshview);
        this.view_main = (LinearLayout) rootView.findViewById(R.id.view_main);
        this.banner = (Banner) rootView.findViewById(R.id.banner);
        this.judge_network();
        this.xRefreshView();
        this.bannerView();
        return rootView;
    }

    private void bannerView() {
        banner.setImages(BaseApplication.images)
                .setImageLoader(new GlideImageLoader())
                .setBannerTitles(BaseApplication.titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setOnBannerListener(this)
                .setBannerAnimation(ZoomOutSlideTransformer.class)
                .start();
    }

    @Override
    public void OnBannerClick(int position) {
        if (position == 0) {

        } else if (position == 1) {

        } else if (position == 2) {

        } else if (position == 3) {

        } else if (position == 4) {

        } else if (position == 5) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.bindRecycleView();
    }

    private void judge_network() {
        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            xrefreshview.setVisibility(View.GONE);
            rl_empty.setOnClickListener(view -> {
                getData();
                lodingDialog = new LodingDialog(getActivity(), "加载中...");
                lodingDialog.show();
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
            });
        }
    }

    private void xRefreshView() {
        this.xrefreshview.setPullLoadEnable(false);
        this.xrefreshview.setMoveForHorizontal(true);
        QuanTumRefreshHeader header = new QuanTumRefreshHeader(getActivity());
        this.xrefreshview.setCustomHeaderView(header);
        this.xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {

                if (!NetUtil.isNetworkConnected(getActivity())) {
                    rl_empty.setVisibility(View.VISIBLE);
                    view_main.setVisibility(View.GONE);
                    rl_empty.setOnClickListener(v -> {
                        lodingDialog = new LodingDialog(getActivity(), "加载中...");
                        lodingDialog.show();
                        listRoomDatas.clear();
                        getData();
                        new Handler().postDelayed(() -> lodingDialog.dismiss(), 1000);
                        new Handler().postDelayed(() -> xrefreshview.stopRefresh(), 1000);
                    });
                } else {
                    listRoomDatas.clear();
                    getData();
                    new Handler().postDelayed(() -> xrefreshview.stopRefresh(), 1000);
                }
            }
        });
    }


    private void bindRecycleView() {
        this.getData();
        this.listRoomAdapter = new RecyclerAdapter<LiveRoomListModel>(getActivity(), listRoomDatas,
                R.layout.item_live_room) {
            @Override
            public void convert(RecyclerViewHolder helper, LiveRoomListModel item, int position) {
                TextView tv_live_state = helper.getView(R.id.tv_live_state);
                live_state = helper.getView(R.id.tv_live_state);
                ImageView iv_bg = helper.getView(R.id.iv_bg);
                helper.setText(R.id.tv_speaker_title, item.getSpeaker_title());
                helper.setText(R.id.tv_speaker_name, item.getSpeaker_name());
                helper.setText(R.id.tv_speaker_description, item.getSpeaker_description());
                helper.setText(R.id.tv_speaker_description1, item.getSpeaker_description1());
                helper.setImageUrl(R.id.im_speaker_img, item.getSpeaker_img());
                helper.setText(R.id.tv_speaker_popularity, item.getSpeaker_popularity());
                if (position == 0) {
                    iv_bg.setImageResource(R.mipmap.icon_live_bg01);
                } else if (position == 1) {
                    iv_bg.setImageResource(R.mipmap.icon_live_bg02);
                } else if (position == 2) {
                    iv_bg.setImageResource(R.mipmap.icon_live_bg03);
                } else if (position == 3) {
                    iv_bg.setImageResource(R.mipmap.icon_live_bg04);
                } else {
                    iv_bg.setImageResource(R.mipmap.icon_live_bg05);
                }
                if (item.getSpeaker_state().equals("0")) {
                    live_state.setText("休息中");
                    tv_live_state.setTextColor(ContextCompat.getColor(context, R.color.color_tab_text));
                } else if (item.getSpeaker_state().equals("1")) {
                    live_state.setText("直播中");
                    tv_live_state.setTextColor(ContextCompat.getColor(context, R.color.main_txt_nor));
                }
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1,
                LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.listRoomAdapter);
        this.listRoomAdapter.setOnItemClickListener((parent, position) -> {
            if (listRoomDatas.get(position).getSpeaker_state().equals("0")) {
                UIHelper.toastMessage(getActivity(), getResources().getString(R.string.tv_not_broadcast));
            } else if (listRoomDatas.get(position).getSpeaker_state().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putInt(LiveRoomActivity.ROOM_ID, listRoomDatas.get(position).getRoom_id());
                UIHelper.startActivity(getActivity(), LiveRoomActivity.class, bundle);
            }


        });

        this.getChannelData();
        this.channelReycAdapter = new RecyclerAdapter<String>(getActivity(), channelReycList,
                R.layout.item_live_channel) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView tvState = helper.getView(R.id.tv_head_title);
                ImageView ivHead = helper.getView(R.id.iv_head);
                if (position == 0) {
                    tvState.setText("全部频道");
                    ivHead.setImageResource(R.mipmap.icon_all);
                } else if (position == 1) {
                    tvState.setText("基金");
                    ivHead.setImageResource(R.mipmap.icon_jijin);
                } else if (position == 2) {
                    tvState.setText("黄金频道");
                    ivHead.setImageResource(R.mipmap.icon_hj);
                } else if (position == 3) {
                    tvState.setText("原油");
                    ivHead.setImageResource(R.mipmap.icon_yy);
                } else if (position == 4) {
                    tvState.setText("美股");
                    ivHead.setImageResource(R.mipmap.icon_mg);
                }

            }

        };

        this.recViewChannel.setHasFixedSize(true);
        this.recViewChannel.setLayoutManager(new GridLayoutManager(getActivity(), 1,
                LinearLayoutManager.HORIZONTAL, false));
        this.recViewChannel.setAdapter(this.channelReycAdapter);
    }

    private void getChannelData() {
        channelReycList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            channelReycList.add("" + i);
        }

    }

    /**
     * 获取直播房间列表
     */
    private void getData() {
        SharedPreferences sp = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        token = sp.getString("USER_TOKEN", "");
        RequestParams params = new RequestParams(URLS.LIVE_GETSPEAKERLIST);
        params.addBodyParameter("token", token);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                rl_empty.setVisibility(View.GONE);
                MessageResult message = MessageResult.parse(result);
                List<LiveRoomListModel> roomList = JSON.parseArray(message.getData(),
                        LiveRoomListModel.class);
                listRoomDatas.clear();
                listRoomDatas.addAll(roomList);
                listRoomAdapter.notifyDataSetChanged();
                rl_empty.setVisibility(View.GONE);
                xrefreshview.setVisibility(View.VISIBLE);
                view_main.setVisibility(View.VISIBLE);
                lodingDialog.dismiss();
                xrefreshview.stopRefresh();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });

    }
}
