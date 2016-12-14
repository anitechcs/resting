package com.anitech.resting;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anitech.resting.config.RestingRequestConfig;
import com.anitech.resting.exception.RestingException;
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
	 * 
	 * @param endpointUrl
	 * @return
	 * @throws RestingException
	 */
	public RestingResponse GET(String endpointUrl) throws RestingException {
		logger.debug("Inside GET!");
		httpClient = HttpClients.createDefault();
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
	public RestingResponse GET(String endpointUrl, RestingRequestConfig config) throws RestingException {
		logger.debug("Inside GET!");
		httpClient = HttpClients.createDefault();
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			HttpGet get = new HttpGet(baseURI + endpointUrl);
			get.setConfig(requestConfig);
			get.setHeaders(RestingUtil.getHeadersFromRequest(config.getHeaders()));

			HttpResponse res = httpClient.execute(get);
			RestingResponse response = new RestingResponse(res);
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
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @return
	 * @throws RestingException
	 */
	public RestingResponse POST(String endpointUrl, Object inputParams) throws RestingException {
		logger.debug("Inside POST!");
		httpClient = HttpClients.createDefault();
		RestingRequestConfig config = RestingUtil.getDefaultRequestConfig();
		return POST(endpointUrl, inputParams, config);
	}

	/**
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @param config
	 * @return
	 * @throws RestingException
	 */
	public RestingResponse POST(String endpointUrl, Object inputParams, RestingRequestConfig config) throws RestingException {
		logger.debug("Inside POST!");
		httpClient = HttpClients.createDefault();
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			HttpPost post = new HttpPost(baseURI + endpointUrl);
			post.setConfig(requestConfig);
			post.setHeaders(RestingUtil.getHeadersFromRequest(config.getHeaders()));
			post.setEntity(RequestDataMassager.massageRequestData(inputParams, config));

			HttpResponse res = httpClient.execute(post);
			RestingResponse response = new RestingResponse(res);
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
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @throws RestingException
	 */
	public RestingResponse PUT(String endpointUrl, Object inputParams) throws RestingException {
		logger.debug("Inside PUT!");
		httpClient = HttpClients.createDefault();
		RestingRequestConfig config = RestingUtil.getDefaultRequestConfig();
		return PUT(endpointUrl, inputParams, config);
	}

	/**
	 * 
	 * @param endpointUrl
	 * @param inputParams
	 * @throws RestingException
	 */
	public RestingResponse PUT(String endpointUrl, Object inputParams, RestingRequestConfig config) throws RestingException {
		logger.debug("Inside PUT!");
		httpClient = HttpClients.createDefault();
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			HttpPut put = new HttpPut(baseURI + endpointUrl);
			put.setConfig(requestConfig);
			put.setEntity(RequestDataMassager.massageRequestData(inputParams, config));

			HttpResponse res = httpClient.execute(put);
			RestingResponse response = new RestingResponse(res);
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
	 * 
	 * @param endpointUrl
	 * @throws RestingException
	 */
	public RestingResponse DELETE(String endpointUrl) throws RestingException {
		logger.debug("Inside DELETE!");
		httpClient = HttpClients.createDefault();
		RestingRequestConfig config = RestingUtil.getDefaultRequestConfig();
		return DELETE(endpointUrl, config);
	}

	/**
	 * @param endpointUrl
	 * @param config
	 * @throws RestingException
	 */
	public RestingResponse DELETE(String endpointUrl, RestingRequestConfig config) throws RestingException {
		logger.debug("Inside DELETE!");
		httpClient = HttpClients.createDefault();
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(config.getSocketTimeout())
					.setConnectTimeout(config.getConnectTimeout())
					.build();

			HttpDelete delete = new HttpDelete(baseURI + endpointUrl);
			delete.setConfig(requestConfig);
			delete.setHeaders(RestingUtil.getHeadersFromRequest(config.getHeaders()));

			HttpResponse res = httpClient.execute(delete);
			RestingResponse response = new RestingResponse(res);
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
