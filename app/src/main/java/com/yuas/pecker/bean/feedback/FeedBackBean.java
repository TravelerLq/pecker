package com.yuas.pecker.bean.feedback;


import com.yuas.pecker.bean.BaseBean;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class FeedBackBean extends BaseBean {
    String feedbackTitle;
    String fendbackContent;
    String pictureUrl;



    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFendbackContent() {
        return fendbackContent;
    }

    public void setFendbackContent(String fendbackContent) {
        this.fendbackContent = fendbackContent;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
