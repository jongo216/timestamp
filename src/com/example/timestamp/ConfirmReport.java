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

import com.example.timestamp.model.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class ConfirmReport extends Fragment {
	
	public String[] projectsMenuString; // = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	public int[] projectMenuIds;
	private ArrayList<Project> projects;
	private int[] projectMenuIds2;
	private ArrayList<Project> projects2;
	private Spinner spinnerProjectView2;
	private DB db;
	private Button button;
	private View rootView;
	private FragmentActivity parentActivity;
	private Spinner spinner;

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_confirmreport, container, false);
        db = new DB(getActivity().getApplicationContext());
        activityInitConfirmReport();
        return rootView;
    }
	
	
	@Override
	public void onResume()
	{	
		super.onResume();
		plotTimeTable(1);
	}
	

	public void activityInitConfirmReport(){
		
		parentActivity = getActivity();
	
		//Letar efter en spinner i activity_main.xml med ett specifict id
		spinner = (Spinner) rootView.findViewById(R.id.projects_menu_spinner2);
				
		
		int selectedRow = 0;
		
		int currentProject = SettingsManager.getCurrentProjectId(parentActivity);
	
		projects = db.getAllProjects();
		projectsMenuString = new String[projects.size() + 1];
		projectMenuIds = new int[projects.size()+1];
		
		for (int n = 0; n < projects.size(); n++)
		{
			projectsMenuString[n] = projects.get(n).getName();
			projectMenuIds[n] = projects.get(n).getId();
			if (currentProject == projectMenuIds[n])
				selectedRow = n;
		}
		
		projectsMenuString[projects.size()]=  "List all projects";
		projectMenuIds[projects.size()] = -1;
		
		
		
		
		//Hämtar namn från string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, projectsMenuString){
			
						
			// Style för Spinnern.. Sätter textstorlek samt centrerar..
			public View getView(int position, View convertView,ViewGroup parent) {

		        View v = super.getView(position, convertView, parent);

		        ((TextView) v).setGravity(Gravity.CENTER);
		        ((TextView) v).setTextColor(Color.WHITE);
		        ((TextView) v).setTextSize(25);

		        return v;

		    }
			//Style för dropdownmenyn under spinnern..
			public View getDropDownView(int position, View convertView,ViewGroup parent) {

		        View v = super.getDropDownView(position, convertView,parent);

		        ((TextView) v).setGravity(Gravity.CENTER);
		        ((TextView) v).setBackgroundColor(Color.BLACK);
		        ((TextView) v).setTextColor(Color.WHITE);
		        ((TextView) v).setTextSize(18);

		        return v;
		    }	
		};
		//Spinnern använder items från en valt adapter.
		spinner.setAdapter(adapter);

		spinnerListener();
		spinner.setSelection(selectedRow);
				
		button = (Button) rootView.findViewById(R.id.sendReportButton);

		button.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
	
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				
				builder.setTitle("Are you sure you want to send in the report?");
				
				builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // Skicka in rapport (tas till redigera vyn?)
			           }
			    });
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // Cancel
			           }
			    });
					
				AlertDialog alertDialog = builder.create();
				
				alertDialog.show();
			}
		
		});
		
		plotTimeTable(1);
		
		
	}
	
	public void plotTimeTable(int projectID){
		DB db = new DB(this.getActivity());
		TableLayout table = (TableLayout) rootView.findViewById(R.id.time_table);
		
		//Return if no time posts exist for a given project
		if(db.empty(projectID))
			return;
		
		//Get list of time posts
		ArrayList<TimePost> times = db.getTime(projectID);
		
		//Remove old rows from table (except the header row)
		int numRows = table.getChildCount();
		if (numRows > 1)
			table.removeViews(1, numRows - 1);
		
		//Add time posts to the table
		for(int i  = 0; i < times.size(); ++i){
			//Init objects
			TableRow row = new TableRow(rootView.getContext());
			TextView day = new TextView(rootView.getContext());
			TextView interval = new TextView(rootView.getContext());
			TextView time = new TextView(rootView.getContext());
			TextView comment = new TextView(rootView.getContext());
			GregorianCalendar start = times.get(i).startTime;
			GregorianCalendar end = times.get(i).endTime;
			
			//Put data in text views
			if(true) {  //TODO: Chose how much detail to show.. if(LARGESCREEN)  
				day.setText(Constants.WEEK_DAY_STRINGS[start.get(Calendar.DAY_OF_WEEK)]);
				interval.setText(start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE) + " - " + end.get(Calendar.HOUR_OF_DAY) + ":" + end.get(Calendar.MINUTE));
				time.setText(times.get(i).getWorkedHoursFormated() + "h");
				
				String com = times.get(i).comment;
				if(com.length() > 10) com = com.substring(0, 8) + "...";
				comment.setText(com);
			}
			//else //Show less detail if small screen
			
			
			//Config text views
			day.setGravity(Gravity.CENTER);
			interval.setGravity(Gravity.CENTER);
			time.setGravity(Gravity.CENTER);
			comment.setGravity(Gravity.CENTER);
			
			//Add text views to object
			row.addView(day);
			row.addView(interval);
			row.addView(time);
			row.addView(comment);
			
			//Config row
			if(i%2 == 1)
				row.setBackgroundColor(Color.parseColor("#CCCCCC"));
			row.setClickable(true);
			row.setId(times.get(i).id);
			row.setOnClickListener(new OnClickListener(){
				@Override
			    public void onClick(View v) {
			        //Inform the user the button has been clicked
			        Toast.makeText(getActivity(), "Clicked timepost with id = " + v.getId(), 2).show();
			        Intent editIntent = new Intent(getActivity(), EditReport.class);
			        startActivity(editIntent);
			        
			    }
			});
			
			//Add row to table
			table.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		} //End of for loop
	}
	

	public void spinnerListener() {
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				
				// TODO Auto-generated method stub
				if(projectMenuIds[pos] != -1){
					SettingsManager.setCurrentProjectId(projectMenuIds[pos], getActivity());
					
				}else{
					//Lista alla projekt
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if (isVisibleToUser) { plotTimeTable(1); }
	    else {  }
	}

}