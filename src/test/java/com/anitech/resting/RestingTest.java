package com.anitech.resting;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Resting main library
 * 
 * @author Tapas
 *
 */
public class RestingTest {
	
	private Resting resting;
	private String endpointUrl;
	private Map<Object, Object> inputs;
	
	@Before
	public void setupTestEnv() {
		resting = Resting.getInstance();
		endpointUrl = "http://google.com";
		
		inputs = new HashMap<Object, Object>();
		inputs.put("Name", "Tapas");
	}

	@Test
	public void testResting() {
		resting.sayHello();
		
		resting.GET(endpointUrl);
		
		resting.POST(endpointUrl, inputs);
		
		resting.PUT(endpointUrl, inputs);
		
		resting.DELETE(endpointUrl);
	}
	
}
