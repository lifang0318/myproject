package com.renlink.push.msg;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestClass {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		int count = 100;
		int size = 10;
		
		int mod = count%size;
		
		System.out.println(mod);

	}

}
