/**
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import com.anitech.resting.exception.RestingException;
import com.anitech.resting.http.Header;
import com.anitech.resting.http.request.RequestConfig;
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
	private static Header[] headers = {
	    new Header("Content-Type", "application/json"),
	    new Header("Accept", "application/xml,text/plain,application/json"),
	    new Header("Connection", "keep-alive")
	};
	
	@BeforeClass
	public static void setupTestEnv() {
		logger.info("Initializing setupTestEnv!");
		
		inputs = new HashMap<Object, Object>();
		inputs.put("name", "Tapas");
		
		RequestConfig globalConfig = new RequestConfig();
		globalConfig.setConnectTimeout(1000);
		globalConfig.setSocketTimeout(1000);
		globalConfig.setHeaders(headers);
		
		resting = Resting
					.getInstance()
					.enableMetrics()
					.enableProcessingHooks()
					.globalRequestConfig(globalConfig)
					.baseURI("https://jsonplaceholder.typicode.com");
		
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
		
		RequestConfig config = new RequestConfig();
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
		
		RequestConfig config = new RequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		RestingResponse res = resting.POST("/posts", inputs, config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithConfigXML() throws RestingException {
		
		RequestConfig config = new RequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.addHeader(new Header("Content-Type", "application/json"));
		
		RestingResponse res = resting.POST("/posts", inputs, config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(201, res.getStatusCode());
	}
	
	@Test
	public void testRestingPOSTWithConfigXMLFile() throws RestingException {
		
		RequestConfig config = new RequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.addHeader("Content-Type", "application/xml");
		
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
		
		RequestConfig config = new RequestConfig();
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
		
		RequestConfig config = new RequestConfig();
		config.setConnectTimeout(5000);
		config.setSocketTimeout(5000);
		config.setHeaders(headers);
		
		RestingResponse res = resting.DELETE("/posts/1", config);
		logger.info(res.getHttpStatus());
		Assert.assertEquals(200, res.getStatusCode());
	}
	
}
