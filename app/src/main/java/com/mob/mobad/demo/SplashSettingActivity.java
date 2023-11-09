package com.mob.mobad.demo;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.mobad.demo.utils.Const;

public class SplashSettingActivity extends BaseActivity implements View.OnClickListener {
	private EditText slotEtv;
	String currentSlotId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_setting);
	}

	@Override
	protected void initView() {
		findViewById(R.id.btn_fetch_only).setOnClickListener(this);
		findViewById(R.id.btn_jumptosplash).setOnClickListener(this);
		findViewById(R.id.btn_jumptosplashforskip).setOnClickListener(this);
		findViewById(R.id.btn_jumptosplashforbottom).setOnClickListener(this);
		slotEtv = findViewById(R.id.et_pos_id);
		slotEtv.setText(currentSlotId);
	}

	@Override
	protected void init() {
		super.init();
		currentSlotId = sharedPreferences.getString(Const.KEY_SPLASH_SLOT, Const.SLOT_ID_SPLASH);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = new Intent(SplashSettingActivity.this, SplashActivity.class);
		String posId = slotEtv.getText().toString();
		if (TextUtils.isEmpty(posId)) {
			Toast.makeText(SplashSettingActivity.this, "广告位id为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!posId.equals(currentSlotId)) {
			currentSlotId = posId;
			sharedPreferences.edit().putString(Const.KEY_SPLASH_SLOT, posId).commit();
		}
		intent.putExtra("posId", posId);
		switch (id) {
			case R.id.btn_fetch_only:
				intent.putExtra("fetch_only", true);
				startActivity(intent);
				break;
			case R.id.btn_jumptosplash:
				startActivity(intent);
				break;
			case R.id.btn_jumptosplashforskip:
				intent.putExtra("customSkip", true);
				startActivity(intent);
				break;
			case R.id.btn_jumptosplashforbottom:
				intent.putExtra("customLogo", true);
				startActivity(intent);
				break;
		}
	}
}