package com.portal.commonpotal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;

import com.portal.common.util.ImageUtil;

import java.util.List;
import java.util.Locale;

public class A extends Application {

	private static A INSTANCE;

	// public static JSONArray branchArr;


	public static boolean LANGUAGE_STATUS = false;

	/**
	 * token 가져오기, 서비스가 연결되어 있지 않는 경우에는 서비스 연결을 재 요청 한다
	 * 
	 * @return
	 */
	public static String getId() {

		try {



		} catch (Exception e) {


		}

		return "";

	}





	@Override
	public void onCreate() {
		super.onCreate();
		INSTANCE = this;
		try {
			init();
			// 在主进程设置信鸽相关的内容
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static A getBaseAppContext() {
		return INSTANCE;
	}

	private void init() {

//		Fresco.initialize(getApplicationContext(), ImageUtil
//				.getDefaultImagePipelineConfig(getApplicationContext()));
//		 	Config.DEBUG = true;
//		 	QueuedWork.isUseThreadPool = false;
//	        UMShareAPI.get(this);
//	        PlatformConfig.setWeixin("wx8ab5a1f852d8663c", "b57c0626ccab1ef315b4ad7225e08da3");
//	        PlatformConfig.setSinaWeibo("3859364376", "1f3a02ed0032b94a661bd9e8e8864754","http://pay.shelongwang.com");
//	        PlatformConfig.setQQZone("1105955455", "sh0sSUOnMosPpbhZ");
//	        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
//	        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
//	        PlatformConfig.setAlipay("2015111700822536");
//	        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
//	        PlatformConfig.setPinterest("1439206");
//	        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
//	        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
//	        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
//	        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
//	        PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");


//	@Override
//	public void onLowMemory() {
//		// TODO Auto-generated method stub
//		super.onLowMemory();
//
//	}
//
//	public boolean isMainProcess() {
//		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
//		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//		String mainProcessName = getPackageName();
//		int myPid = android.os.Process.myPid();
//		for (RunningAppProcessInfo info : processInfos) {
//			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
//				return true;
//			}
//		}
//		return false;
	}
}