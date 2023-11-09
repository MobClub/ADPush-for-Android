package com.mob.mobad.demo.utils;

import android.widget.Toast;

import com.mob.MobSDK;

public class LogUtils {
	public static void d(String msg) {
		Toast.makeText(MobSDK.getContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
