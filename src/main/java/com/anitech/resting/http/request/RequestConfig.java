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
package com.anitech.resting.http.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.anitech.resting.http.Header;

/**
 * Request Configuration class for Resting library
 * 
 * @author Tapas
 *
 */
public class RequestConfig {

	private List<Header> headers = new ArrayList<Header>();
	
	private int socketTimeout = -1;
	
	private int connectTimeout = -1;
	
	
	public int getSocketTimeout() {
		return socketTimeout;
	}

	/**
     * Defines the socket timeout in milliseconds,
     * which is the timeout for waiting for data  or, put differently,
     * a maximum period inactivity between two consecutive data packets).
     * <p>
     * A timeout value of zero is interpreted as an infinite timeout.
     * A negative value is interpreted as undefined (system default).
     * </p>
     * <p>
     * Default: {@code -1}
     * </p>
     */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
     * Determines the timeout in milliseconds until a connection is established.
     * A timeout value of zero is interpreted as an infinite timeout.
     * <p>
     * A timeout value of zero is interpreted as an infinite timeout.
     * A negative value is interpreted as undefined (system default).
     * </p>
     * <p>
     * Default: {@code -1}
     * </p>
     */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Header[] getHeaders() {
		Header[] headersArr = new Header[headers.size()];
		return headers.toArray(headersArr);
	}

	public void setHeaders(Header[] headers) {
		this.headers = Arrays.asList(headers);
	}
	
	/**
	 * Returns respective Header
	 * 
	 * @param headerName
	 * @return Header
	 */
	public Header getHeader(String headerName) {
		for(Header header : headers ) {
		    if(headerName.equals(header.getName())) {
		    	return header;
		    }
		}
		return null;
	}

	/**
	 * Add the header
	 * 
	 * @param header
	 */
	public void addHeader(Header header) {
		this.headers.add(header);
	}
	
	/**
	 * Adds to header array
	 * 
	 * @param name
	 * @param value
	 */
	public void addHeader(String name, String value) {
		this.headers.add(new Header(name, value));
	}

}
