package com.youwonn_invest.widget.extent;

/**
 * Created by ShuLin on 2017/8/25.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class BannerItem {
    private String title;
    private String image;
    private String bewrite;

    public BannerItem() {

    }


    public BannerItem(String title, String bewrite, String image) {
        this.title = title;
        this.image = image;
        this.bewrite = bewrite;
    }

    public String getBewrite() {
        return bewrite;
    }

    public void setBewrite(String bewrite) {
        this.bewrite = bewrite;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
