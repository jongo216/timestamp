/**  Copyright (c) 2014, Group D in course TNM082
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the {organization} nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/

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
	
	public static void setExportEmailAddress(String emailAddress, Activity activity) {
	    SharedPreferences.Editor editor = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0).edit();
	    editor.putString(Constants.SETTING_EXPORT_EMAIL_ADDRESS, emailAddress);
	    editor.commit();
	}
	
	public static String getExportEmailAddress(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0);
		return settings.getString(Constants.SETTING_EXPORT_EMAIL_ADDRESS, null);
	}
	
	public static void setExportToggleCC(boolean toggleCC, Activity activity) {
	    SharedPreferences.Editor editor = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0).edit();
	    editor.putBoolean(Constants.SETTING_EXPORT_TOGGLE_CC, toggleCC);
	    editor.commit();
	}
	
	public static boolean getExportToggleCC(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0);
		return settings.getBoolean(Constants.SETTING_EXPORT_TOGGLE_CC, false);
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
