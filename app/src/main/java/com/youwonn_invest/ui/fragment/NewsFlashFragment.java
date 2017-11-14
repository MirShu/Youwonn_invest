package com.youwonn_invest.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.andview.refreshview.XRefreshView;
import com.google.gson.Gson;
import com.youwonn_invest.R;
import com.youwonn_invest.adapter.recycler.RecyclerAdapter;
import com.youwonn_invest.adapter.recycler.RecyclerViewHolder;
import com.youwonn_invest.constant.URLS;
import com.youwonn_invest.model.MarketViewRollModel;
import com.youwonn_invest.model.MessageResult;
import com.youwonn_invest.model.NewsFlashResponse;
import com.youwonn_invest.ui.view.MyBGARefreshViewHolder;
import com.youwonn_invest.utils.NetUtil;
import com.youwonn_invest.widget.extent.MarqueeView;
import com.youwonn_invest.widget.loding.LodingDialog;
import com.youwonn_invest.widget.scollview.ScrollViewEx;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class NewsFlashFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String NEWS_ID_TAG = "news_id_tag";
    public static final String NEWS_TIPS = "news_tips";
    private View rootView;
    private View view_up;
    private View view_upto;
    private LinearLayout ll_up;
    private RecyclerView recyclerView;
    private MarqueeView rcMarketView_up;
    private List<NewsFlashResponse.DataBean> listNewsFlash = new ArrayList<>();
    private List<NewsFlashResponse.DataBean> listNewsFlashNew = new ArrayList<>();
    private RecyclerAdapter<NewsFlashResponse.DataBean> newsFlashAdapter;
    private List<MarketViewRollModel> listMarket = new ArrayList<>();
    private MarqueeView.InnerAdapter marketAdapter;
    private List<String> showDayList = new ArrayList<>();
    private List<String> showMonthList = new ArrayList<>();
    private RelativeLayout rl_unscroll;
    private XRefreshView xrefreshview;
    private ScrollViewEx scrollViewEx;
    private BGARefreshLayout bgaRefreshLayout;
    private SpannableString spanString;
    private boolean isFirstLoad = true;
    private int readsize = 0;
    private int ordertime = 0;
    private String mday = null;
    private int firstPosition;
    private RelativeLayout rl_empty;
    private RelativeLayout rl_main_viwe;
    private LodingDialog lodingDialog;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            RequestParams mparams = new RequestParams(URLS.MARKET_NOWMARKET);
            x.http().get(mparams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    listMarket.clear();
                    MessageResult message = MessageResult.parse(result);
                    List<MarketViewRollModel> marketList = JSON.parseArray(message.getData(),
                            MarketViewRollModel.class);
                    listMarket.addAll(marketList);
                    marketAdapter.notifyDataSetChanged();
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

            firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            RequestParams params = new RequestParams(URLS.GET_NEWS_FLASH);
            params.addBodyParameter("ordertime", String.valueOf(ordertime));
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    NewsFlashResponse response = gson.fromJson(result, NewsFlashResponse.class);
                    if (response.getCode().equals("0")) {
                        int flashSize = response.getData().size();
                        firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                        List<NewsFlashResponse.DataBean> newsList = response.getData();
                        listNewsFlashNew.clear();
                        listNewsFlashNew.addAll(newsList);
                        listNewsFlashNew.addAll(listNewsFlash);
                        listNewsFlash.clear();
                        listNewsFlash.addAll(listNewsFlashNew);

                        //用集合记录需要显示日期的条目
                        showDayList.clear();
                        showMonthList.clear();
                        for (int j = 0; j < listNewsFlash.size(); j++) {
                            showDayList.add("0");
                            showMonthList.add("0");
                        }

                        for (int j = 0; j < listNewsFlash.size(); j++) {
                            if (!listNewsFlash.get(j).getDay().equals(mday)) {
                                showDayList.add(j, listNewsFlash.get(j).getDay());
                                showMonthList.add(j, listNewsFlash.get(j).getMonth());
                                break;
                            }
                        }

                        ordertime = response.getData().get(0).getOrdertime();
                        readsize = listNewsFlash.size();
                        for (int j = 0; j < listNewsFlash.size(); j++) {
                        }
                        newsFlashAdapter.notifyDataSetChanged();
                        linearLayoutManager.scrollToPositionWithOffset(flashSize + firstPosition, 0);
                    } else {
                        firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                        if (firstPosition != -1) {
                        }
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
            handler.postDelayed(this, 6000);
        }
    };

    LocalBroadcastManager broadcastManager;
    IntentFilter intentFilter;
    BroadcastReceiver mReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_news_flash, null);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        this.rcMarketView_up = (MarqueeView) rootView.findViewById(R.id.rcMarketView_up);
        this.ll_up = (LinearLayout) rootView.findViewById(R.id.ll_up);
        this.view_up = rootView.findViewById(R.id.view_up);
        this.view_upto = rootView.findViewById(R.id.view_upto);
        this.bgaRefreshLayout = (BGARefreshLayout) rootView.findViewById(R.id.bga_refresh);
        rl_empty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        rl_main_viwe = (RelativeLayout) rootView.findViewById(R.id.rl_main_viwe);

        rl_unscroll = (RelativeLayout) rootView.findViewById(R.id.rl_unscroll);
        rl_unscroll.setOnClickListener(null);
        this.ll_up.bringToFront();
        rl_unscroll.bringToFront();
        this.view_up.bringToFront();
        this.view_upto.bringToFront();
//        this.bindRecycleView();
//        judge_network();
        bgaRefreshLayout.setDelegate(this);
        bgaRefreshLayout.setRefreshViewHolder(new MyBGARefreshViewHolder(getActivity(), true));
        return rootView;
    }

    /**
     * 判断是否有网络
     */
    private void judge_network() {
        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            rl_main_viwe.setVisibility(View.GONE);
            rl_empty.setOnClickListener(view -> {
                lodingDialog = new LodingDialog(getActivity(), "加载中...");
                lodingDialog.show();
                listNewsFlash.clear();
                listNewsFlashNew.clear();
                listMarket.clear();
                ordertime = 0;
                readsize = 0;
                mday = null;
//                this.bindRecycleView();
                initData();
                markeData();
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
            });
        }
    }


    /**
     * eventBus 传递服务器发送的消息推送
     *
     * @param event
     */
//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void receiveMsg(FirstEvent event) {
//        String tag = event.getTag();
//        if (tag != null && !TextUtils.isEmpty(tag)) {
//            Log.i("hemiy", "收到了tag的消息");
//            Log.i("hemiymsg", event.getmMsg());
//            UIHelper.toastMessage(getActivity(), event.getmMsg());
//            listNewsFlash.clear();
//            initData();
//        } else {
//            UIHelper.toastMessage(getActivity(), event.getmMsg());
//        }
//    }

    /**
     * 判断当前fragment 是否可见, 课件则让请求服务器,让服务器推送
     * 不可见 则请求服务器,让服务器不要推送
     * fragment的可见与不可见判断比较麻烦,用到setUserVisibleHint 和 onPouse 和onResume
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            rcMarketView_up.startMarquee();
            if (isFirstLoad) {
                this.bindRecycleView();
                judge_network();
                bgaRefreshLayout.beginRefreshing();
                isFirstLoad = false;
            }
            handler.postDelayed(runnable, 6000);
        } else {
//            rcMarketView_up.stopMarquee();
            handler.removeCallbacks(runnable);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        rcMarketView_up.stopMarquee();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            rcMarketView_up.startMarquee();
            handler.postDelayed(runnable, 6000);
        } else {
            rcMarketView_up.stopMarquee();
        }
    }


    /**
     * 让recycleview跳转到指定条目方法
     *
     * @param manager
     * @param mRecyclerView
     * @param n
     */
    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

    private int i;

    private void bindRecycleView() {
        /*******************
         * 绑定头条新闻数据
         *******************/
        this.initData();

        this.newsFlashAdapter = new RecyclerAdapter<NewsFlashResponse.DataBean>(this.getActivity(), listNewsFlash,
                R.layout.item_newsflash_recycler) {
            @Override
            public void convert(RecyclerViewHolder helper, NewsFlashResponse.DataBean item, int position) {
                TextView tv_time = helper.getView(R.id.tv_time);
                TextView tv_day = helper.getView(R.id.tv_day);
                RelativeLayout rl_day = helper.getView(R.id.rl_day);
                TextView tv_line_up = helper.getView(R.id.tv_line_up);
                TextView tv_line_up_day = helper.getView(R.id.tv_line_up_day);
//                try {

                //判断显示日期的圆圈是否隐藏
                if (showMonthList.get(position) == "0") {
                    rl_day.setVisibility(View.GONE);//隐藏日期显示
                    tv_time.setVisibility(View.VISIBLE);
                    //是否隐藏小时上面的蓝线
                    if (position == 0) {
                        tv_line_up.setVisibility(View.VISIBLE);
                    } else {
                        tv_line_up.setVisibility(View.GONE);
                    }

                } else {
                    rl_day.setVisibility(View.VISIBLE);//显示日期
                    tv_line_up.setVisibility(View.GONE);
                    //是否隐藏day视图上面的蓝线
                    if (position == 0) {
                        tv_line_up_day.setVisibility(View.VISIBLE);
                    } else {
                        tv_line_up_day.setVisibility(View.GONE);
                    }
                }

//                    if (position == 0) {
//                        tv_line_up.setVisibility(View.VISIBLE);
//                    } else {
//                        tv_line_up.setVisibility(View.GONE);
//                    }
                helper.setText(R.id.tv_time, item.getTime());
                String itemContent = item.getContent().replace("*=*", "\n");
                spanString = new SpannableString(itemContent);
                int colorOnStart = itemContent.indexOf(item.getFont_color());
                int bOnStart = itemContent.indexOf(item.getFont_b());
                if (item.getColor_start().equals(false)) {
                    if (item.getB_start().equals(false)) {
                        helper.setText(R.id.tv_text, itemContent);
                    } else {
                        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), bOnStart, bOnStart + item.getFont_b().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                } else {
                    if (item.getB_start().equals(false)) {
                        spanString.setSpan(new ForegroundColorSpan(Color.RED), colorOnStart, colorOnStart + item.getFont_color().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), bOnStart, bOnStart + item.getFont_b().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanString.setSpan(new ForegroundColorSpan(Color.RED), colorOnStart, colorOnStart + item.getFont_color().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                tv_day.setText(item.getMonth() + "\n" + item.getDay());
                helper.setText(R.id.tv_text, spanString);
            }

        };

        /***************
         * 实时行情数据
         ***************/
        markeData();
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, linearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.newsFlashAdapter);

    }

    /*****************
     * 获取头条新闻列表数据
     ****************/
    private void initData() {
        RequestParams params = new RequestParams(URLS.NEWS_FLASH);
        params.addBodyParameter("read", String.valueOf(readsize));
        params.addBodyParameter("ordertime", String.valueOf(ordertime));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NewsFlashResponse response = gson.fromJson(result, NewsFlashResponse.class);
                if (response.getCode().equals("0")) {
                    List<NewsFlashResponse.DataBean> newsList = response.getData();
                    listNewsFlash.addAll(newsList);
                    ordertime = listNewsFlash.get(0).getOrdertime();
                    readsize = listNewsFlash.size();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd");
                    Date curDate = new Date(System.currentTimeMillis());
                    mday = formatter.format(curDate);
                    //用集合记录需要显示当前天日期的条目
                    showDayList.clear();
                    showMonthList.clear();
                    for (int j = 0; j < listNewsFlash.size(); j++) {
                        showDayList.add("0");
                        showMonthList.add("0");
                    }

                    for (int j = 0; j < listNewsFlash.size(); j++) {
                        if (!listNewsFlash.get(j).getDay().equals(mday)) {
                            showDayList.add(j, listNewsFlash.get(j).getDay());
                            showMonthList.add(j, listNewsFlash.get(j).getMonth());
                            break;
                        }
                    }
                    recyclerView.setLayoutManager(linearLayoutManager);
                    firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    newsFlashAdapter.notifyDataSetChanged();
                    rl_main_viwe.setVisibility(View.VISIBLE);
                    rl_empty.setVisibility(View.GONE);
                    lodingDialog.dismiss();
                    bgaRefreshLayout.endRefreshing();
                    bgaRefreshLayout.endLoadingMore();
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


    private void markeData() {
        RequestParams mparams = new RequestParams(URLS.MARKET_NOWMARKET);
        x.http().get(mparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listMarket.clear();
                MessageResult message = MessageResult.parse(result);
                List<MarketViewRollModel> marketList = JSON.parseArray(message.getData(),
                        MarketViewRollModel.class);

                listMarket.addAll(marketList);
                rcMarketView_up.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
                marketAdapter = new MarqueeView.InnerAdapter(listMarket, getActivity());
                rcMarketView_up.setAdapter(marketAdapter);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            rl_main_viwe.setVisibility(View.GONE);
            rl_empty.setOnClickListener(v -> {
                lodingDialog = new LodingDialog(getActivity(), "加载中...");
                lodingDialog.show();
                listNewsFlash.clear();
                ordertime = 0;
                readsize = 0;
                mday = null;
                initData();
                markeData();
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
                new Handler().postDelayed(() -> bgaRefreshLayout.endRefreshing(), 1000);
            });


        } else {
            new Handler().postDelayed(() -> {
                listNewsFlash.clear();
                ordertime = 0;
                readsize = 0;
                mday = null;
                initData();
                bgaRefreshLayout.endRefreshing();
            }, 1000);
        }


    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new Handler().postDelayed(() -> {
            initData();
            bgaRefreshLayout.endLoadingMore();
        }, 1000);
        return true;
    }
}
