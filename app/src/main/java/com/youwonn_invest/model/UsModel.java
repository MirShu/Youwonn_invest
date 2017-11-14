package com.youwonn_invest.model;

import java.io.Serializable;

/**
 * Created by ShuLin on 2017/8/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class UsModel implements Serializable {
    private String about_us;
    private String contact_us;
    private String agreement;
    private String login_type;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getContact_us() {
        return contact_us;
    }

    public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

}
