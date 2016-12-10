package com.anitech.resting;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anitech.resting.config.RestingRequestConfig;
import com.anitech.resting.exception.RestingException;
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
	
	private static CloseableHttpClient httpClient = HttpClients.createDefault();
	
	
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
	
	public Resting baseURI(String baseURI) {
		Resting.baseURI = baseURI;
		return instance;
	}

	
	public HttpResponse GET(String endpointUrl) throws RestingException {
		logger.info("GET!");
		RestingRequestConfig config = RestingUtil.getDefaultRequestConfig();
		return GET(endpointUrl, config);
	}
	
	/**
	 * Executes a GET request
	 * 
	 * @param endpointUrl
	 * @param config
	 * @return
	 * @throws RestingException
	 */
	public HttpResponse GET(String endpointUrl, RestingRequestConfig config) throws RestingException {
		logger.info("GET!");
		try {
			RequestConfig requestConfig = RequestConfig.custom()
				    .setSocketTimeout(config.getSocketTimeout())
				    .setConnectTimeout(config.getConnectTimeout())
				    .build();

			HttpGet get = new HttpGet(baseURI + endpointUrl);
			get.setConfig(requestConfig);
			get.setHeaders(RestingUtil.getHeadersFromRequest(config.getHeaders()));
			
			return httpClient.execute(get);
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
			throw new RestingException(cpe);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RestingException(ioe);
		}
	}
	
	public void POST(String endpointUrl, Map<Object, Object> inputParams) {
		logger.info("POST!");
		
	}

	public void PUT(String endpointUrl, Map<Object, Object> inputParams) {
		logger.info("PUT!");
	}

	public void DELETE(String endpointUrl) {
		logger.info("DELETE!");
	}
	
	public void HEAD(String endpointUrl) {
		logger.info("HEAD!");
	}
	
	public void TRACE(String endpointUrl) {
		logger.info("TRACE!");
	}
	
	public void OPTIONS(String endpointUrl) {
		logger.info("OPTIONS!");
	}
	
}
