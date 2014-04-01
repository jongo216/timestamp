package com.example.timestamp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Start extends Activity {
	
	final Context context = this;
	String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		//setContentView(R.layout.activity_two);
		
		activityInitMain();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	public void activitySwitchOne(View v){
		setContentView(R.layout.activity_two);
		activityInitTwo();
	}
	public void activitySwitchToMain(View v){
		setContentView(R.layout.activity_start);	
		//activityInitMain();
	}
	
	private void activityInitMain(){

		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinner = (Spinner) findViewById(R.id.projects_menu_spinner);
		
		//H�mtar namn fr�n string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, projectsMenuString);
		
		//Spinnern anv�nder items fr�n en valt adapter.
		spinner.setAdapter(adapter);

		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
	}
	
	private void activityInitTwo(){
		button = (Button) findViewById(R.id.sendReportButton);

		button.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
	
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				
				builder.setTitle("Är du säkert på att du vill skicka in rapporten?");
				
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
	
	builder.setTitle("Är du säkert på att du vill skicka in rapporten?");
	
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

