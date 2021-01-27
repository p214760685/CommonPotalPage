//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.Timer;
import java.util.TimerTask;

public class  AsyncTask {
    public static final int WIFI_NOT_OPEN = 151585168;
    public static final int FAILURE_IN_LINK = 151585169;
    public static final int LOCAL_ANOMALIES = 151585170;
    public static final int SERVICE_EXCEPTION = 151585171;
    public static final int OPERATION_WIN = 151585172;
    public static final int OPERATION_GRANT = 151585173;
    private final int timeoutConnection = 30000;
    private int startType = 1;
    private boolean isThread = true;

    public AsyncTask() {
    }

    public int getStartType() {
        return this.startType;
    }

    public void setStartType(int startType) {
        this.startType = startType;
    }

    public void stopThread() {
        this.isThread = false;
    }

    public void startThread(int startType, final OnMutual onMutual) {
        this.isThread = true;
        this.startType = startType;
        onMutual.showView();
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if(AsyncTask.this.isThread) {
                    onMutual.hideView();
                    AsyncTask.this.stopThread();
                    if(msg == null) {
                        msg = new Message();
                        msg.what = LOCAL_ANOMALIES;
                        msg.obj = "消息异常(消息为空)";
                    }

                    switch(msg.what) {
                        case WIFI_NOT_OPEN:
                            onMutual.wifiNotOpenError(msg);
                            break;
                        case FAILURE_IN_LINK:
                            onMutual.failureInLinkError(msg);
                            break;
                        case LOCAL_ANOMALIES:
                            onMutual.localAnomaliesError(msg);
                            break;
                        case SERVICE_EXCEPTION:
                            onMutual.serviceExceptionError(msg);
                            break;
                        case OPERATION_WIN:
                            onMutual.operationWin(msg);
                            break;
                        case AsyncTask.OPERATION_GRANT:
                            onMutual.gradeFailError(msg);
                            break;
                        default:
                            onMutual.outPut(msg);
                    }
                }

            }
        };
        final Timer timer = new Timer();
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();
                onMutual.sleepThread();
                if(AsyncTask.this.isThread) {
                    Message message = onMutual.inPut();
                    if(AsyncTask.this.isThread) {
                        if(message != null) {
                            handler.sendMessage(message);
                        } else {
                            message = new Message();
                            message.what = LOCAL_ANOMALIES;
                            message.obj = "消息异常(消息为空)";
                            handler.sendMessage(message);
                        }
                    } else {
                        handler.sendMessage(new Message());
                    }
                } else {
                    handler.sendMessage(new Message());
                }

                timer.cancel();
                Looper.loop();
            }
        };
        thread.start();

        try {
            timer.schedule(new TimerTask() {
                public void run() {
                    if(AsyncTask.this.isThread) {
                        Message message = new Message();
                        message.what = LOCAL_ANOMALIES;
                        message.obj = "连接网络超时";
                        handler.sendMessage(message);
                    } else {
                        handler.sendMessage(new Message());
                    }

                    timer.cancel();
                }
            }, timeoutConnection);
        } catch (Exception var7) {
            ;
        }

    }
}
