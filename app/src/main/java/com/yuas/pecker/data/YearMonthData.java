package com.yuas.pecker.data;


import com.google.gson.reflect.TypeToken;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.utils.reservoir.Reservoir;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 18/7/25.
 */

public class YearMonthData {
    private static List<String> listData = new ArrayList<>();

    //删除 reservoir的bean

    public static void removeReimItemBean() {
        try {

            //  saveReimItemList(list);
            //

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveStringList(List<String> list) {
        try {
            listData.clear();
            listData.addAll(list);
            Reservoir.put(AppConstant.SERIAL_KEY, listData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> getStringList() {
        List<String> list = null;
        Type resultType = new TypeToken<List<String>>() {

        }.getType();

        try {
            list = Reservoir.get(AppConstant.SERIAL_KEY, resultType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void removeReimList() {
        try {
            listData.clear();
            Reservoir.delete(AppConstant.KEY_REIM_ITEM_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void updateReimList(int pos) {
//        try {
//            Reservoir.delete(AppConstant.KEY_REIM_ITEM_LIST);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
