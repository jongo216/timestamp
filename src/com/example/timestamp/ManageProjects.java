package com.example.timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.timestamp.model.*;

import android.app.Activity;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

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
		
		
		createNewProjectbtn = (Button)findViewById(R.id.createnewprojectbtn);

		createNewProjectbtn.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(getBaseContext(), CreateNewProject.class);
				intent.putExtra(Constants.PROJECT_ID, 0); //Optional parameters
				startActivity(intent);
				
			}
		});
		
		initProjectTable();
		
					
	}
	
	public void initProjectTable()
	{
		DB db = new DB(this);
		TableLayout table = (TableLayout) findViewById(R.id.project_table);
		ArrayList<Project> projects = db.getAllProjects();
		
		int numRows = table.getChildCount();
		if (numRows > 1)
			table.removeViews(1, numRows - 1);
		
		//Add time posts to the table
		for(int i  = 0; i < projects.size(); ++i){
			//Init objects
			TableRow row = new TableRow(this);
			TextView name = new TextView(this);
			TextView customer = new TextView(this);
			TextView description = new TextView(this);
			
			
			//Put data in text views
			if(true) {  //TODO: Chose how much detail to show.. if(LARGESCREEN)  
				name.setText(projects.get(i).getName());
				customer.setText(projects.get(i).getCustomer());
				description.setText(projects.get(i).getDescription());
			}
			//else //Show less detail if small screen
			
			
			//Configure text views
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT);
			name.setGravity(Gravity.CENTER);
			name.setTextColor(Color.BLACK);
			customer.setGravity(Gravity.CENTER);
			customer.setTextColor(Color.BLACK);
			description.setGravity(Gravity.CENTER);
			description.setTextColor(Color.BLACK);
			
			//Add text views to object
			row.addView(name);
			row.addView(customer);
			row.addView(description);
			
			//Configure row
			if(i%2 == 1)
				row.setBackgroundColor(Color.parseColor("#CCCCCC"));
			row.setPadding(3, 9, 3, 9);
			row.setClickable(true);
			row.setId(projects.get(i).getId());
			row.setOnClickListener(new OnClickListener(){
				@Override
			    public void onClick(View v) {
			        //Inform the user the button has been clicked
			        //Toast.makeText(getBaseContext(), "Clicked project with id = " + v.getId(), 2).show();
			        
					//Start the edit/create project activity
					Intent intent = new Intent(getBaseContext(), CreateNewProject.class);
					intent.putExtra(Constants.PROJECT_ID, v.getId()); //Optional parameters
					startActivity(intent);
			        
			    }
			});
			
			
			//Add row to table
			table.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
		} //End of for loop
		
	}
	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_no_manage_projects, menu);
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
		
		case R.id.action_yoursettings:
			 intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default: 
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onResume()
	{	
		super.onResume();
		initProjectTable();
	}
	
}
