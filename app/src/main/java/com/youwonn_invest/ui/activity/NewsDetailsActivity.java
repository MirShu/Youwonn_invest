package com.youwonn_invest.ui.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.youwonn_invest.R;
import com.youwonn_invest.base.BaseActivity;

/**
 * Created by ShuLin on 2017/11/10.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class NewsDetailsActivity extends BaseActivity {
    public static final String NEWSURL = "news_url";
    public static final String NEWTITLE = "title";
    private TextView tv_head_title;
    private ImageView iv_back_icon;
    private String url;
    private String title;
    private WebView dataWeb;

    @Override
    public void initView() {
        setContentView(R.layout.activity_news_details);
        this.tv_head_title = (TextView) findViewById(R.id.tv_main_title);
        this.iv_back_icon = (ImageView) findViewById(R.id.iv_back_icon);
        this.dataWeb = (WebView) findViewById(R.id.data_web);
        this.url = getIntent().getStringExtra(NEWSURL);
        this.title = getIntent().getStringExtra(NEWTITLE);
        this.tv_head_title.setText(title);
        this.iv_back_icon.setOnClickListener(view -> finish());
        dataWeb.setOnLongClickListener(view -> true);
        dataWeb.loadUrl(url);
        dataWeb.getSettings().setJavaScriptEnabled(true);
        dataWeb.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        dataWeb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能f
        dataWeb.getSettings().setDomStorageEnabled(true);
        dataWeb.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void fillView() {

    }
}
