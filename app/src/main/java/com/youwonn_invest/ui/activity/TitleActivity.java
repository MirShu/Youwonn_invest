package com.youwonn_invest.ui.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.youwonn_invest.R;
import com.youwonn_invest.base.BaseActivity;

/**
 * Created by ShuLin on 2017/11/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class TitleActivity extends BaseActivity {
    private TextView tv_head_title;
    private ImageView iv_back_icon;
    public static final String NEWSURL = "news_url";
    private String url;
    private WebView newsWeb;

    @Override
    public void initView() {
        setContentView(R.layout.activity_title);
        this.url = getIntent().getStringExtra(NEWSURL);
        this.tv_head_title = (TextView) findViewById(R.id.tv_main_title);
        this.iv_back_icon = (ImageView) findViewById(R.id.iv_back_icon);
        this.newsWeb= (WebView) findViewById(R.id.news_web);
        this.tv_head_title.setText("资讯");
        this.iv_back_icon.setOnClickListener(view -> finish());
        newsWeb.setOnLongClickListener(view -> true);
        newsWeb.loadUrl(url);
        newsWeb.getSettings().setJavaScriptEnabled(true);
        newsWeb.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        newsWeb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        newsWeb.getSettings().setDomStorageEnabled(true);
        newsWeb.setWebViewClient(new WebViewClient(){
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
