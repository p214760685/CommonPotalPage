package com.portal.common.util;

import android.util.Log;

public class UtilLog {
    private static final String TAG = "TAG";
    private static final String ERROR = "ERROR";
    private static final String NETWORKING = "NETWORKING";

    public UtilLog() {
    }

    public static void showLog(String str, boolean isDebug) {
        if(isDebug) {
            Log.d("TAG", str);
        }

    }

    public static void showLog(String tag, String str, boolean isDebug) {
        if(isDebug) {
            Log.d(tag, str);
        }

    }

    public static void showError(String str, boolean isDebug) {
        if(isDebug) {
            Log.e("ERROR", str);
        }

    }

    public static void shownNet(String str, boolean isDebug) {
        if(isDebug) {
            Log.d("NETWORKING", str);
        }

    }
}
