package com.yuas.pecker.bean.pecker;



import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class IndustryTypeBean extends BaseBean {


    /**
     * id : 2
     * code :
     * name : 农、林、牧、渔业
     * introduction :
     * cid :
     * sonList : [{"id":3,"code":"","name":"农业","introduction":"","cid":"","sonList":""},{"id":42,"code":"","name":"林业","introduction":"","cid":"","sonList":null}]
     */

    private String id;
    private String code;
    private String name;
    private String introduction;
    private String cid;
    private List<SonListBean> sonList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<SonListBean> getSonList() {
        return sonList;
    }

    public void setSonList(List<SonListBean> sonList) {
        this.sonList = sonList;
    }

    public static class SonListBean {
        /**
         * id : 3
         * code :
         * name : 农业
         * introduction :
         * cid :
         * sonList :
         */

        private String id;
        private String code;
        private String name;
        private String introduction;
        private String cid;
        private String sonList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getSonList() {
            return sonList;
        }

        public void setSonList(String sonList) {
            this.sonList = sonList;
        }
    }
}
