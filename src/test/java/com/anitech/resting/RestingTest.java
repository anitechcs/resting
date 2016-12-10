package com.anitech.resting;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.anitech.resting.config.RestingRequestConfig;
import com.anitech.resting.exception.RestingException;

/**
 * Unit test for Resting main library
 * 
 * @author Tapas
 *
 */
public class RestingTest {
	
	private static final Logger logger = LogManager.getLogger(RestingTest.class);
	
	private Resting resting;
	private String endpointUrl;
	private Map<Object, Object> inputs;
	private Map<String, String> headers;
	
	@Before
	public void setupTestEnv() {
		resting = Resting.getInstance().baseURI("https://www.google.com");
		endpointUrl = "/search?q=httpclient&btnG=Google+Search&aq=f&oq=";
		
		inputs = new HashMap<Object, Object>();
		inputs.put("Name", "Tapas");
		
		headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Allow", "*");
	}

	@Test
	public void testRestingGET() throws RestingException {
		
		HttpResponse res = resting.GET(endpointUrl);
		logger.debug(res.getStatusLine());
		
	}
	
	@Test
	public void testRestingGETWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(1000);
		config.setSocketTimeout(1000);
		config.setHeaders(headers);
		
		HttpResponse res = resting.GET(endpointUrl, config);
		logger.debug(res.getStatusLine());
		
	}
	
	@Test
	public void testRestingPOST() throws RestingException {
		resting.POST(endpointUrl, inputs);
	}
	
	@Test
	public void testRestingPUT() throws RestingException {
		resting.PUT(endpointUrl, inputs);
	}
	
	@Test
	public void testRestingDELETE() throws RestingException {
		resting.DELETE(endpointUrl);
	}
	
}
