package com.yuas.pecker.data;

import com.google.gson.reflect.TypeToken;
import com.yuas.pecker.bean.pecker.FileInfoBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.utils.reservoir.Reservoir;

import java.lang.reflect.Type;
import java.util.List;

public class ExcelListData {

    public static void saveExcelFileList(List<FileInfoBean> fileInfoBeanList){
        try {
            Reservoir.put("list_excel", fileInfoBeanList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //保存公司集合
    public static List<FileInfoBean> getExcelFileList() {
        List<FileInfoBean> list = null;
        try {
            Type resultType = new TypeToken<List<FileInfoBean>>() {

            }.getType();

            list = Reservoir.get("list_excel", resultType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
