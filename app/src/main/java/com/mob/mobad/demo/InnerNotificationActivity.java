package com.mob.mobad.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.ad.ExpressAdListener;
import com.mob.ad.InnerNotificationLoader;
import com.mob.ad.MobAd;
import com.mob.ad.AdParam;
import com.mob.mobad.demo.utils.Const;
import com.mob.mobad.demo.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class InnerNotificationActivity extends BaseActivity implements View.OnClickListener {
	private EditText slotEtv;
	private Map<String, InnerNotificationLoader> innerNotificationLoaders = new HashMap<>();

	private String currentSlotId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inner_notification);
	}

	@Override
	protected void init() {
		super.init();
		currentSlotId = sharedPreferences.getString(Const.KEY_NOTIFICATION_SLOT, Const.SLOT_ID_NOTIFICATION);
	}

	@Override
	protected void initView() {
		findViewById(R.id.request_notification_btn).setOnClickListener(this);
		findViewById(R.id.close_notifiload_btn).setOnClickListener(this);
		slotEtv = findViewById(R.id.slot_notification_etv);
		if (!TextUtils.isEmpty(currentSlotId)) {
			slotEtv.setText(currentSlotId);
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.request_notification_btn) {
			String slot = slotEtv.getText().toString();
			if (TextUtils.isEmpty(slot)) {
				Toast.makeText(this, "请输入广告id", Toast.LENGTH_SHORT).show();
			}
			if (!slot.equals(currentSlotId)) {
				currentSlotId = slot;
				sharedPreferences.edit().putString(Const.KEY_NOTIFICATION_SLOT, slot).commit();
			}
			InnerNotificationLoader loader = innerNotificationLoaders.get(slot);
			AdParam adParam = new AdParam.Builder(currentSlotId)
					.build();
			if (loader == null) {
				loader = MobAd.createInnerNotificationLoader(this, adParam, new ExpressAdListener() {
					@Override
					public void onAdLoaded() {
						LogUtils.d("onAdLoaded");
					}

					@Override
					public void onAdExposure() {
						LogUtils.d("onAdExposure");
					}

					@Override
					public void onAdClosed() {
						LogUtils.d("onAdClosed");
					}

					@Override
					public void onAdClick() {
						LogUtils.d("onAdClick");
					}

					@Override
					public void onAdError(int code, String msg) {
						LogUtils.d("code:" + code + ";msg=" + msg);
					}
				});
				innerNotificationLoaders.put(slot, loader);
			}
			loader.loadAd();
		} else if (v.getId() == R.id.close_notifiload_btn) {
			for (InnerNotificationLoader loader :
					innerNotificationLoaders.values()) {
				loader.destroy();
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}