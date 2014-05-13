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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.example.timestamp.model.DB;
import com.example.timestamp.model.SettingsManager;
import com.example.timestamp.model.TimePost;


public class StatsBarChartFragment extends Fragment implements UpdateableStatistics {

	private View rootView;
	private XYPlot barChart;
	private DB db;
	private ArrayList<TimePost> timePosts;
	private XYSeries data;
	
	
	public void setDB(DB database)
	{
		db = database;
		
	}
	
	@Override		//mother of all inits!
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.stats_bar_chart_fragment, container, false);
        
        barChart = (XYPlot) rootView.findViewById(R.id.barChart);        
        //Link to xml objects
        barChart = (XYPlot)rootView.findViewById(R.id.barChart);

        barChart.getLegendWidget().setTableModel(new DynamicTableModel(1, 2));
        barChart.getLegendWidget().setSize(new SizeMetrics(150, SizeLayoutType.ABSOLUTE, 200, SizeLayoutType.ABSOLUTE));

        
        
        // Set domain values for x axis
        barChart.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
        
        //Titles for axis
        barChart.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        // Settings for ticks and labels on x and y axis
        barChart.setTicksPerRangeLabel(3);               
        barChart.setDomainStep(XYStepMode.SUBDIVIDE, 2);
        
        //Set background color o.s.v
        barChart.getBorderPaint().setColor(Color.TRANSPARENT);
        barChart.getBackgroundPaint().setColor(Color.TRANSPARENT);
        barChart.getGraphWidget().getBackgroundPaint().setColor(Color.TRANSPARENT);
        barChart.getGraphWidget().getGridBackgroundPaint().setColor(Color.TRANSPARENT);
        barChart.getGraphWidget().setPaddingBottom(30);
        
        //Domain (X-labels) settings
        barChart.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.BLACK);
        barChart.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        barChart.getGraphWidget().getRangeOriginLabelPaint().setColor(Color.BLACK);
        
        barChart.setTicksPerRangeLabel(1); 
        barChart.setTicksPerDomainLabel(1);
        barChart.setDomainStep(XYStepMode.SUBDIVIDE, 7);
        
        
        //Range (Y-labels) settings
        barChart.getGraphWidget().setRangeValueFormat(new DecimalFormat("0"));
        barChart.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);
        barChart.getRangeLabelWidget().getLabelPaint().setColor(Color.BLACK);

        
        barChart.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 2); // Set tick and a fixed value of worked hours..
        barChart.setRangeBoundaries(0, 12, BoundaryMode.FIXED);
        
        //Margins and Padding for whole plot
        barChart.getGraphWidget().setMarginLeft(30);
        barChart.getGraphWidget().setPaddingLeft(0);
        barChart.getGraphWidget().setMarginRight(200);
        //hide legend
        barChart.getLegendWidget().setVisible(false);
        
        initChart();
        update();
        
        
        return rootView;
    }
	
	
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//updateChart();
	}


	private void initChart()
	{
		
		
		
	}
	
	@Override
	public void update()
	{   
		//Log.d("Fragment info: ", getActivity().toString());
		
		
		if (barChart == null)
		{
			Log.d("Fragment info: ", "barChart = null");
			return;
		}
		
		if (db == null)
			db = new DB(getParentFragment().getActivity());
		
		
		//DB db = new DB(getActivity());
		int currentProject = SettingsManager.getCurrentProjectId(getParentFragment().getActivity());
		timePosts = db.getTimePosts(currentProject);
		Number[] hoursPerDay = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
		
		GregorianCalendar startOfWeek = new GregorianCalendar();
		startOfWeek.setTimeInMillis(startOfWeek.getTimeInMillis() - 1000 * 3600 * 24 * 2);
		startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		startOfWeek.set(Calendar.HOUR_OF_DAY, 1);
		
		for (int n = 0; n < timePosts.size(); n++) {
			if (timePosts.get(n).startTime.getTimeInMillis() > startOfWeek.getTimeInMillis())
			{
				int day = (timePosts.get(n).startTime.get(Calendar.DAY_OF_WEEK) + 6) % 7;
				hoursPerDay[day-1] = (Number)(timePosts.get(n).getWorkedHours() + hoursPerDay[day].floatValue());
			}
		}

		//BarFormatter formatter = new BarFormatter(Color.argb(200, 100, 150, 100), Color.argb(200, 10, 15, 10));
		
		//Format for days of the week
		Number[] xValues = {0, 1, 2, 3, 4, 5, 6};
		
		data = new SimpleXYSeries(Arrays.asList(hoursPerDay), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Worked Hours");

		barChart.clear();
		MyBarFormatter seriesFormat = new MyBarFormatter(Color.parseColor("#063A70"), Color.WHITE);
        barChart.addSeries(data, seriesFormat); 
        ((MyBarRenderer) barChart.getRenderer(MyBarRenderer.class)).setBarWidthStyle(BarRenderer.BarWidthStyle.FIXED_WIDTH);
        ((MyBarRenderer) barChart.getRenderer(MyBarRenderer.class)).setBarWidth(23);
		
		barChart.redraw();
	}
	
	private class GraphXLabelFormat extends Format {

	    String LABELS[] = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

	    @Override
	    public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
	        int parsedInt = Math.round(Float.parseFloat(object.toString()));
	        String labelString = LABELS[parsedInt];
	        buffer.append(labelString);
	        return buffer;
	    }
	    @Override
	    public Object parseObject(String string, ParsePosition position) {
	        return java.util.Arrays.asList(LABELS).indexOf(string);
	    }
	}    
		    
	@Override
	public void onResume()
	{	
		super.onResume();
	}
	
	
	class MyBarFormatter extends BarFormatter {
        public MyBarFormatter(int fillColor, int borderColor) {
            super(fillColor, borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass() {
            return MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer getRendererInstance(XYPlot plot) {
            return new MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter> {

        public MyBarRenderer(XYPlot plot) {
            super(plot);
        }

        protected MyBarFormatter getFormatter(int index, XYSeries series) {
            return getFormatter(series);
        }
    }
	

}
