package com.anitech.resting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.anitech.resting.config.RestingRequestConfig;
import com.anitech.resting.exception.RestingException;
import com.anitech.resting.http.response.RestingResponse;

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
	public void testRestingGET() throws RestingException, UnsupportedEncodingException, IOException, ParseException {
		
		RestingResponse res = resting.GET("/posts/1");
		JSONObject json = (JSONObject) res.getBody();
		logger.info(json.toJSONString());
		logger.info(res.getHttpStatus());
		Assert.assertEquals(200, res.getStatusCode());
		
	}
	
	@Test
	public void testRestingGETWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		RestingResponse res = resting.GET("/posts/2", config);
		JSONObject json = (JSONObject) res.getBody();
		logger.info(json.toJSONString());
		logger.info(res.getHttpStatus());
		Assert.assertEquals(200, res.getStatusCode());
		
	}
	
	@Test
	public void testRestingPOST() throws RestingException {
		RestingResponse res = resting.POST("/posts", inputs);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithFile() throws RestingException {
		File jsonFile = new File(getClass().getClassLoader().getResource("input.json").getFile());
		RestingResponse res = resting.POST("/posts", jsonFile);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithInputStream() throws RestingException {
		InputStream jsonFile = getClass().getClassLoader().getResourceAsStream("input.json");
		RestingResponse res = resting.POST("/posts", jsonFile);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithFileInputStream() throws RestingException, FileNotFoundException {
		File file = new File(getClass().getClassLoader().getResource("input.json").getFile());
		FileInputStream jsonFile = new FileInputStream(file);
		RestingResponse res = resting.POST("/posts", jsonFile);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		RestingResponse res = resting.POST("/posts", inputs, config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithConfigXML() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/xml");
		config.setHeaders(header);
		
		RestingResponse res = resting.POST("/posts", inputs, config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithConfigXMLFile() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/xml");
		config.setHeaders(header);
		
		File xmlFile = new File(getClass().getClassLoader().getResource("input.xml").getFile());
		RestingResponse res = resting.POST("/posts", xmlFile, config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPUT() throws RestingException {
		RestingResponse res = resting.PUT("/posts/1", inputs);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(200, res.getStatusCode());
	}
	
	@Test
	public void testRestingPUTWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		RestingResponse res = resting.PUT("/posts/1", inputs, config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(200, res.getStatusCode());
	}
	
	@Test
	public void testRestingDELETE() throws RestingException {
		RestingResponse res = resting.DELETE("/posts/1");
		logger.info(res.getHttpStatus());
		Assert.assertEquals(200, res.getStatusCode());
	}
	
	@Test
	public void testRestingDELETEWithConfig() throws RestingException {
		
		RestingRequestConfig config = new RestingRequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		RestingResponse res = resting.DELETE("/posts/1", config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(200, res.getStatusCode());
	}
	
}
