package com.youwonn_invest.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.youwonn_invest.R;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MyBGARefreshViewHolder extends BGARefreshViewHolder {
    private TextView mHeaderStatusTv;
    private ImageView mHeaderArrowIv;
    private ImageView mHeaderChrysanthemumIv;
    private AnimationDrawable mHeaderChrysanthemumAd;
    private RotateAnimation mUpAnim;
    private RotateAnimation mDownAnim;

    private String mPullDownRefreshText = "下拉刷新";
    private String mReleaseRefreshText = "释放更新";
    private String mRefreshingText = "加载中...";
    private int[] arrayAnim = new int[]{R.mipmap.ic_anim_0000, R.mipmap.ic_anim_0001,
            R.mipmap.ic_anim_0002, R.mipmap.ic_anim_0003, R.mipmap.ic_anim_0004, R.mipmap.ic_anim_0005, R.mipmap.ic_anim_0006, R.mipmap.ic_anim_0007,
            R.mipmap.ic_anim_0008,
            R.mipmap.ic_anim_0009, R.mipmap.ic_anim_00010, R.mipmap.ic_anim_00011, R.mipmap.ic_anim_00012, R.mipmap.ic_anim_00013,
            R.mipmap.ic_anim_00014, R.mipmap.ic_anim_00015, R.mipmap.ic_anim_00016, R.mipmap.ic_anim_00017, R.mipmap.ic_anim_00018,
            R.mipmap.ic_anim_00019, R.mipmap.ic_anim_00020, R.mipmap.ic_anim_00021, R.mipmap.ic_anim_00022, R.mipmap.ic_anim_00023,
            R.mipmap.ic_anim_00024, R.mipmap.ic_anim_00025, R.mipmap.ic_anim_00026,};

    /**
     * 是否开启加载更多功能
     */
    private boolean mIsLoadingMoreEnabled = true;

    /**
     * 整个加载更多控件的背景颜色资源id
     */
    private int mLoadMoreBackgroundColorRes = -1;

    /**
     * 整个加载更多控件的背景drawable资源id
     */
    private int mLoadMoreBackgroundDrawableRes = -1;

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public MyBGARefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
//        initAnimation();
    }




    private void initAnimation() {
        mUpAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUpAnim.setDuration(150);
        mUpAnim.setFillAfter(true);

        mDownAnim = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDownAnim.setFillAfter(true);
    }

    /**
     * 设置未满足刷新条件，提示继续往下拉的文本
     *
     * @param pullDownRefreshText
     */
    public void setPullDownRefreshText(String pullDownRefreshText) {
        mPullDownRefreshText = pullDownRefreshText;
    }

    /**
     * 设置满足刷新条件时的文本
     *
     * @param releaseRefreshText
     */
    public void setReleaseRefreshText(String releaseRefreshText) {
        mReleaseRefreshText = releaseRefreshText;
    }

    /**
     * 设置正在刷新时的文本
     *
     * @param refreshingText
     */
    public void setRefreshingText(String refreshingText) {
        mRefreshingText = refreshingText;
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_news_refresh_header, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mHeaderStatusTv = (TextView) mRefreshHeaderView.findViewById(R.id.tv_refreshtext);
            mHeaderArrowIv = (ImageView) mRefreshHeaderView.findViewById(R.id.iv_default);
            mHeaderChrysanthemumIv = (ImageView) mRefreshHeaderView.findViewById(R.id.iv_refresh);
            mHeaderChrysanthemumAd = (AnimationDrawable) mHeaderChrysanthemumIv.getDrawable();
            mHeaderStatusTv.setText(mPullDownRefreshText);
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {

        int index = (int) (((float) scale / (float)1) *arrayAnim.length);

        if (index < 0) {
            return;
        }
        if (index > arrayAnim.length-1) {
            index = arrayAnim.length-1;
        }
        Log.e("-----------",index+"");
        this.mHeaderArrowIv.setImageResource(arrayAnim[index]);
    }

    @Override
    public void changeToIdle() {
    }

    @Override
    public void changeToPullDown() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
//        mDownAnim.setDuration(150);
//        mHeaderArrowIv.startAnimation(mDownAnim);
    }

    @Override
    public void changeToReleaseRefresh() {
        mHeaderStatusTv.setText(mReleaseRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
//        mHeaderArrowIv.startAnimation(mUpAnim);
    }

    @Override
    public void changeToRefreshing() {
        mHeaderStatusTv.setText(mRefreshingText);
        // 必须把动画清空才能隐藏成功
        mHeaderArrowIv.clearAnimation();
        mHeaderArrowIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumIv.setVisibility(View.VISIBLE);
        mHeaderChrysanthemumAd.start();
    }

    @Override
    public void onEndRefreshing() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
//        mDownAnim.setDuration(0);
//        mHeaderArrowIv.startAnimation(mDownAnim);
    }

    @Override
    public View getLoadMoreFooterView() {

        if (!mIsLoadingMoreEnabled) {
            return null;
        }
        if (mLoadMoreFooterView == null) {
            mLoadMoreFooterView = View.inflate(mContext, R.layout.view_news_refresh_footer, null);
            mLoadMoreFooterView.setBackgroundColor(Color.TRANSPARENT);
            if (mLoadMoreBackgroundColorRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundColorRes);
            }
            if (mLoadMoreBackgroundDrawableRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundDrawableRes);
            }
            mFooterStatusTv = (TextView) mLoadMoreFooterView.findViewById(R.id.tv_normal_refresh_footer_status);
            mFooterChrysanthemumIv = (ImageView) mLoadMoreFooterView.findViewById(R.id.iv_normal_refresh_footer_chrysanthemum);
            mFooterChrysanthemumAd = (AnimationDrawable) mFooterChrysanthemumIv.getDrawable();
//            mFooterStatusTv.setText(mLodingMoreText);
        }

        return super.getLoadMoreFooterView();
    }
}
