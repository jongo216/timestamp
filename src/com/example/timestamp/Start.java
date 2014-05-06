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
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.timestamp.model.*;


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
	private TextView textView;
	
	//private FragmentActivity parentActivity;
	//private DB db;
	

	
	
	@Override		//mother of all inits!
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_start, container, false);
        //parentActivity = getActivity();
        
        //Link to xml objects
		chronometer = (Chronometer)rootView.findViewById(R.id.chronometer);
		chronometer.setVisibility(View.GONE);
		textView = (TextView)rootView.findViewById(R.id.textStamplaIn);
		imgButton = (LinearLayout) rootView.findViewById(R.id.btnCheckIn);
		spinnerProjectView = (Spinner) rootView.findViewById(R.id.projects_menu_spinner2);
		
        
        //db = new DB(getActivity().getApplicationContext());
        //Log.d("DatabaseHelper","New DB");
        
		initTimer();
		initProjectSpinner();
		initTimerButton();
		dbButtonListener(); //Button is just for debug and not visible anyways. But i leave this ftm.
        return rootView;
    }


	// Initierar startvyn..
	private void initProjectSpinner(){
		
		int selectedRow = 0;
		int currentProject = SettingsManager.getCurrentProjectId(getActivity());
		
		//Fetch projects froom data base and use them to create arrays 
		DB db = new DB(getActivity());
		projects = db.getAllProjects();
		projectsMenuString = new String[projects.size() + 1];
		projectMenuIds = new int[projects.size()+1];
		
		//Check if there are any projects
		//if there are not, direct the user
		//to create a new project
		//Or call on a boolean to check if the are any projects
		if(db.projectsEmpty()){		
			//create new project
			Intent intent = new Intent(getActivity(), CreateNewProject.class);
			intent.putExtra(Constants.PROJECT_ID, 0); //Optional parameters
			startActivity(intent);		
		}
		
		for (int n = 0; n < projects.size(); n++)
		{
			projectsMenuString[n] = projects.get(n).getName();
			projectMenuIds[n] = projects.get(n).getId();
			if (currentProject == projectMenuIds[n])
				selectedRow = n;
		}
		
		projectsMenuString[projects.size()]= getString(R.string.add_project);
		projectMenuIds[projects.size()] = -1;
		
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

		
		//Set action listener for the spinner
		spinnerProjectView.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				
				// TODO Auto-generated method stub
				if(projectMenuIds[pos] != -1){
					SettingsManager.setCurrentProjectId(projectMenuIds[pos], getActivity());
					
				}else{
					//Skapa nytt projekt
					Intent intent = new Intent(getActivity(), CreateNewProject.class);
					intent.putExtra(Constants.PROJECT_ID, 0); //Optional parameters
					startActivity(intent);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		//spinnerListener();
		
	}
	
	public void initTimer(){
		boolean timerRunning;
		timerRunning = SettingsManager.getIsTimerRunning(getActivity());
		if (timerRunning)
		{
			GregorianCalendar startTime = SettingsManager.getStartTime(getActivity());
			GregorianCalendar currentTime = new GregorianCalendar();
			imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_green) );
			chronometer.setBase(SystemClock.elapsedRealtime() - currentTime.getTimeInMillis() + startTime.getTimeInMillis());
			chronometer.start();
			chronometer.setVisibility(View.VISIBLE);
			textView.setVisibility(View.GONE);
		}
		else imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_white) );
		
	}
	
	public void spinnerListener() {
		
		
	}
	
	
    public void initTimerButton(){
		
		imgButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0){
				boolean timerRunning = SettingsManager.getIsTimerRunning(getActivity());
					
				if(timerRunning){
					imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_white) );
					
					SettingsManager.setIsTimerRunning(false, getActivity());
					chronometer.stop();
					textView.setVisibility(View.VISIBLE);
					chronometer.setVisibility(View.GONE);
					TimePost p = new TimePost();
					p.setProjectId(SettingsManager.getCurrentProjectId(getActivity()));
					p.setStartTime(SettingsManager.getStartTime(getActivity()));
					p.setEndTimeNow();
					DB db = new DB(getActivity());
					db.set(p);
				}
				else{
					imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_green) );
					chronometer.setBase(SystemClock.elapsedRealtime());
					chronometer.start();
					chronometer.setVisibility(View.VISIBLE);
					textView.setVisibility(View.GONE);
					SettingsManager.setIsTimerRunning(true, getActivity());
					SettingsManager.setStartTime(new GregorianCalendar(), getActivity());
				}
			}	
		});
	}
    
    //database testing!
    public void dbButtonListener(){
       	//projects
    	Button projectsBtn = (Button) rootView.findViewById(R.id.Projects);
    	projectsBtn.setOnClickListener(new OnClickListener(){
    		public void onClick(View arg0){
    			DB db = new DB(getActivity());
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
	

	
    
	@Override
	public void onResume()
	{	
		super.onResume();
		initTimer();
		initProjectSpinner();
	}
	
	
	//OLD METHOD. DEPRECATED?
	/*public void startTime(View view){
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
	}*/
	
}
