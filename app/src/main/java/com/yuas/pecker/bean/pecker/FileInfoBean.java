package com.yuas.pecker.bean.pecker;

import android.os.Environment;

import com.yuas.pecker.bean.BaseBean;
import com.yuas.pecker.utils.search.FileSearcherUtil;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInfoBean extends BaseBean {

    static String storagePath = Environment.getExternalStorageDirectory().getPath() + File.separator;
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd,HH:mm");


    private String name, detail, path, abPath;
    private File file;
    private boolean isChecked;

    public FileInfoBean(File file) {
        this.file = file;
        this.name = file.getName();
        this.detail = FileSearcherUtil.byteSizeFormatter(file.length()) + "," + FileInfoBean.formatter.format(new Date(file.lastModified()));
        this.path = file.getPath().replace(storagePath, "");
        this.abPath = file.getAbsolutePath();
        isChecked = false;

    }

    public String getAbPath() {
        return abPath;
    }

    public void setAbPath(String abPath) {
        this.abPath = abPath;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

}
