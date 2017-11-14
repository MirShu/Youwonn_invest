package com.youwonn_invest.ui.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;
import com.youwonn_invest.R;
import com.youwonn_invest.adapter.KChartAdapter;
import com.youwonn_invest.base.BaseActivity;
import com.youwonn_invest.common.KLineEntity;
import com.youwonn_invest.utils.DataRequest;

import java.text.MessageFormat;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ShuLin on 2017/11/10.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class RadixKLine extends BaseActivity {
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

    @Override
    public void initView() {
        this.marketName = getIntent().getStringExtra(MARKETNAME);
        this.marketPrice = getIntent().getStringExtra(MARKETPRICE);
        this.marketFloat = getIntent().getStringExtra(MARKETFLOAT);
        setContentView(R.layout.activity_radixkline);
//        if (type == 0) {
//            setContentView(R.layout.activity_radixkline);
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////                Window window = getWindow();
////                window.setFlags(
////                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
////                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            }
//        } else {
//            setContentView(R.layout.activity_radixkline);
//        }

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
    public void fillView() {
        mAdapter = new KChartAdapter();
        mKChartView.setAdapter(mAdapter);
        mKChartView.setDateTimeFormatter(new DateFormatter());
        mKChartView.setGridRows(4);
        mKChartView.setGridColumns(4);
        mKChartView.setOnSelectedChangedListener((view, point, index) -> {
            KLineEntity data = (KLineEntity) point;
            Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.getClosePrice());
        });
    }

    @Override
    public void initData() {
        mKChartView.showLoading();
        new Thread(() -> {
            final List<KLineEntity> data = DataRequest.getALL(RadixKLine.this);
            runOnUiThread(() -> {
                mAdapter.addFooterData(data);
                mKChartView.startAnimation();
                mKChartView.refreshEnd();
            });
        }).start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLlStatus.setVisibility(View.GONE);
            mKChartView.setGridRows(3);
            mKChartView.setGridColumns(8);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLlStatus.setVisibility(View.VISIBLE);
            mKChartView.setGridRows(4);
            mKChartView.setGridColumns(4);
        }
    }

}
