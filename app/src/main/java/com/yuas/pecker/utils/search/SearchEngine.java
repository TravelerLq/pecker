package com.yuas.pecker.utils.search;



import com.yuas.pecker.bean.pecker.FileInfoBean;

import java.io.File;
import java.util.List;

public class SearchEngine {
    private final File path;
    private final FileFilter filter;
    private boolean isSearching;
    private volatile boolean stop;
    private SearchEngineCallback callback;
    private CallbackExecutor callbackExecutor;

    public SearchEngine(File path, FileFilter filter) {
        this.path = path;
        this.filter = filter;
    }

    public void start(final SearchEngineCallback callback) {
        isSearching = true;
        stop = false;
        callbackExecutor = new CallbackExecutor(callback, 200);
        new Thread(new Runnable() {
            @Override
            public void run() {
                findFileRecursively(path);
                callbackExecutor.onFinish();
                isSearching = false;
            }
        }).start();
    }

    public void start() {
        if (callback != null) {
            start(callback);
        }
    }

    public void stop() {
        stop = isSearching;
    }

    public boolean isSearching() {
        return isSearching;
    }

    public void setCallback(SearchEngineCallback callback) {
        this.callback = callback;
    }

    private void findFileRecursively(final File file) {
        if (stop || !filter.isShowHidden() && file.getName().startsWith(".")) {
            return;
        }
        //Log.d("file name",file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                callbackExecutor.onSearchDirectory(file);
                for (File f : files) {
                    findFileRecursively(f);
                }
            }
        } else {

            if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")) {
//                FileItem item = new FileItem(file);
                FileInfoBean item = new FileInfoBean(file);
                callbackExecutor.onFind(item);
            }
//            if(filter.filter(file)){
//                FileItem item = new FileItem(file);
//                callbackExecutor.onFind(item);
//            }
        }
    }

    public interface SearchEngineCallback {
        void onFind(List<FileInfoBean> fileItems);

        void onSearchDirectory(File file);

        void onFinish();
    }

}

