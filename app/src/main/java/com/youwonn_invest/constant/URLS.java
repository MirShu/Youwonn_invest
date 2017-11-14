package com.youwonn_invest.constant;

import java.text.MessageFormat;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class URLS {
    /**
     * 基路径交易板块获取webview Url: http://app.youwonn.com/Api/Webview/getDealUrl
     * 研报板块获取webview Url: http://app.youwonn.com/Api/Webview/getResearchReportUrl
     * 以前的接口前缀地址都改成这个:http://app.youwonn.com/
     */
    public static final String BASE_SERVER_URL = "http://app.youwonn.com/Api/";
    public static final String NEWS_NEWSRELEVANT = MessageFormat.format("{0}News/NewsRelevant", BASE_SERVER_URL);
    public static final String NEWS_NEWSLIST = MessageFormat.format("{0}News/NewsList", BASE_SERVER_URL);
    public static final String NEWS_NEWSINFO = MessageFormat.format("{0}News/NewsInfo", BASE_SERVER_URL);
    public static final String MARKET_NOWMARKET = MessageFormat.format("{0}Market/NowMarket", BASE_SERVER_URL);
    public static final String ADVENT_BANNERADVEN = MessageFormat.format("{0}Advent/bannerAdvent", BASE_SERVER_URL);
    public static final String GET_ABOUTSUS = MessageFormat.format("{0}Company/getAboutsUs", BASE_SERVER_URL);
    public static final String LIVE_GETSPEAKERLIST = MessageFormat.format("{0}Live/getSpeakerList", BASE_SERVER_URL);
    public static final String LIVE_GET_LIVEVIDEO = BASE_SERVER_URL + "Live/getVideoSource";
    public static final String NEWS_FLASH = BASE_SERVER_URL + "News/getNewsFlashList";
    public static final String GET_NEWS_FLASH = BASE_SERVER_URL + "News/getNewsFlash";
    public static final String ADD_USER_DEVICE = BASE_SERVER_URL + "User/addUserDevice";
    public static final String GETDEAL_URL = BASE_SERVER_URL + "Webview/getDealUrl";  //交易板块
    public static final String GET_RESEARCH_REPORT_URL = BASE_SERVER_URL + "Webview/getResearchReportUrl";//研报板块
    public static final String GET_BARGAIN = "http://web.juhe.cn:8080/fund/zcgjj/?key=99657bd68e18ff21a27429ad6cf24aff";//f交易行情
    public static final String GET_FUND = "http://japi.juhe.cn/jingzhi/query.from?page=1&pagesize=20&type=netvalue&key=47730e351b84b6569a280560cbb56178";
    public static final String TOUTIAO_INDEX = "http://v.juhe.cn/toutiao/index";
}
