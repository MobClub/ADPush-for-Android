package com.mob.mobad.demo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TestActivityC extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity_c);
		TextView viewById = (TextView) findViewById(R.id.textc);
		viewById.setText(String.format("页面路径%s", this.getComponentName().getClassName()));
		findViewById(R.id.back_mian).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TestActivityC.this, MainActivity.class));
			}
		});
	}
}