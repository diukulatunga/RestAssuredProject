package com.sydneytrains.testcases;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static io.restassured.RestAssured.*;//.baseURI;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.hamcrest.Matchers.*;

public class SoapApiTest {
	
	@Test
	public void SoapXMPRequest() throws Exception {
		
		// http://www.dneonline.com/calculator.asmx?wsdl
		baseURI = "http://www.dneonline.com";
		String xmlRquestPayLoadFileLocation = "./SoapRequest/Calculator_Add.xml";
		
		File xmlRquestPayLoadFile = new File(xmlRquestPayLoadFileLocation);
		
		if (xmlRquestPayLoadFile.exists()) {
			
			FileInputStream xmlRquestPayLoad = new FileInputStream(xmlRquestPayLoadFile);
			String requestBody = IOUtils.toString(xmlRquestPayLoad, "UTF-8");
		
			Response soapResponse = RestAssured.given().
				contentType("text/xml").
				accept(ContentType.XML).
				body(requestBody).
			when().
				post("/calculator.asmx");
			//then().
			//	statusCode(200).
			//	log().all();
			//and().
				//body("//*:AddResult.text()", equalTo("13"));
			
			System.out.println(soapResponse.asPrettyString());
			
			List<String> tagValues = this.getValueByTagNameFromResponse(soapResponse.asPrettyString(), "AddResult");
			
			System.out.println(tagValues.get(0));
			//.getEntity().getContent()
		
		} else
			System.out.println("No Payload File");
	
	
	}
	
	public static Document loadXMLString(String response) throws Exception
	{
	    DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(response));

	    return db.parse(is);
	}

	private List<String> getValueByTagNameFromResponse(String soapResponse, String tagName) throws Exception {
		List<String> tagValues = new ArrayList<String>();
		
		Document xmlDoc = loadXMLString(soapResponse);
		
		NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
		
		if (nodeList.getLength() > 1 ) { 	// Was (nodeList.getLength() != 1)
			
			//List<String> ids = new ArrayList<String>(nodeList.getLength());
			for(int i=0; i<nodeList.getLength(); i++) {
				Node x = nodeList.item(i);
				tagValues.add(x.getFirstChild().getNodeValue());             
				System.out.println(tagName + " : " + nodeList.item(i).getFirstChild().getNodeValue());	 // No need
			} 
	    	
		} else if (nodeList.getLength() == 1){
			tagValues.add(nodeList.item(0).getFirstChild().getNodeValue());
			System.out.println(tagName + " : " + tagValues.get(0));  // No need
		}
		return tagValues;
	}
	
	
}
