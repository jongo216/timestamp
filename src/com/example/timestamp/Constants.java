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

package com.example.timestamp;

public class Constants {
	
	public static final boolean DEMO_MODE = true;
	
	//Constants for settings
	public static final String SETTINGS_TIMESTAMP = "timeStampSettingsFile";
	public static final String SETTING_START_TIME = "startTime";
	public static final String SETTING_CURRENT_PROJECT = "currentProject";
	public static final String SETTING_IS_TIMER_RUNNING = "isTimerRunning";
	public static final String SETTING_EXPORT_EMAIL_ADDRESS = "exportEmailAddress";
	public static final String SETTING_EXPORT_TOGGLE_CC = "exportToggleCC";
	public static final String SETTING_EXPORT_TOGGLE_COMMENTS = "exportToggleComments";
	public static final String[] WEEK_DAY_STRINGS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	public static final String TIME_POST_ID = "timePostId";
	public static final String PROJECT_ID = "projectId";
	
	//Widget
	public static final String WIDGET_TIMER_ACTION = "com.example.timestamp.widgetTimerAction";
	public static final String WIDGET_ID = "com.example.timestamp.widgetId"; 
	
	//Alarm/notifications
	public static final int NOTIFICATION_ID = 7;
	public static final String NOTIFY_REMIND_TO_SEND_REPORT_ACTION = "com.example.timestamp.notifyRemindToSendReportAction";
	public static final String NOTIFY_REMIND_TO_STOP_WORKING_ACTION = "com.example.timestamp.notifyRemindToStopWorkingAction";
	public static final String SEND_TIMES_ACTION = "com.example.timestamp.sendTimesAction";
}
