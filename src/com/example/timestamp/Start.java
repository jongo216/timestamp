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
	private ImageButton imgButton;
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
		
		boolean timerRunning = SettingsManager.getIsTimerRunning(parentActivity);
		chronometer = (Chronometer)rootView.findViewById(R.id.chronometer);
		
		imgButton = (ImageButton) rootView.findViewById(R.id.btnCheckIn);
		int currentProject = SettingsManager.getCurrentProjectId(parentActivity);
		
		if (timerRunning)
		{
			GregorianCalendar startTime = SettingsManager.getStartTime(parentActivity);
			GregorianCalendar currentTime = new GregorianCalendar();
			imgButton.setBackgroundColor(Color.GREEN);
			chronometer.setBase(SystemClock.elapsedRealtime() - currentTime.getTimeInMillis() + startTime.getTimeInMillis());
			chronometer.start();
		}
		else imgButton.setBackgroundColor(Color.WHITE);
		
		
		projects = db.getAllProjects();
		Log.d("DatabaseHelper","Projsize: "+projects.size());
		projectsMenuString = new String[projects.size() + 1];
		projectMenuIds = new int[projects.size()+1];
		int selectedRow = 0;
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
					imgButton.setBackgroundColor(Color.WHITE);
					
					SettingsManager.setIsTimerRunning(false, getActivity());
					chronometer.stop();
					TimePost p = new TimePost();
					p.setProjectId(SettingsManager.getCurrentProjectId(getActivity()));
					p.setStartTime(SettingsManager.getStartTime(getActivity()));
					p.setEndTimeNow();
					db.set(p);
				}
				else{
					imgButton.setBackgroundColor(Color.GREEN);
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
    			int currentProjectSelected = SettingsManager.getCurrentProjectId(getActivity());
    			if(!db.empty(currentProjectSelected)){
    				ArrayList<TimePost> times = db.getTime(currentProjectSelected); // PROJECT ID fix....
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


