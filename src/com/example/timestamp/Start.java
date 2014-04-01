package com.example.timestamp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Start extends Activity {
	
	final Context context = this;
	String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinner = (Spinner) findViewById(R.id.projects_menu_spinner);
		
		//H�mtar namn fr�n string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, projectsMenuString);
		
		//Spinnern anv�nder items fr�n en valt adapter.
		spinner.setAdapter(adapter);

		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	public void activitySwitchOne(View v){
		setContentView(R.layout.activity_two);
	}
	public void activitySwitchTwo(View v){
		setContentView(R.layout.activity_start);
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
	final Context context = this;
	
	String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinner = (Spinner) findViewById(R.id.projects_menu_spinner);
		
		//H�mtar namn fr�n string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, projectsMenuString);
		
		//Spinnern anv�nder items fr�n en valt adapter.
		spinner.setAdapter(adapter);

		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	public void startTime(View view){
		System.out.println("HEJ!");
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
 
			// set title
			alertDialogBuilder.setTitle("Timestamp");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Du har nu st�mplat in!")
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
*/

