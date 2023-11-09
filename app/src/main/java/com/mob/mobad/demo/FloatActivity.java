package com.mob.mobad.demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.ad.AdParam;
import com.mob.ad.ExpressIconAdListener;
import com.mob.ad.ExpressIconLoader;
import com.mob.ad.IconAd;
import com.mob.ad.MobAd;
import com.mob.mobad.demo.utils.Const;
import com.mob.mobad.demo.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FloatActivity extends BaseActivity implements View.OnClickListener {
	private EditText slotEtv;
	private EditText positionYEtv, positionXEtv;
	private Map<String, ExpressIconLoader> expressIconLoaderMap = new HashMap<>();
	private String currentSlotId;
	private int positionY;
	private int positionX;
	int gravity = -1;
	private WeakReference<IconAd> iconAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_float);
	}

	@Override
	protected void init() {
		super.init();
		currentSlotId = sharedPreferences.getString(Const.KEY_FLOAT_SLOT, Const.SLOT_ID_FLOAT);
		positionY = sharedPreferences.getInt(Const.FLOAT_POSITION_Y, 100);
		positionX = sharedPreferences.getInt(Const.FLOAT_POSITION_X, 20);
	}

	@Override
	protected void initView() {
		slotEtv = findViewById(R.id.slot_etv);
		slotEtv.setText(currentSlotId);
		positionYEtv = findViewById(R.id.position_y_etv);
		positionYEtv.setText(String.valueOf(positionY));
		positionXEtv = findViewById(R.id.position_x_etv);
		positionXEtv.setText(String.valueOf(positionX));
		findViewById(R.id.request_ad_render_by_sdk).setOnClickListener(this);
		findViewById(R.id.request_ad_render_by_app).setOnClickListener(this);
		findViewById(R.id.jump_test).setOnClickListener(this);
		findViewById(R.id.ficon_hide).setOnClickListener(this);
		findViewById(R.id.ficon_popout).setOnClickListener(this);
		findViewById(R.id.ficon_gone).setOnClickListener(this);
		findViewById(R.id.ficon_visible).setOnClickListener(this);
		findViewById(R.id.float_destory).setOnClickListener(this);
		findViewById(R.id.fi_left).setOnClickListener(this);
		findViewById(R.id.fi_right).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (R.id.jump_test == id) {
			startActivity(new Intent(this, TestActivityA.class));
		} else if (R.id.fi_left == id) {
			gravity = 1;
		} else if (R.id.fi_right == id) {
			gravity = 2;
		} else if (id == R.id.ficon_hide) {
			if (iconAd != null) {
				IconAd iconAd1 = iconAd.get();
				if (iconAd1 == null) {
					Toast.makeText(FloatActivity.this, "icon 被回收", Toast.LENGTH_SHORT).show();
				} else {
					iconAd1.hidden();
				}
			} else {
				Toast.makeText(FloatActivity.this, "icon null", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.ficon_popout) {
			if (iconAd != null) {
				IconAd iconAd1 = iconAd.get();
				if (iconAd1 == null) {
					Toast.makeText(FloatActivity.this, "icon 被回收", Toast.LENGTH_SHORT).show();
				} else {
					iconAd1.popOut();
				}
			} else {
				Toast.makeText(FloatActivity.this, "icon null", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.ficon_gone) {
			if (iconAd != null) {
				IconAd iconAd1 = iconAd.get();
				if (iconAd1 == null) {
					Toast.makeText(FloatActivity.this, "icon 被回收", Toast.LENGTH_SHORT).show();
				} else {
					iconAd1.onUnVisible();
				}
			} else {
				Toast.makeText(FloatActivity.this, "icon null", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.ficon_visible) {
			if (iconAd != null) {
				IconAd iconAd1 = iconAd.get();
				if (iconAd1 == null) {
					Toast.makeText(FloatActivity.this, "icon 被回收", Toast.LENGTH_SHORT).show();
				} else {
					iconAd1.onVisible();
				}
			} else {
				Toast.makeText(FloatActivity.this, "icon null", Toast.LENGTH_SHORT).show();
			}
		} else if (R.id.float_destory == id) {
			for (ExpressIconLoader loader :
					expressIconLoaderMap.values()) {
				loader.destroy();
			}
		} else {
			String slot = slotEtv.getText().toString();
			if (TextUtils.isEmpty(slot)) {
				Toast.makeText(this, "请输入广告位", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!slot.equals(currentSlotId)) {
				currentSlotId = slot;
				sharedPreferences.edit().putString(Const.KEY_FLOAT_SLOT, slot).commit();
			}
			String pY = positionYEtv.getText().toString();
			String pX = positionXEtv.getText().toString();
			int py = 0;
			int px = 0;
			try {
				if (!TextUtils.isEmpty(pY)) {
					py = Integer.parseInt(pY);
				}
				if (!TextUtils.isEmpty(pX)) {
					px = Integer.parseInt(pX);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			AdParam.Builder builder = new AdParam.Builder(currentSlotId);
			if (py != positionY) {
				positionY = py;
				sharedPreferences.edit().putInt(Const.FLOAT_POSITION_Y, positionY).commit();
			}
			if (px != positionX) {
				positionX = px;
				sharedPreferences.edit().putInt(Const.FLOAT_POSITION_X, positionX).commit();
			}
			if (px != 0) {
				builder.setPositionX(positionX);
			}
			if (py != 0) {
				builder.setPositionY(positionY);
			}
			if (gravity != -1) {
				builder.setGravity(gravity);
			}
			AdParam adParam = builder.build();
			if (R.id.request_ad_render_by_sdk == id) {
				ExpressIconLoader expressIconLoader = expressIconLoaderMap.get(slot);
				if (expressIconLoader == null) {
					expressIconLoader = MobAd.createExpressIconLoader(this, adParam, new ExpressIconAdListener() {
						@Override
						public void onAdLoaded(IconAd iconAd) {
							LogUtils.d("onAdLoaded");
							FloatActivity.this.iconAd = new WeakReference<>(iconAd);
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
							LogUtils.d("onAdError code=" + code + ";msg=" + msg);
						}
					});
					expressIconLoaderMap.put(slot, expressIconLoader);
				}
				expressIconLoader.loadAd();

			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Iterator<ExpressIconLoader> iterator = expressIconLoaderMap.values().iterator();
		while (iterator.hasNext()) {
			iterator.next().destroy();
		}
	}
}