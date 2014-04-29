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
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timestamp.model.DB;
import com.example.timestamp.model.Project;
import com.example.timestamp.model.SettingsManager;
import com.example.timestamp.model.TimePost;


public class Start extends Fragment{
	
	// Instansvariabler
	//final Context context = this;
	public String[] projectsMenuString; // = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	public int[] projectMenuIds;
	private ArrayList<Project> projects;
	private LinearLayout imgButton;
	private Spinner spinnerProjectView;
	private View rootView;
	private Chronometer chronometer;
	private FragmentActivity parentActivity;
	private DB db;
	

	
	
	@Override		//mother of all inits!
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_start, container, false);
        
        db = new DB(getActivity().getApplicationContext());
        Log.d("DatabaseHelper","New DB");
        
        //db.dbHelper.showTables();
        //db.set(new TimePost(1942,10,23,13,37));
        //db.set(new TimePost(1942,10,24,13,37));
        //db.set(new TimePost(1942,10,25,13,37));
        Log.d("DatabaseHelper","Done inserting...");
        //db.dbHelper.showTables();
        
        activityInitStart();
        
        //db.terminateDatabaseHelper();
       
        return rootView;
    }


	// Initierar startvyn..
	private void activityInitStart(){
		
		parentActivity = getActivity();
		
		
		chronometer = (Chronometer)rootView.findViewById(R.id.chronometer);
		

		imgButton = (LinearLayout) rootView.findViewById(R.id.btnCheckIn);
		
		
	
		

		initTimer();
		
		
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
		
		projectsMenuString[projects.size()]= getString(R.string.add_project);
		projectMenuIds[projects.size()] = -1;
		

		
		
	
		
		//Letar efter en spinner i activity_main.xml med ett specifict id
		spinnerProjectView = (Spinner) rootView.findViewById(R.id.projects_menu_spinner2);
		
		//För att välja vilken typ av graf man vill se. 
		//Hämtar namn från string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_spinner_dropdown_item, projectsMenuString){
				
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
		        ((TextView) v).setTextColor(Color.WHITE);
		        ((TextView) v).setBackgroundColor(Color.BLACK);
		        ((TextView) v).setTextSize(18);

		        return v;
		    }	
		};
		
		
		//Spinnern använder items från en valt adapter.
		spinnerProjectView.setAdapter(adapter);
		
		spinnerProjectView.setSelection(selectedRow);

		//spinnerOverView.setAdapter(adapterView);
		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		imageButtonListener();
		spinnerListener();
		dbButtonListener();
		
		
	}
	
	
	public void initTimer(){
		boolean timerRunning;
		timerRunning = SettingsManager.getIsTimerRunning(parentActivity);
		if (timerRunning)
		{
			GregorianCalendar startTime = SettingsManager.getStartTime(parentActivity);
			GregorianCalendar currentTime = new GregorianCalendar();
			imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_green) );
			chronometer.setBase(SystemClock.elapsedRealtime() - currentTime.getTimeInMillis() + startTime.getTimeInMillis());
			chronometer.start();
		}
		else imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_white) );
		
	}
	
	public void spinnerListener() {
		spinnerProjectView.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				
				// TODO Auto-generated method stub
				if(projectMenuIds[pos] != -1){
					SettingsManager.setCurrentProjectId(projectMenuIds[pos], getActivity());
					
				}else{
					//Skapa nytt projekt
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	
    public void imageButtonListener(){
		
		imgButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0){
				boolean timerRunning = SettingsManager.getIsTimerRunning(getActivity());
							
				if(timerRunning){
					imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_white) );
					
					SettingsManager.setIsTimerRunning(false, getActivity());
					chronometer.stop();
					TimePost p = new TimePost();
					p.setProjectId(SettingsManager.getCurrentProjectId(getActivity()));
					p.setStartTime(SettingsManager.getStartTime(getActivity()));
					p.setEndTimeNow();
					db.set(p);
				}
				else{
					imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_green) );
					chronometer.setBase(SystemClock.elapsedRealtime());
					chronometer.start();
					SettingsManager.setIsTimerRunning(true, getActivity());
					SettingsManager.setStartTime(new GregorianCalendar(), getActivity());
				}
			}	
		});
	}
    
    //database testing!
    public void dbButtonListener(){
    	//times
    	Button timesBtn = (Button) rootView.findViewById(R.id.Times);
    	timesBtn.setOnClickListener(new OnClickListener(){
    		public void onClick(View arg0){
    			//int timePostID = 3;
    			//db.updateStartTimePost(timePostID, "2014-06-07 17:00:00");
    			int currentProjectSelected = SettingsManager.getCurrentProjectId(getActivity());
    			if(!db.empty(currentProjectSelected)){
    				ArrayList<TimePost> times = db.getTime(currentProjectSelected); 
        			String text = "";
        			for(int i = 0; i < times.size(); ++i){
        				//buggs with printStart/EndTime
        				text = text +times.get(i).id + times.get(i).printStartTime() + " - " + times.get(i).printEndTime() + "\n";
        			}
        			Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    			}
    			else{
    				Toast.makeText(getActivity(), "The database is currently empty!", Toast.LENGTH_LONG).show();
    			}
    		}
    	});
    	//projects
    	Button projectsBtn = (Button) rootView.findViewById(R.id.Projects);
    	projectsBtn.setOnClickListener(new OnClickListener(){
    		public void onClick(View arg0){
    			ArrayList<Project> projects = db.getAllProjects();
    			String text = "";
    			for(int i = 0; i < projects.size(); ++i){
    				//buggs with printStart/EndTime
    				text = text + 	"Project name: " + projects.get(i).getName() + 
    								" \tOwner: " 	 + projects.get(i).getOwner() + 
    								" \tCustomer: "  + projects.get(i).getCustomer() + "\n";
    			}
    			Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    		}
    	});
    }
	

	public void startTime(View view){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
 
		// set title
		alertDialogBuilder.setTitle("Timestamp");
 
			// set dialog message
		alertDialogBuilder
			.setMessage("You have now checked in!")
			.setCancelable(false)
			.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					getActivity().finish();
				}
			  })
			.setNegativeButton("Okay",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
			alertDialog.show();
		}


}


