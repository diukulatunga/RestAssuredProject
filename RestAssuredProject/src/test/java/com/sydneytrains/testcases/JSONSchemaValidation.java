package com.sydneytrains.testcases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import org.testng.annotations.Test;

public class JSONSchemaValidation {
	
		@Test
		public void testJSONSchema() {
		
			baseURI = "https://reqres.in/api";

			given().
				get("/users?page=2").
			then().
				assertThat().
				body(matchesJsonSchemaInClasspath("users-schema.json")).
				statusCode(200).
				log().all();
				
			/*
				then().
				body("data[4].email", equalTo("george.edwards@reqres.in")).
				body("data.first_name", hasItems("George", "Byron")).
				log().all();
				
				*/
		}

}
