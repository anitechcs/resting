package com.anitech.resting.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import com.anitech.resting.config.RestingRequestConfig;
import com.anitech.resting.exception.RestingException;
import com.anitech.resting.util.RestingConstants;

/**
 * This class will convert various input data format to uniform format
 * 
 * @author Tapas
 *
 */
public class RequestDataMassager {

	private static final Logger logger = LogManager.getLogger(RequestDataMassager.class);
	
	/**
	 * Internal method to massage the request data for POST and PUT
	 * 
	 * @param inputData
	 * @param config
	 * @return
	 * @throws RestingException
	 */
	public static StringEntity massageRequestData(Object inputData, RestingRequestConfig config) throws RestingException {
		logger.debug("Inside massageRequestData!");
		StringEntity stringEntity = null;
		String contentType = config.getHeaders().get("Content-Type");
		if(contentType.equalsIgnoreCase(RestingConstants.CONTENT_TYPE_APPLICATION_JSON)) {
			stringEntity = massageJSONData(inputData);
		} else if(contentType.equalsIgnoreCase(RestingConstants.CONTENT_TYPE_APPLICATION_XML)) {
			stringEntity = massageXMLData(inputData);
		} else {
			logger.error("Unsupported Content-Type found in header!");
			throw new RestingException("Unsupported Content-Type found in header!");
		}
		return stringEntity;
	}
	
	private static StringEntity massageJSONData(Object inputData) throws RestingException {
		logger.debug("Inside massageJSONData!");
		StringEntity stringEntity = null;
		if(inputData instanceof Map<?,?>) {
			try {
				logger.debug("Map>"+new JSONObject((Map<?,?>) inputData).toJSONString());
				stringEntity = new StringEntity(new JSONObject((Map<?,?>) inputData).toJSONString());
			} catch (UnsupportedEncodingException e) {
				throw new RestingException(e);
			}
		} else if(inputData instanceof String) {
			logger.debug("String>"+inputData);
			try {
				stringEntity = new StringEntity((String) inputData);
			} catch (UnsupportedEncodingException e) {
				throw new RestingException(e);
			}
		} else if(inputData instanceof StringBuffer || inputData instanceof StringBuilder) {
			logger.debug("String Buffer/Builder>"+inputData.toString());
			try {
				stringEntity = new StringEntity((String) inputData.toString());
			} catch (UnsupportedEncodingException e) {
				throw new RestingException(e);
			}
		} else if(inputData instanceof File) {
			
		} else {
			logger.error("Unparseable data found in inputs!");
			throw new RestingException("Unparseable data found in inputs!");
		}
		return stringEntity;
	}
	
	private static StringEntity massageXMLData(Object inputData) throws RestingException {
		logger.debug("Inside massageXMLData!");
		StringEntity stringEntity = null;
		if(inputData instanceof Map<?,?>) {
			
		} else if(inputData instanceof String) {
			
		} else if(inputData instanceof StringBuffer || inputData instanceof StringBuilder) {
			
		} else if(inputData instanceof File) {
			
		} else {
			logger.error("Unparseable data found in inputs!");
			throw new RestingException("Unparseable data found in inputs!");
		}
		return stringEntity;
	}
	
}
