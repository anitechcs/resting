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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anitech.resting.exception.RestingException;
import com.anitech.resting.http.request.RequestConfig;
import com.anitech.resting.http.request.RequestDataMassager;
import com.anitech.resting.http.response.RestingResponse;
import com.anitech.resting.util.RestingUtil;

/**
 * Resting library public API
 * 
 * @author Tapas
 *
 */
public class Resting {

	private static final Logger logger = LogManager.getLogger(Resting.class);

	private static Resting instance = null;

	private static String baseURI = "";

	private static boolean enableMetrics = false;
	
	private static boolean enableProcessingHooks = false;
	
	private static RequestConfig globalRequestConfig = null;

	private static CloseableHttpClient httpClient;

	// Getters and Setters
	public static String getBaseURI() {
		return baseURI;
	}

	public static void setBaseURI(String baseURI) {
		Resting.baseURI = baseURI;
	}

	public static boolean isEnableMetrics() {
		return enableMetrics;
	}

	public static void setEnableMetrics(boolean enableMetrics) {
		Resting.enableMetrics = enableMetrics;
	}
	
	public static boolean isEnableProcessingHooks() {
		return enableProcessingHooks;
	}

	public static void setEnableProcessingHooks(boolean enableProcessingHooks) {
		Resting.enableProcessingHooks = enableProcessingHooks;
	}

	public static RequestConfig getGlobalRequestConfig() {
		return globalRequestConfig;
	}

	public static void setGlobalRequestConfig(RequestConfig globalRequestConfig) {
		Resting.globalRequestConfig = globalRequestConfig;
	}

	
	/**
	 * Public API to get singleton Resting interface
	 * 
	 * @return
	 */
	public static Resting getInstance() {
		if (instance == null) {
			synchronized (Resting.class) {
				if (instance == null) {
					instance = new Resting();
				}
			}
		}
		return instance;
	}

	protected Resting() {
		// Exists only to defeat instantiation.
	}

	/**
	 * Fluent API to configure Resting instance with base URI
	 * 
	 * @param baseURI
	 * @return Resting
	 */
	public Resting baseURI(String baseURI) {
		Resting.baseURI = baseURI;
		return instance;
	}

	/**
	 * Fluent API to configure Resting instance Metrics enabled
	 * 
	 * @return Resting
	 */
	public Resting enableMetrics() {
		setEnableMetrics(true);
		return instance;
	}
	
	/**
	 * Fluent API to enable pre processing and post processing hooks
	 * 
	 * @return Resting
	 */
	public Resting enableProcessingHooks() {
		setEnableProcessingHooks(true);
		return instance;
	}
	
	/**
	 * Fluent API to set global request configuration for Resting, 
	 * Offcourse you can override it in service level
	 * 
	 * @return Resting
	 */
	public Resting globalRequestConfig(RequestConfig reqConfig) {
		setGlobalRequestConfig(reqConfig);
		return instance;
	}

	/**
	 * Executes a HTTP GET request with default configuration
	 * 
	 * @param endpointUrl
	 * @return RestingResponse
	 * @throws RestingException
	 */
	public RestingResponse GET(String endpointUrl) throws RestingException {
		logger.debug("Inside GET!");
		httpClient = HttpClients.createDefault();
		RequestConfig config = (globalRequestConfig == null)?RestingUtil.getDefaultRequestConfig():globalRequestConfig;
		return GET(endpointUrl, config);
	}

	/**
	 * Executes a HTTP GET request with provided configuration
	 * 
	 * @param endpointUrl
	 * @param config
	 * @return RestingResponse
	 * @throws RestingException
	 */
	public RestingResponse GET(String endpointUrl, RequestConfig config) throws RestingException {
		logger.debug("Inside GET!");
		long startTime = System.nanoTime();
		
		// override the request config
		config = RestingUtil.overrideGlobalRequestConfig(globalRequestConfig, config);
		httpClient = HttpClients.createDefault();
		try {
			org.apache.http.client.config.RequestConfig requestConfig = org.apache.http.client.config.RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			String fullURL = baseURI + endpointUrl;
			HttpGet get = new HttpGet(fullURL);
			get.setConfig(requestConfig);
			get.setHeaders(RestingUtil.getHeadersFromRequest(config.getHeaders()));

			HttpResponse res = httpClient.execute(get);
			RestingResponse response = new RestingResponse(res);
			
			if(enableMetrics) {
				long stopTime = System.nanoTime();
				long elapsedTime = (stopTime - startTime)/1000000;
				logger.fatal("Time taken to execute [GET - "+ fullURL +"] in milliseconds>> " + elapsedTime);
			}
			
			return response;
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
			throw new RestingException(cpe);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RestingException(ioe);
		}
	}

	/**
	 * Executes a HTTP POST request with default configuration
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @return RestingResponse
	 * @throws RestingException
	 */
	public RestingResponse POST(String endpointUrl, Object inputParams) throws RestingException {
		logger.debug("Inside POST!");
		httpClient = HttpClients.createDefault();
		RequestConfig config = (globalRequestConfig == null)?RestingUtil.getDefaultRequestConfig():globalRequestConfig;
		return POST(endpointUrl, inputParams, config);
	}

	/**
	 * Executes a HTTP POST request with provided configuration
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @param config
	 * @return RestingResponse
	 * @throws RestingException
	 */
	public RestingResponse POST(String endpointUrl, Object inputParams, RequestConfig config) throws RestingException {
		logger.debug("Inside POST!");
		long startTime = System.nanoTime();
		
		// override the request config
		config = RestingUtil.overrideGlobalRequestConfig(globalRequestConfig, config);
		httpClient = HttpClients.createDefault();
		try {
			org.apache.http.client.config.RequestConfig requestConfig = org.apache.http.client.config.RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			String fullURL = baseURI + endpointUrl;
			HttpPost post = new HttpPost(fullURL);
			post.setConfig(requestConfig);
			post.setHeaders(RestingUtil.getHeadersFromRequest(config.getHeaders()));
			post.setEntity(RequestDataMassager.massageRequestData(inputParams, config));

			HttpResponse res = httpClient.execute(post);
			RestingResponse response = new RestingResponse(res);
			
			if(enableMetrics) {
				long stopTime = System.nanoTime();
				long elapsedTime = (stopTime - startTime)/1000000;
				logger.fatal("Time taken to execute [POST - "+ fullURL +"] in milliseconds>> " + elapsedTime);
			}
			
			return response;
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
			throw new RestingException(cpe);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RestingException(ioe);
		}
	}

	/**
	 * Executes a HTTP PUT request with default configuration
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @throws RestingException
	 */
	public RestingResponse PUT(String endpointUrl, Object inputParams) throws RestingException {
		logger.debug("Inside PUT!");
		httpClient = HttpClients.createDefault();
		RequestConfig config = (globalRequestConfig == null)?RestingUtil.getDefaultRequestConfig():globalRequestConfig;
		return PUT(endpointUrl, inputParams, config);
	}

	/**
	 * Executes a HTTP PUT request with provided configuration
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @throws RestingException
	 */
	public RestingResponse PUT(String endpointUrl, Object inputParams, RequestConfig config) throws RestingException {
		logger.debug("Inside PUT!");
		long startTime = System.nanoTime();
		
		// override the request config
		config = RestingUtil.overrideGlobalRequestConfig(globalRequestConfig, config);
		httpClient = HttpClients.createDefault();
		try {
			org.apache.http.client.config.RequestConfig requestConfig = org.apache.http.client.config.RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			String fullURL = baseURI + endpointUrl;
			HttpPut put = new HttpPut(fullURL);
			put.setConfig(requestConfig);
			put.setEntity(RequestDataMassager.massageRequestData(inputParams, config));

			HttpResponse res = httpClient.execute(put);
			RestingResponse response = new RestingResponse(res);
			
			if(enableMetrics) {
				long stopTime = System.nanoTime();
				long elapsedTime = (stopTime - startTime)/1000000;
				logger.fatal("Time taken to execute [PUT - "+ fullURL +"] in milliseconds>> " + elapsedTime);
			}
			
			return response;
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
			throw new RestingException(cpe);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RestingException(ioe);
		}
	}

	/**
	 * Executes a HTTP DELETE request with default configuration
	 * 
	 * @param endpointUrl
	 * @throws RestingException
	 */
	public RestingResponse DELETE(String endpointUrl) throws RestingException {
		logger.debug("Inside DELETE!");
		httpClient = HttpClients.createDefault();
		RequestConfig config = (globalRequestConfig == null)?RestingUtil.getDefaultRequestConfig():globalRequestConfig;
		return DELETE(endpointUrl, config);
	}

	/**
	 * Executes a HTTP DELETE request with provided configuration
	 * 
	 * @param endpointUrl
	 * @param config
	 * @throws RestingException
	 */
	public RestingResponse DELETE(String endpointUrl, RequestConfig config) throws RestingException {
		logger.debug("Inside DELETE!");
		long startTime = System.nanoTime();
		
		// override the request config
		config = RestingUtil.overrideGlobalRequestConfig(globalRequestConfig, config);
		httpClient = HttpClients.createDefault();
		try {
			org.apache.http.client.config.RequestConfig requestConfig = org.apache.http.client.config.RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			String fullURL = baseURI + endpointUrl;
			HttpDelete delete = new HttpDelete(fullURL);
			delete.setConfig(requestConfig);
			delete.setHeaders(RestingUtil.getHeadersFromRequest(config.getHeaders()));

			HttpResponse res = httpClient.execute(delete);
			RestingResponse response = new RestingResponse(res);
			
			if(enableMetrics) {
				long stopTime = System.nanoTime();
				long elapsedTime = (stopTime - startTime)/1000000;
				logger.fatal("Time taken to execute [DELETE - "+ fullURL +"] in milliseconds>> " + elapsedTime);
			}
			
			return response;
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
			throw new RestingException(cpe);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RestingException(ioe);
		}
	}

}
