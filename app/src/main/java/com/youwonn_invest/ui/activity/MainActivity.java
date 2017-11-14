package com.youwonn_invest.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.youwonn_invest.R;
import com.youwonn_invest.base.BaseApplication;
import com.youwonn_invest.ui.fragment.LiveFragment;
import com.youwonn_invest.ui.fragment.MallFragment;
import com.youwonn_invest.ui.fragment.MarketFragment;
import com.youwonn_invest.ui.fragment.NewsHomeFragment;
import com.youwonn_invest.utils.BackHandlerHelper;
import com.youwonn_invest.utils.HomeTab;
import com.youwonn_invest.utils.RxBarUtils;
import com.youwonn_invest.utils.UIHelper;
import com.youwonn_invest.widget.extent.HomeFragmentTabHost;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private LayoutInflater mInflater;
    private HomeFragmentTabHost mTabhost;

    private List<HomeTab> mTabs = new ArrayList<>(4);

    private long mExitTime;

    private ProgressDialog progressDialog;

    private BaseApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = (BaseApplication) getApplication(); //获取应用程序的baseapplication
        initTab();

        /**
         * 判断是不是启动app,如果是启动app则核对服务器版本,再判断是否更新
         */
        if (myApp.getVersion().equals("version_first")) {
            VersionUpdate();
            myApp.setVersion("version_second");//用户操作后改变这个全局值,下次启动app时才会判断版本
        }
    }

    public interface FragmentBackHandler {
        boolean onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if ((System.currentTimeMillis() - mExitTime) < 2000) {
                super.onBackPressed();

            } else {
                Object mHelperUtils;
                UIHelper.toastMessage(MainActivity.this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            }
        }
    }

    //判断是否是最新版本.
    private void VersionUpdate() {
//        String version = PackageInfoUtils.getPackageVersion(this);
//        RequestParams params = new RequestParams(URLS.APP_VERSION);
//        params.addBodyParameter("version", version);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Gson gson = new Gson();
//                UpdateVersionResponse response = gson.fromJson(result, UpdateVersionResponse.class);
//                if (response.getCode().equals("2")) {
////                    UIHelper.toastMessage(MainActivity.this,"已经是最新版本,无需升级");
//                } else if (response.getCode().equals("0")) {
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
//                    builder.setCancelable(false);
//                    builder.setTitle("升级提醒");
//                    builder.setMessage("发现新版本,请您升级");
//                    builder.setPositiveButton("立刻升级", (dialog, which) -> {
//                        progressDialog = new ProgressDialog(MainActivity.this);
//                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                        progressDialog.setCanceledOnTouchOutside(false);
//                        progressDialog.show();
//                        File sdDir = Environment.getExternalStorageDirectory();
//                        File file = new File(sdDir, SystemClock.uptimeMillis() + ".apk");
//                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                            RequestParams params1 = new RequestParams((String) response.getData().get(0));
//                            params1.setAutoResume(true);
//                            params1.setAutoRename(false);
//                            params1.setSaveFilePath(String.valueOf(file));
//                            params1.setCancelFast(true);
//                            x.http().get(params1, new ProgressCallback<File>() {
//                                @Override
//                                public void onWaiting() {
//
//                                }
//
//                                @Override
//                                public void onStarted() {
//
//                                }
//
//                                @Override
//                                public void onLoading(long total, long current, boolean isDownloading) {
////                                        progressDialog.setMessage("正在努力加载中........");
//                                    progressDialog.setMax((int) total);
//                                    progressDialog.setProgress((int) current);
//                                }
//
//                                @Override
//                                public void onSuccess(File result1) {
//                                    progressDialog.dismiss();
//                                    UIHelper.toastMessage(MainActivity.this, "下载成功");
////                                        覆盖安装的apk
//                                    Intent intent = new Intent();
//                                    intent.setAction("android.intent.action.VIEW");
//                                    intent.addCategory("android.intent.category.DEFAULT");
//                                    intent.setDataAndType(
//                                            Uri.fromFile(result1),
//                                            "application/vnd.android.package-archive");
//                                    startActivity(intent);
//                                }
//
//                                @Override
//                                public void onError(Throwable ex, boolean isOnCallback) {
//                                    ex.printStackTrace();
//                                    progressDialog.dismiss();
//                                    UIHelper.toastMessage(MainActivity.this, "下载失败");
//                                }
//
//                                @Override
//                                public void onCancelled(CancelledException cex) {
//
//                                }
//
//
//                                @Override
//                                public void onFinished() {
//
//                                }
//                            });
//                        } else {
//                            progressDialog.dismiss();
//                            UIHelper.toastMessage(MainActivity.this, "sd卡不可用,无法自动更新");
//                        }
//                    });
//                    builder.setNegativeButton("下次再说", (dialog, which) -> {
//
//                    });
//                    builder.show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }


    /**
     * 连续按两次返回键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                UIHelper.toastMessage(MainActivity.this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 初始化数据
     */
    private void initTab() {
        HomeTab tab_information = new HomeTab(NewsHomeFragment.class, R.string.tab_information, R.drawable.selector_icon_information);
        HomeTab tab_live = new HomeTab(LiveFragment.class, R.string.tab_live, R.drawable.selector_icon_live);
        HomeTab tab_market = new HomeTab(MarketFragment.class, R.string.tab_market, R.drawable.selector_icon_market);
        HomeTab tab_mall = new HomeTab(MallFragment.class, R.string.tab_mall, R.drawable.selector_icon_mall);
        mTabs.add(tab_information);
        mTabs.add(tab_live);
        mTabs.add(tab_market);
        mTabs.add(tab_mall);

        mInflater = LayoutInflater.from(this);
        mTabhost = (HomeFragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (HomeTab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);
    }

    private View buildIndicator(HomeTab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
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
