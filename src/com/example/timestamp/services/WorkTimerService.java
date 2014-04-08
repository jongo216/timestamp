package com.example.timestamp.services;

import android.app.IntentService;
import android.content.Intent;

public class WorkTimerService extends IntentService {

	public WorkTimerService() {
	      super("WorkTimerService");
	}
	
	@Override
	protected void onHandleIntent(Intent arg0) {
		try {
			wait(2000);
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
