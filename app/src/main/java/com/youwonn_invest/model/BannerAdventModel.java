package com.youwonn_invest.model;

import java.util.List;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class BannerAdventModel {

    /**
     * code : 0
     * data : [{"title":"首页1","bewrite":"描述首页1","location":"www.17173.com","img_src":"http://k.sinaimg.cn/n/finance/20151113/xn6u-fxksqiv8319733.jpg/w120h90l50t1b90.jpg"},{"title":"首页","bewrite":"描述首页","location":"www.baidu.com","img_src":"http://k.sinaimg.cn/n/finance/20151113/xn6u-fxksqiv8319733.jpg/w120h90l50t1b90.jpg"}]
     * msg : 请求成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 首页1
         * bewrite : 描述首页1
         * location : www.17173.com
         * img_src : http://k.sinaimg.cn/n/finance/20151113/xn6u-fxksqiv8319733.jpg/w120h90l50t1b90.jpg
         * "news_product_id": "1"
         */

        private String title;
        private String bewrite;
        private String location;
        private String img_src;
        private String news_product_id;
        private String news_tips;
        private String relevant_type;

        public String getNews_tips() {
            return news_tips;
        }

        public void setNews_tips(String news_tips) {
            this.news_tips = news_tips;
        }

        public String getRelevant_type() {
            return relevant_type;
        }

        public void setRelevant_type(String relevant_type) {
            this.relevant_type = relevant_type;
        }

        public String getNews_product_id() {
            return news_product_id;
        }

        public void setNews_product_id(String news_product_id) {
            this.news_product_id = news_product_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBewrite() {
            return bewrite;
        }

        public void setBewrite(String bewrite) {
            this.bewrite = bewrite;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getImg_src() {
            return img_src;
        }

        public void setImg_src(String img_src) {
            this.img_src = img_src;
        }
    }
}
