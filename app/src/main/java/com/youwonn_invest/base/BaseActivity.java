package com.youwonn_invest.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.youwonn_invest.R;
import com.youwonn_invest.utils.RxBarUtils;

import butterknife.ButterKnife;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public abstract class BaseActivity extends Activity {
    public Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBarUtils.setStatusBarColor(this, R.color.color_178ef2);
        this.initView();
        this.initData();
        this.fillView();
        this.bindListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 点击退出当前activity
     */
    public View.OnClickListener mGoBack = v -> finish();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 加载数据
     */
    public abstract void initData();

    /**
     * 填充视图数据
     */
    public abstract void fillView();

    /**
     * 绑定事件
     */
    public void bindListener() {

    }

}
