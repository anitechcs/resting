package com.anitech.resting.http.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.anitech.resting.exception.RestingException;
import com.anitech.resting.http.Header;



/**
 * HttpResponse Abstraction
 * 
 * @author Tapas
 *
 */
public class RestingResponse {
	
	private static final Logger logger = LogManager.getLogger(RestingResponse.class);

	private static HttpResponse httpResponse;
	
	public RestingResponse(HttpResponse response) {
		httpResponse = response;
	}
	
	public Header[] getAllHeaders() {
		org.apache.http.Header[] headers = httpResponse.getAllHeaders();
		return buildHeaders(headers);
	}
	
	public Header[] getHeaders(String name) {
		org.apache.http.Header[] headers = httpResponse.getHeaders(name);
		return buildHeaders(headers);
	}
	
	//TODO: Think about XML content type
	public Object getBody() throws RestingException {
		try {
			InputStream inputStream = httpResponse.getEntity().getContent();
			JSONParser jsonParser = new JSONParser();
			return jsonParser.parse(new InputStreamReader(inputStream, Charset.defaultCharset()));
		} catch (UnsupportedOperationException uoe) {
			throw new RestingException(uoe);
		} catch (IOException ioe) {
			throw new RestingException(ioe);
		} catch (ParseException pe) {
			throw new RestingException(pe);
		}
	}
	
	public int getStatusCode() {
		return httpResponse.getStatusLine().getStatusCode();
	}
	
	public String getHttpStatus() {
		return httpResponse.getStatusLine().toString();
	}
	
	public long getContentLength() {
		return httpResponse.getEntity().getContentLength();
	}
	
	public Header getContentType() {
		org.apache.http.Header header = httpResponse.getEntity().getContentType();
		return new Header(header.getName(), header.getValue());
	}
	
	public Header getContentEncoding() {
		org.apache.http.Header header = httpResponse.getEntity().getContentEncoding();
		return new Header(header.getName(), header.getValue());
	}
	
	/**
	 * private method to convert from Apache header to Resting header
	 * 
	 * @param headers
	 * @return Header[]
	 */
	private Header[] buildHeaders(org.apache.http.Header[] headers) {
		logger.debug("Inside buildHeaders()");
		Header[] rHeaders = new Header[headers.length];
		int index = 0;
		for(org.apache.http.Header header: headers) {
			Header rHead = new Header(header.getName(), header.getValue());
			rHeaders[index] = rHead;
			++index;
		}
		return rHeaders;
	}
	
}
