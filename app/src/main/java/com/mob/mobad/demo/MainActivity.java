package com.mob.mobad.demo;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.ad.MobAd;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
	private LinearLayout la_container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		la_container = findViewById(R.id.la_container);
		new PrivacyDialogUtils().showPrivacyDialogIfNeed(this,"MobAD");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.request_gps_permissions:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					List<String> lackedPermission = new ArrayList<String>();

					if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
						lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
					}
					if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
						lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
					}
					if (!(checkSelfPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES) == PackageManager.PERMISSION_GRANTED)) {
						lackedPermission.add(Manifest.permission.REQUEST_INSTALL_PACKAGES);
					}

					if (lackedPermission.size() > 0) {
						String[] requestPermissions = new String[lackedPermission.size()];
						lackedPermission.toArray(requestPermissions);
						requestPermissions(requestPermissions, 1024);
					}
				}
				break;
			case R.id.sbmitT:
				MobSDK.submitPolicyGrantResult(true);
				Toast.makeText(this, "同意隐私", Toast.LENGTH_LONG).show();
				break;
			case R.id.sbmitF:
				MobSDK.submitPolicyGrantResult(false);
				Toast.makeText(this, "不同意隐私", Toast.LENGTH_LONG).show();
				break;
			case R.id.init_btn:
				break;
			case R.id.icon_btn:
				startActivity(new Intent(this, IconActivity.class));
				break;
			case R.id.inner_notification_btn:
				startActivity(new Intent(this, InnerNotificationActivity.class));
				break;
			case R.id.splash_btn:
				startActivity(new Intent(this, SplashSettingActivity.class));
				break;
			case R.id.test_act_a:
				startActivity(new Intent(this, TestActivityA.class));
				break;
			case R.id.test_act_b:
				startActivity(new Intent(this, TestActivityB.class));
				break;
			case R.id.float_btn:
				startActivity(new Intent(this, FloatActivity.class));
				break;
			case R.id.but_mianpage:
				EditText editText = (EditText) findViewById(R.id.edit_mianpage);
				String s = editText.getText().toString();
				if (!TextUtils.isEmpty(s)) {
					MobAd.setMainPage(s);
				} else {
					Toast.makeText(this, "设置的首页地址内容为空", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	@Override
	protected void init() {

	}

	@Override
	protected void initView() {
		findViewById(R.id.request_gps_permissions).setOnClickListener(this);
		findViewById(R.id.sbmitT).setOnClickListener(this);
		findViewById(R.id.sbmitF).setOnClickListener(this);
		findViewById(R.id.init_btn).setOnClickListener(this);
		findViewById(R.id.icon_btn).setOnClickListener(this);
		findViewById(R.id.float_btn).setOnClickListener(this);
		findViewById(R.id.inner_notification_btn).setOnClickListener(this);
		findViewById(R.id.test_act_a).setOnClickListener(this);
		findViewById(R.id.test_act_b).setOnClickListener(this);
		findViewById(R.id.but_mianpage).setOnClickListener(this);
		findViewById(R.id.splash_btn).setOnClickListener(this);
	}
}