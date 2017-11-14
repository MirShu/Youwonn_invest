package com.youwonn_invest.ui.activity;

import android.graphics.Color;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.MinuteChartView;
import com.github.tifezh.kchartlib.utils.DateUtil;
import com.youwonn_invest.R;
import com.youwonn_invest.adapter.KChartAdapter;
import com.youwonn_invest.base.BaseActivity;
import com.youwonn_invest.common.MinuteLineEntity;
import com.youwonn_invest.utils.DataRequest;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by ShuLin on 2017/11/11.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MinuteChartActivity extends BaseActivity {
    public static final String MARKETNAME = "market_name";
    public static final String MARKETPRICE = "market_price";
    public static final String MARKETFLOAT = "market_float";
    private TextView tv_head_title;
    private ImageView iv_back_icon;
    private KChartView mKChartView;
    private KChartAdapter mAdapter;
    private LinearLayout mLlStatus;
    private TextView tvPrice;
    private TextView tvFloat;
    private String marketName;
    private String marketPrice;
    private String marketFloat;
    private TextView tvHit;
    private MinuteChartView mMinuteChartView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_minute_chart);
        mMinuteChartView = (MinuteChartView) findViewById(R.id.minuteChartView);
        this.marketName = getIntent().getStringExtra(MARKETNAME);
        this.marketPrice = getIntent().getStringExtra(MARKETPRICE);
        this.marketFloat = getIntent().getStringExtra(MARKETFLOAT);


        this.iv_back_icon = (ImageView) findViewById(R.id.iv_back_icon);
        this.tv_head_title = (TextView) findViewById(R.id.tv_main_title);
        this.mKChartView = (KChartView) findViewById(R.id.kchart_view);
        this.tvPrice = (TextView) findViewById(R.id.tv_price);
        this.tvFloat = (TextView) findViewById(R.id.tv_percent);
        this.mLlStatus = (LinearLayout) findViewById(R.id.ll_status);
        TextView tv_kp = (TextView) findViewById(R.id.tv_kp);
        TextView tv_l = (TextView) findViewById(R.id.tv_l);
        this.tvHit = (TextView) findViewById(R.id.tv_hi);
        String strHit = MessageFormat.format("高 <font color=\"#019f53\">{0}</font> ", "11659.65");
        String strKp = MessageFormat.format("开盘 <font color=\"#fd040a\">{0}</font> ", "3425.118");
        tvHit.setText(Html.fromHtml(strHit));
        tv_kp.setText(Html.fromHtml(strKp));
        this.tv_head_title.setText(marketName);
        this.tvPrice.setText(marketPrice);
        this.tvFloat.setText(marketFloat);
        if (String.valueOf(marketFloat.charAt(0)).equals("-")) {
            tvPrice.setTextColor(Color.parseColor("#019f53"));
            tvFloat.setTextColor(Color.parseColor("#019f53"));
        } else {
            tvPrice.setTextColor(Color.parseColor("#fd040a"));
            tvFloat.setTextColor(Color.parseColor("#fd040a"));
            tvFloat.setText("+" + marketFloat);
        }


        this.iv_back_icon.setOnClickListener(view -> finish());
    }

    @Override
    public void initData() {
        try {
            //整体开始时间
            Date startTime = DateUtil.shortTimeFormat.parse("09:30");
            //整体的结束时间
            Date endTime = DateUtil.shortTimeFormat.parse("15:00");
            //休息开始时间
            Date firstEndTime = DateUtil.shortTimeFormat.parse("11:30");
            //休息结束时间
            Date secondStartTime = DateUtil.shortTimeFormat.parse("13:00");
            //获取随机生成的数据
            List<MinuteLineEntity> minuteData =
                    DataRequest.getMinuteData(this, startTime,
                            endTime,
                            firstEndTime,
                            secondStartTime);
            mMinuteChartView.initData(minuteData,
                    startTime,
                    endTime,
                    firstEndTime,
                    secondStartTime,
                    (float) (minuteData.get(0).price - 0.5 + Math.random()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillView() {

    }
}
