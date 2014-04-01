package com.example.timestamp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Start extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	public void activitySwitchOne(View v){
		setContentView(R.layout.activity_two);
	}
	public void activitySwitchTwo(View v){
		setContentView(R.layout.activity_start);
	}
}
