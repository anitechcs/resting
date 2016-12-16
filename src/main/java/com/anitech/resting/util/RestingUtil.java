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
package com.anitech.resting.util;

import java.util.Map;

import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anitech.resting.http.Header;
import com.anitech.resting.http.request.RequestConfig;

/**
 * Resting Utility class
 * 
 * @author Tapas
 *
 */
public class RestingUtil {
	
	private static final Logger logger = LogManager.getLogger(RestingUtil.class);
	

	/**
	 * Returns default RequestConfig object, Default content type is application/json
	 * 
	 * @return RequestConfig
	 */
	public static RequestConfig getDefaultRequestConfig() {
		logger.debug("Inside getDefaultRequestConfig()");
		Header[] headers = {
			    new Header("Content-Type", "application/json")
			};
		
		RequestConfig reqConfig = new RequestConfig();
		reqConfig.setHeaders(headers);
		
		return reqConfig;
	}
	
	/**
	 * Converts input headers map to http request header array
	 * 
	 * @param headers
	 * @return org.apache.http.Header
	 */
	public static org.apache.http.Header[] getHeadersFromRequest(Header[] headers) {
		logger.debug("Inside getHeadersFromRequest()");
		if(headers == null) {
			return new org.apache.http.Header[0];
		}
		
		org.apache.http.Header[] headerArr = new org.apache.http.Header[headers.length];
		int index = 0;
		for (Header header: headers) {
			headerArr[index] = new BasicHeader(header.getName(), header.getValue());
			++index;
		}
		return headerArr;
	}
	
	/**
	 * Builds the final RequestConfig object by combining global and service level config
	 * 
	 * @param globalReqConfig
	 * @param reqConfig
	 * @return RequestConfig
	 */
	public static RequestConfig overrideGlobalRequestConfig(RequestConfig globalReqConfig, RequestConfig reqConfig) {
		logger.debug("Inside overrideGlobalRequestConfig()");
		// No global config available
		if(globalReqConfig == null) {
			return reqConfig;
		}
		
		if(reqConfig.getConnectTimeout() > -1) {
			globalReqConfig.setConnectTimeout(reqConfig.getConnectTimeout());
		}
		
		if(reqConfig.getSocketTimeout() > -1) {
			globalReqConfig.setSocketTimeout(reqConfig.getSocketTimeout());
		}

		if(reqConfig.getHeaders() != null) {
			globalReqConfig.setHeaders(reqConfig.getHeaders());
		}
		
		return globalReqConfig;
	}
	
	/**
	 * Converts HashMap to XML
	 * 
	 * @param map
	 * @param root
	 * @return String
	 */
	public static String covertMapToXML(Map<?, ?> map, String root) {
		logger.debug("Inside covertMapToXML()");
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<");
        sb.append(root);
        sb.append(">");
        for (Map.Entry<?, ?> e : map.entrySet()) {
            sb.append("<");
            sb.append(e.getKey());
            sb.append(">");

            sb.append(e.getValue());
            sb.append("</");
            sb.append(e.getKey());
            sb.append(">");
        }
        sb.append("</");
        sb.append(root);
        sb.append(">");
        return sb.toString();
    }
	
}
