package com.youwonn_invest.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MarketViewRollModel implements Serializable {
    /**
     * " "market_name": "美元白银",
     * "market_price": "16.68",
     * "market_float": "0.27%"
     */
    private String market_name;
    private String market_price;
    private String market_float;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getMarket_float() {
        return market_float;
    }

    public void setMarket_float(String market_float) {
        this.market_float = market_float;
    }


    public static MarketViewRollModel parse(String result) {
        MarketViewRollModel marketViewRollModel = new MarketViewRollModel();
        try {
            marketViewRollModel = JSON.parseObject(result, MarketViewRollModel.class);
        } catch (Exception e) {
            String error = e.getMessage();

        }
        return marketViewRollModel;
    }
}
