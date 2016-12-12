package com.anitech.resting.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anitech.resting.config.RestingRequestConfig;

/**
 * Resting Utility class
 * 
 * @author Tapas
 *
 */
public class RestingUtil {
	
	private static final Logger logger = LogManager.getLogger(RestingUtil.class);

	/**
	 * Returns default RestingRequestConfig object, Default content type is application/json
	 * 
	 * @return RestingRequestConfig
	 */
	public static RestingRequestConfig getDefaultRequestConfig() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		RestingRequestConfig reqConfig = new RestingRequestConfig();
		reqConfig.setHeaders(headers);
		
		return reqConfig;
	}
	
	/**
	 * Converts input headers map to http request header array
	 * 
	 * @param headers
	 * @return
	 */
	public static Header[] getHeadersFromRequest(Map<String, String> headers) {
		Header[] headerArr = new Header[headers.size()];
		int index = 0;
		for (String key: headers.keySet()) {
			String value = headers.get(key);
			logger.debug(index+" - Header Key: " + key + ": Value: " + value);
			headerArr[index] = new BasicHeader(key, value);
			++index;
		}
		return headerArr;
	}
	
	/**
	 * Converts HashMap to XML
	 * 
	 * @param map
	 * @param root
	 * @return
	 */
	public static String covertMapToXML(Map<?, ?> map, String root) {
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
