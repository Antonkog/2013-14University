package com.example.android_advancedvideo.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.android_advancedvideo.Calc;

public class Test1 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	@Test
	public void testAdd() {
		Calc calc = new Calc();
		assertEquals(calc.add(1, 3), 4);
	}
	
	@Test
	public void testDiv() {
		Calc calc = new Calc();
		assertEquals(calc.div(1, 0), 0);
	}
}
