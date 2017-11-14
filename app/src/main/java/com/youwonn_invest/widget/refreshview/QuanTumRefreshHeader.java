package com.youwonn_invest.widget.refreshview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.youwonn_invest.R;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class QuanTumRefreshHeader extends LinearLayout implements IHeaderCallBack {

    private Context mContext;
    private ImageView iv_default;
    private ImageView iv_refresh;
    private TextView tv_refreshtext;
    private AnimationDrawable anim;
    private int[] arrayAnim = new int[]{R.mipmap.ic_anim_0000, R.mipmap.ic_anim_0001,
            R.mipmap.ic_anim_0002, R.mipmap.ic_anim_0003, R.mipmap.ic_anim_0004, R.mipmap.ic_anim_0005, R.mipmap.ic_anim_0006, R.mipmap.ic_anim_0007,
            R.mipmap.ic_anim_0008,
            R.mipmap.ic_anim_0009, R.mipmap.ic_anim_00010, R.mipmap.ic_anim_00011, R.mipmap.ic_anim_00012, R.mipmap.ic_anim_00013,
            R.mipmap.ic_anim_00014, R.mipmap.ic_anim_00015, R.mipmap.ic_anim_00016, R.mipmap.ic_anim_00017, R.mipmap.ic_anim_00018,
            R.mipmap.ic_anim_00019, R.mipmap.ic_anim_00020, R.mipmap.ic_anim_00021, R.mipmap.ic_anim_00022, R.mipmap.ic_anim_00023,
            R.mipmap.ic_anim_00024, R.mipmap.ic_anim_00025, R.mipmap.ic_anim_00026,};

    public QuanTumRefreshHeader(Context context) {
        super(context);
        this.mContext = context;
        this.initView();
    }

    private void initView() {
        LayoutInflater.from(this.mContext).inflate(R.layout.view_quantum_refresh_header, this);
        this.iv_default = (ImageView) this.findViewById(R.id.iv_default);
        this.iv_refresh = (ImageView) this.findViewById(R.id.iv_refresh);
        this.tv_refreshtext = (TextView) this.findViewById(R.id.tv_refreshtext);
        this.anim = (AnimationDrawable) this.iv_refresh.getBackground();
    }

    @Override
    public void onStateNormal() {
        this.iv_default.setVisibility(VISIBLE);
        this.iv_refresh.setVisibility(GONE);
        this.tv_refreshtext.setText("下拉刷新");
    }

    @Override
    public void onStateReady() {

    }

    @Override
    public void onStateRefreshing() {
        this.iv_default.setVisibility(GONE);
        this.iv_refresh.setVisibility(VISIBLE);
        this.tv_refreshtext.setText("正在刷新...");
        this.anim.start();
    }

    @Override
    public void onStateFinish() {
        this.anim.stop();
        this.tv_refreshtext.setText("刷新完成");
    }

    @Override
    public void onHeaderMove(double offset, int offsetY, int deltaY) {
        try {
            int index = (int) (((float) offsetY / (float) this.getHeaderHeight()) * 100) / 10;
            if (index < 0) {
                return;
            }
            if (index > 9) {
                index = 9;
            }
            this.iv_default.setImageResource(arrayAnim[index]);
        } catch (Exception ex) {
        }
    }

    @Override
    public void setRefreshTime(long lastRefreshTime) {

    }

    @Override
    public void hide() {
    }

    @Override
    public void show() {
        this.setVisibility(View.VISIBLE);
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }

}
