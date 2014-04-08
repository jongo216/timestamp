package com.example.timestamp;


import com.example.timestamp.model.TimePost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Start extends Activity {
	

	final Context context = this;
	String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	String[] overviewMenuString = {"Graf", "Bar", "Summering"};
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_start);
		
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		
		setContentView(R.layout.activity_confirmreport);
		
		Toast.makeText(getApplicationContext(), new TimePost().printStartTime() , Toast.LENGTH_LONG).show();
		
		//activityInitMain(); //  <<<<--- RENAME TO "activityInitSpinner" 
		activityInitConfirmReport();
	}
	// Ta bort ... vi ska väl inte ha någon meny?
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}*/

	public void activitySwitchOne(View v){
		setContentView(R.layout.activity_confirmreport);
		activityInitConfirmReport();
	}
	public void activitySwitchToMain(View v){
		setContentView(R.layout.activity_start);
		activityInitMain();
	}
	
	private void activityInitMain(){

		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinnerProjectView = (Spinner) findViewById(R.id.spinnerProjectView);
		//Spinner spinnerOverView = (Spinner) findViewById(R.id.spinnerOverView);
		
		//Hämtar namn från string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectsMenuString);
		

		//F�r att v�lja vilken typ av graf man vill se. 
		//ArrayAdapter<String> adapterView = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, overviewMenuString);

		
		//Spinnern anv�nder items fr�n en valt adapter.
		spinnerProjectView.setAdapter(adapter);
		//F�r overview

		//spinnerOverView.setAdapter(adapterView);


		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
	}
	
	private void activityInitConfirmReport(){
		//Letar efter en spinner i activity_main.xml med ett specifict id
				Spinner spinner = (Spinner) findViewById(R.id.projects_menu_spinner2);
				
				//Hämtar namn från string array med menu item.
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectsMenuString){
					
					
					// Style för Spinnern.. Sätter textstorlek samt centrerar..
					public View getView(int position, View convertView,ViewGroup parent) {

				        View v = super.getView(position, convertView, parent);

				        ((TextView) v).setGravity(Gravity.CENTER);
				        ((TextView) v).setTextSize(36);

				        return v;

				    }
					//Style för dropdownmenyn under spinnern..
					public View getDropDownView(int position, View convertView,ViewGroup parent) {

				        View v = super.getDropDownView(position, convertView,parent);

				        ((TextView) v).setGravity(Gravity.CENTER);
				        ((TextView) v).setTextSize(20);

				        return v;
				    }
					
					
				};
				//Spinnern använder items från en valt adapter.
				spinner.setAdapter(adapter);
				
		
		button = (Button) findViewById(R.id.sendReportButton);

		button.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
	
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				
				builder.setTitle("Är du säker på att du vill skicka in rapporten?");
				
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setPositiveButton("Skicka", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User clicked OK button
			           }
			    });
					builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User cancelled the dialog
			           }
			    });
					
				AlertDialog alertDialog = builder.create();
				
				alertDialog.show();
				
			}
		
		});
		
	}
	
	public void startTime(View view){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
		// set title
		alertDialogBuilder.setTitle("Timestamp");
 
			// set dialog message
		alertDialogBuilder
			.setMessage("Du har nu stämplat in")
			.setCancelable(false)
			.setPositiveButton("Avsluta",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					Start.this.finish();
				}
			  })
			.setNegativeButton("Okej",new DialogInterface.OnClickListener() {
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

/*
button = (Button) findViewById(R.id.sendReportButton);

button.setOnClickListener(new OnClickListener(){
	
@Override
public void onClick(View arg0){

	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
	builder.setTitle("��r du s��kert p�� att du vill skicka in rapporten?");
	
	// 2. Chain together various setter methods to set the dialog characteristics
	builder.setPositiveButton("Skicka", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // User clicked OK button
           }
    });
		builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // User cancelled the dialog
           }
    });
		
	AlertDialog alertDialog = builder.create();
	
	alertDialog.show();
	
}



});*/

