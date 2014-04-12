package com.example.timestamp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainSettingsActivity extends FragmentActivity implements
ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapterMenu mAdapter;
	private ActionBar actionBar;

	// Tab titles
	private String[] tabs = { "Edit", "Create", "Remove" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_projectsettings);
		
		//Stylear actionbar
		ActionBar actionBarTop = getActionBar(); 
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBarTop.setCustomView(R.layout.actionbar);
		actionBarTop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#11ffffff")));
		
		//draw actionbar
		

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager2);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapterMenu(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		
		 // on swiping the viewpager make respective tab selected
		 
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activitybar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		// Handle action bar actions click
		Log.d("felLog", "onOptionItemsSelected");
		switch (item.getItemId()) {
		
		case R.id.action_settings:

			return true;
		case R.id.action_yoursettings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_edit:
			Intent intent2 = new Intent(this, MainSettingsActivity.class);
			startActivity(intent2);
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}
