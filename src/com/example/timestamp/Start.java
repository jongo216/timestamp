package com.example.timestamp;





import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timestamp.model.DB;
import com.example.timestamp.model.Project;
import com.example.timestamp.model.TimePost;


public class Start extends Fragment{
	
	// Instansvariabler
	//final Context context = this;
	public String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	private ImageButton imgButton;
	private View rootView;

	private DB db;

	
	@Override		//mother of all inits!
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_start, container, false);
        db = new DB(getActivity().getApplicationContext());
        activityInitStart();
        
        db.terminateDatabaseHelper();
       
        return rootView;
    }

	

	// Initierar startvyn..
	private void activityInitStart(){

		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinnerProjectView = (Spinner) rootView.findViewById(R.id.projects_menu_spinner2);
		
		//För att välja vilken typ av graf man vill se. 
		//Hämtar namn från string array med menu item.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, projectsMenuString){
				
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
		

		//spinnerOverView.setAdapter(adapterView);
		//Hur spinnern ska se ut
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		imageButtonListener();
		dbButtonListener();
		
	}
	
    public void imageButtonListener(){
		
		//Call timepost function..?
		imgButton = (ImageButton) rootView.findViewById(R.id.btnCheckIn);
		
		imgButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0){
	
				
				if(db.getLatest().isSigned){
					
					db.set(new TimePost());
					imgButton.setBackgroundColor(Color.GREEN);
					String text = "Starting timelog at: " + db.getLatest().printStartTime();
					Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
				}else{
					db.getLatest().setEndTimeRandom();
					db.getLatest().isSigned = true;
					String text = "Stopped at: " + db.getLatest().printEndTime();
					
					Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
					imgButton.setBackgroundColor(Color.WHITE);
				}
				
				//Log.d("MESSAGE",db.getLatest().printStartTime()+" - "+db.getLatest().printEndTime());
				//Log.d("MESSAGE",Integer.toString(db.dbSize()));
				
				TextView tv = (TextView) rootView.findViewById(R.id.textView2);
				tv.setVisibility(View.VISIBLE);
				tv.setText("Tid: 1.4 timmar");
				
				
				//Toast.makeText(getActivity(), "Du har stämplat in!", Toast.LENGTH_SHORT).show();
			
				if(db.getLatest().isSigned){
					tv.setText("Worked ours" + Double.toString(db.getLatest().getWorkedHours()));
				}
				
			}	
		});
			
	}
    
    //database testing!
    public void dbButtonListener(){
    	//times
    	Button timesBtn = (Button) rootView.findViewById(R.id.Times);
    	timesBtn.setOnClickListener(new OnClickListener(){
    		public void onClick(View arg0){
    			ArrayList<TimePost> times = db.getTime(-1);
    			String text = "";
    			for(int i = 0; i < times.size(); ++i){
    				//buggs with printStart/EndTime
    				text = text + times.get(i).printStartTime() + " - " + times.get(i).printEndTime() + "\n";
    			}
    			Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    		}
    	});
    	//projects
    	Button projectsBtn = (Button) rootView.findViewById(R.id.Projects);
    	projectsBtn.setOnClickListener(new OnClickListener(){
    		public void onClick(View arg0){
    			ArrayList<Project> projects = db.getAllProjects();
    			String text = "";
    			for(int i = 0; i < projects.size(); ++i){
    				//buggs with printStart/EndTime
    				text = text + 	"Project name: " + projects.get(i).getName() + 
    								" \tOwner: " 	 + projects.get(i).getOwner() + 
    								" \tCustomer: "  + projects.get(i).getCustomer() + "\n";
    			}
    			Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    		}
    	});
    }
	

	public void startTime(View view){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
 
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
					getActivity().finish();
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


