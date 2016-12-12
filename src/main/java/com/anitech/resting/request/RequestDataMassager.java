package com.anitech.resting.request;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import com.anitech.resting.config.RestingRequestConfig;
import com.anitech.resting.exception.RestingException;
import com.anitech.resting.util.RestingConstants;
import com.anitech.resting.util.RestingUtil;

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
	 * @return StringEntity
	 * @throws RestingException
	 */
	public static StringEntity massageRequestData(Object inputData, RestingRequestConfig config) throws RestingException {
		logger.debug("Inside massageRequestData!");
		StringEntity stringEntity = null;
		String contentType = config.getHeaders().get("Content-Type");
		//TODO: Support more Content-Types
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
			try {
				logger.debug("File>"+new String(Files.readAllBytes(((File) inputData).toPath()), Charset.defaultCharset()));
				byte[] bytes = Files.readAllBytes(((File) inputData).toPath());
				stringEntity = new StringEntity(new String(bytes, Charset.defaultCharset()));
			} catch (IOException e) {
				throw new RestingException(e);
			}
		} else if(inputData instanceof InputStream) {
			InputStream inputStream = ((InputStream) inputData);
			try {
				byte[] buffer = new byte[inputStream.available()];
				int length = inputStream.read(buffer);
				logger.debug("InputStream>"+new String(buffer, 0, length, Charset.defaultCharset()));
				stringEntity = new StringEntity(new String(buffer, 0, length, Charset.defaultCharset()));
			} catch (IOException e) {
				throw new RestingException(e);
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.error("Unparseable data format found in inputs!");
			throw new RestingException("Unparseable data format found in inputs!");
		}
		return stringEntity;
	}
	
	private static StringEntity massageXMLData(Object inputData) throws RestingException {
		logger.debug("Inside massageXMLData!");
		StringEntity stringEntity = null;
		if(inputData instanceof Map<?,?>) {
			try {
				String xmlString = RestingUtil.covertMapToXML((Map<?, ?>) inputData, RestingConstants.REQUEST_XML_ROOT);
				logger.debug("Map>"+xmlString);
				stringEntity = new StringEntity(xmlString);
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
			try {
				logger.debug("File>"+new String(Files.readAllBytes(((File) inputData).toPath()), Charset.defaultCharset()));
				byte[] bytes = Files.readAllBytes(((File) inputData).toPath());
				stringEntity = new StringEntity(new String(bytes, Charset.defaultCharset()));
			} catch (IOException e) {
				throw new RestingException(e);
			}
		} else if(inputData instanceof InputStream) {
			InputStream inputStream = ((InputStream) inputData);
			try {
				byte[] buffer = new byte[inputStream.available()];
				int length = inputStream.read(buffer);
				logger.debug("InputStream>"+new String(buffer, 0, length, Charset.defaultCharset()));
				stringEntity = new StringEntity(new String(buffer, 0, length, Charset.defaultCharset()));
			} catch (IOException e) {
				throw new RestingException(e);
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.error("Unparseable data format found in inputs!");
			throw new RestingException("Unparseable data format found in inputs!");
		}
		return stringEntity;
	}
	
}
