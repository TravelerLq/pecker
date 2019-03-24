package com.yuas.pecker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.utils.Loger;

import java.util.Timer;
import java.util.TimerTask;

public class NoticeMsgService extends Service {

    //
    private static final  String TAG=NoticeMsgService.class.getSimpleName();
    Timer timer = new Timer();
    private static final int TIME_PERIAO = 15 * 1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Loger.e(TAG+"service-onStartCommand");
//        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    //TimeTask implement Runnable
    private void startTimer() {

        endTimer();
        timer = new Timer();
        //timetask
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                Loger.e(TAG+"=====startTimer ----- ");
                getMsg();
            }
        };
        timer.schedule(timerTask, 0, TIME_PERIAO);

    }

    @Override
    public void onDestroy() {
        endTimer();
        super.onDestroy();
    }

    //endTimer

    private void endTimer() {
        if (timer != null) {
            timer.cancel();
            ;
            timer = null;
        }
    }


    private void getMsg() {
        Intent intent =new Intent(AppConstant.NOTICE_ACTION);
        intent.putExtra("notice","noticeMsgSerive 广播一条消息喽");
        sendBroadcast(intent);


        // new MsgModel.getUNreadMsg(new Msg.ONunredMsgListent(){
        //
        //  @ovvveride
        // oonReceiverMsg   ()--> hanlderMsg() -->  intent =new Intent(Appconstant.NOTICE_ACTION)sendBrodcast(intent);-->f
        //
        //
        // })
    }

    //MsgModel : privae onUnReadMshListenr()   yge interface OunredLister() oid onreci Msg


}
