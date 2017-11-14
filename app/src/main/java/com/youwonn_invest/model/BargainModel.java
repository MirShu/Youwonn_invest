package com.youwonn_invest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ShuLin on 2017/11/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class BargainModel {


    /**
     * resultcode : 200
     * reason : SUCCESSED!
     * result : [{"1":{"code":"601318","name":"中国平安","fundnum":"723","total":"68,151","change":"-7,667","totalcap":"3691030.25","accrate":"3.73","changesta":"-0.42","time":"20170930"},"2":{"code":"600519","name":"贵州茅台","fundnum":"568","total":"4,563","change":"-63","totalcap":"2362274.57","accrate":"3.63","changesta":"-0.05","time":"20170930"},"3":{"code":"600887","name":"伊利股份","fundnum":"495","total":"83,119","change":"980","totalcap":"2285772.51","accrate":"13.78","changesta":"+0.17","time":"20170930"},"4":{"code":"000858","name":"五粮液","fundnum":"468","total":"34,562","change":"-2,792","totalcap":"1979746.35","accrate":"9.11","changesta":"-0.73","time":"20170930"},"5":{"code":"600036","name":"招商银行","fundnum":"424","total":"74,742","change":"-11,544","totalcap":"1909619.38","accrate":"2.96","changesta":"-0.46","time":"20170930"},"6":{"code":"000651","name":"格力电器","fundnum":"423","total":"42,223","change":"-21,083","totalcap":"1600258.1","accrate":"7.07","changesta":"-3.53","time":"20170930"},"7":{"code":"601398","name":"工商银行","fundnum":"406","total":"143,273","change":"-39,238","totalcap":"859503.1","accrate":"0.40","changesta":"-0.11","time":"20170930"},"8":{"code":"601288","name":"农业银行","fundnum":"369","total":"233,945","change":"-57,146","totalcap":"893670.19","accrate":"0.72","changesta":"-0.18","time":"20170930"},"9":{"code":"601166","name":"兴业银行","fundnum":"324","total":"65,732","change":"-11,547","totalcap":"1136519.32","accrate":"3.45","changesta":"-0.61","time":"20170930"},"10":{"code":"000333","name":"美的集团","fundnum":"300","total":"33,836","change":"-8,726","totalcap":"1495214.82","accrate":"5.36","changesta":"-1.39","time":"20170930"},"11":{"code":"000063","name":"中兴通讯","fundnum":"270","total":"26,276","change":"-7,914","totalcap":"743628.14","accrate":"6.28","changesta":"-1.89","time":"20170930"},"12":{"code":"000568","name":"泸州老窖","fundnum":"265","total":"20,639","change":"-4,086","totalcap":"1149082.02","accrate":"14.72","changesta":"-2.92","time":"20170930"},"13":{"code":"601601","name":"中国太保","fundnum":"256","total":"30,959","change":"-10,967","totalcap":"1143325.69","accrate":"3.42","changesta":"-1.21","time":"20170930"},"14":{"code":"000001","name":"平安银行","fundnum":"243","total":"53,488","change":"-9,796","totalcap":"594261.45","accrate":"3.16","changesta":"-0.58","time":"20170930"},"15":{"code":"601328","name":"交通银行","fundnum":"240","total":"118,136","change":"-25,836","totalcap":"746624.52","accrate":"1.59","changesta":"-0.35","time":"20170930"},"16":{"code":"601939","name":"建设银行","fundnum":"235","total":"55,338","change":"-23,105","totalcap":"385706.55","accrate":"0.22","changesta":"-0.09","time":"20170930"},"17":{"code":"601988","name":"中国银行","fundnum":"231","total":"91,430","change":"-36,262","totalcap":"376692.96","accrate":"0.31","changesta":"-0.12","time":"20170930"}}]
     */

    private String resultcode;
    private String reason;
    private List<ResultBean> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * 1 : {"code":"601318","name":"中国平安","fundnum":"723","total":"68,151","change":"-7,667","totalcap":"3691030.25","accrate":"3.73","changesta":"-0.42","time":"20170930"}
         * 2 : {"code":"600519","name":"贵州茅台","fundnum":"568","total":"4,563","change":"-63","totalcap":"2362274.57","accrate":"3.63","changesta":"-0.05","time":"20170930"}
         * 3 : {"code":"600887","name":"伊利股份","fundnum":"495","total":"83,119","change":"980","totalcap":"2285772.51","accrate":"13.78","changesta":"+0.17","time":"20170930"}
         * 4 : {"code":"000858","name":"五粮液","fundnum":"468","total":"34,562","change":"-2,792","totalcap":"1979746.35","accrate":"9.11","changesta":"-0.73","time":"20170930"}
         * 5 : {"code":"600036","name":"招商银行","fundnum":"424","total":"74,742","change":"-11,544","totalcap":"1909619.38","accrate":"2.96","changesta":"-0.46","time":"20170930"}
         * 6 : {"code":"000651","name":"格力电器","fundnum":"423","total":"42,223","change":"-21,083","totalcap":"1600258.1","accrate":"7.07","changesta":"-3.53","time":"20170930"}
         * 7 : {"code":"601398","name":"工商银行","fundnum":"406","total":"143,273","change":"-39,238","totalcap":"859503.1","accrate":"0.40","changesta":"-0.11","time":"20170930"}
         * 8 : {"code":"601288","name":"农业银行","fundnum":"369","total":"233,945","change":"-57,146","totalcap":"893670.19","accrate":"0.72","changesta":"-0.18","time":"20170930"}
         * 9 : {"code":"601166","name":"兴业银行","fundnum":"324","total":"65,732","change":"-11,547","totalcap":"1136519.32","accrate":"3.45","changesta":"-0.61","time":"20170930"}
         * 10 : {"code":"000333","name":"美的集团","fundnum":"300","total":"33,836","change":"-8,726","totalcap":"1495214.82","accrate":"5.36","changesta":"-1.39","time":"20170930"}
         * 11 : {"code":"000063","name":"中兴通讯","fundnum":"270","total":"26,276","change":"-7,914","totalcap":"743628.14","accrate":"6.28","changesta":"-1.89","time":"20170930"}
         * 12 : {"code":"000568","name":"泸州老窖","fundnum":"265","total":"20,639","change":"-4,086","totalcap":"1149082.02","accrate":"14.72","changesta":"-2.92","time":"20170930"}
         * 13 : {"code":"601601","name":"中国太保","fundnum":"256","total":"30,959","change":"-10,967","totalcap":"1143325.69","accrate":"3.42","changesta":"-1.21","time":"20170930"}
         * 14 : {"code":"000001","name":"平安银行","fundnum":"243","total":"53,488","change":"-9,796","totalcap":"594261.45","accrate":"3.16","changesta":"-0.58","time":"20170930"}
         * 15 : {"code":"601328","name":"交通银行","fundnum":"240","total":"118,136","change":"-25,836","totalcap":"746624.52","accrate":"1.59","changesta":"-0.35","time":"20170930"}
         * 16 : {"code":"601939","name":"建设银行","fundnum":"235","total":"55,338","change":"-23,105","totalcap":"385706.55","accrate":"0.22","changesta":"-0.09","time":"20170930"}
         * 17 : {"code":"601988","name":"中国银行","fundnum":"231","total":"91,430","change":"-36,262","totalcap":"376692.96","accrate":"0.31","changesta":"-0.12","time":"20170930"}
         */

        @SerializedName("1")
        private _$1Bean _$1;
        @SerializedName("2")
        private _$2Bean _$2;
        @SerializedName("3")
        private _$3Bean _$3;
        @SerializedName("4")
        private _$4Bean _$4;
        @SerializedName("5")
        private _$5Bean _$5;
        @SerializedName("6")
        private _$6Bean _$6;
        @SerializedName("7")
        private _$7Bean _$7;
        @SerializedName("8")
        private _$8Bean _$8;
        @SerializedName("9")
        private _$9Bean _$9;
        @SerializedName("10")
        private _$10Bean _$10;
        @SerializedName("11")
        private _$11Bean _$11;
        @SerializedName("12")
        private _$12Bean _$12;
        @SerializedName("13")
        private _$13Bean _$13;
        @SerializedName("14")
        private _$14Bean _$14;
        @SerializedName("15")
        private _$15Bean _$15;
        @SerializedName("16")
        private _$16Bean _$16;
        @SerializedName("17")
        private _$17Bean _$17;

        public _$1Bean get_$1() {
            return _$1;
        }

        public void set_$1(_$1Bean _$1) {
            this._$1 = _$1;
        }

        public _$2Bean get_$2() {
            return _$2;
        }

        public void set_$2(_$2Bean _$2) {
            this._$2 = _$2;
        }

        public _$3Bean get_$3() {
            return _$3;
        }

        public void set_$3(_$3Bean _$3) {
            this._$3 = _$3;
        }

        public _$4Bean get_$4() {
            return _$4;
        }

        public void set_$4(_$4Bean _$4) {
            this._$4 = _$4;
        }

        public _$5Bean get_$5() {
            return _$5;
        }

        public void set_$5(_$5Bean _$5) {
            this._$5 = _$5;
        }

        public _$6Bean get_$6() {
            return _$6;
        }

        public void set_$6(_$6Bean _$6) {
            this._$6 = _$6;
        }

        public _$7Bean get_$7() {
            return _$7;
        }

        public void set_$7(_$7Bean _$7) {
            this._$7 = _$7;
        }

        public _$8Bean get_$8() {
            return _$8;
        }

        public void set_$8(_$8Bean _$8) {
            this._$8 = _$8;
        }

        public _$9Bean get_$9() {
            return _$9;
        }

        public void set_$9(_$9Bean _$9) {
            this._$9 = _$9;
        }

        public _$10Bean get_$10() {
            return _$10;
        }

        public void set_$10(_$10Bean _$10) {
            this._$10 = _$10;
        }

        public _$11Bean get_$11() {
            return _$11;
        }

        public void set_$11(_$11Bean _$11) {
            this._$11 = _$11;
        }

        public _$12Bean get_$12() {
            return _$12;
        }

        public void set_$12(_$12Bean _$12) {
            this._$12 = _$12;
        }

        public _$13Bean get_$13() {
            return _$13;
        }

        public void set_$13(_$13Bean _$13) {
            this._$13 = _$13;
        }

        public _$14Bean get_$14() {
            return _$14;
        }

        public void set_$14(_$14Bean _$14) {
            this._$14 = _$14;
        }

        public _$15Bean get_$15() {
            return _$15;
        }

        public void set_$15(_$15Bean _$15) {
            this._$15 = _$15;
        }

        public _$16Bean get_$16() {
            return _$16;
        }

        public void set_$16(_$16Bean _$16) {
            this._$16 = _$16;
        }

        public _$17Bean get_$17() {
            return _$17;
        }

        public void set_$17(_$17Bean _$17) {
            this._$17 = _$17;
        }

        public static class _$1Bean {
            /**
             * code : 601318
             * name : 中国平安
             * fundnum : 723
             * total : 68,151
             * change : -7,667
             * totalcap : 3691030.25
             * accrate : 3.73
             * changesta : -0.42
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$2Bean {
            /**
             * code : 600519
             * name : 贵州茅台
             * fundnum : 568
             * total : 4,563
             * change : -63
             * totalcap : 2362274.57
             * accrate : 3.63
             * changesta : -0.05
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$3Bean {
            /**
             * code : 600887
             * name : 伊利股份
             * fundnum : 495
             * total : 83,119
             * change : 980
             * totalcap : 2285772.51
             * accrate : 13.78
             * changesta : +0.17
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$4Bean {
            /**
             * code : 000858
             * name : 五粮液
             * fundnum : 468
             * total : 34,562
             * change : -2,792
             * totalcap : 1979746.35
             * accrate : 9.11
             * changesta : -0.73
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$5Bean {
            /**
             * code : 600036
             * name : 招商银行
             * fundnum : 424
             * total : 74,742
             * change : -11,544
             * totalcap : 1909619.38
             * accrate : 2.96
             * changesta : -0.46
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$6Bean {
            /**
             * code : 000651
             * name : 格力电器
             * fundnum : 423
             * total : 42,223
             * change : -21,083
             * totalcap : 1600258.1
             * accrate : 7.07
             * changesta : -3.53
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$7Bean {
            /**
             * code : 601398
             * name : 工商银行
             * fundnum : 406
             * total : 143,273
             * change : -39,238
             * totalcap : 859503.1
             * accrate : 0.40
             * changesta : -0.11
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$8Bean {
            /**
             * code : 601288
             * name : 农业银行
             * fundnum : 369
             * total : 233,945
             * change : -57,146
             * totalcap : 893670.19
             * accrate : 0.72
             * changesta : -0.18
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$9Bean {
            /**
             * code : 601166
             * name : 兴业银行
             * fundnum : 324
             * total : 65,732
             * change : -11,547
             * totalcap : 1136519.32
             * accrate : 3.45
             * changesta : -0.61
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$10Bean {
            /**
             * code : 000333
             * name : 美的集团
             * fundnum : 300
             * total : 33,836
             * change : -8,726
             * totalcap : 1495214.82
             * accrate : 5.36
             * changesta : -1.39
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$11Bean {
            /**
             * code : 000063
             * name : 中兴通讯
             * fundnum : 270
             * total : 26,276
             * change : -7,914
             * totalcap : 743628.14
             * accrate : 6.28
             * changesta : -1.89
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$12Bean {
            /**
             * code : 000568
             * name : 泸州老窖
             * fundnum : 265
             * total : 20,639
             * change : -4,086
             * totalcap : 1149082.02
             * accrate : 14.72
             * changesta : -2.92
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$13Bean {
            /**
             * code : 601601
             * name : 中国太保
             * fundnum : 256
             * total : 30,959
             * change : -10,967
             * totalcap : 1143325.69
             * accrate : 3.42
             * changesta : -1.21
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$14Bean {
            /**
             * code : 000001
             * name : 平安银行
             * fundnum : 243
             * total : 53,488
             * change : -9,796
             * totalcap : 594261.45
             * accrate : 3.16
             * changesta : -0.58
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$15Bean {
            /**
             * code : 601328
             * name : 交通银行
             * fundnum : 240
             * total : 118,136
             * change : -25,836
             * totalcap : 746624.52
             * accrate : 1.59
             * changesta : -0.35
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$16Bean {
            /**
             * code : 601939
             * name : 建设银行
             * fundnum : 235
             * total : 55,338
             * change : -23,105
             * totalcap : 385706.55
             * accrate : 0.22
             * changesta : -0.09
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class _$17Bean {
            /**
             * code : 601988
             * name : 中国银行
             * fundnum : 231
             * total : 91,430
             * change : -36,262
             * totalcap : 376692.96
             * accrate : 0.31
             * changesta : -0.12
             * time : 20170930
             */

            private String code;
            private String name;
            private String fundnum;
            private String total;
            private String change;
            private String totalcap;
            private String accrate;
            private String changesta;
            private String time;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFundnum() {
                return fundnum;
            }

            public void setFundnum(String fundnum) {
                this.fundnum = fundnum;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
            }

            public String getTotalcap() {
                return totalcap;
            }

            public void setTotalcap(String totalcap) {
                this.totalcap = totalcap;
            }

            public String getAccrate() {
                return accrate;
            }

            public void setAccrate(String accrate) {
                this.accrate = accrate;
            }

            public String getChangesta() {
                return changesta;
            }

            public void setChangesta(String changesta) {
                this.changesta = changesta;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
