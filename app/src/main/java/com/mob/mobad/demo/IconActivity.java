package com.mob.mobad.demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.ad.AdParam;
import com.mob.ad.MobAd;
import com.mob.ad.NativeAd;
import com.mob.ad.NativeAdListener;
import com.mob.ad.NativeIconLoader;
import com.mob.mobad.demo.utils.Const;
import com.mob.mobad.demo.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class IconActivity extends BaseActivity implements View.OnClickListener {
	private EditText slotEtv;
	private FrameLayout fa_iconContainer;
	private LinearLayout la_container;
	private RelativeLayout ra_container;
	private RadioGroup rad_gp;
	private Map<String, NativeIconLoader> nativeIconLoaderHashMap = new HashMap<>();
	private String currentSlotId;

	private int iconW = 0, iconH = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icon);
	}

	@Override
	protected void init() {
		super.init();
		currentSlotId = sharedPreferences.getString(Const.KEY_ICON_SLOT, Const.SLOT_ID_ICON);
	}

	@Override
	protected void initView() {
		slotEtv = findViewById(R.id.slot_etv);
		slotEtv.setText(currentSlotId);
		fa_iconContainer = findViewById(R.id.fa_container);
		la_container = findViewById(R.id.la_container);
		ra_container = findViewById(R.id.ra_container);
		rad_gp = findViewById(R.id.rad_gp);
		findViewById(R.id.request_ad_render_by_sdk).setOnClickListener(this);
		findViewById(R.id.request_ad_render_by_app).setOnClickListener(this);
		findViewById(R.id.jump_test).setOnClickListener(this);
		findViewById(R.id.but_icon_w).setOnClickListener(this);
		findViewById(R.id.but_icon_h).setOnClickListener(this);
		findViewById(R.id.icon_destory).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (R.id.jump_test == id) {
			startActivity(new Intent(this, TestActivityA.class));
		} else if (R.id.icon_destory == id) {
			for (NativeIconLoader loader :
					nativeIconLoaderHashMap.values()) {
				loader.destroy();
			}
		} else if (R.id.but_icon_w == id) {
			try {
				iconW = Integer.parseInt(((EditText) findViewById(R.id.edit_icon_w)).getText().toString());
			} catch (Throwable t) {
				iconW = 0;
			}
		} else if (R.id.but_icon_h == id) {
			try {
				iconH = Integer.parseInt(((EditText) findViewById(R.id.edit_icon_h)).getText().toString());
			} catch (Throwable throwable) {
				iconH = 0;
			}
		} else {
			String slot = slotEtv.getText().toString();
			if (TextUtils.isEmpty(slot)) {
				Toast.makeText(this, "请输入广告位", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!slot.equals(currentSlotId)) {
				currentSlotId = slot;
				sharedPreferences.edit().putString(Const.KEY_ICON_SLOT, slot).commit();
			}

			AdParam adParam = new AdParam.Builder(currentSlotId)
					.build();
			if (R.id.request_ad_render_by_app == id) {
				NativeIconLoader nativeIconLoader = nativeIconLoaderHashMap.get(slot);
				if (nativeIconLoader == null) {
					nativeIconLoader = MobAd.createNativeIconLoader(this, adParam, new NativeAdListener() {
						@Override
						public void onNativeAdLoad(NativeAd nativeAd) {
							LogUtils.d("onNativeAdLoad");
							View view = nativeAd.getView();

							ViewGroup viewGroup = (ViewGroup) view;
							//获取icon文本控件
							TextView mob_ad_tvContent = (TextView) viewGroup.findViewWithTag("mob_ad_tvContent");
							if (mob_ad_tvContent != null) {
								RelativeLayout.LayoutParams tvCParams = (RelativeLayout.LayoutParams) mob_ad_tvContent.getLayoutParams();
							}


							//获取icon图片控件
							ImageView mob_ad_ivImg = (ImageView) viewGroup.findViewWithTag("mob_ad_ivImg");
							RelativeLayout.LayoutParams ivIParams = (RelativeLayout.LayoutParams) mob_ad_ivImg.getLayoutParams();
							if (iconH != 0) {
								ivIParams.height = iconH;
							}
							if (iconW != 0) {
								ivIParams.width = iconW;
							}

							mob_ad_ivImg.setLayoutParams(ivIParams);

							//获取关闭按钮控件
							ImageView mob_ad_ivClose = (ImageView) viewGroup.findViewWithTag("mob_ad_ivClose");
							if (mob_ad_ivClose != null) {
								//有关闭按钮
//								RelativeLayout.LayoutParams ivCParams = (RelativeLayout.LayoutParams) mob_ad_ivClose.getLayoutParams();
//								ivCParams.width = 50;
//								ivCParams.height = 50;
//								mob_ad_ivClose.setLayoutParams(ivCParams);
							} else {
								//无关闭按钮
							}

							//获取广告logo控件
							ImageView mob_ad_ivLogo = (ImageView) viewGroup.findViewWithTag("mob_ad_ivLogo");
							if (mob_ad_ivLogo != null) {
								//有广告logo
//								RelativeLayout.LayoutParams ivLParams = (RelativeLayout.LayoutParams) mob_ad_ivLogo.getLayoutParams();
//								ivLParams.height = 50;
//								ivLParams.width = 50;
//								mob_ad_ivLogo.setLayoutParams(ivLParams);
							} else {
								//无广告logo
							}

							int checkedRadioButtonId = rad_gp.getCheckedRadioButtonId();
							if (checkedRadioButtonId == R.id.rb_ra) {
								ra_container.addView((View) viewGroup);
							} else if (checkedRadioButtonId == R.id.rb_li) {
								la_container.addView((View) viewGroup);
							} else if (checkedRadioButtonId == R.id.rb_fa) {
								fa_iconContainer.addView((View) viewGroup);
							}
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
					nativeIconLoaderHashMap.put(slot, nativeIconLoader);
				}
				nativeIconLoader.loadAd();

			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}