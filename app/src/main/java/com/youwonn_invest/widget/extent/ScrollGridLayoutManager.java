package com.youwonn_invest.widget.extent;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by ShuLin on 2017/11/10.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class ScrollGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;
    public ScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }

}
