package com.mob.adpush.demo;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.OperationCallback;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean open = sharedPreferences.getBoolean("open", true);
		submitMobPolicy(open);
	}

	private void submitMobPolicy(boolean open) {
		//同意隐私协议
		MobSDK.submitPolicyGrantResult(open, new OperationCallback<Void>() {
			@Override
			public void onComplete(Void aVoid) {
				Toast.makeText(MobSDK.getContext(), "隐私协议 onComplete", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFailure(Throwable throwable) {
				Toast.makeText(MobSDK.getContext(), "隐私协议 onFailure", Toast.LENGTH_LONG).show();
			}
		});
	}
}
