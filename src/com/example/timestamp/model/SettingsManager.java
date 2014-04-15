package com.example.timestamp.model;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.timestamp.Constants;

public class SettingsManager {
	
	
	public static void setStartTime(GregorianCalendar time, Activity activity) {
		//SharedPreferences settings = activity.getSharedPreferences(Constants.TIMESTAMP_SETTINGS, 0);
	    SharedPreferences.Editor editor = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0).edit();
	    editor.putLong(Constants.SETTING_START_TIME, time.getTimeInMillis());
	    editor.commit();
	}
	
	public static GregorianCalendar getStartTime(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0);
		GregorianCalendar time = new GregorianCalendar();
		time.setTimeInMillis(settings.getLong(Constants.SETTING_START_TIME, 0));
		return time;
	}
	
	public static void setCurrentProjectId(int projectId, Activity activity) {
		//SharedPreferences settings = activity.getSharedPreferences(Constants.TIMESTAMP_SETTINGS, 0);
	    SharedPreferences.Editor editor = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0).edit();
	    editor.putInt(Constants.SETTING_CURRENT_PROJECT, projectId);
	    editor.commit();
	}
	
	public static int getCurrentProjectId(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0);
		return settings.getInt(Constants.SETTING_CURRENT_PROJECT, -1);
	}
	
	public static void setIsTimerRunning(boolean isTimerRunning, Activity activity) {
		//SharedPreferences settings = activity.getSharedPreferences(Constants.TIMESTAMP_SETTINGS, 0);
	    SharedPreferences.Editor editor = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0).edit();
	    editor.putBoolean(Constants.SETTING_IS_TIMER_RUNNING, isTimerRunning);
	    editor.commit();
	}
	
	public static boolean getIsTimerRunning(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0);
		return settings.getBoolean(Constants.SETTING_IS_TIMER_RUNNING, false);
	}
	 /* setUser();
	 * getUser();
	 * setAutomationConfig();
	 * getAutomationConfig();
	 * setLatestSync();
	 * getLatestSync();
	 * setActiveProject();
	 * getActiveProject();
	 *  
	 */

}
