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
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.example.timestamp.StatsBarChartFragment.MyBarFormatter;
import com.example.timestamp.StatsBarChartFragment.MyBarRenderer;
import com.example.timestamp.model.DB;

import com.example.timestamp.model.Project;

import com.example.timestamp.model.SettingsManager;
import com.example.timestamp.model.TimePost;

public class StatsBurnDownFragment extends Fragment implements UpdateableStatistics {

	public String[] projectsMenuString; // = {"Projekt 1", "Projekt 2", "Nytt projekt"};
	public int[] projectMenuIds;
	private ArrayList<Project> projects;
	
	private View rootView;
	private DB db;
	private XYPlot plot;
	private ArrayList<TimePost> timePosts;
	private XYSeries data;
	
	
	public void setDB(DB database)
	{
		db = database;
		
	}
	
	@Override		//mother of all inits!
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.stats_burn_down_fragment, container, false);
     // initialize our XYPlot reference:
        plot = (XYPlot) rootView.findViewById(R.id.myBurnDownChart);
        
        //the legend
        Paint legendPaint = new Paint();
        legendPaint.setColor(Color.BLACK);
        legendPaint.setTextSize(25);
        plot.getLegendWidget().setTableModel(new DynamicTableModel(1, 2));
        plot.getLegendWidget().setSize(new SizeMetrics(150, SizeLayoutType.ABSOLUTE, 200, SizeLayoutType.ABSOLUTE));

        plot.getLegendWidget().setTextPaint(legendPaint);
        
               
        // add a semi-transparent black background to the legend
        // so it's easier to see overlaid on top of our plot:
        Paint bgPaint = new Paint();
     
        bgPaint.setColor(Color.TRANSPARENT);
        plot.getLegendWidget().setBackgroundPaint(bgPaint);
 
        // edge of the graph widget:
        plot.getLegendWidget().position(
        		0,
                XLayoutStyle.RELATIVE_TO_RIGHT,
                0,
                YLayoutStyle.RELATIVE_TO_TOP,
                AnchorPosition.RIGHT_TOP
        );
       
        //Titles for axis
        plot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        plot.getGraphWidget().getRangeGridLinePaint().setColor(Color.BLACK);
        plot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        
        // Settings for ticks and labels on x and y axis
        plot.setTicksPerRangeLabel(2);
        plot.setRangeBottomMax(0);
        plot.setDomainStep(XYStepMode.SUBDIVIDE, 6);
        plot.getGraphWidget().setDomainLabelOrientation(0); //Changed from -45
        plot.setRangeStep(XYStepMode.SUBDIVIDE, 6);
  
        plot.getBorderPaint().setColor(Color.TRANSPARENT);
        plot.getBackgroundPaint().setColor(Color.TRANSPARENT);
        plot.getGraphWidget().getBackgroundPaint().setColor(Color.TRANSPARENT);
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.TRANSPARENT);
        
        plot.getGraphWidget().setPaddingBottom(30);
         
        //Domain (X-labels) settings
        plot.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.BLACK);
           
        //Range (Y-labels) settings
        plot.getGraphWidget().setRangeValueFormat(new DecimalFormat("0"));
        plot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);
        plot.getGraphWidget().getRangeOriginLabelPaint().setColor(Color.BLACK);
        
        plot.getRangeLabelWidget().getLabelPaint().setColor(Color.BLACK);

        //Margins and Padding for whole plot
        plot.getGraphWidget().setMarginLeft(30);
        plot.getGraphWidget().setPaddingLeft(0);
        plot.getGraphWidget().setMarginRight(200);
  
        update(); 
        initChart();
        initProjects(); // get all projects

        return rootView;
    }
	
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//updateChart();
	}
	

	
	@Override
	public void update()
	{   
		//Log.d("Fragment info: ", getActivity().toString());
		
		
		if (plot == null)
		{
			Log.d("Fragment info: ", "plot = null");
			return;
		}
		
		if (db == null)
			db = new DB(getParentFragment().getActivity());
		
		
		//DB db = new DB(getActivity());
		int currentProject = SettingsManager.getCurrentProjectId(getParentFragment().getActivity());
		timePosts = db.getTimePosts(currentProject);
		Number[] hoursPerMonth = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}; // We take 6 months interval
		
		GregorianCalendar startMonth = new GregorianCalendar();
		startMonth.set(Calendar.YEAR, Calendar.MAY, 1);

		startMonth.set(Calendar.YEAR, Calendar.MAY, Calendar.DAY_OF_MONTH);
		startMonth.set(Calendar.HOUR_OF_DAY,1);

		
		for (int n = 0; n < timePosts.size(); n++) {
			if (timePosts.get(n).startTime.getTimeInMillis() > startMonth.getTimeInMillis())
			{
				int month = (timePosts.get(n).startTime.get(Calendar.MONTH)) % 4;

				hoursPerMonth[month] = (Number)(timePosts.get(n).getWorkedHours() + hoursPerMonth[month].floatValue());
			}
		}
		for(int i =1; i<hoursPerMonth.length;i++)
		{
			hoursPerMonth[i] = hoursPerMonth[i].floatValue() + hoursPerMonth[i-1].floatValue();
		}
		
		
        Paint plotPaint = new Paint(); 
        
        plotPaint.setColor(Color.TRANSPARENT);
        plot.getLegendWidget().setBackgroundPaint(plotPaint);

		//Format for 6 months ahead
		Number[] xValues = {0, 1, 2, 3, 4, 5};
		
		String datalabel = db.getProject(currentProject).getName();
				
		data = new SimpleXYSeries(Arrays.asList(xValues),Arrays.asList(hoursPerMonth), datalabel);
		
		LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
        seriesFormat.setPointLabelFormatter(new PointLabelFormatter());
        seriesFormat.configure(getActivity(),
                R.xml.line_point_formatter_with_plf1);
        seriesFormat.getPointLabelFormatter().setTextPaint(plotPaint);
        
        //------------
       
        
        plot.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
        
        int max = hoursPerMonth[5].intValue();
        max = max + 5 + (max/10);
        plot.setRangeTopMin(max);
        
       
        
        // add a new series' to the xyplot:
        plot.clear();
        
        plot.addSeries(data, seriesFormat);
		
		plot.redraw();
	}
	

	private class GraphXLabelFormat extends Format {

	    String LABELS[]  = {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

	    @Override
	    public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
	        int parsedInt = Math.round(Float.parseFloat(object.toString()));
	        String labelString = LABELS[parsedInt+4];
	        buffer.append(labelString);
	        return buffer;
	    }

	    @Override
	    public Object parseObject(String string, ParsePosition position) {
	        return java.util.Arrays.asList(LABELS).indexOf(string);
	    }
	}
    
	private void initProjects(){
		projects = db.getAllProjects();
			
	   projectsMenuString = new String[projects.size() + 1];
		//Log.d("HEJ", projectsMenuString[0]);
   	   projectMenuIds = new int[projects.size()+1];

	
	}
	
	
	private void initChart()
	{
		
		
		
	}
     	    
	@Override
	public void onResume()
	{	
		super.onResume();
	}

	
}
