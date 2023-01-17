package com.sydneytrains.testcases;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.matcher.RestAssuredMatchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import static org.hamcrest.Matchers.*;

public class XMLSchemaValidation {
	
		@Test
		public void XMLValidation() throws IOException {
		
			baseURI = "http://www.dneonline.com";
			String xmlRquestPayLoadFileLocation = "./SoapRequest/Calculator_Add.xml";
			
			File xmlRquestPayLoadFile = new File(xmlRquestPayLoadFileLocation);
			
			if (xmlRquestPayLoadFile.exists()) {
				
				FileInputStream xmlRquestPayLoad = new FileInputStream(xmlRquestPayLoadFile);
				//xmlRquestPayLoad.reset();
				String requestBody = IOUtils.toString(xmlRquestPayLoad, "UTF-8");
			
				RestAssured.given().
					contentType("text/xml").
					accept(ContentType.XML).
					body(requestBody).
				when().
					post("/calculator.asmx").
				then().
					statusCode(200).
					log().all().
				and().
					body("//*:AddResult.text()", equalTo("13")).
				and().
					assertThat().
					//body(RestAssuredMatchers.matchesXsdInClasspath("calculator_add.xsd"));
					body(matchesXsdInClasspath("calculator_add.xsd"));
						
			}
		}
}
