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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;

public class TimePost {
	
	public GregorianCalendar startTime;
	public GregorianCalendar endTime;
	public int id;
	public Boolean isSigned;
	public String comment;
	public int projectId; //GET FROM R.LAYOUT.PROJECT ID ELLER LIKNANDE.......
	public Boolean commentShared;
	
	public TimePost(){
		
		setStartTimeNow();
		setEndTimeNow();
		id = 0;
		isSigned = false;
		comment = "";
		projectId = 0;
		commentShared = false;
		
	}
	
	public TimePost(int year, int month, int day, int hour, int min){
		startTime = new GregorianCalendar(year, month-1, day, hour, min);
		id = 0;
		setEndTimeNow();
		isSigned = false;
		comment = "DEFAULT COMMENT";
		projectId = 0;
		commentShared = false;
	}
	
	public TimePost(GregorianCalendar start, GregorianCalendar end, int project){
		startTime = start;
		endTime = end;
		projectId = project;
		
		id = 0;
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
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime.getTime());
		return "";
	}
	//for SQL use do not change!
	public String getEndTime(){
		if(endTime != null)
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime.getTime());
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
	
	public String getWorkedHoursFormated(){
		double time = getWorkedHours();
		DecimalFormat hourFormat = new DecimalFormat("#.00");		
		return hourFormat.format(time);
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
		startTime = new GregorianCalendar();
	}
	
	public void setEndTimeNow(){
		endTime = new GregorianCalendar();
	}
	
	public void setEndTimeRandom(){
		endTime = new GregorianCalendar();
		
		int i = (int)(Math.random()*3)+3;
		//Log.d("MESSAGE", "rand"+i);
		Log.d("MESSAGE",Integer.toString(endTime.get(Calendar.HOUR_OF_DAY)+i));
		setEndHour(endTime.get(Calendar.HOUR_OF_DAY)+i);
		
	}
	
	
	
	
}