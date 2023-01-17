package com.sydneytrains.testcases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.*;

import io.restassured.http.ContentType;

public class RestAssured_GetPost {
	
	@Test	
	public void testGet() {
		
		baseURI = "https://reqres.in/api";
		//Response restResponse = RestAssured.get("/users?page=2");
		given().
			get("/users?page=2").
		then().
			body("data[4].email", equalTo("george.edwards@reqres.in")).
			body("data.first_name", hasItems("George", "Byron")).
			log().all();
	}
	
	@Test
	public void testPost() {
		baseURI = "https://reqres.in/api";
		
//		Map<String, Object> userMap = new HashMap<String, Object>();
//		userMap.put("name", "IndikaK");
//		userMap.put("job", "QA Lead");
//		System.out.println(userMap);
//		JSONObject userRequest = new JSONObject(userMap);
		
		JSONObject userRequest = new JSONObject();
		
		userRequest.put("name", "IndikaK");
		userRequest.put("job", "QA Lead");
		
		System.out.println(userRequest.toJSONString());
		
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(userRequest.toJSONString()).
		when().
			post("/users").
		then().
			statusCode(201).
			log().all();
		
		// https://reqres.in/api/users	

	}
	

}
