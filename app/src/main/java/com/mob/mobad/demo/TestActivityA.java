package com.mob.mobad.demo;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivityA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity_a);
		TextView viewById = (TextView) findViewById(R.id.textA);
		viewById.setText(String.format("页面路径%s", this.getComponentName().getClassName()));
	}
}