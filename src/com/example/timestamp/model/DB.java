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

import android.content.Context;
import android.util.Log;


public class DB {
	
	public DatabaseHelper dbHelper;

	//Constructor
	public DB(Context c){
		
		dbHelper = new DatabaseHelper(c);
	}
	
	public void terminateDatabaseHelper(){
		dbHelper.closeDB();
	}
	
	public void set(TimePost time){
		
		//Put time into DB
		dbHelper.setTimePost(time);
	}
	
	public void set(Project project){
		
		//Put project into DB
		dbHelper.setProject(project);
	}
	
	public ArrayList<TimePost> getTimePosts(int projectId){	
		return dbHelper.getAllTimePost(projectId);
	}
	
	public ArrayList<TimePost> getTimePosts(){	
		return dbHelper.getAllTimePost();
	}
	
	public TimePost getTimePost(int id){	
		return dbHelper.getTimePost(id);
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
	*/
	
	public ArrayList<TimePost> getUnsignedTimes(){
		//Get all unsigned TimePost objects
		return dbHelper.getUnsignedTimes();
	}
	
	public ArrayList<Project> getAllProjects(){
		return dbHelper.getAllProjects();
	}
	
	public Project getProject(int projectId){
		//Get project by id
		return dbHelper.getProject(projectId);
	}

	public boolean timePostEmpty(int pid) {
		return dbHelper.timePostEmpty(pid);
	}
	
	public boolean projectsEmpty() {
		return dbHelper.projectsEmpty();
	}
	// Delete time post by passing id
	public void deleteTimePost(int tid) {
		dbHelper.deleteTimePost(tid);
	}
	// Delete time post by passing TimePost
	public void deleteTimePost(TimePost t) {
		dbHelper.deleteTimePost(t.id);
	}
	
	// Delete project by passing id
	public void deleteProject(int pid) {
		dbHelper.deleteProject(pid);
	}
	// Delete project by passing project
	public void deleteProject(Project p) {
		dbHelper.deleteProject(p.getId());
	}


	
	/*
	public void sync(){
	
		//Sync remote and local data
	}
	*/
	
}
