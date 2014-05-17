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

package com.example.timestamp.services;

import com.example.timestamp.Constants;
import com.example.timestamp.MainActivity;
import com.example.timestamp.R;
import com.example.timestamp.model.DB;
import com.example.timestamp.model.Project;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ReminderNotification extends BroadcastReceiver{

	@Override
    public void onReceive(Context context, Intent intent) {
		if (intent.getAction() == Constants.NOTIFY_REMIND_TO_STOP_WORKING_ACTION)
		{
			Intent newIntent = new Intent(context, MainActivity.class);
	        newIntent.setAction(Constants.NOTIFY_REMIND_TO_STOP_WORKING_ACTION);
	        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
	        
	        NotificationManager mNM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	        Notification notification = new NotificationCompat.Builder(context)
	        		.setContentTitle("You have worked 8 hours")
	        		.setContentText("Time to stop?")
	        		.setSmallIcon(R.drawable.clock_white)
	        		.setContentIntent(contentIntent)
	        		.setAutoCancel(true)
	        		.build();
	        mNM.notify(0, notification);
		}
		else
		{
			int projectId = intent.getIntExtra(Constants.PROJECT_ID, 0);
			DB db = new DB(context);
			Project project = db.getProject(projectId);
			
			Intent newIntent = new Intent(context, MainActivity.class);
	        newIntent.setAction(Constants.SEND_TIMES_ACTION);
	        newIntent.putExtra(Constants.PROJECT_ID, projectId);
	        
	        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
	        
	        NotificationManager mNM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	        Notification notification = new NotificationCompat.Builder(context)
	        		.setContentTitle(project.getName())
	        		.setContentText("Time to send your report")
	        		.setSmallIcon(R.drawable.clock_white)
	        		.setContentIntent(contentIntent)
	        		.setAutoCancel(true)
	        		.build();
	        mNM.notify(projectId, notification);
	        
	        Log.d("Oskar", "Notification created. projectId = " + projectId);
	        Log.d("Oskar", "pendingIntent = " + contentIntent);
		}
    }
}
