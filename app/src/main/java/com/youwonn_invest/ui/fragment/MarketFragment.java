package com.youwonn_invest.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xiaosu.DataSetAdapter;
import com.xiaosu.VerticalRollingTextView;
import com.youwonn_invest.R;
import com.youwonn_invest.adapter.recycler.RecyclerAdapter;
import com.youwonn_invest.adapter.recycler.RecyclerViewHolder;
import com.youwonn_invest.constant.URLS;
import com.youwonn_invest.model.FundMessagResult;
import com.youwonn_invest.model.FundModel;
import com.youwonn_invest.model.MarketViewRollModel;
import com.youwonn_invest.model.MessageResult;
import com.youwonn_invest.model.RelevantNewsModel;
import com.youwonn_invest.ui.activity.DetailsActivity;
import com.youwonn_invest.ui.activity.MainActivity;
import com.youwonn_invest.ui.activity.MinuteChartActivity;
import com.youwonn_invest.ui.activity.NewsDetailsActivity;
import com.youwonn_invest.ui.activity.RadixKLine;
import com.youwonn_invest.ui.activity.TitleActivity;
import com.youwonn_invest.utils.UIHelper;
import com.youwonn_invest.widget.extent.CustomLinearLayoutManager;
import com.youwonn_invest.widget.extent.ScrollGridLayoutManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MarketFragment extends Fragment implements MainActivity.FragmentBackHandler, View.OnClickListener, VerticalRollingTextView.OnItemClickListener {
    private View rootView;
    private RecyclerView marketRecy;
    private List<MarketViewRollModel> listmMrket = new ArrayList<>();
    private RecyclerAdapter<MarketViewRollModel> marketAdapter;

    private RecyclerView recyViewFund;
    private RecyclerAdapter<FundModel> fundAdapter;
    private List<FundModel> fundModelList = new ArrayList<>();

    private RecyclerView recyChannel;
    private RecyclerAdapter<String> channelAdapter;
    private List<String> channelModelList = new ArrayList<>();


    private RecyclerView recyRelevant;
    private RecyclerAdapter<RelevantNewsModel.DataBean> rAdapter;
    private List<RelevantNewsModel.DataBean> rModelList = new ArrayList<>();

    private TextView tv_01, tv_02, tv_03, tv_04, tv_05;
    private Bundle bundle;
    private Intent intent = new Intent();
    private VerticalRollingTextView mVerticalRollingView;
    String[] mStrs = {
            "外资券商保险持股放宽至51%意味着什么",
            "【焦点】天猫精灵引爆国内智能音箱行业",
            "【机构】茅台新高 机构却大举减持白酒股",
            "【新股】证监会核发7家IPO 募资不超32亿",
            "【聚集】地方金融任务重压力大要抓住机遇补短板",
            "乐视网IPO工作底稿曾被查阅 当年保代仍在正常工"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_market, container, false);
        this.marketRecy = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        this.recyViewFund = (RecyclerView) rootView.findViewById(R.id.recyViewFund);
        this.recyChannel = (RecyclerView) rootView.findViewById(R.id.recyChannel);
        this.recyRelevant = (RecyclerView) rootView.findViewById(R.id.recyRelevant);
        this.mVerticalRollingView = (VerticalRollingTextView) rootView.findViewById(R.id.verticalRollingView);
        this.tv_01 = (TextView) rootView.findViewById(R.id.tv_01);
        this.tv_02 = (TextView) rootView.findViewById(R.id.tv_02);
        this.tv_03 = (TextView) rootView.findViewById(R.id.tv_03);
        this.tv_04 = (TextView) rootView.findViewById(R.id.tv_04);
        this.tv_05 = (TextView) rootView.findViewById(R.id.tv_05);
        this.tv_01.setOnClickListener(this);
        this.tv_02.setOnClickListener(this);
        this.tv_03.setOnClickListener(this);
        this.tv_04.setOnClickListener(this);
        this.tv_05.setOnClickListener(this);
        this.bindRecycleView();


        mVerticalRollingView.setDataSetAdapter(new DataSetAdapter<String>(Arrays.asList(mStrs)) {

            @Override
            protected String text(String s) {
                return s;
            }
        });
        mVerticalRollingView.setOnItemClickListener(this);
        mVerticalRollingView.run();
        return rootView;
    }

    private void bindRecycleView() {
        this.getChannelData();
        this.channelAdapter = new RecyclerAdapter<String>(getActivity(), channelModelList,
                R.layout.item_channel) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView tvState = helper.getView(R.id.tv_head_title);
                ImageView ivHead = helper.getView(R.id.iv_head);
                if (position == 0) {
                    tvState.setText("全部频道");
                    ivHead.setImageResource(R.mipmap.icon_can01);
                } else if (position == 1) {
                    tvState.setText("基金");
                    ivHead.setImageResource(R.mipmap.icon_can02);
                } else if (position == 2) {
                    tvState.setText("黄金频道");
                    ivHead.setImageResource(R.mipmap.icon_can03);
                } else if (position == 3) {
                    tvState.setText("原油");
                    ivHead.setImageResource(R.mipmap.icon_can05);
                } else if (position == 4) {
                    tvState.setText("美股");
                    ivHead.setImageResource(R.mipmap.icon_can06);
                } else if (position == 5) {
                    tvState.setText("私募直播");
                    ivHead.setImageResource(R.mipmap.icon_can07);
                } else if (position == 6) {
                    tvState.setText("基金现场");
                    ivHead.setImageResource(R.mipmap.icon_can08);
                } else if (position == 7) {
                    tvState.setText("外汇");
                    ivHead.setImageResource(R.mipmap.icon_can09);
                }

            }

        };

        this.recyChannel.setHasFixedSize(true);
        this.recyChannel.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        this.recyChannel.setAdapter(this.channelAdapter);
        this.channelAdapter.setOnItemClickListener((parent, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(RadixKLine.MARKETNAME, listmMrket.get(position).getMarket_name());
            bundle.putString(RadixKLine.MARKETPRICE, listmMrket.get(position).getMarket_price());
            bundle.putString(RadixKLine.MARKETFLOAT, listmMrket.get(position).getMarket_float());
            UIHelper.startActivity(getActivity(), MinuteChartActivity.class, bundle);
        });

        marketData();
        this.marketAdapter = new RecyclerAdapter<MarketViewRollModel>(getActivity(), listmMrket,
                R.layout.itme_bargain) {
            @Override
            public void convert(RecyclerViewHolder helper, MarketViewRollModel item, int position) {
                TextView tvTotalcap = helper.getView(R.id.tv_totalcap);
                TextView tvChangesta = helper.getView(R.id.tv_changesta);
                helper.setText(R.id.tv_name, item.getMarket_name());
                helper.setText(R.id.tv_totalcap, item.getMarket_price());
                helper.setText(R.id.tv_changesta, item.getMarket_float());

                if (String.valueOf(item.getMarket_float().charAt(0)).equals("-")) {
                    tvTotalcap.setTextColor(Color.parseColor("#019f53"));
                    tvChangesta.setTextColor(Color.parseColor("#019f53"));
                } else {
                    tvTotalcap.setTextColor(Color.parseColor("#fd040a"));
                    tvChangesta.setTextColor(Color.parseColor("#fd040a"));
                }

            }
        };

        this.marketRecy.setHasFixedSize(true);
        this.marketRecy.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        this.marketRecy.setAdapter(this.marketAdapter);
        this.marketAdapter.setOnItemClickListener((parent, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(RadixKLine.MARKETNAME, listmMrket.get(position).getMarket_name());
            bundle.putString(RadixKLine.MARKETPRICE, listmMrket.get(position).getMarket_price());
            bundle.putString(RadixKLine.MARKETFLOAT, listmMrket.get(position).getMarket_float());
            UIHelper.startActivity(getActivity(), RadixKLine.class, bundle);
        });


        this.fundData();
        this.fundAdapter = new RecyclerAdapter<FundModel>(getActivity(), fundModelList,
                R.layout.item_fund) {
            @Override
            public void convert(RecyclerViewHolder helper, FundModel item, int position) {
                TextView tvSym = helper.getView(R.id.tv_symbol);
                TextView tvTotal = helper.getView(R.id.tv_total_nav);
                TextView tvYes = helper.getView(R.id.tv_yesterday_nav);
                helper.setText(R.id.tv_jjlx, item.getJjlx());
                helper.setText(R.id.tv_symbol, item.getSymbol());
                helper.setText(R.id.tv_total_nav, item.getTotal_nav());
                helper.setText(R.id.tv_yesterday_nav, "+" + item.getYesterday_nav() + "%");

                int symInt = Integer.parseInt(item.getSymbol());
                if (symInt % 2 == 0) {
                    tvSym.setTextColor(Color.parseColor("#019f53"));
                    tvTotal.setTextColor(Color.parseColor("#019f53"));
                    tvYes.setTextColor(Color.parseColor("#019f53"));
                } else {
                    tvSym.setTextColor(Color.parseColor("#fd040a"));
                    tvTotal.setTextColor(Color.parseColor("#fd040a"));
                    tvYes.setTextColor(Color.parseColor("#fd040a"));
                }


            }
        };

        this.recyViewFund.setHasFixedSize(true);
        this.recyViewFund.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        this.recyViewFund.setAdapter(fundAdapter);


        this.fundAdapter.setOnItemClickListener((parent, position) -> {
            UIHelper.startActivity(getActivity(), DetailsActivity.class);
        });


        this.getRData();
        this.rAdapter = new RecyclerAdapter<RelevantNewsModel.DataBean>(getActivity(), rModelList,
                R.layout.item_rnews) {
            @Override
            public void convert(RecyclerViewHolder helper, RelevantNewsModel.DataBean item, int position) {
                helper.setImageUrl(R.id.thumbnail_pic_s, item.getThumbnail_pic_s());
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.date, item.getDate());
                helper.setText(R.id.category, item.getCategory());
                helper.setText(R.id.author_name, item.getAuthor_name());

            }
        };

        this.recyRelevant.setHasFixedSize(true);
        this.recyRelevant.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        this.recyRelevant.setAdapter(this.rAdapter);
        this.recyRelevant.setNestedScrollingEnabled(false);
        this.rAdapter.setOnItemClickListener((parent, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(NewsDetailsActivity.NEWSURL, rModelList.get(position).getUrl());
            bundle.putString(NewsDetailsActivity.NEWTITLE, rModelList.get(position).getTitle());
            UIHelper.startActivity(getActivity(), NewsDetailsActivity.class, bundle);
        });
    }

    private void getChannelData() {
        channelModelList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            channelModelList.add("" + i);
        }

    }


    private void fundData() {
        RequestParams params = new RequestParams(URLS.GET_FUND);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    FundMessagResult message = FundMessagResult.parse(result);
                    List<FundModel> fundMode = JSON.parseArray(message.getResult(),
                            FundModel.class);
                    fundModelList.addAll(fundMode);
                    fundAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
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

    /**
     * 获取列表数据    05ad7acd5f743c8a563342df32c2549b
     */

    private void marketData() {
        RequestParams params = new RequestParams(URLS.MARKET_NOWMARKET);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listmMrket.clear();
                try {
                    MessageResult message = MessageResult.parse(result);
                    List<MarketViewRollModel> marketList = JSON.parseArray(message.getData(),
                            MarketViewRollModel.class);
                    listmMrket.addAll(marketList);
                    marketRecy.setAdapter(marketAdapter);
                } catch (Exception e) {

                }
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

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_01:
                bundle = new Bundle();
                bundle.putString(TitleActivity.NEWSURL, "http://news.10jqka.com.cn/m601522986/");
                UIHelper.startActivity(getActivity(), TitleActivity.class, bundle);
                break;
            case R.id.tv_02:
                bundle = new Bundle();
                bundle.putString(TitleActivity.NEWSURL, "http://news.10jqka.com.cn/m590986037/");
                UIHelper.startActivity(getActivity(), TitleActivity.class, bundle);
                break;
            case R.id.tv_03:
                bundle = new Bundle();
                bundle.putString(TitleActivity.NEWSURL, "http://news.10jqka.com.cn/m590987045/");
                UIHelper.startActivity(getActivity(), TitleActivity.class, bundle);
                break;
            case R.id.tv_04:
                bundle = new Bundle();
                bundle.putString(TitleActivity.NEWSURL, "http://news.10jqka.com.cn/m590984947/");
                UIHelper.startActivity(getActivity(), TitleActivity.class, bundle);
            case R.id.tv_05:
                bundle = new Bundle();
                bundle.putString(TitleActivity.NEWSURL, "http://news.10jqka.com.cn/m601522625/");
                UIHelper.startActivity(getActivity(), TitleActivity.class, bundle);
                break;
        }
    }


    private void getRData() {
        RequestParams params = new RequestParams(URLS.TOUTIAO_INDEX);
        params.addBodyParameter("key", "70b52ac2225c6a42adc3575302673f4b");
        params.addBodyParameter("type", "caijing");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<RelevantNewsModel.DataBean> list;
                RelevantNewsModel model = JSON.parseObject(message.getResult(), RelevantNewsModel.class);
                model.getData();
                list = model.getData();
                rModelList.addAll(list);
                rAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(VerticalRollingTextView view, int index) {

    }
}
