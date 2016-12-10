package com.anitech.resting;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.anitech.resting.config.RestingRequestConfig;
import com.anitech.resting.exception.RestingException;

/**
 * Unit test for Resting main library
 * We are using free REST API's available from https://jsonplaceholder.typicode.com
 * 
 * @author Tapas
 *
 */
public class RestingTest {
	
	private static final Logger logger = LogManager.getLogger(RestingTest.class);
	
	private static Resting resting;
	private static Map<Object, Object> inputs;
	private static Map<String, String> headers;
	
	@BeforeClass
	public static void setupTestEnv() {
		logger.info("Initializing setupTestEnv!");
		resting = Resting
					.getInstance()
					.enableMetrics()
					.baseURI("https://jsonplaceholder.typicode.com");
		
		inputs = new HashMap<Object, Object>();
		inputs.put("name", "Tapas");
		
		headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "*");
	}

	@Test
	public void testRestingGET() throws RestingException {
		
		HttpResponse res = resting.GET("/posts/1");
		logger.info(res.getStatusLine());
		Assert.assertEquals(200, res.getStatusLine().getStatusCode());
		
	}
	
	@Test
	public void testRestingGETWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		HttpResponse res = resting.GET("/posts/1", config);
		logger.info(res.getStatusLine());
		Assert.assertEquals(200, res.getStatusLine().getStatusCode());
		
	}
	
	@Test
	public void testRestingPOST() throws RestingException {
		HttpResponse res = resting.POST("/posts", inputs);
		logger.info(res.getStatusLine());
		Assert.assertEquals(201, res.getStatusLine().getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		HttpResponse res = resting.POST("/posts", inputs, config);
		logger.info(res.getStatusLine());
		Assert.assertEquals(201, res.getStatusLine().getStatusCode());
	}
	
	@Test
	public void testRestingPUT() throws RestingException {
		HttpResponse res = resting.PUT("/posts/1", inputs);
		logger.info(res.getStatusLine());
		Assert.assertEquals(200, res.getStatusLine().getStatusCode());
	}
	
	@Test
	public void testRestingPUTWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		HttpResponse res = resting.PUT("/posts/1", inputs, config);
		logger.info(res.getStatusLine());
		Assert.assertEquals(200, res.getStatusLine().getStatusCode());
	}
	
	@Test
	public void testRestingDELETE() throws RestingException {
		HttpResponse res = resting.DELETE("/posts/1");
		logger.info(res.getStatusLine());
		Assert.assertEquals(200, res.getStatusLine().getStatusCode());
	}
	
	@Test
	public void testRestingDELETEWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		HttpResponse res = resting.DELETE("/posts/1", config);
		logger.info(res.getStatusLine());
		Assert.assertEquals(200, res.getStatusLine().getStatusCode());
	}
	
}
