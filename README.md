# resting [![Build Status](https://travis-ci.org/anitechcs/resting.svg?branch=master)](https://travis-ci.org/anitechcs/resting)

> Easy REST API testing library

## What is Resting??
> REST + TESTING = RESTING

Resting is a simple and light weight library for testing REST API. The main features are:

   * Simple and Easy to use
   * Fluent API
   * Secured API call support(Basic Auth + Token)
   * Request builder and JSON File payload support
   * Stateless and Statefull(Cookie) Service invocation 
   * JSON and XML Service support
   * BDD Style Fluent API


## Installation:

`Resting` is a java library, So you can download the `resting-1.x.x.jar` and directly consume in your application however we are recommending to use a build system like Maven or Gradle. Please refer to below sections based on your build system: 

* Maven

	You can add below dependency to your pom.xml
	
	```
	<dependency>
	    <groupId>com.anitech</groupId>
	    <artifactId>resting</artifactId>
	    <version>0.0.1-SNAPSHORT</version>
	</dependency>
	```
	

* Gradle
	
	```
	Coming Soon
	```
	

* Custom(Not recommended) 

	Add `resting-0.0.1-SNAPSHORT.jar` to your classpath along with following dependent jars:

	- JUnit
	- Log4j2
	- Apache HTTP Client
	- json-simple
	

## Getting Started:

Once you have resting in your classpath (Refer #Installation section), you are ready to take it for a spin. You need to get a handle to `Resting` instance first like below:

	
	Resting resting = Resting.getInstance();
	
Also you can configure few thing with provided fluent API like below:
	
	
	Resting	resting = Resting
					.getInstance()
					.enableMetrics()
					.baseURI("https://jsonplaceholder.typicode.com");
	

You can call any REST service through Resting like below:

```
RestingRequestConfig config = new RestingRequestConfig();
config.setConnectTimeout(5000);
config.setSocketTimeout(5000);
config.setHeaders(headers);
```
### GET
```
// GET Without Extra Config
HttpResponse res = resting.GET("/posts/1");
	
// GET With Extra Config
HttpResponse res = resting.GET("/posts/1", config);
```
### POST
```	
// POST Without Extra Config
HttpResponse res = resting.POST("/posts", inputs);
	
// POST With Extra Config	
HttpResponse res = resting.POST("/posts", inputs, config);
```
### PUT
```
// PUT Without Extra Config
HttpResponse res = resting.PUT("/posts/1", inputs);
	
// PUT With Extra Config	
HttpResponse res = resting.PUT("/posts/1", inputs, config);
```
### DELETE
```
// DELETE Without Extra Config
HttpResponse res = resting.DELETE("/posts/1");
	
// DELETE With Extra Config
HttpResponse res = resting.DELETE("/posts/1", config);
```
		
