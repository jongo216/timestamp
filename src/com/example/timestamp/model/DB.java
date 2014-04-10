package com.example.timestamp.model;

import java.util.ArrayList;

public class DB {
	private ArrayList<TimePost> timeList;		//mock-up
	private ArrayList<Project> projectList;		//mock-up
	
	//Constructor
	public DB(){
		System.out.println("Connecting to remote DB...");
		//mock-up data
		timeList = new ArrayList<TimePost>(20);
		projectList = new ArrayList<Project>(20);
		TimePost post = new TimePost();
		//one week mock-up data
		post.startTime.set(2014, 4, 7, 8, 15);
		post.endTime.set  (2014, 4, 7, 17, 00);
		timeList.add(post);						//day 1
		post.startTime.set(2014, 4, 8, 8, 15);
		post.endTime.set  (2014, 4, 8, 17, 00);
		timeList.add(post);						//day 2
		post.startTime.set(2014, 4, 9, 8, 15);
		post.endTime.set  (2014, 4, 9, 17, 00);
		timeList.add(post);						//day 3
		post.startTime.set(2014, 4, 10, 8, 15);
		post.endTime.set  (2014, 4, 10, 17, 00);
		timeList.add(post);						//day 4
		post.startTime.set(2014, 4, 11, 8, 15);
		post.endTime.set  (2014, 4, 12, 17, 00);
		timeList.add(post);						//day 5
	}
	
	public void set(TimePost time){
		
		//Put time into DB
		timeList.add(time);
	}
	
	public TimePost getLatest(){
		return timeList.get(timeList.size()-1);
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
		
		//get all TimePostObjects associated with projectId
		ArrayList<TimePost> ret = new ArrayList<TimePost>(10);
		for(int i = 0; i < timeList.size(); ++i){
			if(timeList.get(i).projectId == projectId){
				ret.add(timeList.get(i));
			}
		}
		return ret;
	}
	/*
	public ArrayList<TimePost> getTimes(Calendar fromDate, Calendar toDate, int projectId){
		
		//Get several TimePost objects
		return new ArrayList<TimePost>();
	}
	
	public ArrayList<TimePost> getTimes(Calendar fromDate, Calendar toDate){
		
		//Get several TimePost objects
		return new ArrayList<TimePost>();
	}
	
	public ArrayList<TimePost> getUnsignedTimes(){
		
		//Get all unsigned TimePost objects
		return new ArrayList<TimePost>();
	}
	*/
	public Project getProject(int projectId){
	
		//Get project by id
		return new Project();
	}
	/*
	public ArrayList<Project> getProjects(Boolean onlyPrivate, Boolean onlyShared, Date activeSinceDate){
	
		//return project by stuff
		return new ArrayList<Project>();
	}
	
	public void sync(){
	
		//Sync remote and local data
	}
	
	
	*/
	
}
