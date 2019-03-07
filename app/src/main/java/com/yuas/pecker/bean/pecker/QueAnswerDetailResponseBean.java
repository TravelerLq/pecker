package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class QueAnswerDetailResponseBean extends BaseBean {


    /**
     * id : 14
     * description : 所得税有偏差
     * imageStr : []
     * quizzerId : 28
     * answererId : 25
     * aid :
     * createtime : 2019-01-12 12:26
     * updatetime : 2019-01-12 12:26
     * imageList : [""]
     * teachName : 周先康
     * exchangeList : [{"id":10,"msgValue":"教授，我应该如何处理这个问题的？","uid":28,"type":1,"createtime":1547267222000,"updatetime":1547267222000,"queId":14},{"id":11,"msgValue":"如何解决","uid":28,"type":1,"createtime":1547267241000,"updatetime":1547267241000,"queId":14},{"id":12,"msgValue":"教授不回答什么情况？","uid":28,"type":1,"createtime":1547473936000,"updatetime":1547473936000,"queId":14},{"id":13,"msgValue":"为啥不回答","uid":28,"type":1,"createtime":1548251998000,"updatetime":1548251998000,"queId":14},{"id":18,"msgValue":"这个我需要考虑一下","uid":25,"type":2,"createtime":1548383001000,"updatetime":1548383001000,"queId":14}]
     * identity :
     * title : 高级教授
     * photo : 1546399523650.jpeg
     */

    private int id;
    private String description;
    private String imageStr;
    private int quizzerId;
    private int answererId;
    private String aid;
    private String createtime;
    private String updatetime;
    private String teachName;
    private String identity;
    private String title;
    private String photo;
    private List<String> imageList;
    private List<ExchangeListBean> exchangeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public int getQuizzerId() {
        return quizzerId;
    }

    public void setQuizzerId(int quizzerId) {
        this.quizzerId = quizzerId;
    }

    public int getAnswererId() {
        return answererId;
    }

    public void setAnswererId(int answererId) {
        this.answererId = answererId;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getTeachName() {
        return teachName;
    }

    public void setTeachName(String teachName) {
        this.teachName = teachName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<ExchangeListBean> getExchangeList() {
        return exchangeList;
    }

    public void setExchangeList(List<ExchangeListBean> exchangeList) {
        this.exchangeList = exchangeList;
    }

    public static class ExchangeListBean {
        /**
         * id : 10
         * msgValue : 教授，我应该如何处理这个问题的？
         * uid : 28
         * type : 1
         * createtime : 1547267222000
         * updatetime : 1547267222000
         * queId : 14
         */

        private int id;
        private String msgValue;
        private int uid;
        private int type;
        private long createtime;
        private long updatetime;
        private int queId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsgValue() {
            return msgValue;
        }

        public void setMsgValue(String msgValue) {
            this.msgValue = msgValue;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public long getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(long updatetime) {
            this.updatetime = updatetime;
        }

        public int getQueId() {
            return queId;
        }

        public void setQueId(int queId) {
            this.queId = queId;
        }
    }

}
