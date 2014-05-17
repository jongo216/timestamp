package com.example.timestamp.appwidget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.timestamp.*;
import com.example.timestamp.model.DB;
import com.example.timestamp.model.SettingsManager;
import com.example.timestamp.model.TimePost;
import com.example.timestamp.services.TimedReminder;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {
	
	
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
			
	}
	
	
	
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        
		final int N = appWidgetIds.length;
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0);
		
		boolean timerRunning = settings.getBoolean(Constants.SETTING_IS_TIMER_RUNNING, false);
		long now = new GregorianCalendar().getTimeInMillis();
		long start = settings.getLong(Constants.SETTING_START_TIME, 0);
		int currentProject = settings.getInt(Constants.SETTING_CURRENT_PROJECT, -1);
		String project = "No project"; 
		
		if (currentProject != -1) {
			DB db = new DB(context);
			project = db.getProject(currentProject).getName();
		}
			
		
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
        	
        	AppWidgetProviderInfo info = appWidgetManager.getAppWidgetInfo(appWidgetIds[i]);
        	if (info != null) {
        		
	            int appWidgetId = appWidgetIds[i];
	
	            // Create intent to start main activity
	            Intent intent = new Intent(context, MainActivity.class);
	            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
	
	            //Create intent to handle button
	            Intent timerIntent = new Intent(context, Widget.class);
	            timerIntent.setAction(Constants.WIDGET_TIMER_ACTION);
	            //intent.putExtra(Constants.WIDGET_ID, appWidgetId);
	            PendingIntent pendingTimerIntent = PendingIntent.getBroadcast(context, 0, timerIntent, 0);
	            
	            
	            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.timestamp_appwidget);
	            views.setCharSequence(R.id.widget_current_project_text, "setText", project);
	            views.setOnClickPendingIntent(R.id.widget_current_project_button, pendingIntent);
	            
	            views.setOnClickPendingIntent(R.id.widget_button, pendingTimerIntent);
	            
	            if (timerRunning) {
	            	views.setImageViewResource(R.id.widget_button, R.drawable.clock_green);
	            	views.setChronometer(R.id.widget_timer, SystemClock.elapsedRealtime() - now + start, null, true);
		        }
	            else {
	            	views.setImageViewResource(R.id.widget_button, R.drawable.clock_white);
	            	views.setChronometer(R.id.widget_timer, SystemClock.elapsedRealtime(), null, false);
	            }
	            
	            
	            // Tell the AppWidgetManager to perform an update on the current app widget
	            appWidgetManager.updateAppWidget(appWidgetId, views);
        	}
        }
    }
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (intent.getAction() == Constants.WIDGET_TIMER_ACTION)
		{
			DB db = new DB(context);
			SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_TIMESTAMP, 0);
			boolean timerRunning = settings.getBoolean(Constants.SETTING_IS_TIMER_RUNNING, false);
			
			if(timerRunning){
				SharedPreferences.Editor editor = settings.edit();
			    editor.putBoolean(Constants.SETTING_IS_TIMER_RUNNING, false);
			    editor.commit();
			    
				TimePost p = new TimePost();
				p.setProjectId(settings.getInt(Constants.SETTING_CURRENT_PROJECT, -1));
				p.startTime.setTimeInMillis(settings.getLong(Constants.SETTING_START_TIME, 0));
				p.setEndTimeNow();
				db.set(p);
				
				TimedReminder.CancelFullWorkDayReminder(context);
				TimedReminder.RemindToReportTimes(context, p.projectId);
			}
			else{
				SharedPreferences.Editor editor = settings.edit();
			    editor.putBoolean(Constants.SETTING_IS_TIMER_RUNNING, true);
			    editor.putLong(Constants.SETTING_START_TIME, new GregorianCalendar().getTimeInMillis());
			    editor.commit();
			    
			    //Set reminder after 8 hours worked
				GregorianCalendar startOfDay = new GregorianCalendar();
				startOfDay.set(Calendar.HOUR_OF_DAY,  0);
				startOfDay.set(Calendar.MINUTE, 0);
				startOfDay.set(Calendar.SECOND, 0);
				startOfDay.set(Calendar.MILLISECOND, 0);
				ArrayList<TimePost> times = db.getByInterval(startOfDay, new GregorianCalendar());
				long timeWorked = 0;
				for (TimePost t : times)
					timeWorked += t.getWorkedHours() * 3600 * 1000;
				long timeLeft = Constants.DEMO_MODE ? 20 * 1000 : 3600 * 8 * 1000 - timeWorked;
				TimedReminder.RemindAfterFullWorkDay(context, timeLeft);
			}
			
			updateWidget(context);
			
		}
		
		super.onReceive(context, intent);
		
	}
	
	
	
	//Call this from app anytime an action should update the widget 
	public static void updateWidget(Context context) {
		Intent intent = new Intent(context, Widget.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
		// since it seems the onUpdate() is only fired on that:
		int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, Widget.class));
		
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
		context.sendBroadcast(intent);
	}
	
}
