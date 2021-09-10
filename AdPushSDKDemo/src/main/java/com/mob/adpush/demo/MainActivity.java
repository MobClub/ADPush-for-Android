package com.mob.adpush.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mob.adpush.AdPush;
import com.mob.adpush.MobAdListener;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = findViewById(R.id.switchs);

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean open = sharedPreferences.getBoolean("open", true);
		button.setText(String.format(button.getText().toString(), open));

		AdPush.disableADPushOnForeground(true, 2);
//		AdPush.setIconRegion(0,0,1000,1500);
		AdPush.setMobAdListener(new MobAdListener() {
			@Override
			public void onAdClick() {
				Log.e(TAG, "onAdClick: ");
				Toast.makeText(MainActivity.this, "广告点击", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdExposure() {
				Log.e(TAG, "onAdExposure: ");
				Toast.makeText(MainActivity.this, "广告曝光", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdClose() {
				Log.e(TAG, "onAdClose: ");
				Toast.makeText(MainActivity.this, "广告关闭", Toast.LENGTH_SHORT).show();
			}
		});
	}


	public void switchs(View view) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean open = !sharedPreferences.getBoolean("open", true);
		sharedPreferences.edit().putBoolean("open", open).apply();
	}

	public void gotos(View view) {
		Intent intent = new Intent(this, EmptyActivity.class);
		startActivity(intent);
	}
}