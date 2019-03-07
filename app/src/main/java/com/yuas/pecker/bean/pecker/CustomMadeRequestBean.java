package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class CustomMadeRequestBean extends BaseBean {


    /**
     * uid : 24
     * nature : 1
     * vatColl : test_video
     * incomeColl : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.mp4
     * classificationIndustry : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg
     * linkMan : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg
     * phone : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg
     * placeRegistration : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg
     * station : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg
     * individuationList : ["1","2"]
     */

    private String uid;
    private String nature;
    private String vatColl;
    private String incomeColl;
    private String classificationIndustry;
    private String linkMan;
    private String phone;
    private String placeRegistration;
    private String station;
    private List<String> individuationList;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getVatColl() {
        return vatColl;
    }

    public void setVatColl(String vatColl) {
        this.vatColl = vatColl;
    }

    public String getIncomeColl() {
        return incomeColl;
    }

    public void setIncomeColl(String incomeColl) {
        this.incomeColl = incomeColl;
    }

    public String getClassificationIndustry() {
        return classificationIndustry;
    }

    public void setClassificationIndustry(String classificationIndustry) {
        this.classificationIndustry = classificationIndustry;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlaceRegistration() {
        return placeRegistration;
    }

    public void setPlaceRegistration(String placeRegistration) {
        this.placeRegistration = placeRegistration;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<String> getIndividuationList() {
        return individuationList;
    }

    public void setIndividuationList(List<String> individuationList) {
        this.individuationList = individuationList;
    }
}
