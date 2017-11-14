package com.youwonn_invest.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.youwonn_invest.R;
import com.youwonn_invest.base.BaseActivity;
import com.youwonn_invest.utils.PrefUtils;
import com.youwonn_invest.utils.RxBarUtils;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class SplashActivity extends BaseActivity {
    private ImageView iv_background;

    @Override
    public void initView() {
        RxBarUtils.hideStatusBar(this);
        setContentView(R.layout.activity_splash);
        iv_background = (ImageView) findViewById(R.id.iv_background);
    }

    @Override
    public void initData() {
        //缩放动画
        ScaleAnimation animScale = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animScale.setDuration(1000);
        animScale.setFillAfter(true);// 保持动画结束状态

        iv_background.startAnimation(animScale);

        animScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束,跳转页面
                //如果是第一次进入程序,则进入新手引导
                //否则跳转主界面
                boolean isFirstEnter = PrefUtils.getBoolean(
                        SplashActivity.this, "is_first_enter", true);

                Intent intent;
                if (isFirstEnter) {
                    //新手引导
                    intent = new Intent(getApplicationContext(), GuideActivity.class);
                    //初始化直播和新闻推送状态
                    SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("LIVE_STATE", "0");
                    editor.putString("NEWS_STATE", "1");
                    editor.commit();

                } else {
                    //主界面
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


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
