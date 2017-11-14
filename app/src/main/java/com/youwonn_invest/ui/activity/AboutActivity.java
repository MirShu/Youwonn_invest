package com.youwonn_invest.ui.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.umeng.analytics.MobclickAgent;
import com.youwonn_invest.R;
import com.youwonn_invest.base.BaseActivity;
import com.youwonn_invest.constant.URLS;
import com.youwonn_invest.model.MessageResult;
import com.youwonn_invest.model.UsModel;
import com.youwonn_invest.utils.NetUtil;
import com.youwonn_invest.widget.scollview.ReboundScrollView;

import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class AboutActivity extends BaseActivity {
    private TextView tv_head_title;
    private ImageView iv_back_icon;
    private RelativeLayout rl_empty;
    private ReboundScrollView rl_main_viwe;
    private WebView wv_about;

    @Override
    public void initView() {
        setContentView(R.layout.activity_about);
        this.tv_head_title = (TextView) findViewById(R.id.tv_main_title);
        this.iv_back_icon = (ImageView) findViewById(R.id.iv_back_icon);
        this.rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        this.rl_main_viwe = (ReboundScrollView) findViewById(R.id.rl_main_viwe);
        this.wv_about = (WebView) findViewById(R.id.wv_about);
        this.tv_head_title.setText(R.string.tv_about);
        this.iv_back_icon.setOnClickListener(view -> finish());
        WebSettings wvSettings = wv_about.getSettings();
        wvSettings.setJavaScriptEnabled(true);
        wvSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvSettings.setAppCacheEnabled(true);
        wvSettings.setDomStorageEnabled(true);
        wvSettings.setDatabaseEnabled(true);
        wvSettings.setAllowFileAccess(true);
        wv_about.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wv_about.getSettings().setDomStorageEnabled(true);
        wv_about.setOnLongClickListener(view -> true);


    }

    @Override
    public void initData() {
        RequestParams requestParams = new RequestParams(URLS.GET_ABOUTSUS);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        MessageResult message = MessageResult.parse(result);
                        UsModel usModel = JSON.parseObject(message.getData(), UsModel.class);
                        String newsContent = "<html><head><style type=\"text/css\">body " +
                                "{text-align:justify; font-size:px; line-height: " + (30) + "px;color:#666666}</style></head> \n" +
                                "<body>" + EncodingUtils.getString(usModel.getAbout_us().getBytes(), "UTF-8") + "</body> \n </html>";
                        wv_about.loadDataWithBaseURL(null, getNewContent(newsContent),
                                "text/html", "utf-8", null);
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
                }
        );
    }

    /******************************************************
     * 通过第三方jar包修改HTML 代码块中IMG图片自适应手机
     *****************************************************/
    private String getNewContent(String htmltext) {
        org.jsoup.nodes.Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (org.jsoup.nodes.Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }


    @Override
    public void fillView() {
        if (!NetUtil.isNetworkConnected(AboutActivity.this)) {
            rl_empty.setVisibility(View.VISIBLE);
            rl_main_viwe.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计
    }
}
