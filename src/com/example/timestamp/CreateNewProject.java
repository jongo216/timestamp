package com.example.timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.timestamp.model.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;


import com.example.timestamp.model.DB;
import com.example.timestamp.model.Project;

public class CreateNewProject extends Activity {

	final Context context = this;
	Button saveButton, deleteButton;
	Project project;
	EditText name, code, customer, description;
	TextView label;
	CheckBox isSharedProject;
	
	//Menu-bar
	//Orginal code without menu
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_project);
		
		//Stylear actionbar
		ActionBar actionBarTop = getActionBar(); //Action bar med rubrik + settingsknapp
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionBarTop.setCustomView(R.layout.actionbar);
		actionBarTop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#11ffffff")));
		actionBarTop.setDisplayShowHomeEnabled(true); //Nödvändig(??) för att visa action bars i rätt ordning
		
		//Fix som döljer ikonen i övre vänstra hörnet
		View homeIcon = findViewById(android.R.id.home);
		((View) homeIcon.getParent()).setVisibility(View.GONE);
		
		//Link to xml objects
		saveButton = (Button)findViewById(R.id.save_project_button);
		deleteButton = (Button)findViewById(R.id.delete_project_button);
		label = (TextView)findViewById(R.id.createProjectTitleTextView);
		name = (EditText)findViewById(R.id.projectNameEditText);
		customer = (EditText)findViewById(R.id.projectCustomerEditText);
		description = (EditText)findViewById(R.id.projectDescriptionEditText);
		isSharedProject = (CheckBox)findViewById(R.id.projectSharedCheckBox);
		
		//Check if the intent specifies a specific project. In that case open that project.
		Intent intent = getIntent();
		int projectId = intent.getIntExtra(Constants.PROJECT_ID, 0);
		if (projectId > 0)
		{
			DB db = new DB(this);
			project = db.getProject(projectId);
			if (project == null) project = new Project();
		}
		else
			project = new Project();
		
		//Set header label depending on situation
		if (project.getId() > 0){
			label.setText("Edit Project");
			deleteButton.setVisibility(0); //visible=0 invisible=1 gone=2
			initDeleteButton();
		}
		else{
			label.setText("Create New Project");
		}
		
		//Call init methods
		initTextFields();
		initSaveButton();
	}
	
	private void initTextFields() {
		name.setText(project.getName());
		customer.setText(project.getCustomer());
		description.setText(project.getDescription());
		isSharedProject.setChecked(!project.getIsPrivate());
	}
	
	private void initDeleteButton() {
		deleteButton.setOnClickListener(new OnClickListener(){
			@Override
		    public void onClick(View v) {
				AlertDialog.Builder ad = new AlertDialog.Builder(context);
				
				ad.setMessage(R.string.editProjectConfirmMessage);
				ad.setCancelable(false);
				ad.setPositiveButton(R.string.editProjectConfirmPositive,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						DB db = new DB(context);
						db.deleteProject(project);
						finish();
					}
				});
				ad.setNegativeButton(R.string.editProjectConfirmNegative,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
	 
				// create alert dialog
				AlertDialog alertDialog = ad.create();
 
				// show it
				alertDialog.show();
		    }
		});
	}
	
	private void initSaveButton() {
		saveButton.setOnClickListener(new OnClickListener(){
			@Override
		    public void onClick(View v) {
		        //Start the EditReport activity
		        project.setName(name.getEditableText().toString());
		        project.setCustomer(customer.getEditableText().toString());
		        project.setDescription(description.getEditableText().toString());
		        project.setIsPrivate(!isSharedProject.isChecked());
				DB db = new DB(context);
				db.set(project);
				
				finish();
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activitybar, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



}
