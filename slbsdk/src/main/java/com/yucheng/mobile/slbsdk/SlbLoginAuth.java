package com.yucheng.mobile.slbsdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * 描述：
 * 开发： Mr Peng
 * 时间： on 2018/7/3
 * 邮箱： 307491878@qq.com
 */

public class SlbLoginAuth {

    public static final int RESULT_SLBCODE = 10010576;

    public static void LoginAuthToSLB(Activity activity,String key, String phone, String packageName,String activityName ,int type){
        try {
            ComponentName comp = new ComponentName("com.yucheng.mobile.wportal", "com.yucheng.mobile.wportal.third.T01_LoginActivity");
            Intent it = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("key", key);
            bundle.putString("phone", phone);
            bundle.putString("packageName", packageName);
            bundle.putString("activityName", activityName);
            bundle.putInt("type", type);
            it.putExtras(bundle);
            it.setComponent(comp);
            activity.startActivityForResult(it, RESULT_SLBCODE);
        }catch (ActivityNotFoundException e){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.yucheng.mobile.wportal");
            intent.setData(content_url);
            activity.startActivity(intent);
        }
    }





}
