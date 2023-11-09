package com.mob.mobad.demo;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.ad.AdParam;
import com.mob.ad.MobAd;
import com.mob.ad.SplashAd;
import com.mob.ad.SplashListener;
import com.mob.ad.SplashLoader;
import com.mob.mobad.demo.utils.LogUtils;
import com.mob.tools.utils.DH;
import com.mob.tools.utils.ResHelper;

public class SplashActivity extends Activity implements View.OnClickListener {
	ImageView adLogoView;
	FrameLayout splash_container;
	TextView skipView;
	SplashLoader splashLoader;
	SplashAd splashAd;
	LinearLayout group;
	boolean canJump = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		String posId = getIntent().getStringExtra("posId");
		boolean fetchOnly = getIntent().getBooleanExtra("fetch_only", false);
		boolean customSkip = getIntent().getBooleanExtra("customSkip", false);
		boolean customLogo = getIntent().getBooleanExtra("customLogo", false);
		adLogoView = findViewById(R.id.app_logo);
		splash_container = findViewById(R.id.splash_container);
		skipView = new TextView(SplashActivity.this);
		skipView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
		group = findViewById(R.id.group);
		findViewById(R.id.btn_finish).setOnClickListener(this);
		findViewById(R.id.btn_showad).setOnClickListener(this);
		AdParam.Builder builder = new AdParam.Builder(posId);
		AdParam build = builder.build();
		splashLoader = MobAd.createSplashLoader(SplashActivity.this, customSkip ? skipView : null, build, new SplashListener() {
			@Override
			public void onAdLoaded(SplashAd splashAd) {
				LogUtils.d("onSplashAdLoaded");
				if (fetchOnly) {
					SplashActivity.this.splashAd = splashAd;
				} else {
					splashAd.show(splash_container);
				}
			}

			@Override
			public void onAdExposure() {
				LogUtils.d("onSplashAdExposure");
			}

			@Override
			public void onAdClosed() {
				LogUtils.d("onSplashAdClosed");
				next();
			}

			@Override
			public void onAdClick() {
				LogUtils.d("onSplashAdClick");
			}

			@Override
			public void onAdTick(long millisUnitFinished) {
				Log.e("ad demo--- ", "onAdTick" + millisUnitFinished);
				skipView.setText(String.format("自定义跳过 %d秒", millisUnitFinished / 1000));
			}

			@Override
			public void onAdError(int code, String msg) {
				LogUtils.d("onSplashAdError Errcode:" + code + ",ErrMsg:" + msg);
				next();
			}
		});

		if (fetchOnly) {
			findViewById(R.id.cl_root).setBackground(null);
			group.setVisibility(View.VISIBLE);
			splashLoader.loadAd();
		} else {
			if (customLogo) {
				adLogoView.getLayoutParams().height = (int) (ResHelper.getScreenHeight(SplashActivity.this) * 0.25);
				adLogoView.setVisibility(View.VISIBLE);
			}
			splashLoader.loadAd();
		}


	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.btn_finish:
				finish();
				break;
			case R.id.btn_showad:
				if (this.splashAd != null) {
					group.setVisibility(View.GONE);
					splashAd.show(splash_container);
				} else {
					Toast.makeText(SplashActivity.this, "splashAd为空", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	/**
	 * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	private void next() {
		if (canJump) {
			this.finish();
			if (splashLoader != null) {
				splashLoader.destroy();
			}
		} else {
			canJump = true;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		canJump = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (canJump) {
			next();
		}
		canJump = true;
	}
}