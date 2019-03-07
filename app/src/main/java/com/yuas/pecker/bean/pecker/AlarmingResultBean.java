package com.yuas.pecker.bean.pecker;


import com.yuas.pecker.bean.BaseBean;

public class AlarmingResultBean extends BaseBean {


    /**
     * id : 340
     * uid : 24
     * result : [主营业务收入为0，企业未发生销售收入。, 主营业务成本为0，企业未展开经营。, [本期数额营业收入, 本期数额销售费用, 本期数额管理费用, 本期数额财务费用, 本期数额利润总额, 本期数额营业成本, 本期数额应纳税所得额, 本期数额应纳所得税额, 本期数额税金及附加, 本期数额资产减值损失, 本期数额公允价值变动收益, 本期数额投资收益, 本期数额营业利润, 本期数额营业外收入, 本期数额营业外支出, 本期数额境外所得, 本期数额抵免所得税额, 本期数额本年应补所得税额, 本期数额其他, 本期数额非流动资产处置损失, 本期数额净利润本期数, 本期数额流动资产合计期末数, 本期数额流动负债合计期末数, 本期数额负债合计期末数, 本期数额所有者权益合计期末数, 本期数额流动资产合计期初数, 本期数额流动负债合计期初数, 本期数额负债合计期初数, 本期数额所有者权益合计期初数]]
     * createtime : 2019-01-31 15:45
     * state : 1
     */

    private String id;
    private String uid;
    private String result;
    private String createtime;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
