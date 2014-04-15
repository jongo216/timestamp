package com.example.timestamp.tests;

import java.util.GregorianCalendar;

import com.example.timestamp.model.TimePost;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestTimePost{
	
	private TimePost time;
	
	/*
	@BeforeClass
	public static void setupBeforeClass(){
		System.out.println("setupBeforeClass...");		
	}*/
	
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
	public void testgetEndTimeNull(){
		//System.out.println("testGetEndTimeNull()...");
		time.endTime = null;
		String expected = "";
		//System.out.println(time.getEndTime());
		assertEquals(time.getEndTime(), expected);
	}
	/**********************************************************/
	
	@Test
	public void testGetWorkedHours(){
		time.setStartMinute(15);
		System.out.println(time.getWorkedHours());
		assertEquals(time.getWorkedHours(), 3.75, 0.005);
	}
}
