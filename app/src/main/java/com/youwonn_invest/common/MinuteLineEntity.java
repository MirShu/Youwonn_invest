package com.youwonn_invest.common;

import com.github.tifezh.kchartlib.chart.EntityImpl.MinuteLineImpl;

import java.util.Date;

/**
 * Created by ShuLin on 2017/11/10.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MinuteLineEntity implements MinuteLineImpl {
    /**
     * time : 09:30
     * price : 3.53
     * avg : 3.5206
     * vol : 9251
     */

    public Date time;
    public float price;
    public float avg;
    public int vol;

    @Override
    public float getAvgPrice() {
        return avg;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public Date getDate() {
        return time;
    }
}
