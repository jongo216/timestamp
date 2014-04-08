package com.example.timestamp;


//Imports
import android.app.ActionBar;
import com.example.timestamp.model.TimePost;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Start extends Activity {
	
	// Instansvariabler
	final Context context = this;
	public String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	String[] overviewMenuString = {"Graf", "Bar", "Summering"};

	private ImageButton imgButton;

	// Skapar aktivitet...
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Stylear actionbar
		ActionBar actionBar = getActionBar(); 
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.actionbar);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#11ffffff")));
		
		// Byter till ConfirmReportview 
		//activitySwitchToConfirmReportView();
		activityInitStart();
	}
	
	// Skapar menyn i actionbar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activitybar, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	//navigerar till confirmReportView
	public void activitySwitchToConfirmReportView(){

		Intent intent = new Intent(this, ConfirmReport.class);
		setContentView(R.layout.activity_confirmreport);
		startActivity(intent);
	}
	
	//navigerar till Start vyn
	public void activitySwitchToStart(View v){
		setContentView(R.layout.activity_start);
		activityInitStart();
	}
	

	// Initierar startvyn..
	private void activityInitStart(){
		setContentView(R.layout.activity_start);
		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinnerProjectView = (Spinner) findViewById(R.id.projects_menu_spinner2);
		//Spinner spinnerOverView = (Spinner) findViewById(R.id.spinnerOverView);
		
		
		//För att välja vilken typ av graf man vill se. 
		//Hämtar namn från string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectsMenuString){
				
			// Style för Spinnern.. Sätter textstorlek samt centrerar..
			public View getView(int position, View convertView,ViewGroup parent) {

		        View v = super.getView(position, convertView, parent);

		        ((TextView) v).setGravity(Gravity.CENTER);
		        ((TextView) v).setTextSize(25);

		        return v;

		    }
			//Style för dropdownmenyn under spinnern..
			public View getDropDownView(int position, View convertView,ViewGroup parent) {

		        View v = super.getDropDownView(position, convertView,parent);

		        ((TextView) v).setGravity(Gravity.CENTER);
		        ((TextView) v).setTextSize(18);

		        return v;
		    }	
		};
		
		//Spinnern använder items från en valt adapter.
		spinnerProjectView.setAdapter(adapter);
		//För overview

		//spinnerOverView.setAdapter(adapterView);


		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		imageButtonListener();
		
	}
	
    public void imageButtonListener(){
		
		//Call timepost function..?
		imgButton = (ImageButton) findViewById(R.id.btnCheckIn);
		
		imgButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0){
				
				TextView tv = (TextView) findViewById(R.id.textView2);
				tv.setVisibility(View.VISIBLE);
				tv.setText("Tid: 1.4 timmar");
				Toast.makeText(Start.this, "Du har stämplat in!", Toast.LENGTH_SHORT).show();
				
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



});*/

