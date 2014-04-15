package com.example.timestamp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;

public class TimePost {
	
	public Calendar startTime;
	public Calendar endTime;
	public int id;
	public Boolean isSigned;
	public String comment;
	public int projectId; //GET FROM R.LAYOUT.PROJECT ID ELLER LIKNANDE.......
	public Boolean commentShared;
	
	public TimePost(){
		
		setStartTimeNow();
		id = -1;
		isSigned = false;
		comment = "";
		projectId = -1;
		commentShared = false;
		
	}
	
	TimePost(int year, int month, int day, int hour, int min){
		startTime = new GregorianCalendar(year, month-1, day, hour, min);
		id = -1;
		isSigned = false;
		comment = "";
		projectId = -1;
		commentShared = false;
	}
	
	public String printStartTime(){
		//System.out.println(new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(startTime.getTime()));
		return new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(startTime.getTime());
	}
	public String printEndTime(){
		//System.out.println(new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(endTime.getTime()));
		return new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(endTime.getTime());
	}
	
	public String getStartTime(){
		return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(startTime.getTime());
	}
	
	public String getEndTime(){
		return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(endTime.getTime());
	}
	
	public void setStartTime(int year, int month, int day, int hour, int min){
		/*startTime.set(Calendar.YEAR, year);
		startTime.set(Calendar.MONTH, month);
		startTime.set(Calendar.DAY_OF_MONTH, day);
		startTime.set(Calendar.HOUR_OF_DAY, hour);
		startTime.set(Calendar.MINUTE, min);*/
		startTime = new GregorianCalendar(year, month-1, day, hour, min);
		
	}
	public void setEndTime(int year, int month, int day, int hour, int min){
		/*endTime.set(Calendar.YEAR, year);
		endTime.set(Calendar.MONTH, month);
		endTime.set(Calendar.DAY_OF_MONTH, day);
		endTime.set(Calendar.HOUR_OF_DAY, hour);
		endTime.set(Calendar.MINUTE, min);*/
		endTime = new GregorianCalendar(year, month-1, day, hour, min);
	}
	
	public double getWorkedHours(){
		int hourS = startTime.get(Calendar.HOUR_OF_DAY);
		int hourE = endTime.get(Calendar.HOUR_OF_DAY);
		
		return (double)(hourE-hourS);
	}
	
	public void startYear(int year){startTime.set(Calendar.YEAR, year);}
	public void startMonth(int month){startTime.set(Calendar.MONTH, month);}
	public void startDay(int day){startTime.set(Calendar.DAY_OF_MONTH, day);}
	public void startHour(int hour){startTime.set(Calendar.HOUR_OF_DAY, hour);}
	public void startMinute(int minute){startTime.set(Calendar.MINUTE, minute);}
	
	public void endYear(int year){endTime.set(Calendar.YEAR, year);}
	public void endMonth(int month){endTime.set(Calendar.MONTH, month);}
	public void endDay(int day){endTime.set(Calendar.DAY_OF_MONTH, day);}
	public void endHour(int hour){endTime.set(Calendar.HOUR_OF_DAY, hour);}
	public void endMinute(int minute){endTime.set(Calendar.MINUTE, minute);}
	
	public void setStartTimeNow(){
		startTime = Calendar.getInstance();
	}
	
	public void setEndTimeNow(){
		endTime = Calendar.getInstance();
	}
	
	public void setEndTimeRandom(){
		endTime = Calendar.getInstance();
		
		int i = (int)(Math.random()*3)+3;
		//Log.d("MESSAGE", "rand"+i);
		Log.d("MESSAGE",Integer.toString(endTime.get(Calendar.HOUR_OF_DAY)+i));
		endHour(endTime.get(Calendar.HOUR_OF_DAY)+i);
		
	}
	
	
	
	
}