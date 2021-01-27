
package com.portal.common.util;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;

public class UtilNet {
    public UtilNet() {
    }

    /**
     * 判断网络是否连通
     */
    public static boolean isOnline(Context context) {
        boolean flag = false;
        if(context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if(mNetworkInfo != null) {
                mNetworkInfo.isAvailable();
                flag = true;
            } else {
                flag = false;
            }
        }

        return flag;
    }

    public static int getWlanState(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        boolean wifi = mConnectivityManager.getNetworkInfo(1).isConnectedOrConnecting();
        boolean gprs = mConnectivityManager.getNetworkInfo(0).isConnectedOrConnecting();
        return gprs?1:(wifi?2:0);
    }

    public static boolean isGPS(Context context) {
        LocationManager locationManager = (LocationManager)context.getSystemService("location");
        boolean gps = locationManager.isProviderEnabled("gps");
        return gps;
    }

    public static boolean openSttingForWlan(Context context) {
        Intent intent = null;
        if(VERSION.SDK_INT > 10) {
            intent = new Intent("android.settings.WIRELESS_SETTINGS");
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }

        context.startActivity(intent);
        return true;
    }

    public static boolean openSttingForGPS(Context context) {
        boolean flagSetting = false;
        Intent intent = new Intent();
        intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
        intent.setFlags(268435456);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException var6) {
            intent.setAction("android.settings.SETTINGS");

            try {
                context.startActivity(intent);
                flagSetting = true;
            } catch (Exception var5) {
                flagSetting = false;
            }
        }

        return flagSetting;
    }

    public static void openGPS(Context context) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        context.sendBroadcast(intent);
        String provider = Secure.getString(context.getContentResolver(), "location_providers_allowed");
        if(!provider.contains("gps")) {
            Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory("android.intent.category.ALTERNATIVE");
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }

    }
}
