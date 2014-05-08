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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.timestamp.model.DB;
import com.example.timestamp.model.SettingsManager;
import com.example.timestamp.model.TimePost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StatsSummaryFragment extends Fragment implements UpdateableStatistics {

	private View rootView;
	private TextView weeklyHours, weeklyProjectHours, totalProjectHours;
	
	
	@Override		//mother of all inits!
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.stats_summary_fragment, container, false);
        //parentActivity = getActivity();
        
        weeklyHours = (TextView)rootView.findViewById(R.id.summary_weekly_hours2);
        weeklyProjectHours = (TextView)rootView.findViewById(R.id.summary_weekly_project_hours);
        totalProjectHours = (TextView)rootView.findViewById(R.id.summary_total_project_hours);
        
        return rootView;
    }


	
		    
	@Override
	public void onResume()
	{	
		super.onResume();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (weeklyHours == null) return;
		
		DB db = new DB(getParentFragment().getActivity());
		
		int currentProject = SettingsManager.getCurrentProjectId(getParentFragment().getActivity());
		
		ArrayList<TimePost> timePosts = db.getTimePosts();
		
		double wh = 0, wph = 0, tph = 0; 
		GregorianCalendar startOfWeek = new GregorianCalendar();
		startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		startOfWeek.set(Calendar.HOUR_OF_DAY, 0);
		startOfWeek.set(Calendar.MINUTE, 1);
		long startOfWeekAsLong = startOfWeek.getTimeInMillis();
		
		for (TimePost t : timePosts)
		{
			if (t.projectId == currentProject) {
				tph += t.getWorkedHours();
				if (t.startTime.getTimeInMillis() > startOfWeekAsLong) {
					wph += t.getWorkedHours();
					wh += t.getWorkedHours();
				}
			}
			else if (t.startTime.getTimeInMillis() > startOfWeekAsLong)
				wh += t.getWorkedHours();
		}
		DecimalFormat df = new DecimalFormat("#.#h");
		
		weeklyHours.setText(df.format(wh));
		weeklyProjectHours.setText(df.format(wph));
		totalProjectHours.setText(df.format(tph));
	}
}
