package com.example.timestamp;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class ManageProjects extends Activity {
	
	private Button createNewProjectbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_projects);
	
		
		
		//Stylear actionbar
		ActionBar actionBarTop = getActionBar(); //Action bar med rubrik + settingsknapp
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBarTop.setCustomView(R.layout.actionbar);
		actionBarTop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#11ffffff")));
		actionBarTop.setDisplayShowHomeEnabled(true); //Nödvändig(??) för att visa action bars i rätt ordning
		
		//Fix som döljer ikonen i övre vänstra hörnet
		View homeIcon = findViewById(android.R.id.home);
		((View) homeIcon.getParent()).setVisibility(View.GONE);
		
		createNewProjectbtn = (Button)findViewById(R.id.createnewprojectbtn);

		createNewProjectbtn.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
				Intent myIntent = new Intent(ManageProjects.this, CreateNewProject.class);
				//myIntent.putExtra("key", value); //Optional parameters
				ManageProjects.this.startActivity(myIntent);
				
			}
		});
					
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activitybar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// toggle nav drawer on selecting action bar app icon/title
		// Handle action bar actions click
		Intent intent;
		Log.d("felLog", "onOptionItemsSelected");
		switch (item.getItemId()) {
		
		case R.id.action_settings:
			return true;

		case R.id.action_yoursettings:
			 intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_edit:
			intent = new Intent(this, EditReport.class);
			startActivity(intent);
			return true;
		case R.id.action_manageprojects: /// För hantera projektvyn
			return true; 
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
