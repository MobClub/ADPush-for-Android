package com.mob.mobad.demo;

import android.app.Application;

import com.mob.ad.GlobalExpressAdListener;
import com.mob.ad.InitListener;
import com.mob.ad.MobAd;
import com.mob.mobad.demo.utils.LogUtils;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		MobAd.init(this, new InitListener() {
			@Override
			public void onSuccess() {
				LogUtils.d("初始化onSuccess");
			}

			@Override
			public void onFail() {
				LogUtils.d("初始化onFail");
			}
		});
		MobAd.registerGlobalInnerNotificationListener(new GlobalExpressAdListener() {
			@Override
			public void onAdLoaded(String slot) {
				LogUtils.d("onAdLoaded " + slot);
			}

			@Override
			public void onAdExposure(String slot) {
				LogUtils.d("onAdExposure " + slot);
			}

			@Override
			public void onAdClosed(String slot) {
				LogUtils.d("onAdClosed:" + slot);
			}

			@Override
			public void onAdClick(String slot) {
				LogUtils.d("onAdClick:" + slot);
			}

			@Override
			public void onAdError(String slot, int code, String msg) {
				LogUtils.d("onAdError code=" + code + ";msg=" + msg);
			}
		});
	}
}
