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

package com.example.timestamp.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.ContentProvider;
import android.content.Context;
import android.util.Log;

public class Exporter {
	
	String csvFile = "mycsv.csv";
	BufferedReader br;
	
	
	public void createCSV(Context c, ArrayList<TimePost> tplist){
		try{
		
			
			DB db = new DB(c);
			
			FileOutputStream fOut = c.openFileOutput(csvFile, Context.MODE_WORLD_WRITEABLE);
		    OutputStreamWriter writer = new OutputStreamWriter(fOut); 			
			String output="";
			
			
			//CSV header 
			output += "Project";
			output += ',';
			output += "Start time";
			output += ',';
			output += "End time";
			output += ',';
			output += "Comment";
			output += '\n';
			
			
			//Write all timepost to csv output string.
			for (TimePost temp : tplist){
				
				output += db.getProject(temp.projectId).getName();
				output += ',';
				output += temp.getStartTime();
				output += ',';
				output += temp.getEndTime();
				output += ',';
				output += temp.getComment();
				output += '\n';
				
			}

			
			writer.write(output);
		    writer.close();
		    
			
		}catch(IOException e){
			e.printStackTrace();
			Log.d("hej","write fail");
		}
		
		
	}
	
	//Function for debugging CSV file.
	public void readCSV(Context c){
		try{
			FileInputStream in = c.openFileInput(csvFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String line;
			StringBuilder out = new StringBuilder();
			
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }
	        Log.d("hej", out.toString());  
			
			reader.close();
		}
		catch(IOException e){
			Log.d("hej", "Read fail");
			e.printStackTrace();
		}
	}
	/*
	 * 
	 * No functions defined yet!
	 * 
	 */
	
}
