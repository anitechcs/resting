# resting [![Build Status](https://travis-ci.org/anitechcs/resting.svg?branch=master)](https://travis-ci.org/anitechcs/resting)

> Easy REST API testing library

## What is Resting??
> REST + TESTING = RESTING

`Resting` is a simple and light weight library for accessing REST API with little fuss. You can use `Resting` as a REST client for consuming REST services in your application or You can use it for end-to-end testing of your REST services. The main features are:

   * Simple and Easy to use
   * Fluent API 
   * Convention over Configuration
   * Secured API call support(Basic Auth + Token)
   * Request builder and JSON File payload support
   * State-less and State-full(Cookie) Service invocation 
   * REST JSON and XML Service support
   * BDD Style Fluent API
   * Less code, Less Bug


## Installation

`Resting` is a java library, So you can download the `resting-1.x.x.jar` and directly consume in your application however we are recommending to use a build system like Maven or Gradle. Please refer to below sections based on your build system: 

* Maven

	You can add below dependency to your pom.xml
	
	```
	<dependency>
	    <groupId>com.anitechcs</groupId>
	    <artifactId>resting</artifactId>
	    <version>0.0.1-SNAPSHOT</version>
	</dependency>
	```
	

* Gradle
	
	```
	Coming Soon
	```
	

* Custom(Not recommended) 

	Add `resting-0.0.1-SNAPSHOT.jar` to your classpath along with following dependent jars:

	- JUnit
	- Log4J2
	- Apache HTTP Client
	- Json-Simple
	

## Getting Started

Once you have resting in your classpath (Refer [#Installation section](https://github.com/anitechcs/resting#installation)), you are ready to take it for a spin. You need to get a handle to `Resting` instance first:

	
	Resting resting = Resting.getInstance();
	
Also you can configure few things globally with provided fluent API like below:
	
	
	Resting	resting = Resting
					.getInstance()
					.enableMetrics()  //enable metrics
					.enableProcessingHooks()  //enables pre and post processing hooks
					.globalRequestConfig(globalConfig)  //global request configuration
					.baseURI("https://jsonplaceholder.typicode.com");  //set base URL
	

Enjoy calling your REST services through `Resting` with less code and easier API:

### Header
```
Header[] headers = {
    new Header("Content-Type", "application/json"),
    new Header("Accept", "application/xml,text/plain,application/json"),
    new Header("Connection", "keep-alive")
};
```
### RequestConfig (Optional)
```
RequestConfig config = new RequestConfig();
config.setConnectTimeout(5000);
config.setSocketTimeout(5000);

config.setHeaders(headers);
config.addHeader(new Header("Content-Type", "application/json"));
config.addHeader("Content-Type", "application/json");
```
### GET
```
// GET Without Extra Config
RestingResponse res = resting.GET("/posts/1");
JSONObject json = (JSONObject) res.getBody();

// GET Without Extra Config - Multiple records
RestingResponse res = resting.GET("/posts");
JSONArray jsonArr = (JSONArray) res.getBody();
	
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

### Supported Payload (Input Data) Formats
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

### Note
> Make sure you are importing `RequestConfig` and `Header` from `Resting` library instead of Apache HTTP library

### Got a feedback?
We really appriciate your feedback and looking forward to make `Resting` better and better. Please raise a defect or pull request if you want to discuss anything with us regarding `Resting`

	
	
