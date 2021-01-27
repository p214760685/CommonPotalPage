
package com.portal.common.network;

import android.os.Message;

public abstract class OnMutual {
    public void sleepThread() {
    }

    public void showView() {
    }

    public void hideView() {
    }

    public void gradeFailError(Message msg){
    }

    public abstract Message inPut();

    public abstract void operationWin(Message var1);

    public abstract void serviceExceptionError(Message var1);

    public void localAnomaliesError(Message msg) {
        this.serviceExceptionError(msg);
    }

    public void failureInLinkError(Message msg) {
        this.localAnomaliesError(msg);
    }

    public void wifiNotOpenError(Message msg) {
        this.failureInLinkError(msg);
    }

    public void outPut(Message message) {
    }

    public OnMutual() {
    }
}
