package com.yuas.pecker.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.ExcelRecycleAdapter;
import com.yuas.pecker.bean.pecker.FileInfoBean;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.utils.search.FileFilter;
import com.yuas.pecker.utils.search.SearchEngine;


import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelRecycleActivity extends AppCompatActivity {

//    @BindView(R.id.recycle_excel)
//    RecyclerView recyclerView;
//
//    @BindView(R.id.file_searcher_main_no_result_found)
//    TextView emptyView;


    private Context context;
    private ExcelRecycleAdapter mAdapter;
    private List<FileInfoBean> list = new ArrayList<>();
    private String fileDate;
    private MyTask mTask;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView emptyView;


    private SearchEngine searchEngine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_recycle);

        context = ExcelRecycleActivity.this;

        toolbar = findViewById(R.id.file_searcher_main_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_excel);
        emptyView = (TextView) findViewById(R.id.file_searcher_main_no_result_found);
        toolbar.setNavigationIcon(R.drawable.back_holo_dark_no_trim_no_padding);
        setSupportActionBar(toolbar);
        // testParseData();
        initializeSearchEngine();

        searchEngine.start();
        initRecycleView();

        checkAuthority();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    //添加读写访问权限


    private void checkAuthority() {


        if (PermissionsUtil.hasPermission(ExcelRecycleActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {


        } else {
            PermissionsUtil.requestPermission(ExcelRecycleActivity.this, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permission) {
                            Log.e("--", "permissionGranted: 用户授予了访问外部存储的权限");
                            searchEngine.start();

                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permission) {
                            Log.e("--", "permissionDenied: 用户拒绝了访问外部存储的申请");
                            // needPermissionTips();
                            ExcelRecycleActivity.this.finish();

                        }
                    }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    private void initializeSearchEngine() {
//        FileFilter filter = (FileFilter) getIntent().getSerializableExtra(FileSearcher.FILE_FILTER);
//        File path = (File) getIntent().getSerializableExtra(FileSearcher.SEARCH_PATH);
//

//        if(filter == null || path == null){
//            throw new NullPointerException("the filter and path cannot be null!");
//        }
        final File path = Environment.getExternalStorageDirectory();
        FileFilter filter = new FileFilter();

        searchEngine = new SearchEngine(path, filter);
        searchEngine.setCallback(new SearchEngine.SearchEngineCallback() {
            @Override
            public void onFind(List<FileInfoBean> items) {
                //
                mAdapter.addItem(items);
            }

            @Override
            public void onSearchDirectory(File file) {

                String searchTitle = file.getPath().replace(Environment.getExternalStorageDirectory().getPath() + File.separator, "");
                Loger.e("---searchTitle" + searchTitle);
                toolbar.setSubtitle(file.getPath().replace(Environment.getExternalStorageDirectory().getPath() + File.separator, ""));
            }

            @Override
            public void onFinish() {


                if (mAdapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                }
                toolbar.setTitle(getResources().getString(R.string.file_total) + mAdapter.getItemCount());
                toolbar.setSubtitle(getResources().getString(R.string.search_finish));
            }
        });
    }

    private void initRecycleView() {
//        mTask = new MyTask();
//        mTask.execute();

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));
        mAdapter = new ExcelRecycleAdapter(context, list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExcelRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {


                setResultForIntent(pos);


            }
        });


    }


    private void setResultForIntent(int position) {

        Intent mIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("file", list.get(position));
        mIntent.putExtras(bundle);
        // 设置结果，并进行传送
        setResult(Activity.RESULT_OK, mIntent);
        finish();
    }

    public void testParseData() {
        List<String> listString1 = new ArrayList<>();
        listString1.add("znagsn");
        listString1.add("2ert");


        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
        Map<String, Object> map = new HashMap<>();
        map.put("array", array);
        String jsonRequestStr = JSONObject.toJSONString(map);
        Loger.e("---jsonRequestStr" + jsonRequestStr);
        JSONObject jsonObject = JSON.parseObject(jsonRequestStr);
        Loger.e("--getJsonString" + JSON.parseArray(jsonObject.getString("array")));
        List<String> listString = JSON.parseArray(jsonObject.getString("array"), String.class);
        for (int i = 0; i < listString.size(); i++) {
            Loger.e("list" + list.get(i));
        }


    }

    private String filePath = Environment.getExternalStorageDirectory().toString() + File.separator;
    // String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath() + "/";


    /****
     * 递归算法获取本地文件
     * @param path
     */
    private void doSearch(String path) {
        File file = new File(path);
        long start1 = System.currentTimeMillis();
        if (file.exists()) {
            if (file.isDirectory()) {

                File[] fileArray = file.listFiles();

                for (File f : fileArray) {

                    if (f.isDirectory()) {
                        doSearch(f.getPath());
                    } else {
                        //xlsx
                        if (f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")) {
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(f);
                                String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(f.lastModified()));
                                FileInfoBean info = new FileInfoBean(file);

                                list.add(info);
                                fileDate += f.getAbsolutePath() + ",";

                                System.out.println("文件名称：" + f.getName());
                                System.out.println("文件是否存在：" + f.exists());
                                System.out.println("文件的相对路径：" + f.getPath());
                                System.out.println("文件的绝对路径：" + f.getAbsolutePath());
                                System.out.println("文件可以读取：" + f.canRead());
                                System.out.println("文件可以写入：" + f.canWrite());
                                System.out.println("文件上级路径：" + f.getParent());
                                System.out.println("文件大小：" + f.length() + "B");
                                System.out.println("文件最后修改时间：" + new Date(f.lastModified()));
                                System.out.println("是否是文件类型：" + f.isFile());
                                System.out.println("是否是文件夹类型：" + f.isDirectory());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }

        }


    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
//            showProgress();
            doSearch(filePath);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (mAdapter == null) {
                mAdapter = new ExcelRecycleAdapter(context, list);
            }
//            dimissProgress();
            mAdapter.notifyDataSetChanged();

            super.onPostExecute(s);
        }
    }


    ArrayList<String> FOLDERS = new ArrayList<String>();
    ArrayList<String> XLS_FILES = new ArrayList<String>();

    int CALL_COUNT = 0;


}
