package com.example.timestamp;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Start extends Fragment{
	
	// Instansvariabler
	//final Context context = this;
	public String[] projectsMenuString = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	private ImageButton imgButton;
	private Button button;
	private View rootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_start, container, false);
        activityInitStart();
       
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
		
	}
	
    public void imageButtonListener(){
		
		//Call timepost function..?
		imgButton = (ImageButton) rootView.findViewById(R.id.btnCheckIn);
		
		imgButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0){

				
				TextView tv = (TextView) rootView.findViewById(R.id.textView2);
				tv.setVisibility(View.VISIBLE);
				tv.setText("Tid: 1.4 timmar");
				Toast.makeText(getActivity(), "Du har stämplat in!", Toast.LENGTH_SHORT).show();
				
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


