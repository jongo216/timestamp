package com.example.timestamp.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.content.Context;

//enum ProjectType {PRIVATE, PUBLIC, ALL}


public class DB {
	
	public DatabaseHelper dbHelper;
	
	private ArrayList<TimePost> timeList;		//mock-up
	private ArrayList<Project> projectList;		//mock-up
	
	//Constructor
	public DB(Context c){
		
		dbHelper = new DatabaseHelper(c);
		
		System.out.println("Connecting to remote DB...");
		//mock-up data

//		timeList = new ArrayList<TimePost>(20);
		projectList = new ArrayList<Project>(20);
//		//one week mock-up data
//		for(int i = 0; i < 5; ++i){
//			TimePost post = new TimePost(
//					new GregorianCalendar(2014, 4-1, 7+i, 8, 15),
//					new GregorianCalendar(2014, 4-1, 7+i, 17, 00), 1);
//			post.isSigned=true;
//			timeList.add(post);
//		}
//		//to projects to report to
		projectList.add(new Project(1 						/* id */,
									"Project 1"				/* name */,
									false					/* isPrivate */,
									"First mock-up project" /* Description */,
									"Jonas" 				/* owner */,
									"Joakim" 				/* customer */));

		
	}
	
	public void terminateDatabaseHelper(){
		dbHelper.closeDB();
	}
	
	public void set(TimePost time){
		
		//Put time into DB
		//timeList.add(time);
		dbHelper.createTimePost(time);
	}
	
	public TimePost getLatest(){
		return timeList.get(timeList.size()-1);
	}
	
	public int dbSize(){
		return timeList.size();
	}
	
	public void set(ArrayList<TimePost> times){
		
		//Put times into DB
		timeList.addAll(times);
	}
	
	public void set(Project project){
		
		//Put project into DB
		projectList.add(project);
	}
	
	public ArrayList<TimePost> getTime(int projectId){
		
		return dbHelper.getAllTimePost(projectId);
		
		
		/*
		//get all TimePostObjects associated with projectId
		ArrayList<TimePost> ret = new ArrayList<TimePost>(10);
		for(int i = 0; i < timeList.size(); ++i){
			if(timeList.get(i).projectId == projectId){
				ret.add(timeList.get(i));
			}
		}
		return ret;*/
	}
	/*
	public ArrayList<TimePost> getTimes(GregorianCalendar fromDate, GregorianCalendar toDate, int projectId){
		
		//Get several TimePost objects
		return new ArrayList<TimePost>();
	}
	
	public ArrayList<TimePost> getTimes(GregorianCalendar fromDate, GregorianCalendar toDate){
		
		//Get several TimePost objects
		return new ArrayList<TimePost>();
	}
	
	public ArrayList<TimePost> getUnsignedTimes(){
		
		//Get all unsigned TimePost objects
		return new ArrayList<TimePost>();
	}
	*/
	public ArrayList<Project> getAllProjects(){
		return projectList;
	}
	
	public Project getProject(int projectId){
	
		//Get project by id
		for(int i = 0; i < projectList.size(); ++i){
			if(projectList.get(i).getId() == projectId)
				return projectList.get(i);
		}
		return null;
	}

	public boolean empty(int pid) {
		// TODO Auto-generated method stub
		return dbHelper.empty(pid);
	}
	
	/*public ArrayList<Project> getProjects
			(ProjectType type, GregorianCalendar activeSinceDate)
	{
		ArrayList<Project> ret = new ArrayList<Project>();
		//return project by stuff
		for(int i = 0; i < projectList.size(); ++i){
			switch(type){
				case PRIVATE:{
					
				}
				case PUBLIC:{
					
				}
				case ALL:{
					
				}
			}
		}
		return ret;
	}*/
	/*
	public void sync(){
	
		//Sync remote and local data
	}
	*/
	
}
