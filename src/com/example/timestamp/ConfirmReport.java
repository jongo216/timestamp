package com.example.timestamp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfirmReport extends Activity {
	
	String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	final Context context = this;
	private Button button;
	
	public ConfirmReport(){
		
		
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_start);
		
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    //getActionBar().hide();
		ActionBar actionBar = getActionBar(); 
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBar.setCustomView(R.layout.actionbar);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#11ffffff")));
		
		setContentView(R.layout.activity_confirmreport);
		
		activityInitConfirmReport();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activitybar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public void activityInitConfirmReport(){
		setContentView(R.layout.activity_confirmreport);
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
}