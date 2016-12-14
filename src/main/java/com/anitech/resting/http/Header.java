package com.anitech.resting.http;

import java.io.Serializable;

/**
 * Resting Http header
 * 
 * @author Tapas
 *
 */
public class Header implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String name;
    private final String value;
    
    /**
     * Constructor with name and value
     *
     * @param name the header name
     * @param value the header value
     */
    public Header(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Value: " + value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
}
