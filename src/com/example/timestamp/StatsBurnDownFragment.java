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
import java.util.Arrays;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.example.timestamp.model.DB;
import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;

public class StatsBurnDownFragment extends Fragment{

	private View rootView;
	
	private DB db;
	private XYPlot plot;

	@Override		//mother of all inits!
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.stats_burn_down_fragment, container, false);
     // initialize our XYPlot reference:
        plot = (XYPlot) rootView.findViewById(R.id.myBurnDownChart);
        
        //the legend
        plot.getLegendWidget().setTableModel(new DynamicTableModel(1, 2));
        plot.getLegendWidget().setSize(new SizeMetrics(200, SizeLayoutType.ABSOLUTE, 350, SizeLayoutType.ABSOLUTE));
        
        
        
        // add a semi-transparent black background to the legend
        // so it's easier to see overlaid on top of our plot:
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setAlpha(140);
        plot.getLegendWidget().setBackgroundPaint(bgPaint);
 
        // adjust the padding of the legend widget to look a little nicer:
        plot.getLegendWidget().setPadding(10, 1, 1, 1);     
        


        // edge of the graph widget:
        plot.getLegendWidget().position(
        		0,
                XLayoutStyle.ABSOLUTE_FROM_RIGHT,
                400,
                YLayoutStyle.ABSOLUTE_FROM_BOTTOM,
                AnchorPosition.RIGHT_BOTTOM
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
        		"Saab");
        
        plot.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getActivity(),
                R.xml.line_point_formatter_with_plf1);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
     // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getActivity(),
        R.xml.line_point_formatter_with_plf2);
        plot.addSeries(series2, series2Format);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(4);
                          
        plot.setDomainStep(XYStepMode.SUBDIVIDE, 6);
        plot.getGraphWidget().setDomainLabelOrientation(0); //Changed from -45
        
        //Set background color o.s.v
        plot.getBorderPaint().setColor(Color.WHITE);
        plot.getBackgroundPaint().setColor(Color.WHITE);
        plot.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        
        //Domain (X-labels) settings
        plot.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.BLACK);
        plot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        
        //Range (Y-labels) settings
        plot.getGraphWidget().setRangeValueFormat(new DecimalFormat("0"));
        plot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);
        
        db = new DB(getActivity());
        //parentActivity = getActivity();
        
        return rootView;
    }
	
	private class GraphXLabelFormat extends Format {

	    String LABELS[]  = {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec",};

	    @Override
	    public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
	        int parsedInt = Math.round(Float.parseFloat(object.toString()));
	        String labelString = LABELS[parsedInt];
	        System.out.println(LABELS[parsedInt]);
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
	
}
