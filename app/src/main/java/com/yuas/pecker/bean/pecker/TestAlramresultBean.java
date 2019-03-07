package com.yuas.pecker.bean.pecker;



import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class TestAlramresultBean extends BaseBean {


    /**
     * response : success
     * message : 成功
     * data : {"date":null,"resList":["数据填报有误，请检查进项税额本月数数据是否正确","数据填报有误，请检查销项税额本月数数据是否正确","数据填报有误，请检查应纳税额合计一般项目本年累计数据是否正确","数据填报有误，请检查按适用税率计税销售额一般项目本年累计数据是否正确","数据填报有误，请检查按简易办法计税销售额一般项目本年累计数据是否正确","数据填报有误，请检查免抵退办法出口销售额一般项目本年累计数据是否正确","数据填报有误，请检查免税销售额一般项目本年累计数据是否正确"],"id":133}
     */

    private String response;
    private String message;
    private DataBean data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * date : null
         * resList : ["数据填报有误，请检查进项税额本月数数据是否正确","数据填报有误，请检查销项税额本月数数据是否正确","数据填报有误，请检查应纳税额合计一般项目本年累计数据是否正确","数据填报有误，请检查按适用税率计税销售额一般项目本年累计数据是否正确","数据填报有误，请检查按简易办法计税销售额一般项目本年累计数据是否正确","数据填报有误，请检查免抵退办法出口销售额一般项目本年累计数据是否正确","数据填报有误，请检查免税销售额一般项目本年累计数据是否正确"]
         * id : 133
         */

        private Object date;
        private String id;
        private List<String> resList;

        public Object getDate() {
            return date;
        }

        public void setDate(Object date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getResList() {
            return resList;
        }

        public void setResList(List<String> resList) {
            this.resList = resList;
        }
    }
}
