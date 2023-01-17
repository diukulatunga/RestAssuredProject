package com.sydneytrains.testcases;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LocalRestApi {
	
	@BeforeTest
	public void setUp() {
		baseURI = "http://localhost:3000/";
	}
	
	@Test
	public void getUsers() {
		/*
		given().
			get("/users/2").
		then().
			log().all();
		*/
		
		Response getUsersResponse = given().get("/users");
		JsonPath getUsersResponseJsonPath = new JsonPath(getUsersResponse.asString());
		
		if (getUsersResponseJsonPath.getJsonObject("id") != null){
			System.out.println("Response Size: " + getUsersResponseJsonPath.getInt("data.size()"));
			if (getUsersResponseJsonPath.getInt("data.size()")>1) {
				ArrayList<String> userLastNames = new ArrayList<String>();
				userLastNames = getUsersResponseJsonPath.get("lastName");
				System.out.println("User Last Name(s): " + userLastNames);
			} else if (getUsersResponseJsonPath.getInt("data.size()") == 1) {
				String userLastName = getUsersResponseJsonPath.get("[0].lastName");
				System.out.println("User Last Name: " + userLastName);
			}
		} 
		else
			System.out.println("No User Data");
		
		
		/*
		Response getUsersResponse = given().get("/users");
		JsonPath getUsersResponseJsonPath = getUsersResponse.jsonPath();
		
		List<HashMap<String, Object>> jsonObjects = getUsersResponseJsonPath.getList("data");

		if (jsonObjects != null) {
			System.out.println("No of Objects: " + jsonObjects.size());
			if (jsonObjects.size()>1) {
				ArrayList<String> userLastNames = new ArrayList<String>();
				userLastNames = getUsersResponseJsonPath.get("lastName");
				System.out.println("User Last Name(s): " + userLastNames);
			} else if (jsonObjects.size() == 1) {
				String userLastName = getUsersResponseJsonPath.get("[0].lastName");
				System.out.println("User Last Name: " + userLastName);
			}	
		} else
			System.out.println("No User Data");
		*/
		
	}
	
	@Test
	public void setUsers() {
		JSONObject newUser = new JSONObject();
		
		newUser.put("id", 3);
		newUser.put("title", "Miss");
		newUser.put("firstName", "Onadhi");
		newUser.put("lastName", "Kulatunga");
		newUser.put("subjectId", 2);
				
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(newUser.toJSONString()).
		when().
			post("/users").
		then().
			statusCode(201).
			log().all();
			
	}

}
