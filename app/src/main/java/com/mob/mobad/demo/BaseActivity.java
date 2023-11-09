package com.mob.mobad.demo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseActivity extends Activity {
	protected SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}

	protected void init() {
		sharedPreferences = getSharedPreferences("cf", Context.MODE_PRIVATE);
	}

	;

	protected abstract void initView();
}
