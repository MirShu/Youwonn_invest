package com.youwonn_invest.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.alibaba.fastjson.JSON;
import com.youwonn_invest.R;
import com.youwonn_invest.adapter.recycler.RecyclerAdapter;
import com.youwonn_invest.adapter.recycler.RecyclerViewHolder;
import com.youwonn_invest.constant.URLS;
import com.youwonn_invest.model.MarketViewRollModel;
import com.youwonn_invest.model.MessageResult;
import com.youwonn_invest.model.NewsModel;
import com.youwonn_invest.ui.activity.NewsDetailActivity;
import com.youwonn_invest.ui.view.MyBGARefreshViewHolder;
import com.youwonn_invest.utils.NetUtil;
import com.youwonn_invest.utils.UIHelper;
import com.youwonn_invest.widget.extent.MarqueeView;
import com.youwonn_invest.widget.loding.LodingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class NewsChannelFragment  extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private View view;
    private RecyclerView recyclerView;
    private MarqueeView rcMarketView;
    private List<NewsModel> listNews = new ArrayList<>();
    private List<MarketViewRollModel> listMarket = new ArrayList<>();
    private RecyclerAdapter<NewsModel> newsAdapter;
    private MarqueeView.InnerAdapter marketAdapter;
    private static final String ARG_POSITION = "position";
    private int newsType;
    private int readsize = 0;
    private int orreadsize = 0;
    private String newsTime = "0";
    private String ornewsTime = "0";
    private List<String> orreadTimelist = new ArrayList();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int newsId = 0;
    private LinearLayout ll_main_view;
    private RelativeLayout rl_empty;
    private LodingDialog lodingDialog;
    private BGARefreshLayout bgaRefreshLayout;
    private RelativeLayout rl_unscroll;
    private boolean isFirstLoad = true;
    private int isFirstMakeDate = 0;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            makeDateNotify();

            handler.postDelayed(this, 10000);
        }


    };
    private void makeDateNotify() {
        RequestParams mparams = new RequestParams(URLS.MARKET_NOWMARKET);
        x.http().get(mparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    listMarket.clear();
                    MessageResult message = MessageResult.parse(result);
                    List<MarketViewRollModel> marketList = JSON.parseArray(message.getData(),
                            MarketViewRollModel.class);
                    listMarket.addAll(marketList);
                    marketAdapter.notifyDataSetChanged();
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

    public static NewsChannelFragment newInstance(int position) {
        NewsChannelFragment newsChannelFragment = new NewsChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        newsChannelFragment.setArguments(bundle);
        return newsChannelFragment;
    }

    /**************************************************************
     * NewsHomeFragment 通过标签传递参数跳转到 NewsChannelFragment
     **************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsType = getArguments().getInt(ARG_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_new_channel, null);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.rcMarketView = (MarqueeView) view.findViewById(R.id.rcMarketView);
        this.ll_main_view = (LinearLayout) view.findViewById(R.id.ll_main_view);
        this.rl_empty = (RelativeLayout) view.findViewById(R.id.rl_empty);
        this.bgaRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.bga_refresh);
        this.rl_unscroll = (RelativeLayout) view.findViewById(R.id.rl_unscroll);
        this.rl_unscroll.bringToFront();
        this.rl_unscroll.setOnClickListener(null);
//        this.judge_network();
//        this.bindRecycleView();
        this.bgaRefreshLayout.setDelegate(this);
        this.bgaRefreshLayout.setRefreshViewHolder(new MyBGARefreshViewHolder(getActivity(), true));
        return view;
    }

    private void judge_network() {
        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            ll_main_view.setVisibility(View.GONE);
            rl_empty.setOnClickListener(view -> {
                getData();
                markeData();
                lodingDialog = new LodingDialog(getActivity(), "加载中...");
                lodingDialog.show();
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
            });
        }
    }

    private void bindRecycleView() {
        /**
         * 绑定头条新闻数据
         */
        this.getData();
        this.newsAdapter = new RecyclerAdapter<NewsModel>(this.getActivity(), listNews,
                R.layout.item_headnews_recycler) {
            @Override
            public void convert(RecyclerViewHolder helper, NewsModel item, int position) {
                TextView tv_news_tag = helper.getView(R.id.tv_news_tag);
                View line_do = helper.getView(R.id.line_do);
                ImageView news_img = helper.getView(R.id.news_img);
                news_img.setScaleType(ImageView.ScaleType.FIT_XY);
                line_do.setVisibility(View.GONE);
                LinearLayout ll_news_item_three = helper.getView(R.id.ll_news_item_three);
                LinearLayout ll_news_item = helper.getView(R.id.ll_news_item);
                if (item.getNews_tips().equals("0")) {
                    tv_news_tag.setVisibility(View.VISIBLE);
                    ll_news_item.setVisibility(View.VISIBLE);
                    ll_news_item_three.setVisibility(View.GONE);
                    helper.setImageUrl(R.id.news_img, item.getNews_img());
                    helper.setText(R.id.news_title, item.getNews_title());
                    helper.setText(R.id.news_time, item.getNews_time_show());
                    helper.setText(R.id.news_by, item.getNews_by());
                    tv_news_tag.setVisibility(View.GONE);
                } else if (item.getNews_tips().equals("1")) {
                    orreadTimelist.add(item.getNews_time());
                    tv_news_tag.setVisibility(View.GONE);
                    ll_news_item.setVisibility(View.GONE);
                    ll_news_item_three.setVisibility(View.VISIBLE);
                    String str = item.getNews_img();
                    str = str.substring(1, str.length() - 1);
                    String[] strarray = str.split(",");
                    switch (strarray.length) {
                        case 1:
                            helper.setImageUrl(R.id.iv_hor_01, strarray[0].substring(1, strarray[0].length() - 1));
                            helper.getView(R.id.iv_hor_02).setVisibility(View.GONE);
                            helper.getView(R.id.iv_hor_03).setVisibility(View.GONE);
                            break;
                        case 2:
                            helper.setImageUrl(R.id.iv_hor_01, strarray[0].substring(1, strarray[0].length() - 1));
                            helper.setImageUrl(R.id.iv_hor_02, strarray[1].substring(1, strarray[1].length() - 1));
                            helper.getView(R.id.iv_hor_03).setVisibility(View.GONE);
                            break;
                        case 3:
                            helper.setImageUrl(R.id.iv_hor_01, strarray[0].substring(1, strarray[0].length() - 1));
                            helper.setImageUrl(R.id.iv_hor_02, strarray[1].substring(1, strarray[1].length() - 1));
                            helper.setImageUrl(R.id.iv_hor_03, strarray[2].substring(1, strarray[2].length() - 1));
                    }

                    helper.setText(R.id.news_title_three, item.getNews_title());
                    helper.setText(R.id.news_time_three, item.getNews_time());
                    helper.setText(R.id.news_by_three, item.getNews_by());
                }
            }
        };

        markeData();
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.newsAdapter);

        //携带参数跳转新闻详情界面
        this.newsAdapter.setOnItemClickListener((parent, position) -> {
            if (!NetUtil.isNetworkConnected(getActivity())) {
                UIHelper.toastMessage(getActivity(), getResources().getString(R.string.tv_network_desertion));
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(NewsDetailActivity.NEWS_ID_TAG, listNews.get(position).getId());
                bundle.putInt(NewsDetailActivity.NEWS_TIPS, Integer.parseInt(listNews.get(position).getNews_tips()));
                UIHelper.startActivity(getActivity(), NewsDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 行情数据获取
     */
    private void markeData() {
        RequestParams mparams = new RequestParams(URLS.MARKET_NOWMARKET);
        x.http().get(mparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    listMarket.clear();
                    MessageResult message = MessageResult.parse(result);
                    List<MarketViewRollModel> marketList = JSON.parseArray(message.getData(),
                            MarketViewRollModel.class);
                    listMarket.addAll(marketList);
                    rcMarketView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
                    marketAdapter = new MarqueeView.InnerAdapter(listMarket, getActivity());
                    rcMarketView.setAdapter(marketAdapter);
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
     * 获取新闻频道更多资讯数据
     */
    private void getData() {
//        String readSize = String.valueOf(readsize);
        RequestParams params = new RequestParams(URLS.NEWS_NEWSLIST);
//        Log.e("--------readsize-------", "" + readsize);
//        Log.e("--------orreadsize----", "" + orreadsize);
        params.addBodyParameter("type", "" + newsType);
        params.addBodyParameter("read", String.valueOf(readsize));
        params.addBodyParameter("news_time", newsTime);
        params.addBodyParameter("orread", String.valueOf(orreadsize));
        params.addBodyParameter("ornews_time", ornewsTime);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    orreadTimelist.clear();
                    readsize = 0;
                    orreadsize = 0;
                    if (isFirstMakeDate <= 1) {
                        listNews.clear();
                        isFirstMakeDate++;
                    }
                    MessageResult message = MessageResult.parse(result);
                    List<NewsModel> newsList = JSON.parseArray(message.getData(),
                            NewsModel.class);
                    listNews.addAll(newsList);
                    for (int j = 0; j < listNews.size(); j++) {
                        if (listNews.get(j).getNews_tips().equals("1")) {
                            orreadTimelist.add(listNews.get(j).getNews_time());
                        }
                    }

                    if (newsTime == "0") {

                        newsTime = listNews.get(0).getNews_time();
                        for (int j = 0; j < listNews.size(); j++) {
                            if (df.parse(listNews.get(j).getNews_time()).after(df.parse(newsTime))) {
                                newsTime = listNews.get(j).getNews_time();
                            }
                        }
                    }

                    if (ornewsTime == "0" && !orreadTimelist.isEmpty()) {
                        ornewsTime = orreadTimelist.get(0);
                        for (int j = 0; j < orreadTimelist.size(); j++) {
                            if (df.parse(orreadTimelist.get(j)).after(df.parse(ornewsTime))) {
                                ornewsTime = orreadTimelist.get(j);
                            }
                        }
                    }
                    readsize = listNews.size() - orreadTimelist.size();
                    orreadsize = orreadTimelist.size();
                    newsAdapter.notifyDataSetChanged();
                    bgaRefreshLayout.endLoadingMore();
                    bgaRefreshLayout.endLoadingMore();
                    ll_main_view.setVisibility(View.VISIBLE);
                    rl_empty.setVisibility(View.GONE);
                    lodingDialog.dismiss();
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

            rcMarketView.startMarquee();
            if (isFirstLoad) {
                this.judge_network();
                this.bindRecycleView();
//                this.bgaRefreshLayout.setDelegate(this);
//                this.bgaRefreshLayout.setRefreshViewHolder(new MyBGARefreshViewHolder(getActivity(), true));
                bgaRefreshLayout.beginRefreshing();
                if (isFirstMakeDate==0){

                    isFirstLoad = false;
                }
                isFirstMakeDate = 0;
            }
            handler.postDelayed(runnable, 10000);
        } else {
            handler.removeCallbacks(runnable);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        rcMarketView.stopMarquee();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            rcMarketView.startMarquee();
            handler.postDelayed(runnable, 6000);
        }else {
            rcMarketView.stopMarquee();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            ll_main_view.setVisibility(View.GONE);
            rl_empty.setOnClickListener(v -> {
                lodingDialog = new LodingDialog(getActivity(), "加载中...");
                lodingDialog.show();
                listNews.clear();
                readsize = 0;
                newsTime = "0";
                orreadsize = 0;
                ornewsTime = "0";
                orreadTimelist.clear();
                isFirstMakeDate = 2;
                getData();
                markeData();
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
                new Handler().postDelayed(() -> bgaRefreshLayout.endRefreshing(), 1000);
            });
        } else {
            new Handler().postDelayed(() -> bgaRefreshLayout.endRefreshing(), 1000);
            listNews.clear();
            readsize = 0;
            newsTime = "0";
            orreadsize = 0;
            ornewsTime = "0";
            orreadTimelist.clear();
            markeData();
            getData();
        }


    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new Handler().postDelayed(() -> {
            getData();
            bgaRefreshLayout.endLoadingMore();
        }, 1000);
        return true;
    }
}
