package com.yuas.pecker.bean.pecker;


import com.yuas.pecker.bean.BaseBean;

public class WordVideoBean extends BaseBean {


    /**
     * userId : 24
     * lemmaId : 1
     * title : test_video
     * videoUrl : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.mp4
     * videoThumbnailUrl : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg
     */

    private String userId;
    private String lemmaId;
    private String title;
    private String videoUrl;
    private String videoThumbnailUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLemmaId() {
        return lemmaId;
    }

    public void setLemmaId(String lemmaId) {
        this.lemmaId = lemmaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoThumbnailUrl() {
        return videoThumbnailUrl;
    }

    public void setVideoThumbnailUrl(String videoThumbnailUrl) {
        this.videoThumbnailUrl = videoThumbnailUrl;
    }
}
