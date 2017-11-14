package com.youwonn_invest.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youwonn_invest.R;
import com.youwonn_invest.ui.activity.AboutActivity;
import com.youwonn_invest.utils.RxBarUtils;
import com.youwonn_invest.utils.UIHelper;
import com.youwonn_invest.widget.scollview.HorizontalScrollViewEx;

import java.lang.reflect.Field;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class NewsHomeFragment extends Fragment {
    private HorizontalScrollViewEx scrollViewEx;
    private DisplayMetrics dm;

    private InformationFragment informationFragment;
    private NewsFlashFragment newsFlashFragment;

    private View view;
    private ViewPager viewPager;
    private ImageView iv_user_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RxBarUtils.setStatusBarColor(getActivity(), R.color.color_178ef2);
        this.view = inflater.inflate(R.layout.fragment_news_home, null);
        this.setOverflowShowingAlways();
        this.dm = getResources().getDisplayMetrics();
        this.viewPager = (ViewPager) view.findViewById(R.id.pager);
        this.viewPager.setOffscreenPageLimit(13);
        this.scrollViewEx = (HorizontalScrollViewEx) view.findViewById(R.id.tabs);
        this.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        this.scrollViewEx.setViewPager(viewPager);
        this.iv_user_icon = (ImageView) view.findViewById(R.id.iv_user_icon);
        this.iv_user_icon.setOnClickListener(v -> UIHelper.startActivity(getActivity(), AboutActivity.class));
        setTabsValue();
        onPageChangeListener();
        return view;
    }

    private void onPageChangeListener() {
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 对HorizontalScrollViewEx的各项属性进行赋值。
     */
    private void setTabsValue() {
        this.scrollViewEx.setShouldExpand(true);
        this.scrollViewEx.setDividerColor(Color.TRANSPARENT);
        this.scrollViewEx.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 15, dm));
        this.scrollViewEx.setSelectedTextColor(Color.parseColor("#f23b17"));
        this.scrollViewEx.setTabBackground(0);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"头条", "快讯", "现货", "股票", "外汇", "理财", "基金", "白银",
                "黄金", "原油", "邮币卡", "私募", "美股"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (informationFragment == null) {
                    informationFragment = new InformationFragment();
                }
                return informationFragment;
            }
            if (position == 1) {
                if (newsFlashFragment == null) {
                    newsFlashFragment = new NewsFlashFragment();
                }
                return newsFlashFragment;
            }

            return NewsChannelFragment.newInstance(position);
        }

    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration
                    .get(getParentFragment().getActivity());
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
