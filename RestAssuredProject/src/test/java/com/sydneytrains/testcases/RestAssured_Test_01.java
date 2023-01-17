package com.sydneytrains.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class RestAssured_Test_01 {
	
	
	public void showGreetings() {
		System.out.println("Hello Indika");
	}
	
	@Test
	public void getMethod() {
		
		Response restResponse = RestAssured.get("https://reqres.in/api/users?page=2");
		
		System.out.println("Response: " + restResponse);
		System.out.println("Status Code: " + restResponse.getStatusCode());
		System.out.println("Response Time: " + restResponse.getTime());

		System.out.println("Response Header: " + restResponse.getHeader("content-type"));
		
		System.out.println("Response in String: " + restResponse.asPrettyString());
		
		Assert.assertEquals(restResponse.getStatusCode(), 200);
	}

	@Test
	public void getMethod2() {
		baseURI = "https://reqres.in/api";
		
		given().
			get("/users?page=2").
		then().
			body("data[1].email", equalTo("lindsay.ferguson@reqres.in")).
			log().all();
		

	}

	
}
