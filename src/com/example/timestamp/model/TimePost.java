package com.example.timestamp.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimePost {
	
	public Calendar startTime;
	public Calendar endTime;
	public int id;
	public Boolean isSigned;
	public String comment;
	public int projectId;
	public Boolean commentIsShared;
	
	
	TimePost(){
		
		//Tillsvidare
		startTime = new GregorianCalendar(2014,Calendar.FEBRUARY,28,8,24,56);
		endTime = new GregorianCalendar(2014,Calendar.FEBRUARY,28,18,24,56);
		//-----------
		
		id = -1;
		isSigned = false;
		comment = "";
		projectId = -1;
		commentIsShared = false;
		
	}
	
	
}
