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
import com.example.timestamp.model.SettingsManager;
import com.example.timestamp.model.TimePost;

public class StatsBurnDownFragment extends Fragment implements UpdateableStatistics {

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
        
     // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {20, 16, 15, 12, 6, 4};
        Number[] series2Numbers = {24, 20, 10, 3, 2, 1};
        
        Number[] xValues = {0, 1, 2, 3, 4, 5};
         
        XYSeries series1 = new SimpleXYSeries(
        		 Arrays.asList(xValues) // SimpleXYSeries takes a List so turn our array into a List
                ,Arrays.asList(series1Numbers), // Y_VALS_ONLY means use the element index as the x value
                "Combitech");
     // same as above
        XYSeries series2 = new SimpleXYSeries(
        		Arrays.asList(xValues),
        		Arrays.asList(series2Numbers),
        		"Siemens");
        
        plot.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getActivity(),
                R.xml.line_point_formatter_with_plf1);
        series1Format.getPointLabelFormatter().setTextPaint(bgPaint);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
     // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getActivity(),
        		R.xml.line_point_formatter_with_plf2);
        series2Format.getPointLabelFormatter().setTextPaint(bgPaint);
        
        plot.addSeries(series2, series2Format);
       
        //Titles for axis
        plot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        plot.getGraphWidget().getRangeGridLinePaint().setColor(Color.BLACK);
        plot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        
        
        // Settings for ticks and labels on x and y axis
        plot.setTicksPerRangeLabel(1);               
        plot.setDomainStep(XYStepMode.SUBDIVIDE, 6);
        plot.getGraphWidget().setDomainLabelOrientation(0); //Changed from -45
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL,10);
        plot.setRangeValueFormat(new DecimalFormat("10"));
               

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
        
                
        //db = new DB(getActivity());
        //parentActivity = getActivity();
        
        initChart();
        //update();
        
        return rootView;
    }
	
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//updateChart();
	}
	
	private void initChart()
	{
		
		
		
	}
	
	/*@Override
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
		startMonth.set(Calendar.MONTH, Calendar.MONTH);
		
		startOfWeek.setTimeInMillis(startOfWeek.getTimeInMillis() - 1000 * 3600 * 24 * 2); // Make a return to week before
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
	}*/
	
	
	
	private class GraphXLabelFormat extends Format {

	    String LABELS[]  = {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec",};

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

     
	
		    
	@Override
	public void onResume()
	{	
		super.onResume();
	}




	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
