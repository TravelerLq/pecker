package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class WordExplainBean extends BaseBean {


    /**
     * id : 1
     * statementId : 1
     * statementName : 资产负债表
     * lemmaName : 无形资产
     * lemmaExplain : 无形资产是指企业拥有或者控制的没有实物形态的可辨认非货币性资产。
     * pictureUrl :
     * pictureThumbnailUrl :
     * videoList : [{"id":1,"userId":73,"title":"苏港","videoUrl":"http://cs.royalsecurity.cn/dcim/video/statement/video-51557fc7b5644877a30a38f98be4a979.mp4","videoThumbnailUrl":"http://cs.royalsecurity.cn/dcim/video/statement/video-51557fc7b5644877a30a38f98be4a979.jpg","approve":1}]
     */

    private int id;
    private int statementId;
    private String statementName;
    private String lemmaName;
    private String lemmaExplain;
    private String pictureUrl;
    private String pictureThumbnailUrl;
    private List<VideoListBean> videoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatementId() {
        return statementId;
    }

    public void setStatementId(int statementId) {
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

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public static class VideoListBean {
        /**
         * id : 1
         * userId : 73
         * title : 苏港
         * videoUrl : http://cs.royalsecurity.cn/dcim/video/statement/video-51557fc7b5644877a30a38f98be4a979.mp4
         * videoThumbnailUrl : http://cs.royalsecurity.cn/dcim/video/statement/video-51557fc7b5644877a30a38f98be4a979.jpg
         * approve : 1
         */

        private int id;
        private int userId;
        private String title;
        private String videoUrl;
        private String videoThumbnailUrl;
        private int approve;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public int getApprove() {
            return approve;
        }

        public void setApprove(int approve) {
            this.approve = approve;
        }
    }

}
