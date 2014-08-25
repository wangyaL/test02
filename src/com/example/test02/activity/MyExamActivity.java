package com.example.test02.activity;

import com.example.test02.R;
import com.example.test02.R.layout;
import com.example.test02.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyExamActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_exam);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_exam, menu);
		return true;
	}

}
