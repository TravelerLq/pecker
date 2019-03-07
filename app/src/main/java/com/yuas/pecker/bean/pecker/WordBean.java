package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class WordBean extends BaseBean {


    /**
     * id : 1
     * statementId :
     * statementName :
     * lemmaName : 无形资产
     * lemmaExplain :
     * pictureUrl :
     * pictureThumbnailUrl :
     * videoList : []
     */

    private int id;
    private String statementId;
    private String statementName;
    private String lemmaName;
    private String lemmaExplain;
    private String pictureUrl;
    private String pictureThumbnailUrl;
    private List<String> videoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getStatementName() {
        return statementName;
    }

    public void setStatementName(String statementName) {
        this.statementName = statementName;
    }

    public String getLemmaName() {
        return lemmaName;
    }

    public void setLemmaName(String lemmaName) {
        this.lemmaName = lemmaName;
    }

    public String getLemmaExplain() {
        return lemmaExplain;
    }

    public void setLemmaExplain(String lemmaExplain) {
        this.lemmaExplain = lemmaExplain;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureThumbnailUrl() {
        return pictureThumbnailUrl;
    }

    public void setPictureThumbnailUrl(String pictureThumbnailUrl) {
        this.pictureThumbnailUrl = pictureThumbnailUrl;
    }

    public List<String> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<String> videoList) {
        this.videoList = videoList;
    }
}
