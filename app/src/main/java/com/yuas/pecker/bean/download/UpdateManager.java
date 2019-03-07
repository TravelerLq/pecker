package com.yuas.pecker.bean.download;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;



/**
 * Created by liqing on 18/8/15.
 */

public class UpdateManager {


    private Context mContext;
    private String downloadUrl;

    public UpdateManager(Context context,String url) {
        this.mContext = context;
        downloadUrl=url;
    }

    /**
     * 显示更新对话框
     *
     * @param version_info
     */
    private void showNoticeDialog(String version_info) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新提示");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

               // mContext.startService(new Intent(mContext, DownLoadService.class));
               // MyIntentService.startUpdateService(mContext, downloadUrl, "");
            }
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
