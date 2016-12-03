package com.anitech.resting;

import java.util.Map;

/**
 * Resting library public API
 * 
 * @author Tapas
 *
 */
public class Resting {

	public static Resting getInstance() {
		return new Resting();
	}
	
	public void sayHello() {
		System.out.println("Hello!");
	}
	
	public void GET(String endpointUrl) {
		System.out.println("GET!");
	}
	
	public void POST(String endpointUrl, Map<Object, Object> inputParams) {
		System.out.println("POST!");
	}

	public void PUT(String endpointUrl, Map<Object, Object> inputParams) {
		System.out.println("PUT!");
	}

	public void DELETE(String endpointUrl) {
		System.out.println("DELETE!");
	}
	
}
