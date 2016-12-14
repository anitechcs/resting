# resting [![Build Status](https://travis-ci.org/anitechcs/resting.svg?branch=master)](https://travis-ci.org/anitechcs/resting)

> Easy REST API testing library

## What is Resting??
> REST + TESTING = RESTING

`Resting` is a simple and light weight library for accessing REST API with little fuss. You can use `Resting` as a REST client for consuming REST services in your application or You can use it for end-to-end testing of your REST services. The main features are:

   * Simple and Easy to use
   * Fluent API
   * Secured API call support(Basic Auth + Token)
   * Request builder and JSON File payload support
   * State-less and State-full(Cookie) Service invocation 
   * JSON and XML Service support
   * BDD Style Fluent API


## Installation

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
	

## Getting Started

Once you have resting in your classpath (Refer #Installation section), you are ready to take it for a spin. You need to get a handle to `Resting` instance first like below:

	
	Resting resting = Resting.getInstance();
	
Also you can configure few thing with provided fluent API like below:
	
	
	Resting	resting = Resting
					.getInstance()
					.enableMetrics()
					.baseURI("https://jsonplaceholder.typicode.com");
	

You can call any REST service through Resting like below:

### Request Config (Optional)
```
RestingRequestConfig config = new RestingRequestConfig();
config.setConnectTimeout(5000);
config.setSocketTimeout(5000);
config.setHeaders(headers);
```
### GET
```
// GET Without Extra Config
RestingResponse res = resting.GET("/posts/1");
JSONObject json = (JSONObject) res.getBody();
	
// GET With Extra Config
RestingResponse res = resting.GET("/posts/1", config);
JSONObject json = (JSONObject) res.getBody();
```
### POST
```	
// POST Without Extra Config
RestingResponse res = resting.POST("/posts", inputs);
JSONObject json = (JSONObject) res.getBody();
	
// POST With Extra Config	
RestingResponse res = resting.POST("/posts", inputs, config);
JSONObject json = (JSONObject) res.getBody();
```
### PUT
```
// PUT Without Extra Config
RestingResponse res = resting.PUT("/posts/1", inputs);
JSONObject json = (JSONObject) res.getBody();
	
// PUT With Extra Config	
RestingResponse res = resting.PUT("/posts/1", inputs, config);
JSONObject json = (JSONObject) res.getBody();
```
### DELETE
```
// DELETE Without Extra Config
RestingResponse res = resting.DELETE("/posts/1");
JSONObject json = (JSONObject) res.getBody();
	
// DELETE With Extra Config
RestingResponse res = resting.DELETE("/posts/1", config);
JSONObject json = (JSONObject) res.getBody();
```

### Supported Payload(Input Data) Formats
For `POST` and `PUT` services you need to pass payload to the service. You can see syntax wise we are accepting `Object` type which means you can pass your data in any supported format below and `Resting` will take care of the rest.

	- Map<?, ?>
	- JSON String (e.g. {"name": "Tapas", "country": "India"})
	- JSONObject
	- StringBuffer
	- StringBuilder
	- File (e.g. new File("/data/input.json"))
	- InputStream
	- FileInputStream	

If you still need more input format support, Please let us know!


	
	