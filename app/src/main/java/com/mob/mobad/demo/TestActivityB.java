package com.mob.mobad.demo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TestActivityB extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_b);
		TextView viewById = (TextView) findViewById(R.id.textB);
		viewById.setText(String.format("页面路径%s", this.getComponentName().getClassName()));
		findViewById(R.id.but_goto3).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TestActivityB.this, TestActivityC.class));
			}
		});
	}
}