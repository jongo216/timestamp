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
