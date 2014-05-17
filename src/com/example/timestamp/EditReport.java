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

package com.example.timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.timestamp.model.*;

public class EditReport extends Activity {

	private Button button_save, button_delete;
	final Context message = this;
	final Activity thisAct = this;
	private EditText commentField;
	
	//private DB db; 
	private ArrayList<String> projects;
	private List<Integer> projectIds;
	
	
	TimePicker startPicker, endPicker;
	DatePicker datePicker;
	Spinner spinnerProjectSelector;
	TimePost timePost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editreport);
		
		ActionBar actionBarTop = getActionBar(); //Action bar med rubrik + settingsknapp
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBarTop.setCustomView(R.layout.actionbar);
		
		
		//Link GUI objects with xml ids
		button_save = (Button)findViewById(R.id.button_save);
		button_delete = (Button)findViewById(R.id.button_delete);
		commentField = (EditText) findViewById(R.id.editTextComment);
		startPicker = (TimePicker) findViewById(R.id.timePickerStart);
		endPicker = (TimePicker) findViewById(R.id.timePickerEnd);
		datePicker = (DatePicker)findViewById(R.id.datePickerEditReport);
		spinnerProjectSelector = (Spinner) findViewById(R.id.editReportProjectSpinner);
		
		//Initialize time post object (create new if id = 0)
		int timePostId = getIntent().getIntExtra(Constants.TIME_POST_ID, 0);
		
		if (timePostId != 0) {
			DB db = new DB(this);
			timePost = db.getTimePost(timePostId);
			if (timePost == null) timePost = new TimePost();
			Log.d("EditReport","SIGNED? = "+timePost.isSigned);
			button_delete.setVisibility(0);
		}
		else{
			//DB db = new DB(this);
			Log.d("new time post", "inne i else då id=0");
			timePost = new TimePost();
			timePost.projectId = SettingsManager.getCurrentProjectId(this);
			
			// If it is a new time post 
			this.setTitle(R.string.title_activity_add_new_post);
			
			//Rename button strings
			button_save.setText(R.string.addNewPostButton);
			//button_delete.setText(R.string.cancelNewPostButton);
			
		}
		timePost.showMeEveryThing();
		//Init GUI functionality
		initCommentField();
		initTimePickers();
		initSaveButton();
		initDeleteButton();
		initSpinner();
	}
	

	
	private void initSpinner() {
		
		//spinnerProjectSelector = (Spinner) findViewById(R.id.editReportProjectSpinner);
		projects = new ArrayList<String>();
		projectIds = new ArrayList<Integer>();
		
		DB db = new DB(this);
		ArrayList<Project> projectsFromDB = db.getAllProjects();
		db.terminateDatabaseHelper();
		for(int i=0; i<projectsFromDB.size(); i++){
			projects.add(projectsFromDB.get(i).getName());
			projectIds.add(projectsFromDB.get(i).getId());
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_dropdown_item, projects){
				
			// Style för Spinnern.. Sätter textstorlek samt centrerar..
			public View getView(int position, View convertView,ViewGroup parent) {

		        View v = super.getView(position, convertView, parent);

		        ((TextView) v).setGravity(Gravity.CENTER);
		        ((TextView) v).setTextColor(Color.WHITE);
		        ((TextView) v).setTextSize(20);

		        return v;
		    }
			//Style för dropdownmenyn under spinnern..
			public View getDropDownView(int position, View convertView,ViewGroup parent) {

		        View v = super.getDropDownView(position, convertView,parent);

		        ((TextView) v).setGravity(Gravity.CENTER);
		        ((TextView) v).setTextColor(Color.WHITE);
		        ((TextView) v).setBackgroundColor(Color.BLACK);
		        ((TextView) v).setTextSize(18);

		        return v;
		    }	
		};

		spinnerProjectSelector.setAdapter(dataAdapter);
		//spinnerProjectSelector.setSelection(position);
		
		for(int i = 0; i<projectIds.size(); i++){
			if(projectIds.get(i) == SettingsManager.getCurrentProjectId(thisAct)){
				spinnerProjectSelector.setSelection(i);
				break;
			}
		}
		
		spinnerProjectSelector.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				timePost.projectId = projectIds.get(pos);
				Log.d("EditReport", "Selectedwith: projget="+projectIds.get(pos)+" pos="+pos);
				//timePost.setProjectId(projectIds.get(pos));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
			
		});
	}

	private void initDeleteButton() {
		
		button_delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder ad = new AlertDialog.Builder(message);
				//ad.setTitle("Confirm"); 
				ad.setMessage(R.string.editReportConfirmMessage);
				ad.setCancelable(false);
				ad.setPositiveButton(R.string.editReportConfirmPositive,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						DB db = new DB(message);
						db.deleteTimePost(timePost);
						EditReport.this.finish();
					}
				});
				ad.setNegativeButton(R.string.editReportConfirmNegative,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
	 
				// create alert dialog
				AlertDialog alertDialog = ad.create();
				
				// show it
				alertDialog.show();
			}
		});
	}


	public void initSaveButton() {
		
		button_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				timePost.startTime.set(Calendar.YEAR, datePicker.getYear());
				timePost.endTime.set(Calendar.YEAR, datePicker.getYear());
				
				timePost.startTime.set(Calendar.MONTH, datePicker.getMonth());
				timePost.endTime.set(Calendar.MONTH, datePicker.getMonth());
				
				timePost.startTime.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
				timePost.endTime.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
				
				timePost.startTime.set(Calendar.HOUR_OF_DAY, startPicker.getCurrentHour());
				timePost.startTime.set(Calendar.MINUTE, startPicker.getCurrentMinute());
				
				timePost.endTime.set(Calendar.HOUR_OF_DAY, endPicker.getCurrentHour());
				timePost.endTime.set(Calendar.MINUTE, endPicker.getCurrentMinute());
				
				timePost.comment = commentField.getEditableText().toString();
				
				DB db = new DB(message);
				db.set(timePost);
				timePost.showMeEveryThing();
				finish();
			};
		});
	}
	
	public void initTimePickers() {
		datePicker.updateDate(timePost.startTime.get(Calendar.YEAR), timePost.startTime.get(Calendar.MONTH), timePost.startTime.get(Calendar.DAY_OF_MONTH));
		
		startPicker.setIs24HourView(true);
        startPicker.setCurrentHour(timePost.startTime.get(Calendar.HOUR_OF_DAY));
        startPicker.setCurrentMinute(timePost.startTime.get(Calendar.MINUTE));

        endPicker.setIs24HourView(true);
        endPicker.setCurrentHour(timePost.endTime.get(Calendar.HOUR_OF_DAY));
        endPicker.setCurrentMinute(timePost.endTime.get(Calendar.MINUTE));
	}
	
	public void initCommentField(){
		if (timePost.comment.length() > 0)
			commentField.setText(timePost.comment);
		
		commentField.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d("hej", "click");
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				return false;
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// toggle nav drawer on selecting action bar app icon/title
		// Handle action bar actions click
		Intent intent;
		Log.d("felLog", "onOptionItemsSelected");
		switch (item.getItemId()) {
		
		case R.id.action_yoursettings:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_manageprojects: /// För hantera projektvyn
			intent = new Intent(this, ManageProjects.class);
			startActivity(intent); 
			return true;
			
		default: 
			return super.onOptionsItemSelected(item);
		}
	}

}


