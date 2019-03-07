package com.yuas.pecker.bean.feedback;

import com.yuas.pecker.bean.BaseBean;

import java.io.File;

/**
 * Created by liqing on 17/11/24.
 */

public class UploadPhotoRequestBean extends BaseBean {
    String userId;
    String path;
    File file;


    public UploadPhotoRequestBean(String userId, String path, File file) {
        this.userId = userId;
        this.path = path;
        this.file = file;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
