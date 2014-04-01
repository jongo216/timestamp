package com.example.timestamp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {

	String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	private Button button;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_three);
		
		
		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinner = (Spinner) findViewById(R.id.projects_menu_spinner2);
		
		//Hämtar namn från string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, projectsMenuString);
		
		//Spinnern använder items från en valt adapter.
		spinner.setAdapter(adapter);

		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
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


		
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
