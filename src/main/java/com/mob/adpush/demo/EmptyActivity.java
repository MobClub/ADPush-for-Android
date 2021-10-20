package com.mob.adpush.demo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.mob.adpush.AdPush;

/**
 * @author panda
 * created at 2021/8/10 3:59 下午
 */
public class EmptyActivity extends AppCompatActivity {

	private EditText leftEdit, topEdit, rightEdit, bottomEdit;
	private RegionView regionView;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.empty_activity);

		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null)
			actionBar.hide();

		leftEdit = findViewById(R.id.left);
		topEdit = findViewById(R.id.top);
		rightEdit = findViewById(R.id.right);
		bottomEdit = findViewById(R.id.bottom);
		regionView = findViewById(R.id.rv);
	}

	public void setRegion(View view) {

		int l = strToInt(leftEdit.getText().toString());
		int t = strToInt(topEdit.getText().toString());
		int r = strToInt(rightEdit.getText().toString());
		int b = strToInt(bottomEdit.getText().toString());

		if(l < 0 || t < 0 || r < 0 || b < 0 || r < l || b < t)
			Toast.makeText(this, "请输入正确的格式， 值不能小于0且right值不能小于left值，bottom值不能小于top值", Toast.LENGTH_LONG).show();

		AdPush.setIconRegion(l, t, r, b);
		regionView.setRegion(l, t, r, b);

	}

	private int strToInt(String str){

		if(TextUtils.isEmpty(str))
			return 0;

		return Integer.valueOf(str);
	}

}
