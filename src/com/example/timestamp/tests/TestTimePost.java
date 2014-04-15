package com.example.timestamp.tests;

import com.example.timestamp.model.TimePost;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestTimePost{
	
	private TimePost time;
	
	@BeforeClass
	public static void setupBeforeClass(){
		System.out.println("setupBeforeClass...");		
	}
	
	@Before
	public void setupBefore(){
		System.out.println("\nsetupBefore()...");
		time = new TimePost();
	}
	
	@Test
	public void testDefaultConstructor(){
		time = null;
		time = new TimePost();
		System.out.println("testDefaultConstructor()...");
		assertNotEquals(time.startTime, null);
		assertNotEquals(time.endTime, null);
		assertNotEquals(time.id, null);
		assertNotEquals(time.isSigned, null);
		assertNotEquals(time.comment, null);
		assertNotEquals(time.projectId, null);
		assertNotEquals(time.commentIsShared, null);
	}
	
	@Test
	public void testConstructor(){
		time = null;
		time = new TimePost(2014, 4, 15, 11, 18);
		System.out.println("testConstructor()...");
		assertNotEquals(time.startTime, null);
		assertNotEquals(time.endTime, null);
		assertNotEquals(time.id, null);
		assertNotEquals(time.isSigned, null);
		assertNotEquals(time.comment, null);
		assertNotEquals(time.projectId, null);
		assertNotEquals(time.commentIsShared, null);
	}
	
	@Test
	public void testAdd(){
		System.out.println("testAdd()...");
		
		String str = "Junit is working fine";
		assertEquals("Junit is working fine", str);
	}
	
	@Test
	public void testGetTime(){
		System.out.println("testGetTime()...");
		//String expected = "hejehej";
		//assertEquals(time.id, -1);
	}

}
