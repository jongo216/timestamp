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
		setEndTimeNow();
		id = -1;
		isSigned = false;
		comment = "";
		projectId = -1;
		commentShared = false;
		
	}
	
	public TimePost(int year, int month, int day, int hour, int min){
		startTime = new GregorianCalendar(year, month-1, day, hour, min);
		setEndTimeNow();
		id = -1;
		isSigned = false;
		comment = "";
		projectId = -1;
		commentShared = false;
	}
	
	public TimePost(GregorianCalendar start, GregorianCalendar end, int project){
		startTime = start;
		endTime = end;
		projectId = project;
		
		id = -1;
		isSigned = false;
		comment = "";
		commentShared = false;
	}
	
	//for printing nice output
	public String printStartTime(){
		//System.out.println(new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(startTime.getTime()));
		if(startTime != null)
			return new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(startTime.getTime());
		return "";
	}
	//for printing nice output
	public String printEndTime(){
		//System.out.println(new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(endTime.getTime()));
		if(endTime != null)
			return new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(endTime.getTime());
		return "";
	}
	
	
	//for SQL use do not change!
	public String getStartTime(){
		if(startTime != null)
			return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(startTime.getTime());
		return "";
	}
	//for SQL use do not change!
	public String getEndTime(){
		if(endTime != null)
			return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(endTime.getTime());
		return "";
	}
	
	public void setStartTime(GregorianCalendar time){ startTime = time; }
	
	public void setEndTime(GregorianCalendar time)	{ endTime = time; }	
	
	public void setProjectId(int id){ projectId = id; }
	
	public double getWorkedHours(){
		long start = startTime.getTimeInMillis();
		long end = endTime.getTimeInMillis();
		
		return (double)(end-start)/(1000*60*60); //from ms -> s -> min -> h
	}
	
	public void setStartYear(int year){startTime.set(Calendar.YEAR, year);}
	public void setStartMonth(int month){startTime.set(Calendar.MONTH, month);}
	public void setStartDay(int day){startTime.set(Calendar.DAY_OF_MONTH, day);}
	public void setStartHour(int hour){startTime.set(Calendar.HOUR_OF_DAY, hour);}
	public void setStartMinute(int minute){startTime.set(Calendar.MINUTE, minute);}
	
	public void setEndYear(int year){endTime.set(Calendar.YEAR, year);}
	public void setEndMonth(int month){endTime.set(Calendar.MONTH, month);}
	public void setEndDay(int day){endTime.set(Calendar.DAY_OF_MONTH, day);}
	public void setEndHour(int hour){endTime.set(Calendar.HOUR_OF_DAY, hour);}
	public void setEndMinute(int minute){endTime.set(Calendar.MINUTE, minute);}
	
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
		setEndHour(endTime.get(Calendar.HOUR_OF_DAY)+i);
		
	}
	
	
	
	
}