package com.youwonn_invest.ui.fragment;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.youwonn_invest.R;
import com.youwonn_invest.adapter.recycler.RecyclerAdapter;
import com.youwonn_invest.adapter.recycler.RecyclerViewHolder;
import com.youwonn_invest.constant.URLS;
import com.youwonn_invest.model.BannerAdventModel;
import com.youwonn_invest.model.MarketViewRollModel;
import com.youwonn_invest.model.MessageResult;
import com.youwonn_invest.model.NewsModel;
import com.youwonn_invest.ui.activity.NewsDetailActivity;
import com.youwonn_invest.ui.view.MyBGARefreshViewHolder;
import com.youwonn_invest.ui.view.NewsLoopPicture;
import com.youwonn_invest.utils.NetUtil;
import com.youwonn_invest.utils.UIHelper;
import com.youwonn_invest.widget.extent.BannerItem;
import com.youwonn_invest.widget.extent.CustomLinearLayoutManager;
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
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class InformationFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String NEWS_ID_TAG = "news_id_tag";
    public static final String NEWS_TIPS = "news_tips";
    private View rootView;
    private View view_up;
    private View view_upto;
    private RelativeLayout rl_empty;
    private RecyclerView recyclerView;
    private MarqueeView rcMarketView;
    private List<NewsModel> listNews = new ArrayList<>();
    private List<MarketViewRollModel> listMarket = new ArrayList<>();
    private RecyclerAdapter<NewsModel> headNewsAdapter;
    private MarqueeView.InnerAdapter marketAdapter;
    private BGARefreshLayout bgaRefreshLayout;
    private NewsLoopPicture newsLoopPicture;
    private RelativeLayout rl_unscroll;
    private List<BannerItem> bannerItems = new ArrayList<>();
    private int readsize = 0;
    private ScrollView scrollView;
    private int orreadsize = 0;
    private String newsTime = "0";
    private String ornewsTime = "0";
    private CustomLinearLayoutManager linearLayoutManager;
    private boolean hasStarted = false;
    private List<String> orreadTimelist = new ArrayList();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LodingDialog lodingDialog;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
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

            handler.postDelayed(this, 10000);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_information, null);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        this.rcMarketView = (MarqueeView) rootView.findViewById(R.id.rcMarketView);
        this.newsLoopPicture = (NewsLoopPicture) rootView.findViewById(R.id.loop_picture);
        this.bgaRefreshLayout = (BGARefreshLayout) rootView.findViewById(R.id.bga_refresh);
        this.scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        this.rl_empty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        this.view_up = rootView.findViewById(R.id.view_up);
        this.view_upto = rootView.findViewById(R.id.view_upto);
        rl_unscroll = (RelativeLayout) rootView.findViewById(R.id.rl_unscroll);
        rl_unscroll.bringToFront();
        rl_unscroll.setOnClickListener(null);
        this.judge_network();
        this.getBannerInfo();
        this.bindRecycleView();
        bgaRefreshLayout.setDelegate(this);
        bgaRefreshLayout.setRefreshViewHolder(new MyBGARefreshViewHolder(getActivity(), true));
        return rootView;

    }

    private void judge_network() {
        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            bgaRefreshLayout.setVisibility(View.GONE);
            rl_empty.setOnClickListener(view -> {
                lodingDialog = new LodingDialog(getActivity(), "加载中...");
                lodingDialog.show();
                orreadTimelist.clear();
                listMarket.clear();
                bannerItems.clear();
                initData();
                getBannerInfo();
                markeData();
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
            });
        }

    }

    /**
     * 加载首页轮播图
     */
    public void getBannerInfo() {
        RequestParams params = new RequestParams(URLS.ADVENT_BANNERADVEN);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                BannerAdventModel response = gson.fromJson(result, BannerAdventModel.class);
                if (response.getCode().equals("0")) {
                    Fresco.initialize(getActivity());
                    String imageUrl[] = new String[response.getData().size()];
                    String[] titles = new String[response.getData().size()];
                    String[] newsTitle = new String[response.getData().size()];
                    String[] newsTips = new String[response.getData().size()];
                    String[] newsId = new String[response.getData().size()];
                    String[] newsLocation = new String[response.getData().size()];
                    for (int i = 0; i < response.getData().size(); i++) {
                        imageUrl[i] = response.getData().get(i).getImg_src();
                        titles[i] = String.valueOf(i + 1);
                        newsTips[i] = response.getData().get(i).getNews_tips();
                        newsTitle[i] = response.getData().get(i).getTitle();
                        newsId[i] = response.getData().get(i).getNews_product_id();
                        newsLocation[i] = response.getData().get(i).getLocation();
                        bannerItems.add(new BannerItem(response.getData().get(i).getTitle(),
                                response.getData().get(i).getBewrite(), response.getData().get(i).getImg_src()));
                    }

                    //传递数据给轮询器,次方法比较low, 有时间了改进.(改进为直接传一个实体类就可以)
                    newsLoopPicture.setImageUrl(imageUrl, true);
                    newsLoopPicture.setImageTitle(titles, newsTitle, newsId, newsTips, newsLocation);

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

    private void bindRecycleView() {
        /*******************
         * 绑定新闻数据
         *******************/
        this.initData();
        this.headNewsAdapter = new RecyclerAdapter<NewsModel>(this.getActivity(), listNews,
                R.layout.item_headnews_recycler) {
            @Override
            public void convert(RecyclerViewHolder helper, NewsModel item, int position) {
                TextView tv_news_tag = helper.getView(R.id.tv_news_tag);
                ImageView news_img = helper.getView(R.id.news_img);
                news_img.setScaleType(ImageView.ScaleType.FIT_XY);
                View line_do = helper.getView(R.id.line_do);
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
                    if (position == 0) {
                        line_do.setVisibility(View.VISIBLE);
                        tv_news_tag.setVisibility(View.VISIBLE);
                        tv_news_tag.setText(R.string.tv_head_news);
                        tv_news_tag.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_font_bag));
                        tv_news_tag.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
                    } else if (position == 6) {
                        tv_news_tag.setVisibility(View.VISIBLE);
                        tv_news_tag.setText(R.string.tv_hot_news);
                        line_do.setVisibility(View.GONE);
                        tv_news_tag.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_informatin_hot));
                        tv_news_tag.setTextColor(ContextCompat.getColor(getContext(), R.color.color_f24a4a));
                    } else {
                        tv_news_tag.setVisibility(View.GONE);
                        line_do.setVisibility(View.GONE);
                    }
                } else if (item.getNews_tips().equals("1")) {
                    line_do.setVisibility(View.GONE);
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

        /***************
         * 实时行情数据
         ***************/
        markeData();
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.headNewsAdapter);
        //携带参数跳转新闻详情界面
        this.headNewsAdapter.setOnItemClickListener((parent, position) -> {
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

    /*****************
     * 获取新闻列表数据
     ****************/
    private void initData() {
        String readSize = String.valueOf(readsize);
        RequestParams params = new RequestParams(URLS.NEWS_NEWSLIST);
        params.addBodyParameter("type", "1");
        params.addBodyParameter("read", readSize);
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

                    headNewsAdapter.notifyDataSetChanged();
                    bgaRefreshLayout.endLoadingMore();
                    bgaRefreshLayout.endRefreshing();
                    bgaRefreshLayout.setVisibility(View.VISIBLE);
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


    private void markeData() {
        RequestParams mparams = new RequestParams(URLS.MARKET_NOWMARKET);
        x.http().get(mparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listMarket.clear();
                try {
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
            hasStarted = true;
            handler.postDelayed(runnable, 10000);
        } else {
            if (hasStarted) {
                handler.removeCallbacks(runnable);
            }
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
        rcMarketView.startMarquee();
        if (getUserVisibleHint()) {
            handler.postDelayed(runnable, 10000);
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            bgaRefreshLayout.setVisibility(View.GONE);
            rl_empty.setOnClickListener(v -> {
                lodingDialog = new LodingDialog(getActivity(), "加载中...");
                lodingDialog.show();
                listNews.clear();
                readsize = 0;
                newsTime = "0";
                orreadsize = 0;
                ornewsTime = "0";
                orreadTimelist.clear();
                bannerItems.clear();
                initData();
                bgaRefreshLayout.endRefreshing();
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
            });
        } else {
            new Handler().postDelayed(() -> {
                listNews.clear();
                readsize = 0;
                newsTime = "0";
                orreadsize = 0;
                ornewsTime = "0";
                orreadTimelist.clear();
                bannerItems.clear();
                initData();
                bgaRefreshLayout.endRefreshing();
            }, 1500);
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
