package com.example.timestamp.services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.timestamp.Constants;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TimedReminder {
	
	public static void RemindAfterFullWorkDay(Context context, long timeLeft)
	{
		Intent intent = new Intent(context, ReminderNotification.class);
		intent.setAction(Constants.NOTIFY_REMIND_TO_STOP_WORKING_ACTION);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		
        am.set(AlarmManager.RTC, new GregorianCalendar().getTimeInMillis() + timeLeft, pendingIntent);
	}
	
	public static void CancelFullWorkDayReminder(Context context)
	{
		Intent intent = new Intent(context, ReminderNotification.class);
		intent.setAction(Constants.NOTIFY_REMIND_TO_STOP_WORKING_ACTION);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pendingIntent);
	}
	
	
	public static void RemindToReportTimes(Context context, int projectId)
	{
		Intent intent = new Intent(context, ReminderNotification.class);
		intent.setAction(Constants.NOTIFY_REMIND_TO_SEND_REPORT_ACTION + projectId);
		intent.putExtra(Constants.PROJECT_ID, projectId);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		
		GregorianCalendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
        if (Constants.DEMO_MODE)
        	c.add(Calendar.SECOND, 10);
        else {
        	c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        	c.set(Calendar.HOUR_OF_DAY, 15);
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        }
        long firstTime = c.getTimeInMillis();
        
        // Schedule the alarm!
        am.set(AlarmManager.RTC, firstTime, pendingIntent);
    
	}
	
	
}
