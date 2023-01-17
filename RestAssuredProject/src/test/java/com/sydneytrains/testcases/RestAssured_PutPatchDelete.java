package com.sydneytrains.testcases;

import static io.restassured.RestAssured.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;


public class RestAssured_PutPatchDelete {
	
	@Test
	public void testPut() {
		baseURI = "https://reqres.in/api";
		
		JSONObject userRequest = new JSONObject();
		
		userRequest.put("name", "IndikaK");
		userRequest.put("job", "QA/Test Lead");
		//https://reqres.in/api/users/2
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(userRequest.toJSONString()).
	when().
		put("/users/117").
	then().
		statusCode(200).
		log().all();
		
	}
	
	
	public void testPatch() {
		baseURI = "https://reqres.in/api";
		JSONObject userRequest = new JSONObject();
		
		userRequest.put("name", "IndikaK");
		userRequest.put("job", "QA/Test Lead");
		//https://reqres.in/api/users/2
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(userRequest.toJSONString()).
	when().
		patch("/users/117").
	then().
		statusCode(200).
		log().all();
		
	}
	
	@Test
	public void testDelete() {
		
		baseURI = "https://reqres.in/api";
		given().
		when().
			delete("/users/117").
		then().
			statusCode(204).
			log().all();
		
	}

}
