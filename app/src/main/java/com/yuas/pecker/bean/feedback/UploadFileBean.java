package com.yuas.pecker.bean.feedback;

/**
 * Created by liqing on 2018/9/21.
 */

public class UploadFileBean {

    /**
     * originalUrl : http://192.168.31.86:8080/DCIM/image/head/907d4fa489084982bce9b68305513bf9.jpg
     * status : null
     * thumbnailUrl : http://192.168.31.86:8080/DCIM/image/head/thumb_907d4fa489084982bce9b68305513bf9.jpg
     */

    private String originalUrl;
    private Object status;
    private String thumbnailUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
