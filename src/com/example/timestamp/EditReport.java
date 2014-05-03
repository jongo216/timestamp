/**  Copyright (c) 2014, Group D in course TNM082
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the {organization} nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/

package com.example.timestamp;

import java.util.Calendar;

import com.example.timestamp.model.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;


public class EditReport extends Activity {

	private Button button;
	final Context message = this;
	private EditText commentField;
	
	TimePicker startPicker, endPicker;
	
	TimePost timePost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editreport);
		
		//Initialize time post object (create new if id = 0)
		int timePostId = getIntent().getIntExtra(Constants.TIME_POST_ID, 0);
		if (timePostId != 0) {
			DB db = new DB(this);
			timePost = db.getTimePost(timePostId);
			if (timePost == null) timePost = new TimePost();
		}
		else
			timePost = new TimePost();
		//Link GUI objects with xml ids
		button = (Button)findViewById(R.id.button_save);
		commentField = (EditText) findViewById(R.id.editTextComment);
		startPicker = (TimePicker) findViewById(R.id.timePickerStart);
		endPicker = (TimePicker) findViewById(R.id.timePickerEnd);
		
		//Init GUI functionality
		initCommentField();
		initTimePickers();
		initSaveButton();
	}

	
	public void initSaveButton() {
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder ad = new AlertDialog.Builder(message);
				
				ad.setTitle("Bekräftelse");
				 
				// set dialog message
				ad
					.setMessage("Vill du spara?")
					.setCancelable(false)
					.setPositiveButton("Ja",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							timePost.startTime.set(Calendar.HOUR_OF_DAY, startPicker.getCurrentHour());
							timePost.startTime.set(Calendar.MINUTE, startPicker.getCurrentMinute());
							
							timePost.endTime.set(Calendar.HOUR_OF_DAY, endPicker.getCurrentHour());
							timePost.endTime.set(Calendar.MINUTE, endPicker.getCurrentMinute());
							
							timePost.comment = commentField.getEditableText().toString();
							Log.d("Oskar testar", timePost.comment);
							
							DB db = new DB(message);
							db.set(timePost);
							// if this button is clicked, close
							// current activity
							EditReport.this.finish();
						}
					  })
					.setNegativeButton("Nej",new DialogInterface.OnClickListener() {
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
	
	public void initTimePickers() {
		startPicker.setIs24HourView(true);
        startPicker.setCurrentHour(timePost.startTime.get(Calendar.HOUR_OF_DAY));
        startPicker.setCurrentMinute(timePost.startTime.get(Calendar.MINUTE));

        endPicker.setIs24HourView(true);
        endPicker.setCurrentHour(timePost.endTime.get(Calendar.HOUR_OF_DAY));
        endPicker.setCurrentMinute(timePost.endTime.get(Calendar.MINUTE));
	}
	
	public void initCommentField(){
		if (timePost.comment.length() > 0)
			commentField.setText(timePost.comment);
		
		commentField.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d("hej", "click");
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				return false;
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_report, menu);
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


