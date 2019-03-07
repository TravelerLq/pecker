package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

//财务报表学习 bean
public class LearnFinancialBean extends BaseBean {

    /**
     * id : 1
     * statementName : 资产负债表
     */

    private int id;
    private String statementName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatementName() {
        return statementName;
    }

    public void setStatementName(String statementName) {
        this.statementName = statementName;
    }
}
