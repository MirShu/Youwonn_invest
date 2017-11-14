package com.youwonn_invest.widget.extent;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youwonn_invest.R;
import com.youwonn_invest.model.MarketViewRollModel;
import com.youwonn_invest.utils.DensityUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MarqueeView extends RecyclerView {
    Thread thread = null;
    AtomicBoolean shouldContinue = new AtomicBoolean(false);
    Handler mHandler;

    public MarqueeView(Context context) {
        super(context);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        //主线程的handler，用于执行Marquee的滚动消息
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1://不论是竖直滚动还是水平滚动，都是偏移5个像素
                        MarqueeView.this.scrollBy(DensityUtil.dip2px(getContext(), 1), DensityUtil.dip2px(getContext(), 1));
                        break;
                }
            }
        };
        if (thread == null) {
            thread = new Thread() {
                public void run() {
                    while (shouldContinue.get()) {
                        try {
                            Thread.sleep(18);//值不能开太小,太小低内存手机ANR
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        msg.sendToTarget();
                    }
                    //退出循环时清理handler
                    mHandler = null;
                }
            };
        }
    }

    /**
     * 开始滚动
     */
    public void startMarquee() {
        if (thread == null) {
            shouldContinue.set(true);
            init();
            thread.start();
        }
    }


    /**
     * 停止滚动
     */
    public void stopMarquee() {
        if (thread != null) {
            shouldContinue.set(false);
            thread = null;
        }
    }

    /**
     * Adapter类
     */
    public static class InnerAdapter extends Adapter<InnerAdapter.MarqueHolder> {
        private List<MarketViewRollModel> mData;
        private int size;
        private LayoutInflater mLayoutInflater;

        public InnerAdapter(List<MarketViewRollModel> data, Context context) {
            mData = data;
            size = mData.size();
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public MarqueHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = mLayoutInflater.inflate(R.layout.item_market, parent, false);

            return new MarqueHolder(view);
        }

        @Override
        public void onBindViewHolder(MarqueHolder holder, int position) {
            try {
                holder.tv_market_float.setText(mData.get(position % size).getMarket_float());
                holder.tv_market_price.setText(mData.get(position % size).getMarket_price());
                holder.tv_market_name.setText(mData.get(position % size).getMarket_name());
                if (String.valueOf(mData.get(position % size).getMarket_float().charAt(0)).equals("-")) {
                    holder.tv_market_float.setTextColor(Color.parseColor("#019f53"));
                    holder.tv_market_price.setTextColor(Color.parseColor("#019f53"));
                } else {
                    holder.tv_market_float.setTextColor(Color.parseColor("#fd040a"));
                    holder.tv_market_price.setTextColor(Color.parseColor("#fd040a"));
                }
            } catch (Exception e) {

            }

        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * * ViewHolder类
         **/
        static class MarqueHolder extends ViewHolder {
            TextView tv_market_name;
            TextView tv_market_price;
            TextView tv_market_float;

            public MarqueHolder(View view) {
                super(view);
                try {
                    tv_market_name = (TextView) view.findViewById(R.id.tv_market_name);
                    tv_market_price = (TextView) view.findViewById(R.id.tv_market_price);
                    tv_market_float = (TextView) view.findViewById(R.id.tv_market_float);
                } catch (Exception e) {

                }

            }

        }
    }
}
