package com.example.timestamp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapterMenu extends FragmentPagerAdapter {
	
	public TabsPagerAdapterMenu(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Change to Start fragment activity
			return new EditFragment();
		case 1:
			// Change to ConfirmReport fragment activity
			return new EditFragment();//new GamesFragment();
		case 2:
			// Change to ConfirmReport fragment activity
			return new EditFragment();//new MoviesFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}
	
	

}
