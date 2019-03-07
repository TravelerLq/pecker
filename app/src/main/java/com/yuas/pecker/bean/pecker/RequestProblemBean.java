package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class RequestProblemBean extends BaseBean {

    /**
     * quizzerId : 24 提问者id
     * description : problems
     * imageList : ["1547721973631","1547721967062","1547721988416","1547721993897"]
     * answererId : 1
     */

    private String quizzerId;
    private String description;
    private String answererId;
    private List<String> imageList;

    public String getQuizzerId() {
        return quizzerId;
    }

    public void setQuizzerId(String quizzerId) {
        this.quizzerId = quizzerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswererId() {
        return answererId;
    }

    public void setAnswererId(String answererId) {
        this.answererId = answererId;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
