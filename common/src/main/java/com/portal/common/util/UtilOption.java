
package com.portal.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager.KeyguardLock;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class UtilOption {
    public UtilOption() {
    }

    public static void call(Context context, String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNumber)));
    }

    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNumber)));
    }

    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (UtilFormat.isEmpty(phoneNumber)?"":phoneNumber));
        Intent intent = new Intent("android.intent.action.SENDTO", uri);
        intent.putExtra("sms_body", UtilFormat.isEmpty(content)?"":content);
        context.startActivity(intent);
    }

    public static void wakeUpAndUnlock(Context context) {
        KeyguardManager km = (KeyguardManager)context.getSystemService("keyguard");
        KeyguardLock kl = km.newKeyguardLock("unLock");
        kl.disableKeyguard();
        PowerManager pm = (PowerManager)context.getSystemService("power");
        WakeLock wl = pm.newWakeLock(268435462, "bright");
        wl.acquire();
        wl.release();
    }

    public static boolean isApplicationBackground(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService("activity");
        List tasks = am.getRunningTasks(1);
        if(!tasks.isEmpty()) {
            ComponentName topActivity = ((RunningTaskInfo)tasks.get(0)).topActivity;
            if(!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager)context.getSystemService("keyguard");
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;
    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService("phone");
        return telephony.getPhoneType() != 0;
    }

    public static int getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager)context.getSystemService("window");
        return manager.getDefaultDisplay().getWidth();
    }

    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager)context.getSystemService("window");
        return manager.getDefaultDisplay().getHeight();
    }

    @TargetApi(3)
    public static String getDeviceIMEI(Context context) {
        String deviceId;
        if(isPhone(context)) {
            TelephonyManager telephony = (TelephonyManager)context.getSystemService("phone");
            deviceId = telephony.getDeviceId();
        } else {
            deviceId = Secure.getString(context.getContentResolver(), "android_id");
        }

        return deviceId;
    }

    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService("wifi");
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress();
        if(null == macAddress) {
            return "";
        } else {
            macAddress = macAddress.replace(":", "");
            return macAddress;
        }
    }

    public static String getAppVersion(Context context) {
        String version = "0";

        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException var3) {
            var3.printStackTrace();
        }

        return version;
    }

    public static Properties collectDeviceInfo(Context context) {
        Properties mDeviceCrashInfo = new Properties();

        try {
            PackageManager fields = context.getPackageManager();
            PackageInfo pi = fields.getPackageInfo(context.getPackageName(), 1);
            if(pi != null) {
                mDeviceCrashInfo.put("1.0", pi.versionName == null?"not set":pi.versionName);
                mDeviceCrashInfo.put("VERSION_CODE", Integer.valueOf(pi.versionCode));
            }
        } catch (NameNotFoundException var9) {
            ;
        }

        Field[] var10 = Build.class.getDeclaredFields();
        Field[] var11 = var10;
        int var4 = var10.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var11[var5];

            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get((Object)null));
            } catch (Exception var8) {
                ;
            }
        }

        return mDeviceCrashInfo;
    }

    public static String collectDeviceInfoStr(Context context) {
        Properties prop = collectDeviceInfo(context);
        Set deviceInfos = prop.keySet();
        StringBuilder deviceInfoStr = new StringBuilder("{\n");
        Iterator iter = deviceInfos.iterator();

        while(iter.hasNext()) {
            Object item = iter.next();
            deviceInfoStr.append("\t\t\t" + item + ":" + prop.get(item) + ", \n");
        }

        deviceInfoStr.append("}");
        return deviceInfoStr.toString();
    }

    public static boolean haveSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    @TargetApi(3)
    public static void hideSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if(view != null) {
            InputMethodManager inputmanger = (InputMethodManager)activity.getSystemService("input_method");
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    @TargetApi(3)
    public static void hideSoftInput(Context context, EditText edit) {
        edit.clearFocus();
        InputMethodManager inputmanger = (InputMethodManager)context.getSystemService("input_method");
        inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    @TargetApi(3)
    public static void showSoftInput(Context context, EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService("input_method");
        inputManager.showSoftInput(edit, 0);
    }

    @TargetApi(3)
    public static void toggleSoftInput(Context context, EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService("input_method");
        inputManager.toggleSoftInput(2, 0);
    }

    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent("android.intent.action.MAIN");
        mHomeIntent.addCategory("android.intent.category.HOME");
        mHomeIntent.addFlags(270532608);
        context.startActivity(mHomeIntent);
    }

    @TargetApi(3)
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static String getNetworkOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        return telephonyManager.getNetworkOperator();
    }

    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        return telephonyManager.getNetworkOperatorName();
    }

    public static int getPhoneType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        return telephonyManager.getPhoneType();
    }
}
