package com.example.timestamp;

import com.example.timestamp.model.*;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.Build;

public class CreateNewProject extends Activity {

	final Context context = this;
	Button saveButton;
	Project project;
	EditText name, code, customer, description;
	TextView label;
	CheckBox isSharedProject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_project);
		
		//Link to xml objects
		saveButton = (Button)findViewById(R.id.save_project_button);
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
		if (project.getId() > 0)
			label.setText("Edit Project");
		else
			label.setText("Create New Project");
		
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

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_project, menu);
		return true;
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
