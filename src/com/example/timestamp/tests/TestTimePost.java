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

package com.example.timestamp.tests;

import java.util.GregorianCalendar;

import com.example.timestamp.model.TimePost;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestTimePost{
	
	private TimePost time;
	
	@Before
	public void setupBefore(){
		//System.out.println("\nsetupBefore()...");
		time = new TimePost(new GregorianCalendar(2014, 3, 15, 13, 00), 
							new GregorianCalendar(2014, 3, 15, 17, 00), 1);
	}
	
	/**************** CONSTRUCTOR TIME TESTING ****************/
	@Test
	public void testDefaultConstructor(){
		time = null;
		time = new TimePost();
		//System.out.println("testDefaultConstructor()...");
		assertNotEquals(time.startTime, null);
		assertNotEquals(time.endTime, null);
		assertNotEquals(time.id, null);
		assertNotEquals(time.isSigned, null);
		assertNotEquals(time.comment, null);
		assertNotEquals(time.projectId, null);
		assertNotEquals(time.commentShared, null);
	}
	
	@Test
	public void testConstructor(){
		time = null;
		time = new TimePost(2014, 4, 15, 11, 18);
		//System.out.println("testConstructor()...");
		assertNotEquals(time.startTime, null);
		assertNotEquals(time.endTime, null);
		assertNotEquals(time.id, null);
		assertNotEquals(time.isSigned, null);
		assertNotEquals(time.comment, null);
		assertNotEquals(time.projectId, null);
		assertNotEquals(time.commentShared, null);
	}
	
	@Test
	public void testStartStopConstructor(){
		time = null;
		time = new TimePost(new GregorianCalendar(), new GregorianCalendar(), 1);
		//System.out.println("testStartStopConstructor()...");
		assertNotEquals(time.startTime, null);
		assertNotEquals(time.endTime, null);
		assertNotEquals(time.id, null);
		assertNotEquals(time.isSigned, null);
		assertNotEquals(time.comment, null);
		assertNotEquals(time.projectId, null);
		assertNotEquals(time.commentShared, null);
	}
	/**********************************************************/
	
	/******************* START TIME TESTING *******************/
	@Test
	public void testPrintStartTime(){
		//System.out.println("testPrintStartTime()...");
		String expected = "2014 Apr 15 13:00:00";
		//System.out.println(time.printStartTime());
		assertEquals(time.printStartTime(), expected);
	}
	
	@Test
	public void testGetStartTime(){
		//System.out.println("testGetStartTime()...");
		String expected = "2014-Apr-15 13:00:00";
		//System.out.println(time.getStartTime());
		assertEquals(time.getStartTime(), expected);
	}
	
	@Test
	public void testPrintStartTimeNull(){
		//System.out.println("testPrintEndTimeNull()...");
		time.startTime = null;
		String expected = "";
		//System.out.println(time.getEndTime());
		assertEquals(time.printStartTime(), expected);
	}
	
	@Test
	public void testGetStartTimeNull(){
		//System.out.println("testGetEndTimeNull()...");
		time.startTime = null;
		String expected = "";
		//System.out.println(time.getEndTime());
		assertEquals(time.getStartTime(), expected);
	}
	
	@Test
	public void testSetStartTime(){
		time.startTime = null;
		assertEquals(time.startTime, null);
		time.setStartTime(new GregorianCalendar());
		assertNotEquals(time.startTime,null);
	}
	
	/**********************************************************/
	
	/******************** END TIME TESTING ********************/
	@Test
	public void testPrintEndTime(){
		//System.out.println("testPrintStartTime()...");
		String expected = "2014 Apr 15 17:00:00";
		//System.out.println(time.printEndTime());
		assertEquals(time.printEndTime(), expected);
	}
	
	@Test
	public void testGetEndTime(){
		//System.out.println("testGetEndTime()...");
		String expected = "2014-Apr-15 17:00:00";
		//System.out.println(time.getEndTime());
		assertEquals(time.getEndTime(), expected);
	}
	
	@Test
	public void testPrintEndTimeNull(){
		//System.out.println("testPrintEndTimeNull()...");
		time.endTime = null;
		String expected = "";
		//System.out.println(time.printEndTime());
		assertEquals(time.printEndTime(), expected);
	}

	@Test
	public void testGetEndTimeNull(){
		//System.out.println("testGetEndTimeNull()...");
		time.endTime = null;
		String expected = "";
		//System.out.println(time.getEndTime());
		assertEquals(time.getEndTime(), expected);
	}
	
	@Test
	public void testSetEndTime(){
		time.endTime = null;
		assertEquals(time.endTime, null);
		time.setEndTime(new GregorianCalendar());
		assertNotEquals(time.endTime,null);
	}
	/**********************************************************/
	
	@Test
	public void testGetWorkedHours(){
		time.setStartMinute(15);
		//System.out.println(time.getWorkedHours());
		assertEquals(time.getWorkedHours(), 3.75, 0.005);
	}
	
	@Test
	public void testSetProjectId(){
		int id = time.projectId;
		time.setProjectId(id+1);
		assertNotEquals(time.projectId,id);
	}
	
	/********************* SET FUNCTIONS **********************/
	@Test
	public void testSetStart(){
		time.setStartYear(2015);
		time.setStartMonth(0);
		time.setStartDay(1);
		time.setStartHour(0);
		time.setStartMinute(59);
		
		String startTime = time.getStartTime();
		
		String year = startTime.substring(0, 4);
		String month = startTime.substring(5, 8);
		String day = startTime.substring(9,11);
		String hour  = startTime.substring(12,14);
		String minute  = startTime.substring(15,17);
		
		assertEquals(year, "2015");
		assertEquals(month, "Jan");
		assertEquals(day, "01");
		assertEquals(hour, "00");
		assertEquals(minute, "59");
	}
	
	@Test
	public void testSetEnd(){
		time.setEndYear(2015);
		time.setEndMonth(0);
		time.setEndDay(1);
		time.setEndHour(0);
		time.setEndMinute(59);
		
		String endTime = time.getEndTime();
		
		String year = endTime.substring(0, 4);
		String month = endTime.substring(5, 8);
		String day = endTime.substring(9,11);
		String hour  = endTime.substring(12,14);
		String minute  = endTime.substring(15,17);
		
		assertEquals(year, "2015");
		assertEquals(month, "Jan");
		assertEquals(day, "01");
		assertEquals(hour, "00");
		assertEquals(minute, "59");
	}
	
	@Test
	public void testSetStartTimeNow() throws InterruptedException{
		time.setStartTimeNow();
		String startTime = time.getStartTime();
		Thread.sleep(2000);
		time.setStartTimeNow();
		assertNotEquals(startTime,time.getStartTime());
	}

	
	@Test
	public void testSetEndTimeNow() throws InterruptedException{
		time.setEndTimeNow();
		String endTime = time.getEndTime();
		Thread.sleep(2000);
		time.setEndTimeNow();
		assertNotEquals(endTime,time.getEndTime());
	}
	/**********************************************************/
}