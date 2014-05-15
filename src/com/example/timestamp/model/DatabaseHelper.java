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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();
 
    // Database Version
    private static final int DATABASE_VERSION = 23;
 
    // Database Name
    private static final String DATABASE_NAME = "TimeStamp";
 
    // Table Names
    private static final String TABLE_TIMEPOST = "TimePost";
    private static final String TABLE_PROJECTS = "Projects";
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_PROJECT_USERS = "Project_Users";
    
    // Common column names
    private static final String KEY_PID = "pID";
    private static final String KEY_MAIL_ADRESS = "mailAdress";
 
    // TimePost Table - column names
    private static final String KEY_TID = "tID";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_END_TIME = "endTime";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_IS_SIGNED = "isSigned";
    private static final String KEY_COMMENT_SHARED = "commentShared";
    // Projects table - column names
    private static final String KEY_PROJECT_NAME = "projectName";
    private static final String KEY_PROJECT_OWNER = "projectOwner";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CUSTOMER = "customer";
    // USER Table - column names
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PASSWORD = "userPassword";
 
    
    // Table Create Statements
   private static final String CREATE_TABLE_TIME_STAMP = "CREATE TABLE " + TABLE_TIMEPOST
            + " (" + KEY_TID + " INTEGER PRIMARY KEY, " 
    		+ KEY_PID + " INTEGER, "
            + KEY_START_TIME + " DATETIME, "
    		+ KEY_END_TIME +" DATETIME, "
    		+ KEY_COMMENT +" VARCHAR, " 
    		+ KEY_IS_SIGNED +" TINYINT, " 
    		+ KEY_COMMENT_SHARED +" TINYINT, " 
    		+ "FOREIGN KEY ("+KEY_PID+") REFERENCES "+TABLE_PROJECTS+"("+KEY_PID+") ON DELETE CASCADE)";
 
   
    // Projects table create statement
    private static final String CREATE_TABLE_PROJECTS = "CREATE TABLE " + TABLE_PROJECTS
            + " (" + KEY_PID + " INTEGER PRIMARY KEY, "
            + KEY_PROJECT_NAME + " VARCHAR, "
            + KEY_PROJECT_OWNER + " VARCHAR, "
    		+ KEY_DESCRIPTION +" VARCHAR, " 
    		+ KEY_CUSTOMER +" VARCHAR)";
    //+ "FOREIGN KEY ("+KEY_PROJECT_OWNER+") REFERENCES "+TABLE_USERS+"("+KEY_MAIL_ADRESS+") ON DELETE CASCADE)"
    
 // Users  table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + " (" + KEY_MAIL_ADRESS + " VARCHAR PRIMARY KEY, "
    		+ KEY_USER_NAME + " VARCHAR, " 
            + KEY_USER_PASSWORD + " VARCHAR)";
    
 // Users projects table
    private static final String CREATE_TABLE_PROJECT_USER = "CREATE TABLE "
            + TABLE_PROJECT_USERS + " (" + KEY_PID + " INTEGER, "
    		+ KEY_MAIL_ADRESS + " VARCHAR, PRIMARY KEY("+KEY_MAIL_ADRESS+","+KEY_PID+"))";
 
   
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_TIME_STAMP);
        db.execSQL(CREATE_TABLE_PROJECTS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PROJECT_USER);
    }
  
    //Called if versionnumber is changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
    	Log.d(LOG, "Update req, removing database..." );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMEPOST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_USERS);
        Log.d(LOG,"Done creating new db" );
        
        // create new tables
        onCreate(db);
    }

    
    /* ----------- TIMEPOST FUNCTIONS -------------- */
    public void setTimePost(TimePost timePost) {
       
    	if(timePost.id == 0){
    		SQLiteDatabase db = this.getWritableDatabase();
    	 
	        ContentValues values = new ContentValues();
	        
	        values.put(KEY_PID, timePost.projectId);
	        values.put(KEY_START_TIME, timePost.getStartTime());
	        values.put(KEY_END_TIME, timePost.getEndTime());
	        values.put(KEY_COMMENT, timePost.comment);
	        values.put(KEY_IS_SIGNED, timePost.getIsSigned());
	        values.put(KEY_COMMENT_SHARED, timePost.commentShared);
	 
	        // insert row
	        db.insert(TABLE_TIMEPOST, null, values);
	        db.close();
    	}
    	else{
    		String myQuery = 
    		"UPDATE "+TABLE_TIMEPOST+" SET "+
    		KEY_START_TIME+"='"+timePost.getStartTime()+"', "+
    		KEY_END_TIME+"='"+timePost.getEndTime()+"', "+
    		KEY_COMMENT+"='"+timePost.comment+"', "+
    		KEY_IS_SIGNED+"='"+timePost.getIsSigned()+"', "+
    		KEY_COMMENT_SHARED+"='"+timePost.commentShared+
    		"' WHERE "+KEY_TID+"="+timePost.id;
    		
	    	try {
	    		SQLiteDatabase db = this.getWritableDatabase();
	    		db.execSQL(myQuery);
	    		db.close();
			} 
	    	catch (SQLException e) {
				Log.d(LOG,e.toString());
			}
    	}
    	Log.d("EditReport","Database.issigned? = "+timePost.isSigned);
    	
    }
    
    public TimePost getTimePost(int tid){
    	
    	String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST+" WHERE "+KEY_TID+"="+tid;
    	TimePost temp = new TimePost();
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
    		
        	if (c.getCount() != 0){
        		c.moveToFirst();
        			
        		String st = c.getString(c.getColumnIndex(KEY_START_TIME));
        		temp.setStartTimeByString(st);
        			
        		st = c.getString(c.getColumnIndex(KEY_END_TIME));
        		temp.setEndTimeByString(st);
        			
        		temp.id = c.getInt(c.getColumnIndex(KEY_TID));
        			
        		temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
        			
        		temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
        			
        		temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
        			
        		temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));	
        	}
        	else{
        		Log.d(LOG, "Error getting timepost info");
        	}
                
    	}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
    	
    	return temp;
    }
    
    public ArrayList<TimePost> getAllTimePost(int pid){
    	ArrayList<TimePost> ret = new ArrayList<TimePost>();
    	String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST+" WHERE "+KEY_PID+"="+pid;
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
        	
        	if (c.getCount() != 0){
        		c.moveToFirst();
        		
        		do {
        			TimePost temp = new TimePost();
        			
        			String st = c.getString(c.getColumnIndex(KEY_START_TIME));
        			temp.setStartTimeByString(st);
        			
        			st = c.getString(c.getColumnIndex(KEY_END_TIME));
        			temp.setEndTimeByString(st);
        			
        			temp.id = c.getInt(c.getColumnIndex(KEY_TID));
        			
        			temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
        			
        			temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
        			
        			temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
        			
        			temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));
        			
        			ret.add(temp);
                    
                } while (c.moveToNext());
        	}
        	else{
        		Log.d(LOG, "Error getting timepost info");
        	}
        	
    	}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
    	Log.d(LOG, "getAllTimePost(int pid) returnsize="+ret.size());
    	return ret;
    }

    public ArrayList<TimePost> getAllTimePost(){
    	ArrayList<TimePost> ret = new ArrayList<TimePost>();
		String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST;
		
		try{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
	    	
	    	if (c.getCount() != 0){
	    		c.moveToFirst();
	    		
	    		do {
	    			TimePost temp = new TimePost();
	    			
	    			String st = c.getString(c.getColumnIndex(KEY_START_TIME));
	    			temp.setStartTimeByString(st);
	    			
	    			st = c.getString(c.getColumnIndex(KEY_END_TIME));
	    			temp.setEndTimeByString(st);
	    			
	    			temp.id = c.getInt(c.getColumnIndex(KEY_TID));
	    			
	    			temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
	    			
	    			temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
	    			
	    			temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
	    			
	    			temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));
	    			
	    			ret.add(temp);
	                
	            } while (c.moveToNext());
	    	}
	    	else{
	    		Log.d(LOG, "Error getting timepost info");
	    	}
	    	
		}catch(SQLiteException e){
			Log.d(LOG, e.toString());
		}
		Log.d(LOG, "getAllTimePost() returnsize="+ret.size());
		return ret;
    }
    
    public ArrayList<TimePost> getUnsignedTimes() {
    	ArrayList<TimePost> ret = new ArrayList<TimePost>();
    	String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST+" WHERE "+KEY_IS_SIGNED+"=0";
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
        	
        	if (c.getCount() != 0){
        		c.moveToFirst();
        		
        		do {
        			TimePost temp = new TimePost();
        			
        			String st = c.getString(c.getColumnIndex(KEY_START_TIME));
        			temp.setStartTimeByString(st);
        			
        			st = c.getString(c.getColumnIndex(KEY_END_TIME));
        			temp.setEndTimeByString(st);
        			
        			temp.id = c.getInt(c.getColumnIndex(KEY_TID));
        			
        			temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
        			
        			temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
        			
        			temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
        			
        			temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));
        			
        			ret.add(temp);
                    
                } while (c.moveToNext());
        	}
        	else{
        		Log.d(LOG, "Empty table");
        	}
        	
    	}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
    	Log.d(LOG, "getUnsignedTimes() returnsize="+ret.size());
    	return ret;
	}
    
    public ArrayList<TimePost> getUnsignedTimes(int pid) {
    	ArrayList<TimePost> ret = new ArrayList<TimePost>();
    	String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST+" WHERE "+KEY_IS_SIGNED+"=0 AND " + KEY_PID + "=" + pid;
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
        	
        	if (c.getCount() != 0){
        		c.moveToFirst();
        		
        		do {
        			TimePost temp = new TimePost();
        			
        			String st = c.getString(c.getColumnIndex(KEY_START_TIME));
        			temp.setStartTimeByString(st);
        			
        			st = c.getString(c.getColumnIndex(KEY_END_TIME));
        			temp.setEndTimeByString(st);
        			
        			temp.id = c.getInt(c.getColumnIndex(KEY_TID));
        			
        			temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
        			
        			temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
        			
        			temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
        			
        			temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));
        			
        			ret.add(temp);
                    
                } while (c.moveToNext());
        	}
        	else{
        		Log.d(LOG, "Empty table");
        	}
        	
    	}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
    	Log.d(LOG, "getUnsignedTimes(int pid) returnsize="+ret.size());
    	return ret;
	}
    
	public Boolean timePostEmpty(int pid) {
		String selectQuery = "SELECT count(*) AS NUMBERS FROM "+TABLE_TIMEPOST; //WHERE pid = pid
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
    		
        	if (c.getCount() != 0){
        		c.moveToFirst();
        		
        		int result = c.getInt(c.getColumnIndex("NUMBERS"));
        		if(result == 0){
        			return true;
        		}
        		else{
        			return false;
        		}
        	}
        	else{
        		Log.d(LOG, "No info");
        	}
        	
    	}
    	catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
		return false;
		
	}
	
	public void deleteTimePost(int tid) {
		String deleteQuery = "DELETE FROM " + TABLE_TIMEPOST + " WHERE " + KEY_TID + "=" + tid;	
    	try {
    		SQLiteDatabase db = this.getWritableDatabase();
    		db.execSQL(deleteQuery);
    		db.close();
		} 
    	catch (SQLException e) {
			Log.d(LOG,e.toString());
		}
	}

	public ArrayList<TimePost> getTimePostsByProjectIdThenTime(){
		ArrayList<TimePost> ret = new ArrayList<TimePost>();
		String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST + " ORDER BY " + KEY_PID +","+KEY_START_TIME+ " ASC";
		//ORDER BY column_name,column_name ASC
		try{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
	    	
	    	if (c.getCount() != 0){
	    		c.moveToFirst();
	    		
	    		do {
	    			TimePost temp = new TimePost();
	    			
	    			String st = c.getString(c.getColumnIndex(KEY_START_TIME));
	    			temp.setStartTimeByString(st);
	    			
	    			st = c.getString(c.getColumnIndex(KEY_END_TIME));
	    			temp.setEndTimeByString(st);
	    			
	    			temp.id = c.getInt(c.getColumnIndex(KEY_TID));
	    			
	    			temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
	    			
	    			temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
	    			
	    			temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
	    			
	    			temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));
	    			
	    			ret.add(temp);
	                
	            } while (c.moveToNext());
	    	}
	    	else{
	    		Log.d(LOG, "Error getting timepost info");
	    	}
	    	
		}catch(SQLiteException e){
			Log.d(LOG, e.toString());
		}
		
		return ret;	
	}
	
	public TimePost getLatestTimePost(int pid) {
		String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST+" WHERE "+KEY_END_TIME+"=";
		String selectQuery2 = "(SELECT MAX("+KEY_END_TIME+") FROM "+TABLE_TIMEPOST+" WHERE "+KEY_PID+"="+pid+")";
		selectQuery += selectQuery2;
		TimePost temp = new TimePost();
	
		try{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
	    	
	    	if (c.getCount() != 0){
	    		c.moveToFirst();
	    		
	    		String st = c.getString(c.getColumnIndex(KEY_START_TIME));
        		temp.setStartTimeByString(st);
        			
        		st = c.getString(c.getColumnIndex(KEY_END_TIME));
        		temp.setEndTimeByString(st);
        			
        		temp.id = c.getInt(c.getColumnIndex(KEY_TID));
        			
        		temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
        			
        		temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
        			
        		temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
        			
        		temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));	
	    		
	    	}
	    	else{
	    		Log.d(LOG, "Empty table");
	    	}
	    	
		}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
		
		return temp;
	}
	
	public TimePost getLatestTimePost() {
		String selectQuery = "SELECT * FROM "+TABLE_TIMEPOST+" WHERE "+KEY_END_TIME+"=";
		String selectQuery2 = "(SELECT MAX("+KEY_END_TIME+") FROM "+TABLE_TIMEPOST+")";
    	selectQuery = selectQuery + selectQuery2;
    	Log.d(LOG,selectQuery);
		TimePost temp = new TimePost();
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
        	
        	
        	if (c.getCount() != 0){
        		c.moveToFirst();
        			
        		String st = c.getString(c.getColumnIndex(KEY_START_TIME));
        		temp.setStartTimeByString(st);
        			
        		st = c.getString(c.getColumnIndex(KEY_END_TIME));
        		temp.setEndTimeByString(st);
        			
        		temp.id = c.getInt(c.getColumnIndex(KEY_TID));
        			
        		temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
        			
        		temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
        			
        		temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
        			
        		temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));	
        	}
        	else{
        		Log.d(LOG, "Error getting timepost info");
        	}
        	
    	}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
    	return temp;
	}
	
	public ArrayList<TimePost> getByInterval(GregorianCalendar startTime, GregorianCalendar endTime) {
		// Get time post by interval
		ArrayList<TimePost> ret = new ArrayList<TimePost>();
		
		int pid = 0;
		
		TimePost t = new TimePost(startTime, endTime, pid);	//Create new time post to get the right date format 
		
    	String selectQuery = "SELECT * FROM " + TABLE_TIMEPOST + " WHERE " + KEY_START_TIME + " >= '" + t.getStartTime() + "' AND " + KEY_END_TIME + " <= '" + t.getEndTime() + "'";
    		
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
	
    	if (c.getCount() != 0){
    		c.moveToFirst();
    		
    		do {
    			TimePost temp = new TimePost();
    			
    			String st = c.getString(c.getColumnIndex(KEY_START_TIME));
    			temp.setStartTimeByString(st);
    			
    			st = c.getString(c.getColumnIndex(KEY_END_TIME));
    			temp.setEndTimeByString(st);
    			
    			temp.id = c.getInt(c.getColumnIndex(KEY_TID));
    			
    			temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
    			
    			temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
    			
    			temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
    			
    			temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));
    			
    			ret.add(temp);
                
            } while (c.moveToNext());
        			
        		
        	}
        	else{
        		Log.d(LOG, "Error getting timepost info");
        	}
                
    	return ret;
		
	}
	
	public ArrayList<TimePost> getByInterval(GregorianCalendar startTime, GregorianCalendar endTime, int pid) {
		
		ArrayList<TimePost> ret = new ArrayList<TimePost>();

		// Temp time post for storing the times
		TimePost t = new TimePost(startTime, endTime, pid);	//Create new time post to get the right date format 
		
    	String selectQuery = "SELECT * FROM " + TABLE_TIMEPOST + " WHERE " + KEY_START_TIME + " >= '" + t.getStartTime() + "' AND " + KEY_END_TIME + " <= '" + t.getEndTime() + "' AND " + KEY_PID + " = " + pid;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
	
    	if (c.getCount() != 0){
    		c.moveToFirst();
    		
    		do {
    			TimePost temp = new TimePost();
    			
    			String st = c.getString(c.getColumnIndex(KEY_START_TIME));
    			temp.setStartTimeByString(st);
    			
    			st = c.getString(c.getColumnIndex(KEY_END_TIME));
    			temp.setEndTimeByString(st);
    			
    			temp.id = c.getInt(c.getColumnIndex(KEY_TID));
    			
    			temp.projectId = c.getInt(c.getColumnIndex(KEY_PID));
    			
    			temp.comment = c.getString(c.getColumnIndex(KEY_COMMENT));
    			
    			temp.setIsSigned(c.getInt(c.getColumnIndex(KEY_IS_SIGNED)));
    			
    			temp.setCommentShared(c.getInt(c.getColumnIndex(KEY_COMMENT_SHARED)));
    			
    			ret.add(temp);
                
            } while (c.moveToNext());
        			
        		
        	}
        	else{
        		Log.d(LOG, "Error getting timepost info");
        	}
                
    	return ret;
	}

	/* -------------------------------------------- */
	
	/* ----------- PROJECT FUNCTIONS -------------- */
	
	public void setProject(Project project) {
		//"IS PRIVATE" MISSING?!
		if(project.getId() == 0){
			SQLiteDatabase db = this.getWritableDatabase();
		   	 
	        ContentValues values = new ContentValues();
	        
	        values.put(KEY_PROJECT_NAME, project.getName());
	        values.put(KEY_PROJECT_OWNER, project.getOwner());
	        values.put(KEY_DESCRIPTION, project.getDescription());
	        values.put(KEY_CUSTOMER, project.getCustomer());
	 
	        // insert row
	        db.insert(TABLE_PROJECTS, null, values);
	        db.close();
		}
		else{
			String myQuery = 
    		"UPDATE "+TABLE_PROJECTS+" SET "+
    		KEY_PROJECT_NAME+"='"+project.getName()+"', "+
    		KEY_PROJECT_OWNER+"='"+project.getOwner()+"', "+
    		KEY_DESCRIPTION+"='"+project.getDescription()+"', "+
    		KEY_CUSTOMER+"='"+project.getCustomer()+"'"+
    		" WHERE "+KEY_PID+"="+project.getId();
    		
	    	try {
	    		SQLiteDatabase db = this.getWritableDatabase();
	    		db.execSQL(myQuery);
	    		db.close();
			} 
	    	catch (SQLException e) {
				Log.d(LOG,e.toString());
			}
		}
	}
	
	public ArrayList<Project> getAllProjects() {
		ArrayList<Project> ret = new ArrayList<Project>();
    	String selectQuery = "SELECT * FROM "+TABLE_PROJECTS;
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
        	
        	if (c.getCount() != 0){
        		c.moveToFirst();
        		
        		do {
        			Project temp = new Project();
        			
        			temp.setId(c.getInt(c.getColumnIndex(KEY_PID)));
        			
        			temp.setName(c.getString(c.getColumnIndex(KEY_PROJECT_NAME)));
        			
        			temp.setOwner(c.getString(c.getColumnIndex(KEY_PROJECT_OWNER)));
        			
        			temp.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
        			
        			temp.setCustomer(c.getString(c.getColumnIndex(KEY_CUSTOMER)));
        			
        			ret.add(temp);
                    
                } while (c.moveToNext());
        	}
        	else{
        		Log.d(LOG, "Empty project table");
        	}
        	
    	}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
    	
    	return ret;
	}

	public Project getProject(int projectId) {
		Project temp = new Project();
    	String selectQuery = "SELECT * FROM "+TABLE_PROJECTS+" WHERE "+KEY_PID+"="+projectId;
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
        	
        	if (c.getCount() != 0){
        		c.moveToFirst();
        		
    			temp.setId(c.getInt(c.getColumnIndex(KEY_PID)));
    			
    			temp.setName(c.getString(c.getColumnIndex(KEY_PROJECT_NAME)));
    			
    			temp.setOwner(c.getString(c.getColumnIndex(KEY_PROJECT_OWNER)));
    			
    			temp.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
    			
    			temp.setCustomer(c.getString(c.getColumnIndex(KEY_CUSTOMER)));
        	}
        	else{
        		Log.d(LOG, "No project found");
        	}
        	
    	}catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
    	
    	return temp;
	}

	public Boolean projectsEmpty() {
		String selectQuery = "SELECT count(*) AS NUMBERS FROM "+TABLE_PROJECTS;
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor c = db.rawQuery(selectQuery, null);
    		
        	if (c.getCount() != 0){
        		c.moveToFirst();
        		
        		int result = c.getInt(c.getColumnIndex("NUMBERS"));
        		if(result == 0){
        			return true;
        		}
        		else{
        			return false;
        		}
        	}
        	else{
        		Log.d(LOG, "No info");
        	}
        	
    	}
    	catch(SQLiteException e){
    		Log.d(LOG, e.toString());
    	}
		return false;
		
	}
	
	public void deleteProject(int pid) {
		String deleteQuery = "DELETE FROM " + TABLE_PROJECTS + " WHERE " + KEY_PID + "=" + pid;
    		
    	try {
    		SQLiteDatabase db = this.getWritableDatabase();
    		db.execSQL(deleteQuery);
    		db.close();
		} 
    	catch (SQLException e) {
			Log.d(LOG,e.toString());
		}
		
    	deleteTimePostsByPid(pid);
	}

	private void deleteTimePostsByPid(int pid) {
		String deleteQuery = "DELETE FROM " + TABLE_TIMEPOST + " WHERE " + KEY_PID + "=" + pid;
		
    	try {
    		SQLiteDatabase db = this.getWritableDatabase();
    		db.execSQL(deleteQuery);
    		db.close();
		} 
    	catch (SQLException e) {
			Log.d(LOG,e.toString());
		}
		
	}
	
	/* -------------------------------------------- */
}
	
