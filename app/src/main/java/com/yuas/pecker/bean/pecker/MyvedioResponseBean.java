package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class MyvedioResponseBean extends BaseBean {


    /**
     * pageCount : 1
     * pageSize : 1
     * totalElements : 3
     * totalPages : 3
     * content : [{"id":3,"userId":24,"title":"test_video","videoUrl":"http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.mp4","videoThumbnailUrl":"http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg","approve":0}]
     * lastPageNumber : false
     */

    private int pageCount;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean lastPageNumber;
    private List<ContentBean> content;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(boolean lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean extends BaseBean {
        /**
         * id : 3
         * userId : 24
         * title : test_video
         * videoUrl : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.mp4
         * videoThumbnailUrl : http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg
         * approve : 0
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
