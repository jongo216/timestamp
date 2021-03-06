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

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timestamp.appwidget.Widget;
import com.example.timestamp.model.DB;
import com.example.timestamp.model.Project;
import com.example.timestamp.model.SettingsManager;
import com.example.timestamp.model.TimePost;
import com.example.timestamp.services.ReminderNotification;
import com.example.timestamp.services.TimedReminder;


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
	private ViewPager statsViewPager;
	private MyAdapter statsPagerAdapter;
	private TextView textView;
	private FragmentManager statsFragmentManager;
	
	
	//private FragmentActivity parentActivity;
	private static DB db;
	
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

		statsViewPager = (ViewPager) rootView.findViewById(R.id.statsViewPager);

        
		Log.d("Activityinfo: ", "Activity of Start: " + getActivity().toString());
		db = new DB(getActivity());
		
		
		
		/*initTimer();
		initProjectSpinner();
		initTimerButton();
		initStats();
		dbButtonListener();*/ //Button is just for debug and not visible anyways. But i leave this ftm.
        return rootView;
    }
	
	
	
	
	/*public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
	}*/


	private void initProjectSpinner(){
		
		int selectedRow = 0;
		int currentProject = SettingsManager.getCurrentProjectId(getActivity());
		
		//Fetch projects froom data base and use them to create arrays 
		 
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
					updateStats();
					
					//Testing notifications
					//AlarmService a = new AlarmService(getActivity());
					//a.startAlarm();
					
					
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
	
	
	
	private void initStats() {
		statsFragmentManager = getChildFragmentManager();
		statsPagerAdapter = new MyAdapter(statsFragmentManager);
	    //statsPagerAdapter.instantiateItem(statsViewPager, 0); //Not needed
	    statsViewPager.setOffscreenPageLimit(5);
	    statsViewPager.setAdapter(statsPagerAdapter);
	    
	    //statsViewPager.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
	    
	}
	
	
	
	public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
        	switch (position) {
        		case 0: 
        			StatsBarChartFragment barChart = new StatsBarChartFragment();
        			
        			//barChart.setRetainInstance(true);
        			//barChart.setDB(db);
        			return barChart;
        			
        		case 1:
        			return new StatsBurnDownFragment();
        		
        		case 2:
        			return new StatsSummaryFragment();
        		
        	}
        	return new StatsSummaryFragment();
        }
	}
	
	
	private void updateStats() {
		List<Fragment> fragments = statsFragmentManager.getFragments();
		if (fragments != null)
			for (int n = 0; n < fragments.size(); n++)
				((UpdateableStatistics)fragments.get(n)).update();
		
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
		else {
			imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_white) );
			chronometer.stop();
			textView.setVisibility(View.VISIBLE);
			chronometer.setVisibility(View.GONE);
		}
		
	}
	

    public void initTimerButton(){
    
    	imgButton.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0){
				boolean timerRunning = SettingsManager.getIsTimerRunning(getActivity());
		    	FragmentActivity context = getActivity();
				
				if(timerRunning){
					imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_white) );
					chronometer.stop();
					textView.setVisibility(View.VISIBLE);
					chronometer.setVisibility(View.GONE);
					TimePost p = new TimePost();
					p.setProjectId(SettingsManager.getCurrentProjectId(context));
					p.setStartTime(SettingsManager.getStartTime(context));
					p.setEndTimeNow();
					db.set(p);
					
					SettingsManager.setIsTimerRunning(false, context);
					TimedReminder.CancelFullWorkDayReminder(context);
					TimedReminder.RemindToReportTimes(context, p.projectId);
				}
				else{
					imgButton.setBackground(getResources().getDrawable(R.drawable.checkinbutton_green) );
					chronometer.setBase(SystemClock.elapsedRealtime());
					chronometer.start();
					chronometer.setVisibility(View.VISIBLE);
					textView.setVisibility(View.GONE);
					SettingsManager.setIsTimerRunning(true, context);
					SettingsManager.setStartTime(new GregorianCalendar(), context);
					
					//Set reminder after 8 hours worked
					GregorianCalendar startOfDay = new GregorianCalendar();
					startOfDay.set(Calendar.HOUR_OF_DAY,  0);
					startOfDay.set(Calendar.MINUTE, 0);
					startOfDay.set(Calendar.SECOND, 0);
					startOfDay.set(Calendar.MILLISECOND, 0);
					ArrayList<TimePost> times = db.getByInterval(startOfDay, new GregorianCalendar());
					long timeWorked = 0;
					for (TimePost t : times)
						timeWorked += t.getWorkedHours() * 3600 * 1000;
					long timeLeft = Constants.DEMO_MODE ? 20 * 1000 : 3600 * 8 * 1000 - timeWorked;
					TimedReminder.RemindAfterFullWorkDay(context, timeLeft);
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
    			//DB db = new DB(getActivity());
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
		//updateStats();
		
		/*initTimer();
		initTimerButton();
		initProjectSpinner();*/
		
		
		initTimer();
		initProjectSpinner();
		initTimerButton();
		initStats();
		dbButtonListener();
	}
	
	
	/*public class AlarmService {
	    private Context context;
	    private PendingIntent mAlarmSender;
	    public AlarmService(Context context) {
	        this.context = context;
	        mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, ReminderNotification.class), 0);
	    }

	    public void startAlarm(){
	        //Set the alarm to 10 seconds from now
	        GregorianCalendar c = new GregorianCalendar();
	        c.add(Calendar.SECOND, 4);
	        long firstTime = c.getTimeInMillis();
	        // Schedule the alarm!
	        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        am.set(AlarmManager.RTC, firstTime, mAlarmSender);
	    }
	}*/
	
	
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
