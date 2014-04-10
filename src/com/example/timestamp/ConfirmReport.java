package com.example.timestamp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfirmReport extends Fragment {
	
	String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	private Button button;
	private View rootView;

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_confirmreport, container, false);
        activityInitConfirmReport();
       
        return rootView;
    }

	public void activityInitConfirmReport(){
	
		//Letar efter en spinner i activity_main.xml med ett specifict id
		Spinner spinner = (Spinner) rootView.findViewById(R.id.projects_menu_spinner2);
				
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
		spinner.setAdapter(adapter);
		
				
		button = (Button) rootView.findViewById(R.id.sendReportButton);

		button.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0){
	
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				
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