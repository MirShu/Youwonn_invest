package com.youwonn_invest.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.RelativeLayout;
import com.umeng.analytics.MobclickAgent;
import com.youwonn_invest.R;
import com.youwonn_invest.base.BaseActivity;
import com.youwonn_invest.utils.PrefUtils;
import com.youwonn_invest.utils.RxBarUtils;

import java.util.ArrayList;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class GuideActivity extends BaseActivity {

    private ImageView point;
    private ViewPager mViewPager;
    private LinearLayout llContainer;
    private ImageView ivRedPoint;
    private TextView btStart;
    private ArrayList<ImageView> mImageViewList;
    private int[] mImageIds = new int[]{R.mipmap.guide_one, R.mipmap.guide_two, R.mipmap.guide_three
    };
    private int mPointDis;

    @Override
    public void initView() {
        RxBarUtils.hideStatusBar(this);
        setContentView(R.layout.activity_guide);
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        ivRedPoint = (ImageView) findViewById(R.id.iv_red_point);
        btStart = (TextView) findViewById(R.id.bt_start);

    }

    @Override
    public void initData() {
        initRecord();
        mViewPager.setAdapter(new GuideAdapter());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int leftMargin = (int) (mPointDis * positionOffset) + position
                        * mPointDis;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint
                        .getLayoutParams();
                params.leftMargin = leftMargin;
                ivRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    btStart.setVisibility(View.VISIBLE);
                    ivRedPoint.setVisibility(View.GONE);
                    llContainer.setVisibility(View.GONE);
                } else {
                    btStart.setVisibility(View.GONE);
                    ivRedPoint.setVisibility(View.VISIBLE);
                    llContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        ivRedPoint.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        mPointDis = llContainer.getChildAt(1).getLeft()
                                - llContainer.getChildAt(0).getLeft();
                    }
                });

        btStart.setOnClickListener(v -> {
            PrefUtils.setBoolean(getApplicationContext(), "is_first_enter", false);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

    }


    public void initRecord() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(view);
            point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i > 0) {
                params.leftMargin = 25;
            }
            point.setLayoutParams(params);
            llContainer.addView(point);
        }
    }

    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void fillView() {
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
