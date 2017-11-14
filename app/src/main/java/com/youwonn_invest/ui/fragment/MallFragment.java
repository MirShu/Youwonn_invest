package com.youwonn_invest.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.youwonn_invest.R;
import com.youwonn_invest.constant.URLS;
import com.youwonn_invest.model.MarketViewRollModel;
import com.youwonn_invest.model.MessageResult;
import com.youwonn_invest.ui.activity.MainActivity;
import com.youwonn_invest.utils.NetUtil;
import com.youwonn_invest.utils.UIHelper;
import com.youwonn_invest.widget.extent.MarketWebView;
import com.youwonn_invest.widget.loding.LodingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MallFragment extends Fragment implements MainActivity.FragmentBackHandler {
    private View rootView;
    private LodingDialog lodingDialog;
    private WebView market_WebView;
    private RelativeLayout rl_empty;
    private ImageView iv_back_icon;
    private String webviewurl;
    private List<MarketViewRollModel> listMarket = new ArrayList<>();
    private TextView main_tv_title;
    private RelativeLayout rl_head;
    private WebSettings wvSettings;
    private String menuUrls;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_mall, container, false);
        this.market_WebView = (MarketWebView) rootView.findViewById(R.id.market_WebView);
        this.rl_empty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        this.main_tv_title = (TextView) rootView.findViewById(R.id.tv_main_title);
        this.iv_back_icon = (ImageView) rootView.findViewById(R.id.iv_back_icon);
        this.rl_head = (RelativeLayout) rootView.findViewById(R.id.rl_head);
        this.iv_back_icon.setVisibility(View.GONE);
        if (!NetUtil.isNetworkConnected(getActivity())) {
            rl_empty.setVisibility(View.VISIBLE);
            rl_empty.setOnClickListener(v -> {
                getUrlVoid();
                showWebViewData();
            });
        } else {
            this.getUrlVoid();
            this.showWebViewData();
        }

        return rootView;
    }

    private void getUrlVoid() {
        RequestParams params = new RequestParams(URLS.GET_RESEARCH_REPORT_URL);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<MarketViewRollModel> marketList = JSON.parseArray(message.getData(),
                        MarketViewRollModel.class);
                listMarket.addAll(marketList);
                webviewurl = listMarket.get(0).getUrl();
                market_WebView.loadUrl(webviewurl);
                rl_empty.setVisibility(View.GONE);
                new Handler().postDelayed(() -> lodingDialog.dismiss(), 1000);
                menuUrls = listMarket.get(0).getUrl();

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
     * 显示行情与隐藏行情上面title标题
     */
    private void showWebViewData() {
        this.market_WebView.setVisibility(View.INVISIBLE);
        lodingDialog = new LodingDialog(getActivity(), "加载中...");
        lodingDialog.show();
        wvSettings = market_WebView.getSettings();
        wvSettings.setJavaScriptEnabled(true);
        wvSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvSettings.setAppCacheEnabled(true);
        wvSettings.setDomStorageEnabled(true);
        wvSettings.setDatabaseEnabled(true);
        wvSettings.setAllowFileAccess(true);
        wvSettings.setUseWideViewPort(true);
        wvSettings.setLoadWithOverviewMode(true);


        wvSettings.setBlockNetworkImage(true);
        wvSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        wvSettings.setDomStorageEnabled(true);
        market_WebView.setOnLongClickListener(view -> true);
        this.market_WebView.setWebViewClient(new marketWebViewClient());
        iv_back_icon.setOnClickListener(v -> {
            market_WebView.goBack();
            market_WebView.setVisibility(View.GONE);
            new Handler().postDelayed(() -> {
                market_WebView.setVisibility(View.VISIBLE);
            }, 1000);
        });

        /**
         * 定时执行
         */
        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /**
         * WebView 控件webViewClient 所有回调方法
         */
        market_WebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (menuUrls.indexOf(url) != -1) {
                    iv_back_icon.setVisibility(View.GONE);
                } else {
                    iv_back_icon.setVisibility(View.VISIBLE);
                }
                market_WebView.setVisibility(View.GONE);
                lodingDialog.show();

            }

            /**
             * 加载给定URL资源内容   该方法待使用    判断webview是否可以返回,不能返回就隐藏返回按钮
             */
            @Override
            public void onLoadResource(final WebView view, String url) {
//                view.loadUrl(jquery);
                super.onLoadResource(view, url);
//                if (market_WebView.canGoBack()) {
//                    iv_back_icon.setVisibility(View.VISIBLE);
//                    market_WebView.setVisibility(View.GONE);
//                    new Handler().postDelayed(() -> {
//                        market_WebView.setVisibility(View.VISIBLE);
//                    }, 1000);
//                } else {
//                    iv_back_icon.setVisibility(View.GONE);
//                    market_WebView.setVisibility(View.VISIBLE);
//                }
            }

            /**
             * 页面加载完成回调方法，获取对应url地址
             * */
            @Override
            public void onPageFinished(final WebView view, String url) {

                new Handler().postDelayed(() -> {
                    market_WebView.setVisibility(View.VISIBLE);
                    rl_empty.setVisibility(View.GONE);
                    lodingDialog.dismiss();
                }, 100);
//                market_WebView.setVisibility(View.VISIBLE);
                wvSettings.setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }

            /**
             * 更新浏览历史记录，获取更新的url地址
             * */
            /*@Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }*/


            /**
             * 加载给定URL资源内容
             */
            /*@Override
            public void onLoadResource(final WebView view, String url) {
                super.onLoadResource(view, url);
            }*/

            /**
             * 正在加载HTTP响应的body内容，回调该方法，获取对应url地址
             */
           /* @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                market_WebView.setVisibility(View.VISIBLE);

            }*/

            /**
             * 回调该方法，处理SSL认证请求
             */
            /*@Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }*/

            /**
             * 回调该方法，处理HTTP认证错误
             */
            /*public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }*/


            /**
             * 回调该方法，请求已授权用户自动登录
             */
            /*public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
            }*/

            /**
             * 回调该方法，处理SSL认证错误
             */
           /* public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }*/


            /**
             * 页面大小改变后回调该方法，获取缩放前后大小
             */
           /* public void onScaleChanged(final WebView view, float oldScale, float newScale) {

                super.onScaleChanged(view, oldScale, newScale);
            }*/

            /**
             * 回调该方法，处理未被WebView处理的事件
             */
           /* public void onUnhandledKeyEvent(WebView view, KeyEvent event) {

                super.onUnhandledKeyEvent(view, event);
            }*/

            /**
             * 自己定义加载错误处理效果，比如：TeachCourse定义在没有网络时候，显示一张无网络的图片
             * API 23 之后调用
             */
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.setVisibility(View.GONE);
                rl_empty.setVisibility(View.VISIBLE);
                super.onReceivedError(view, request, error);

            }

            /**
             * 自己定义加载错误处理效果，比如：TeachCourse定义在没有网络时候，显示一张无网络的图片
             *API 23之前调用
             */
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.setVisibility(View.GONE);
                rl_empty.setVisibility(View.VISIBLE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


            /**
             * ebView加载H5界面，点击按钮发送短信或拨打电话
             */
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("http:") || url.startsWith("https:")) {
//                }
//                if (url.startsWith("sms:")) {
//                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
//                    view.getContext().startActivity(intent);
//                }
                return false;
            }
        });


    }


    public class MThread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    Thread.sleep(1500);
                    Message message = new Message();
                    message.what = 1;
//                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    //利用工具类判断fragment的手机返回键
    @Override
    public boolean onBackPressed() {
//        if (market_WebView.canGoBack()) {
//            market_WebView.goBack();
//            return true;
//        }
        try {
            if (menuUrls.indexOf(market_WebView.getUrl()) == -1) {
                market_WebView.goBack();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private class marketWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                UIHelper.toastMessage(getActivity(), "000000");
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
        }
    }
}
